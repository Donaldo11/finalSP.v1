/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsp.v1;

import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import panamahitek.Arduino.PanamaHitek_Arduino;

/**
 *
 * @author Donaldo
 */
public class MonitorMain extends javax.swing.JFrame {

    //Graficas----------------------------------------------------------------------------------------------------------------------------
    final XYSeries serie = new XYSeries("Luminosidad");
    final XYSeries tempe = new XYSeries("Temperatura");
    final TimeSeriesCollection dataset1 = new TimeSeriesCollection();
    final TimeSeriesCollection dataset2 = new TimeSeriesCollection();
    final XYSeriesCollection Coleccion = new XYSeriesCollection();
    final XYSeriesCollection Temperatura = new XYSeriesCollection();
    JFreeChart GraficaLuminosidad;
    JFreeChart GraficaTemperatura;
    final TimeSeries s1 = new TimeSeries("Temperatura", Second.class);
    final TimeSeries s2 = new TimeSeries("Luminosidad", Second.class);

    //------------------------------------------------------------------------------------------------------------------------------------
    //MYSQL-------------------------------------------------------------------------------------------------------------------------------
    Conectar cc = new Conectar();
    Connection cn = cc.conexion();
    PreparedStatement st, sema, temp, foto, venti;
    //------------------------------------------------------------------------------------------------------------------------------------
    float g, g1;
    int aux;
    String sem2, sem1;

