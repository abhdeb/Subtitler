/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package subtitlergui;

import javax.swing.*;
import java.awt.*;

/**
 * @author abhdeb
 */
public class UpdateChecker extends javax.swing.JFrame implements SubtitlerConstants {


    public UpdateChecker(LookAndFeel lookAndFeel) {
        try {
            if (null != lookAndFeel) {
                UIManager.setLookAndFeel(lookAndFeel);
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        initComponents();
    }


    @SuppressWarnings("unchecked")

    private void initComponents() {
        setTitle("Subtitler Update Check");
        setIconImage(new ImageIcon(getClass().getResource("/images/button_download.png")).getImage());
        jPanel1 = new javax.swing.JPanel();
        jPanel1.setBorder(DSB);
        jPanel1.setBackground(Color.DARK_GRAY);
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100); 
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new Font("Thoma", Font.BOLD, 11));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setText("Checking for update...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 138, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(28, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        SubtitlerUtil.setCenterScreen(this, 148, 470);
        //setAlwaysOnTop(true);
        pack();
    }
    
    public void setOnTop(boolean flag) {
        setAlwaysOnTop(flag);
    }

    public void updateStatus(String status) {
        jLabel1.setText(status);
        //SwingUtilities.updateComponentTreeUI(jLabel1);
    }

    public void updateProgress(int per) {
        jProgressBar1.setValue(per);
        //SwingUtilities.updateComponentTreeUI(jProgressBar1);
    }

    public static void main(String[] args) {
        UpdateChecker updateChecker = new UpdateChecker(null);
        updateChecker.setVisible(true);
    }

    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;

}
