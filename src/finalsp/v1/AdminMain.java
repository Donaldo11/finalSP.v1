/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package finalsp.v1;

import jxl.write.Label;
import jxl.write.Number;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
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

/**
 *
 * @author Donaldo
 */
public class AdminMain extends javax.swing.JFrame {

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
    Conectar cc = new Conectar();
    Connection cn = cc.conexion();
    Thread thread = new Thread(new AdminMain.MyRunnable());
    Thread thread2 = new Thread(new AdminMain.DatosFoto());
    Thread thread3 = new Thread(new AdminMain.DatosTemp());
    Thread thread4 = new Thread(new AdminMain.DatosSema());
    int x = 1;
    int g = 0;
    String modo = "OFF";

    String mes, annio;
    String horaActual;
    String fechaActual;
    String shora, sminutos, ssegundos;
    String horafec;
    int hora, dia, minutos, segundos;
    int sshora, ssminutos, sssegundos;

    public class MyRunnable implements Runnable {

        @Override
        public void run() {

            while (x == 1) {

                Calendar c1 = Calendar.getInstance();
                dia = c1.get(Calendar.DATE);
                hora = c1.get(Calendar.HOUR_OF_DAY);
                minutos = c1.get(Calendar.MINUTE);
                segundos = c1.get(Calendar.SECOND);

            }
        }
    }

    ///-FOTO
    public class DatosFoto implements Runnable {