    public void registrarSem() {
        try {
            sema = cn.prepareStatement("Insert into semaforos (color1,color2,mes,dia,hora,min,seg) values(?,?,?,?,?,?,?)");
            sema.setString(1, sem1);
            sema.setString(2, sem1);
            sema.setInt(3, 12);
            sema.setInt(4, dia);
            sema.setInt(5, hora);
            sema.setInt(6, minutos);
            sema.setInt(7, segundos);
            System.out.println("");
            sema.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarTemp() {
        try {

            temp = cn.prepareStatement("Insert into temp(temp,mes,dia,hora,min,seg) values(?,?,?,?,?,?)");
            temp.setFloat(1, g1);
            temp.setInt(2, 12);
            temp.setInt(3, dia);
            temp.setInt(4, hora);
            temp.setInt(5, minutos);
            temp.setInt(6, segundos);
            temp.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void registrarFoto() {
        try {
            foto = cn.prepareStatement("Insert into foto (foto,mes,dia,hora,min,seg) values(?,?,?,?,?,?)");
            foto.setFloat(1, g);
            foto.setInt(2, 12);
            foto.setInt(3, dia);
            foto.setInt(4, hora);
            foto.setInt(5, minutos);
            foto.setInt(6, segundos);
            foto.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();
    SerialPortEventListener evento = new SerialPortEventListener() {
        @Override

        public void serialEvent(SerialPortEvent spe) {
            try {
                arduino.sendByte(valorInte);
                jProgressBar1.setValue(valorInte);
            } catch (Exception ex) {
                Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (arduino.isMessageAvailable() == true) {

                String m = arduino.printMessage();

                // System.out.println(m);
                if (m.contains("oto")) {
                    g = Integer.parseInt(m.substring(4));

                    //  System.out.println("ENTROOOOOOOOOOO-------");
                } else if (m.contains("rado")) {
                    g1 = Integer.parseInt(m.substring(6, 8));

                } else if (m.equals("Verde")) {
                    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/green.png")));
                    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/green.png")));
                    sem1 = m;

                } else if (m.equals("Amarillo")) {
                    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/yellow.png")));
                    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/yellow.png")));
                    sem1 = m;

                } else if (m.equals("Rojo")) {
                    jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/red.png")));
                    jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/red.png")));
                    sem1 = m;

                }
                /*  registrarSem();
                registrarFoto();
                registrarTemp();
                 */
            }

            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        }

    };

    /**
     * Creates new form MonitorMain
     */
    public MonitorMain() {
        initComponents();

        try {
            arduino.arduinoRXTX("COM3", 9600, evento);

        } catch (Exception ex) {
            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        //-grafica----------------------------------------------

        dataset1.addSeries(s2);
        dataset2.addSeries(s1);

        GraficaLuminosidad = ChartFactory.createTimeSeriesChart("Luz", "Tiempo", "Luminosidad", dataset1, true, true, false);
        GraficaTemperatura = ChartFactory.createTimeSeriesChart("Temperatura", "Tiempo", "Temperatura", dataset2, true, true, false);
        ChartPanel Panel = new ChartPanel(GraficaLuminosidad);
        ChartPanel Panel2 = new ChartPanel(GraficaTemperatura);
        final XYPlot plot = GraficaLuminosidad.getXYPlot();
        final DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("hh:mm:ssa"));
        //--
        final XYPlot plot2 = GraficaTemperatura.getXYPlot();
        final DateAxis axis2 = (DateAxis) plot2.getDomainAxis();
        axis2.setDateFormatOverride(new SimpleDateFormat("hh:mm:ssa"));

        Panel2.setSize(370, 178);
        Panel.setVisible(true);
        Panel.setSize(370, 178);
        jPanel3.add(Panel);
        jPanel4.add(Panel2);

    }
//.------------------------HILO
    Thread thread = new Thread(new MyRunnable());
    Thread thread2 = new Thread(new MyRunnable2());
    Thread thread3 = new Thread(new encenderA());
    Thread thread4 = new Thread(new IntensidadV1());
    int x = 1;
    String sele = "Select estado from arduino";
    int hora, minutos, segundos;
    ResultSet rs;

    String mes, annio;
    int dia;
    String horaActual;
    String fechaActual;
    String shora, sminutos, ssegundos;
    String horafec;

    int sshora, ssminutos, sssegundos;

    public class MyRunnable implements Runnable {

        @Override
        public void run() {

            while (x == 1) {

                Calendar c1 = Calendar.getInstance();
                dia = (c1.get(Calendar.DATE));
                hora = c1.get(Calendar.HOUR_OF_DAY);
                minutos = c1.get(Calendar.MINUTE);
                segundos = c1.get(Calendar.SECOND);
                if (aux < segundos) {
                    s1.addOrUpdate(new Second(segundos, minutos, hora, dia, 12, 2016), g1);
                    s2.addOrUpdate(new Second(segundos, minutos, hora, dia, 12, 2016), g);
                    registrarSem();
                    registrarTemp();
                    registrarFoto();
                   System.out.println(minutos+"-"+segundos);

                    aux++;

                } else if (segundos == 0 && aux == 59) {
                    s1.addOrUpdate(new Second(segundos, minutos, hora, dia, 12, 2016), g1);
                    s2.addOrUpdate(new Second(segundos, minutos, hora, dia, 12, 2016), g);
                    registrarSem();
                    registrarTemp();
                    registrarFoto();
                    aux = 0;

                }

            }
        }
    }
    String previo = "OFF";
    String actual;

    //----------I        N         T        E         N     S        I        D         A         D 
    int valorInte;

    public class IntensidadV1 implements Runnable {

        @Override
        public void run() {

            while (x == 1) {

                Statement stinte;
                try {
                    stinte = cn.createStatement();
                    ResultSet rinte = stinte.executeQuery("Select reg from inten where id IN(select max(id) from inten) ");
                    rinte.next();
                    valorInte = Integer.parseInt(rinte.getString(1));

                    if (esta.equals("ON") && estaP.equals("ON")) {

                    }
                } catch (SQLException | NumberFormatException ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public class MyRunnable2 implements Runnable {

        @Override
        public void run() {

            while (x == 1) {

                try {
                    Statement st2 = cn.createStatement();
                    rs = st2.executeQuery("Select val from ventilador where id IN(select max(id) from ventilador);");
                    while (rs.next()) {
                        actual = rs.getString(1);
                        /*      if (rs.getString(1).equals("ON")) {
                                arduino.sendData("1");
                                  
                                System.out.println("MANDOOOOOOOOOO");

                            } else if (rs.getString(1).equals("OFF")) {
                                arduino.sendData("2");
                                System.out.println("MANDOOOOOOOOOO222222");

                            }*/
                    }
                    if (actual.equals("OFF") & !previo.equals("OFF")) {

                        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/venti.jpg")));
                        previo = "OFF";
                    } else if (actual.equals("ON") && !previo.equals("ON")) {
                        arduino.sendData("1");

                        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/venti.gif")));
                        previo = "ON";
                        //  System.out.println("EEEEEEENTRARARARARARARARARA" + "---" + previo + "--" + actual);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                }

                //try {
                //  registrarSem();
                // registrarFoto();
                //registrarTemp();
                //thread2.sleep(1000);
                // } catch (InterruptedException ex) {
                //   Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                //}
            }
        }
    }
    String esta, estaP = "OFF";

    public class encenderA implements Runnable {

        @Override
        public void run() {

            while (x == 1) {

                try {
                    Statement st = cn.createStatement();
                    ResultSet rs1 = st.executeQuery(sele);
                    while (rs1.next()) {
                        esta = rs1.getString(1);
                        break;
                    }

                    if (esta.equals("OFF") & !estaP.equals("OFF")) {
                        arduino.sendData("0");
                        estaP = "OFF";
                        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/off.png")));

                    } else if (esta.equals("ON") && !estaP.equals("ON")) {
                        arduino.sendData("0");
                        estaP = "ON";
                        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/on.png")));
                    }
                    //  System.out.println("ESTA:" + esta + "--estap:" + estaP);

                    ///......--------
                } catch (SQLException ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Exception ex) {
                    Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
/////---------------------CLLLLLLLLLLOOOOOOOOOOOOOOOOOSSSSSSSSSSSSSSSSSSSSEEEEEEEEEEEEEE

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoSEGOB_encabezado.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo_CENAPRED.png"))); // NOI18N

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/45dc72c53fab5a783389b1a4fd6078d1.png"))); // NOI18N

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Semaforo1"));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sema.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jLabel5)
                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(19, 19, 19))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Semaforo Ciudad "));

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/sema.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel6)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel6)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/off.png"))); // NOI18N
        jLabel3.setText("Estado");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Temperatura"));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 360, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 157, Short.MAX_VALUE)
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fotoresistencia"));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 145, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ventilador"));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/venti.jpg"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Intensidad"));

        jProgressBar1.setForeground(new java.awt.Color(255, 0, 0));
        jProgressBar1.setMaximum(255);
        jProgressBar1.setMinimum(2);
        jProgressBar1.setOrientation(1);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(34, 34, 34)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(30, 30, 30)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(jLabel3)))
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
        this.getContentPane().setBackground(Color.white);
        try {
            aux = segundos;
            Statement ini = cn.createStatement();
            ResultSet iniR = ini.executeQuery("Select estado from arduino");
            while (iniR.next()) {
                estaP = iniR.getString(1);
            }

            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(MonitorMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MonitorMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MonitorMain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables
}
