/*
 * Copyright (c) 2012. Subtitler Renaissance V1.0
 * Renaissance is very simple application to allow search and download subtitles for chosen video and specified language.
 * Copyright (c) Spiral Research 2005-2012(abhra.dev@gmail.com)
 */

package subtitlergui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by IntelliJ IDEA.
 * User: abhdeb
 * Date: Feb 15, 2012
 * Time: 3:31:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class DownloadDialogue extends JDialog implements ActionListener {
    private JPanel myPanel = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private boolean answer = false;

    public boolean getAnswer() {
        return answer;
    }

    public DownloadDialogue(JFrame frame, boolean modal, String myMessage) {
        super(frame, modal);
        myPanel = new JPanel();
        getContentPane().add(myPanel);
        myPanel.add(new JLabel(myMessage));
        yesButton = new JButton("Yes");
        yesButton.addActionListener(this);
        myPanel.add(yesButton);
        noButton = new JButton("No");
        noButton.addActionListener(this);
        myPanel.add(noButton);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screen = tk.getScreenSize();
        int lx = (int) ((screen.getWidth() * 1.0D) / 3.5D);
        int ly = (int) ((screen.getHeight() * 1.0D) / 2.5D);
        setLocation(lx, ly);
        pack();
        setLocationRelativeTo(frame);
        setVisible(true);
        //System.out.println("");
    }

    public void actionPerformed(ActionEvent e) {
        if (yesButton == e.getSource()) {
            System.err.println("User chose yes.");
            answer = true;
            setVisible(false);
        } else if (noButton == e.getSource()) {
            System.err.println("User chose no.");
            answer = false;
            setVisible(false);
        }
    }

}
