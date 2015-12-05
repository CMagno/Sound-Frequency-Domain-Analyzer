/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.media.sound.WaveFileReader;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.WavFile;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.WavFileException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jtransforms.dct.DoubleDCT_1D;
import org.jtransforms.utils.CommonUtils;

/**
 *
 * @author carlosmagno
 */
public class MainFrame extends javax.swing.JFrame {
    
    private static final int NUM_SAMPLES = 44000;
    private static final double FREQ_AMOST = 48000;
    private double[] samples;
    
    WavFile wavFile;
    
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        textArea = new javax.swing.JTextArea();
        graphPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemOpen = new javax.swing.JMenuItem();
        menuItemQuit = new javax.swing.JMenuItem();
        menuTransform = new javax.swing.JMenu();
        menuItemDCT = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        textArea.setEditable(false);
        textArea.setColumns(20);
        textArea.setRows(5);
        jScrollPane1.setViewportView(textArea);

        javax.swing.GroupLayout graphPanelLayout = new javax.swing.GroupLayout(graphPanel);
        graphPanel.setLayout(graphPanelLayout);
        graphPanelLayout.setHorizontalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        graphPanelLayout.setVerticalGroup(
            graphPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 201, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 376, Short.MAX_VALUE))
                .addContainerGap())
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(graphPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jMenu1.setText("File");

        menuItemOpen.setText("Open");
        menuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpenActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemOpen);

        menuItemQuit.setText("Quit");
        menuItemQuit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuItemQuitMouseClicked(evt);
            }
        });
        menuItemQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemQuitActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemQuit);

        menuBar.add(jMenu1);

        menuTransform.setText("Transform");

        menuItemDCT.setText("DCT");
        menuItemDCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemDCTActionPerformed(evt);
            }
        });
        menuTransform.add(menuItemDCT);

        menuBar.add(menuTransform);

        setJMenuBar(menuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(mainPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuItemOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOpenActionPerformed
        // TODO add your handling code here:
        JFileChooser filech = new JFileChooser();
        filech.setFileSelectionMode(JFileChooser.FILES_ONLY);
        filech.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));
        int ret = filech.showOpenDialog(this);
        
        if(ret == JFileChooser.APPROVE_OPTION){
            try {
                wavFile = WavFile.openWavFile(filech.getSelectedFile());
                textArea.append(wavFile.getInfoString());
                samples = new double[wavFile.getNumChannels()* NUM_SAMPLES];
                int nFrames = wavFile.readFrames(samples, NUM_SAMPLES);
                textArea.append(nFrames + " frames lidos.\n");
                
                double valuesX[] = new double[samples.length];
                for(int i = 0; i < samples.length; i++){
                    valuesX[i] = i;
                }
                
                showChart(wavFile.getNumChannels()* NUM_SAMPLES,
                        samples,
                        valuesX,
                        "Amostra",
                        "Amplitude",
                        "Amplitude das Amostras do Áudio",
                        "Audio");
            } catch (IOException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WavFileException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_menuItemOpenActionPerformed

    private void menuItemQuitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuItemQuitMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuItemQuitMouseClicked

    private void menuItemQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemQuitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_menuItemQuitActionPerformed

    private void menuItemDCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemDCTActionPerformed
        // TODO add your handling code here:
        if(samples == null)
            return;
        
        int N = samples.length;
        double f1 = FREQ_AMOST / (2 * (N - 1));
        
        double[] valuesX = new double[samples.length];
        for(int i = 0; i < samples.length; i++){
            valuesX[i] = i * f1;
        }
        
        DoubleDCT_1D dtc_calculator = new DoubleDCT_1D(N);
        dtc_calculator.forward(samples, true);
        
        showChart(N, samples, valuesX, "Hz", "Amplitude", "DCT", "DCT");
    }//GEN-LAST:event_menuItemDCTActionPerformed
    
    public void showChart(int N, double[] valuesY, double[] valuesX, String axisX, String axisY, String graphName, String dataName){
        
        graphPanel.removeAll();
        
        String serieName = dataName;
        String tittle = graphName;
        String xAxisName = axisX;
        String yAxisName = axisY;
        
        XYSeries dataSerie = new XYSeries(serieName);
        
        for(int i = 0; i < N; i++){
            dataSerie.add(valuesX[i], valuesY[i]);
        }
        
        XYSeriesCollection sCollection = new XYSeriesCollection();
        sCollection.addSeries(dataSerie);
        
        JFreeChart jfreechart =  ChartFactory.createScatterPlot(tittle, xAxisName, yAxisName, sCollection);
        
        ChartPanel panel = new ChartPanel(jfreechart);
        panel.setVisible(true);
        
        graphPanel.add(panel);
        graphPanel.setLayout(new GridLayout(1,0));
        graphPanel.validate();
        graphPanel.setVisible(true);
    }
    
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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel graphPanel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem menuItemDCT;
    private javax.swing.JMenuItem menuItemOpen;
    private javax.swing.JMenuItem menuItemQuit;
    private javax.swing.JMenu menuTransform;
    private javax.swing.JTextArea textArea;
    // End of variables declaration//GEN-END:variables
}
