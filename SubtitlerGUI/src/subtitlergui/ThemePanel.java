/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 10, 2012
 * Time: 11:24:25 AM
 * To change this template use File | Settings | File Templates.
 */

import javax.swing.*;
import java.awt.*;

public class ThemePanel extends JPanel {
    private Image img;

    public ThemePanel(Image img) {
        this.img = img;
        Dimension size = new Dimension(img.getWidth(null), img.getHeight(null));

        setSize(size);
        setPreferredSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
        setLayout(null);
    }

    public void paintComponent(Graphics g) {
        g.drawImage(this.img, 0, 0, null);
    }
}