        @Override
        public void run() {

            while (x == 1) {
                try {
                    Statement st = cn.createStatement();
                    ResultSet rs = st.executeQuery("Select foto from foto where id IN(select max(id) from foto)");
                    rs.next();
                    if (modo.equals("ON")) {
                        jProgressBar2.setValue(Integer.parseInt(rs.getString(1)));
                        s2.addOrUpdate(new Second(segundos, minutos, hora, dia, 11, 2016), Integer.parseInt(rs.getString(1)));
                    } else {
                        jProgressBar2.setValue(0);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
//---temp

    public class DatosTemp implements Runnable {

        @Override
        public void run() {

            while (x == 1) {
                try {
                    Statement stT = cn.createStatement();
                    ResultSet rsT = stT.executeQuery("Select temp from temp where id IN(select max(id) from temp)");
                    rsT.next();

                    if (modo.equals("ON")) {
                        jProgressBar1.setValue(Integer.parseInt(rsT.getString(1)));
                        s1.addOrUpdate(new Second(segundos, minutos, hora, dia, 11, 2016), Integer.parseInt(rsT.getString(1)));

                    } else {
                        jProgressBar1.setValue(0);
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    public class DatosSema implements Runnable {

        @Override
        public void run() {

            while (x == 1) {
                try {
                    Statement stS = cn.createStatement();
                    ResultSet rsS = stS.executeQuery("Select color1,color2 from semaforos where id IN(select max(id) from semaforos)");
                    rsS.next();
                    if (modo.equals("ON")) {
                        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + rsS.getString(1) + ".png")));
                        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/" + rsS.getString(2) + ".png")));
                    } else {
                        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sema.png")));
                        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sema.png")));
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }

    /**
     * Creates new form AdminMain
     */
    public AdminMain() {
        initComponents();
        thread.start();
        thread2.start();
        thread3.start();
        thread4.start();
        this.getContentPane().setBackground(Color.white);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jPanel3 = new javax.swing.JPanel();
        jSlider1 = new javax.swing.JSlider();
        jPanel5 = new javax.swing.JPanel();
        jProgressBar2 = new javax.swing.JProgressBar();
        jPanel7 = new javax.swing.JPanel();
        jMonthChooser1 = new com.toedter.calendar.JMonthChooser();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jDateChooser3 = new com.toedter.calendar.JDateChooser();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jRadioButton3 = new javax.swing.JRadioButton();
        jYearChooser1 = new com.toedter.calendar.JYearChooser();
        jButton2 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jPanel8 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/45dc72c53fab5a783389b1a4fd6078d1.png"))); // NOI18N

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logo_CENAPRED.png"))); // NOI18N

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/logoSEGOB_encabezado.png"))); // NOI18N

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/on.png"))); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

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

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Semaforo2"));

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

        jButton1.setText("Graficas");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Temperatura"));

        jProgressBar1.setMaximum(50);
        jProgressBar1.setOrientation(1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(19, Short.MAX_VALUE)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Intensidad"));

        jSlider1.setMajorTickSpacing(49);
        jSlider1.setMaximum(255);
        jSlider1.setMinimum(2);
        jSlider1.setPaintTicks(true);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21))
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Fotoresistencia"));

        jProgressBar2.setMaximum(1025);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Exportar Datos"));

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Mes");

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Lapso");

        jDateChooser3.setMinSelectableDate(new java.util.Date(1479970915000L));
        jDateChooser3.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jDateChooser3AncestorAdded(evt);
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jDateChooser3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jDateChooser3PropertyChange(evt);
            }
        });

        buttonGroup1.add(jRadioButton3);
        jRadioButton3.setText("Año");

        jYearChooser1.setEndYear(2016);
        jYearChooser1.setMinimum(2016);

        jButton2.setText("Exportar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Temperatura", "Fotoresistencia", "Intesidad", "Semaforo", "Ventilador" }));

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jRadioButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jRadioButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(33, 33, 33)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jRadioButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)
                        .addGap(24, 24, 24))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jMonthChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton2)
                    .addComponent(jDateChooser3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton3)
                    .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton2)
                        .addComponent(jYearChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Ventilador"));

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventip.jpg"))); // NOI18N

        jButton3.setText("ON");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                        .addComponent(jButton3)
                        .addGap(30, 30, 30))))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addContainerGap())
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
            .addGroup(layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addComponent(jLabel3)
                .addGap(194, 194, 194)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(270, 270, 270)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(120, 120, 120))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 989, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(80, 80, 80)
                                .addComponent(jButton1)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
        String sql = "update arduino set estado=?";
        PreparedStatement statement;
        try {
            statement = cn.prepareStatement(sql);
            if (modo.equals("OFF")) {

                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/off.png")));
                statement.setString(1, "ON");
                modo = "ON";
            } else if (modo.equals("ON")) {

                modo = "OFF";
                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/on.png")));
                statement.setString(1, "OFF");
            }

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControlRemoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(modo);
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked


    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged

        //----
        try {
            PreparedStatement inte = cn.prepareStatement("Insert into inten (reg,mes,dia,hora,min,seg) values(?,?,?,?,?,?)");

            inte.setInt(1, jSlider1.getValue());
            inte.setInt(2, 11);
            inte.setInt(3, dia);
            inte.setInt(4, sshora);
            inte.setInt(5, ssminutos);
            inte.setInt(6, sssegundos);
            if (modo.equals("ON")) {
                inte.executeUpdate();
            }
            System.out.println("Cambiooooooooooooo");
            // TODO add your handling code here:
        } catch (SQLException ex) {
            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jSlider1StateChanged
    //milliseconds


    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed

        /*        Timer timer = new Timer(delay, new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton3.setEnabled(true);

            }
        });
        timer.setRepeats(false);
        timer.start();
         */
        if (modo.equals("ON")) {
            if (jButton3.getText().equals("ON")) {
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventip.gif")));
                try {
                    PreparedStatement inte = cn.prepareStatement("Insert into ventilador (val,mes,dia,hora,min,seg) values(?,?,?,?,?,?)");

                    inte.setString(1, "ON");
                    inte.setInt(2, 11);
                    inte.setInt(3, dia);
                    inte.setInt(4, sshora);
                    inte.setInt(5, ssminutos);
                    inte.setInt(6, sssegundos);
                    inte.executeUpdate();
                    jButton3.setText("OFF");

                    // TODO add your handling code here:
                } catch (SQLException ex) {
                    Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (jButton3.getText().equals("OFF")) {
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventip.jpg")));
                try {
                    PreparedStatement inte = cn.prepareStatement("Insert into ventilador (val,mes,dia,hora,min,seg) values(?,?,?,?,?,?)");

                    inte.setString(1, "OFF");
                    inte.setInt(2, 11);
                    inte.setInt(3, dia);
                    inte.setInt(4, sshora);
                    inte.setInt(5, ssminutos);
                    inte.setInt(6, sssegundos);
                    inte.executeUpdate();
                    jButton3.setText("ON");
                    // TODO add your handling code here:
                } catch (SQLException ex) {
                    Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ventip.jpg")));
        }

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
    }//GEN-LAST:event_formWindowClosed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing

        String sql = "update arduino set estado=? where id=1";
        PreparedStatement statement;
        try {
            statement = cn.prepareStatement(sql);

            statement.setString(1, "OFF");

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing user was updated successfully!");
            }

            PreparedStatement statement2 = cn.prepareStatement("UPDATE ventilador set val='OFF' WHERE id IN(select max(id) from ( select * from ventilador) as subventi);");
            statement2.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(ControlRemoto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowClosing

    private void jDateChooser3AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jDateChooser3AncestorAdded

        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser3AncestorAdded

    private void jDateChooser3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jDateChooser3PropertyChange
        jDateChooser4.setMinSelectableDate(jDateChooser3.getDate());        // TODO add your handling code here:
    }//GEN-LAST:event_jDateChooser3PropertyChange

    @SuppressWarnings("null")
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String nombrefile = JOptionPane.showInputDialog("Ingrese nombre archivo");
        if (nombrefile == null) {
            JOptionPane.showMessageDialog(rootPane, "Nombre no valido");
        } else {

            File file = new File(nombrefile + ".xls");

            int row = 0;
            //formato fuente para el contenido contenido
            WritableFont wf = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
            WritableCellFormat cf = new WritableCellFormat(wf);

            //Interfaz para una hoja de cálculo
            @SuppressWarnings("UnusedAssignment")
            WritableSheet excelSheet = null;
            WritableWorkbook workbook = null;

            //Establece la configuración regional para generar la hoja de cálculo
            WorkbookSettings wbSettings = new WorkbookSettings();
            wbSettings.setLocale(new Locale("en", "EN"));

            try {
                workbook = Workbook.createWorkbook(file, wbSettings);
            } catch (IOException ex) {
                Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
            }
            //hoja con nombre de la tabla
            workbook.createSheet("CENAPRED", 0);
            excelSheet = workbook.getSheet(0);
            System.out.println("creando hoja excel.....Listo");

            if (jRadioButton1.isSelected()) {
                String sele = jComboBox1.getSelectedItem().toString();
                @SuppressWarnings("UnusedAssignment")
                String sql = null;
                switch (sele) {
                    case "Temperatura":
                        sql = "Select * from temp where mes='" + jMonthChooser1.getMonth() + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Fotoresistencia":
                        sql = "Select * from foto where mes='" + jMonthChooser1.getMonth() + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Semaforo":
                        sql = "Select * from semaforos where mes='" + jMonthChooser1.getMonth() + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Sem1", cf);
                            Label lb22 = new Label(2, row, "Sem2", cf);
                            Label lb3 = new Label(3, row, "Mes", cf);
                            Label lb4 = new Label(4, row, "Dia", cf);
                            Label lb5 = new Label(5, row, "Hora", cf);
                            Label lb6 = new Label(6, row, "Min", cf);
                            Label lb7 = new Label(7, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb22);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);
                                Label val2 = new Label(2, row, res.getString(3), cf);
                                Number mes1 = new Number(3, row, res.getInt(4), cf);
                                Number dia1 = new Number(4, row, res.getInt(5), cf);
                                Number hora1 = new Number(5, row, res.getInt(6), cf);
                                Number min1 = new Number(6, row, res.getInt(7), cf);
                                Number seg1 = new Number(7, row, res.getInt(8), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(val2);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Ventilador":
                        sql = "Select * from ventilador where mes='" + jMonthChooser1.getMonth() + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);

                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);

                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Intensidad":
                        sql = "Select * from inten where mes='" + jMonthChooser1.getMonth() + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        break;
                }

            } else if (jRadioButton2.isSelected()) {//-------------------L         A         P        S           O --------------
                //lapso
                int mess1 = jDateChooser3.getCalendar().get(Calendar.MONTH) + 1;
                int diaa1 = jDateChooser3.getCalendar().get(Calendar.DAY_OF_MONTH);
                int mess2 = jDateChooser4.getCalendar().get(Calendar.MONTH) + 1;
                int diaa2 = jDateChooser4.getCalendar().get(Calendar.DAY_OF_MONTH);
                String sele = jComboBox1.getSelectedItem().toString();
                @SuppressWarnings("UnusedAssignment")
                String sql = null;
                switch (sele) {
                    case "Temperatura":
                        sql = "Select * from temp WHERE mes>='" + mess1 + "' && dia>='" + diaa1 + "' &&  mes<='" + mess2 + "' && dia<='" + diaa2 + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Fotoresistencia":
                        sql = "Select * from foto WHERE mes>='" + mess1 + "' && dia>='" + diaa1 + "' &&  mes<='" + mess2 + "' && dia<='" + diaa2 + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Semaforo":
                        sql = "Select * from semaforos WHERE mes>='" + mess1 + "' && dia>='" + diaa1 + "' &&  mes<='" + mess2 + "' && dia<='" + diaa2 + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Sem1", cf);
                            Label lb22 = new Label(2, row, "Sem2", cf);
                            Label lb3 = new Label(3, row, "Mes", cf);
                            Label lb4 = new Label(4, row, "Dia", cf);
                            Label lb5 = new Label(5, row, "Hora", cf);
                            Label lb6 = new Label(6, row, "Min", cf);
                            Label lb7 = new Label(7, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb22);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);
                                Label val2 = new Label(2, row, res.getString(3), cf);
                                Number mes1 = new Number(3, row, res.getInt(4), cf);
                                Number dia1 = new Number(4, row, res.getInt(5), cf);
                                Number hora1 = new Number(5, row, res.getInt(6), cf);
                                Number min1 = new Number(6, row, res.getInt(7), cf);
                                Number seg1 = new Number(7, row, res.getInt(8), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(val2);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Ventilador":
                        sql = "Select * from ventilador WHERE mes>='" + mess1 + "' && dia>='" + diaa1 + "' &&  mes<='" + mess2 + "' && dia<='" + diaa2 + "'";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);

                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);

                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Intensidad":
                        sql = "Select * from inten WHERE mes>='" + mess1 + "' && dia>='" + diaa1 + "' &&  mes<='" + mess2 + "' && dia<='" + diaa2 + "'";
                        try {
                            //mes

                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        break;
                }

            } else if (jRadioButton3.isSelected()) {//------------------------------------------------------------------
                //año
                String sele = jComboBox1.getSelectedItem().toString();
                @SuppressWarnings("UnusedAssignment")
                String sql = null;
                switch (sele) {
                    case "Temperatura":
                        sql = "Select * from temp";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Fotoresistencia":
                        sql = "Select * from foto ";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Semaforo":
                        sql = "Select * from semaforos";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Sem1", cf);
                            Label lb22 = new Label(2, row, "Sem2", cf);
                            Label lb3 = new Label(3, row, "Mes", cf);
                            Label lb4 = new Label(4, row, "Dia", cf);
                            Label lb5 = new Label(5, row, "Hora", cf);
                            Label lb6 = new Label(6, row, "Min", cf);
                            Label lb7 = new Label(7, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb22);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);
                                Label val2 = new Label(2, row, res.getString(3), cf);
                                Number mes1 = new Number(3, row, res.getInt(4), cf);
                                Number dia1 = new Number(4, row, res.getInt(5), cf);
                                Number hora1 = new Number(5, row, res.getInt(6), cf);
                                Number min1 = new Number(6, row, res.getInt(7), cf);
                                Number seg1 = new Number(7, row, res.getInt(8), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(val2);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Ventilador":
                        sql = "Select * from ventilador";
                        try {
                            //mes
                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Label val1 = new Label(1, row, res.getString(2), cf);

                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);

                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case "Intensidad":
                        sql = "Select * from inten";
                        try {
                            //mes

                            Label lb1 = new Label(0, row, "ID", cf);
                            Label lb2 = new Label(1, row, "Valor", cf);
                            Label lb3 = new Label(2, row, "Mes", cf);
                            Label lb4 = new Label(3, row, "Dia", cf);
                            Label lb5 = new Label(4, row, "Hora", cf);
                            Label lb6 = new Label(5, row, "Min", cf);
                            Label lb7 = new Label(6, row, "Seg", cf);

                            excelSheet.addCell(lb1);
                            excelSheet.addCell(lb2);
                            excelSheet.addCell(lb3);
                            excelSheet.addCell(lb4);
                            excelSheet.addCell(lb5);
                            excelSheet.addCell(lb6);
                            excelSheet.addCell(lb7);
                            row++;
                            Statement st = cn.createStatement();
                            ResultSet res = st.executeQuery(sql);
                            while (res.next()) {
                                Number id1 = new Number(0, row, res.getInt(1), cf);
                                Number val1 = new Number(1, row, res.getFloat(2), cf);
                                Number mes1 = new Number(2, row, res.getInt(3), cf);
                                Number dia1 = new Number(3, row, res.getInt(4), cf);
                                Number hora1 = new Number(4, row, res.getInt(5), cf);
                                Number min1 = new Number(5, row, res.getInt(6), cf);
                                Number seg1 = new Number(6, row, res.getInt(7), cf);
                                row++;
                                excelSheet.addCell(id1);
                                excelSheet.addCell(val1);
                                excelSheet.addCell(mes1);
                                excelSheet.addCell(dia1);
                                excelSheet.addCell(hora1);
                                excelSheet.addCell(min1);
                                excelSheet.addCell(seg1);
                            }

                        } catch (SQLException | WriteException ex) {
                            Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    default:
                        break;
                }
            }
            try {
                workbook.write();
                workbook.close();
                System.out.println("Escribiendo en disco....Listo");
            } catch (IOException | WriteException ex) {
                System.err.println(ex.getMessage());
            }
            //ruta del archivo en el pc
            String fileabrir = (nombrefile + ".xls");

            try {
                //definiendo la ruta en la propiedad file
                Runtime.getRuntime().exec("cmd /c start " + fileabrir);
            } catch (IOException ex) {
                Logger.getLogger(AdminMain.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        // TODO add your handling code here:
    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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

        JFrame njf = new JFrame();
        JFrame njf2 = new JFrame();
        njf.setSize(720, 360);
        njf2.setSize(720, 360);
      
        njf.setVisible(true);
        njf2.setVisible(true);
        njf.add(Panel);
        njf2.add(Panel2);

        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(AdminMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new AdminMain().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1;
    private com.toedter.calendar.JDateChooser jDateChooser3;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private com.toedter.calendar.JMonthChooser jMonthChooser1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JSlider jSlider1;
    private com.toedter.calendar.JYearChooser jYearChooser1;
    // End of variables declaration//GEN-END:variables
}
