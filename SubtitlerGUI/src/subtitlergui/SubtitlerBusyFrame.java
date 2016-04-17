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

import org.jdesktop.swingx.JXBusyLabel;

import javax.swing.*;
import java.awt.*;

/**
 * @author abhdeb
 */
public class SubtitlerBusyFrame extends JFrame {
    public SubtitlerBusyFrame() {
        setUndecorated(true);
        setSize(30, 30);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JXBusyLabel label = new JXBusyLabel();
        label.getBusyPainter().setHighlightColor(Color.DARK_GRAY);

        label.getBusyPainter().setBaseColor(Color.WHITE);
        label.setOpaque(false);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBusy(true);
        add(label);
        SubtitlerUtil.setCenterScreen(this, 30, 30);
        setAlwaysOnTop(true);
    }

    public static void main(String[] args) {
        new SubtitlerBusyFrame().setVisible(true);
    }

    //todo: set transperent screen code
    //AWTUtilities.setWindowOpacity(TransparentFrame.this, 0.5f);
    /*JSlider slider = new JSlider(JSlider.HORIZONTAL, 10, 100, 100);
       slider.addChangeListener(new ChangeListener() {
           @Override
           public void stateChanged(ChangeEvent e) {
               JSlider slider = (JSlider) e.getSource();
               if (!slider.getValueIsAdjusting()) {
                   AWTUtilities.setWindowOpacity(TransparentFrame.this, slider.getValue()/100f);
               }
           }
    });*/
}