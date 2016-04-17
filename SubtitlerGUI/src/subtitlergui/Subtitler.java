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

import com.sun.awt.AWTUtilities;
import org.jdesktop.swingx.JXBusyLabel;
import org.jdesktop.swingx.icon.EmptyIcon;
import org.jdesktop.swingx.painter.BusyPainter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.tree.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author abhdeb
 */
public class Subtitler extends javax.swing.JFrame implements SubtitlerConstants {
    private SubtitlerWebservice subtitlerWebservice = null;
    private DVDCoverPreview dvdCoverPreview = null;
    private About about = null;

    public Subtitler(String dirPath) {
        subtitlerWebservice = new SubtitlerWebservice();
        dvdCoverPreview = new DVDCoverPreview(null);
        about = new About();
        initComponents(dirPath);
    }

    @SuppressWarnings("unchecked")
    public void initComponents(final String dirPath) {
        System.out.println(UIManager.getLookAndFeel().getName());
        setTitle("Subtitler");
        setIconImage(new ImageIcon(getClass().getResource("/images/Subtitler.png")).getImage());
        jPanel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0,
                        getBackground().brighter().brighter(), 0, getHeight(),
                        getBackground().darker().darker());

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(grphcs);
            }
        };
        jPanel1.setOpaque(false);

        jPanel1.setBorder(DSB);
        jPanel1.setBackground(DARK_GRAY);
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jEditorPane2.setText("");
        jEditorPane2.setEditable(false);
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane3 = new javax.swing.JEditorPane();
        jEditorPane3.setText("");
        jEditorPane3.setEditable(false);
        ToolTipManager.sharedInstance().registerComponent(jEditorPane3);
        jLabel1 = new JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new JButton();
        jButton2 = new JButton();
        jButton3 = new JButton();
        jLabel2 = new JLabel();
        jProgressBar1 = new javax.swing.JProgressBar();
        jProgressBar1.setMinimum(0);
        jProgressBar1.setMaximum(100);        
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem2 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem3 = new javax.swing.JCheckBoxMenuItem();
        jCheckBoxMenuItem4 = new javax.swing.JCheckBoxMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        root = new DefaultMutableTreeNode(VIDEO_FILES);
        model = new DefaultTreeModel(root);
        model.setRoot(root);
        jTree1 = new JTree(model);
        jTree1.setName("Root");
        jTree1.setToolTipText("Right click to add video files...");
        jTree1.setFont(new Font("Thoma", Font.BOLD, 11));

        CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();
        jTree1.setCellRenderer(renderer);

        jTree1.setCellEditor(new CheckBoxNodeEditor(jTree1));
        jTree1.setEditable(true);
        jTree1.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                addMovies(me);
            }
        });

        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        jScrollPane1.setViewportView(jTree1);

        //todo:
        m_popup = new JPopupMenu();
        Action m_action = new AbstractAction(ADD_VIDEO) {
            public void actionPerformed(ActionEvent e) {
                startAddMovieThread();
            }
        };
        m_popup.add(m_action);

        m_action = new AbstractAction(REMOVE) {
            public void actionPerformed(ActionEvent e) {
                clearAll();
            }
        };
        m_popup.add(m_action);

        jTree1.add(m_popup);
        jTree1.addMouseListener(new PopupTrigger());


        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
                        .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                        .addContainerGap())
        );

        jTabbedPane1.addTab("Search", jPanel2);

        jEditorPane2.setContentType("text/html");
        jEditorPane3.setContentType("text/html");

        jScrollPane2.setViewportView(jEditorPane2);
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (null != html && !"".equals(html)) {
                    new Thread(new Runnable() {
                        public void run() {
                            if (jEditorPane2.getText() == null || (jEditorPane2.getText() != null &&
                                    jEditorPane2.getText().indexOf(LOADING_IMDB) != -1)) {
                                jEditorPane2.setText(header + html + footer);
                            }
                            jEditorPane2.setCaretPosition(0);
                        }
                    }).start();
                }

                if (null != html && !"".equals(html)) {
                    new Thread(new Runnable() {
                        public void run() {
                            jEditorPane3.setText(header + dvdCoverHtml + footer);
                            jEditorPane3.setCaretPosition(0);
                            //System.out.println(jEditorPane3.getText());
                        }
                    }).start();
                }
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 661, Short.MAX_VALUE)
                        .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addContainerGap())
        );

        jTabbedPane1.addTab("IMDB", jPanel3);

        HyperlinkListener l = new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if (HyperlinkEvent.EventType.ACTIVATED == e.getEventType()) {
                    try {
                        dvdCoverPreview.setImage(e.getURL());
                        dvdCoverPreview.setLookFeel(UIManager.getLookAndFeel().getName());
                        if (!dvdCoverPreview.isVisible()) {
                            dvdCoverPreview.setVisible(true);
                        }
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        };
        jEditorPane3.addHyperlinkListener(l);

        jScrollPane3.setViewportView(jEditorPane3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3)
                        .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                        .addContainerGap())
        );

        jTabbedPane1.addTab("DVD Cover", jPanel4);

        jLabel1.setText("Subtitle Language");
        jLabel1.setForeground(Color.white);
        jLabel1.setFont(new Font("Thoma", Font.BOLD, 11));

        ArrayList langList = Language.getLanguageNames();
        Object[] langObj = langList.toArray();
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(langObj));
        //jComboBox1 = HudWidgetFactory.createHudComboBox((new javax.swing.DefaultComboBoxModel(langObj)));
        jComboBox1.setSelectedItem("English");
        jComboBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jComboBox1MouseClicked(evt);
            }
        });

        jButton1.setText("Search");
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_search.png")));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                searchSubtitle(evt);
                clearDVDCovers();
            }
        });

        jButton2.setText("Download");
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_download.png")));
        jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                downloadSubtitle(evt);
            }
        });

        jButton3.setText("Quit");
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/button_quit.png")));
        jButton3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                quit();
            }
        });

        jLabel2.setText(VIDEO_FILES);
        jLabel2.setForeground(Color.white);
        jLabel2.setFont(new Font("Thoma", Font.BOLD, 11));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTabbedPane1)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(jButton3)))
                        .addGap(29, 29, 29))
        );

        jMenu1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu_video.png"))); // NOI18N
        jMenu1.setText("File");

        jMenuItem1.setText(VIDEO_FILES);
        jMenuItem1.addActionListener(new AddMovieActionListner());
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Quit");
        jMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                quit();
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuBar1.add(jMenu1);

        jMenu3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu_theme.png"))); // NOI18N
        jMenu3.setText("Theme");

        if (UIManager.getLookAndFeel().getName().equals("Nimbus")) {
            jCheckBoxMenuItem1.setSelected(true);
        }
        jCheckBoxMenuItem1.setText("Nimbus");

        jCheckBoxMenuItem1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLookFeel(e, LOOK_AND_FEEL_NIMBUS);
            }
        });

        jMenu3.add(jCheckBoxMenuItem1);

        if (UIManager.getLookAndFeel().getName().equals("Metal")) {
            jCheckBoxMenuItem2.setSelected(true);
        }
        jCheckBoxMenuItem2.setText("Metal");
        jCheckBoxMenuItem2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLookFeel(e, LOOK_AND_FEEL_METAL);
            }
        });

        jMenu3.add(jCheckBoxMenuItem2);
        if (UIManager.getLookAndFeel().getName().equals("CDE/Motif")) {
            jCheckBoxMenuItem3.setSelected(true);
        }
        jCheckBoxMenuItem3.setText("Motif");
        jCheckBoxMenuItem3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLookFeel(e, LOOK_AND_FEEL_MOTIF);
            }
        });
        jMenu3.add(jCheckBoxMenuItem3);
        if (!UIManager.getLookAndFeel().getName().equals("CDE/Motif")
                && !UIManager.getLookAndFeel().getName().equals("Metal")
                && !UIManager.getLookAndFeel().getName().equals("Nimbus")) {
            jCheckBoxMenuItem4.setSelected(true);
        }
        jCheckBoxMenuItem4.setText("System");
        jCheckBoxMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setLookFeel(e, LOOK_AND_FEEL_SYSTEM);
            }
        });
        jMenu3.add(jCheckBoxMenuItem4);

        jMenuBar1.add(jMenu3);

        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/menu_help.png"))); // NOI18N
        jMenu2.setText("Help");

        /*jMenuItem3.setText("Help Topics");
        jMenuItem3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem3MouseClicked(evt);
            }
        });
        jMenu2.add(jMenuItem3);*/

        jMenuItem4.setText("Check for Update...");
        jMenuItem4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                jMenuItem4MouseClicked(e);
            }
        });
        jMenu2.add(jMenuItem4);

        /*jMenuItem6.setText("Register...");
        jMenuItem6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem6MouseClicked(evt);
            }
        });
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem6);*/

        jMenuItem5.setText("Help & About");
        jMenuItem5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenuItem5MouseClicked(evt);
            }
        });
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        SubtitlerUtil.setCenterScreen(this, 417, 769);
        if (null != dirPath && dirPath.length() > 0) {
            new Thread(new Runnable() {
                public void run() {
                    File f = new File(dirPath);
                    if (f.exists() && f.isDirectory()) {
                        addFiles(f);
                        jProgressBar1.setValue(100);
                        jLabel2.setText("Files Added.");
                    }
                }
            }).start();
        }
        pack();
    }

    public JXBusyLabel createSimpleBusyLabel() {
        JXBusyLabel label = new JXBusyLabel();
        label.getBusyPainter().setHighlightColor(new Color(44, 61, 146).darker());

        label.getBusyPainter().setBaseColor(new Color(168, 204, 241).brighter());
        label.setOpaque(false);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setBusy(true);


        return label;

    }

    public JXBusyLabel createComplexBusyLabel() {
        JXBusyLabel label = new JXBusyLabel(new Dimension(38, 38));
        BusyPainter painter = new BusyPainter(
                new Rectangle2D.Float(0, 0, 8.0f, 8.0f),
                new Rectangle2D.Float(5.5f, 5.5f, 27.0f, 27.0f));
        painter.setTrailLength(4);
        painter.setPoints(8);
        painter.setFrame(-1);
        painter.setBaseColor(Color.blue);
        painter.setHighlightColor(Color.orange);
        label.setPreferredSize(new Dimension(38, 38));
        label.setIcon(new EmptyIcon(38, 38));
        label.setBusyPainter(painter);
        label.setToolTipText("complex busy label");
        return label;

    }


    class PopupTrigger extends MouseAdapter {
        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = jTree1.getPathForLocation(x, y);
                if (path != null /*&&
                        VIDEO_FILES.equalsIgnoreCase(path.getLastPathComponent().toString())*/) {
                    m_popup.show(jTree1, x, y);
                }
            }
        }
    }

    class AddMovieActionListner implements ActionListener {
        AddMovieActionListner() {
        }

        public void actionPerformed(ActionEvent e) {
            startAddMovieThread();

        }
    }

    private void clearAll() {
        new Thread(new Runnable() {
            public void run() {
                if (null != root) {
                    TreePath currentSelection = jTree1.getSelectionPath();
                    if (currentSelection != null) {
                        DefaultMutableTreeNode node = (DefaultMutableTreeNode) currentSelection.getLastPathComponent();
                        if (!node.isRoot()) {
                            DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
                            model.removeNodeFromParent(node);
                            Object[] obj = currentSelection.getPath();
                            if (obj.length == 3) {
                                File file = new File(obj[1].toString() + "\\" + obj[2].toString());
                                if (file.exists() && file.isFile()) {
                                    movieFilesList.remove(file);
                                }
                            } else if (obj.length == 2) {
                                for (int i = 0; i < node.getChildCount(); i++) {
                                    String child = node.getChildAt(i).toString();
                                    File file = new File(obj[1].toString() + "\\" + child);
                                    if (file.exists() && file.isFile()) {
                                        movieFilesList.remove(file);
                                    }
                                }
                            }
                            if (!parent.isRoot() && parent.getChildCount() == 0) {
                                model.removeNodeFromParent(parent);
                            }
                            model.reload();
                            expandAll(jTree1, new TreePath(root), true);
                        }
                    }
                }
            }
        }).start();
    }

    private void startAddMovieThread() {
        new Thread(new Runnable() {
            public void run() {
                JFileChooser fileChooser = new JFileChooser(System.getProperty("user.home"));
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int returnVal = fileChooser.showOpenDialog(Subtitler.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    setTranperent();
                    setDisabledFrame();
                    final File files[] = fileChooser.getSelectedFiles();
                    jLabel2.setText("Scanning..");
                    jProgressBar1.setValue(20);
                    if (null != files && files.length > 0) {
                        for (int f1 = 0; f1 < files.length; f1++) {
                            File file = files[f1];
                            addFiles(file);
                        }
                        jProgressBar1.setValue(100);
                        jLabel2.setText("Files Added.");
                    }
                    reSetTranperent();
                    setEnabledFrame();
                }
            }
        }).start();
    }

    private void addFiles(File file) {
        jProgressBar1.setValue(50);
        if (file.isDirectory()) {
            model.reload();
            visitAllDirsAndFiles(file);
            if (!movieFilesList.isEmpty()) {
                DefaultMutableTreeNode node = null;
                DefaultMutableTreeNode node1 = null;
                for (int i = 0; i < movieFilesList.size(); i++) {
                    File f = (File) movieFilesList.get(i);
                    int c = root.getChildCount();
                    boolean isPresent = false;
                    boolean isPresent1 = false;
                    DefaultMutableTreeNode childNode1 = null;
                    DefaultMutableTreeNode childNode2 = null;
                    for (int t = 0; t < c; t++) {
                        childNode1 = (DefaultMutableTreeNode) root.getChildAt(t);
                        if (childNode1.getUserObject().toString().equalsIgnoreCase(f.getParent())) {
                            isPresent = true;
                            break;
                        }
                    }
                    if (isPresent && null != childNode1) {
                        int c1 = childNode1.getChildCount();
                        for (int t = 0; t < c1; t++) {
                            childNode2 = (DefaultMutableTreeNode) childNode1.getChildAt(t);
                            if (childNode2.getUserObject().toString().equalsIgnoreCase(f.getName())) {
                                isPresent1 = true;
                                break;
                            }
                        }
                    }
                    if (isPresent && isPresent1) {
                    } else if (isPresent && !isPresent1) {
                        node = new DefaultMutableTreeNode(f.getName(), true);
                        model.insertNodeInto(node, childNode1, childNode1.getChildCount());
                        model.reload();

                    } else if (!isPresent && !isPresent1) {
                        node = new DefaultMutableTreeNode(f.getName(), true);
                        node1 = new DefaultMutableTreeNode(f.getParent(), true);
                        model.insertNodeInto(node1, root, root.getChildCount());
                        model.reload();
                        model.insertNodeInto(node, node1, node1.getChildCount());
                        model.reload();
                    }
                }
                root.setUserObject(VIDEO_FILES);
                expandAll(jTree1, new TreePath(root), true);
            }
        }
    }

    private void visitAllDirsAndFiles(File dir) {
        process(dir);
        if (null != dir && dir.isDirectory()) {
            String[] children = dir.list();
            if (null != children) {
                for (int i = 0; i < children.length; i++) {
                    visitAllDirsAndFiles(new File(dir, children[i]));
                }
            }
        }
    }

    private void process(File dir) {
        String[] exts = {"AAF", "3GP", "ASF", "AVCHD", "AVI", "DAT", "FLV", "M1V", "M2V", "M4V", "MKV", "MOV", "MPEG", "MPG", "MP4", "OGG", "RM", "SWF", "WMV"};
        if (null != dir && dir.isFile() && !movieFilesList.contains(dir)) {
            String fileName = dir.getName();
            if (fileName.indexOf(".") != -1) {
                String ext = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
                for (int i = 0; i < exts.length; i++) {
                    String ext1 = exts[i];
                    if (ext1.equalsIgnoreCase(ext)) {
                        movieFilesList.add(dir);
                        break;
                    }
                }
            }
        }
    }

    private void jMenuItem2MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void setLookFeel(ActionEvent evt, int lookFeelCode) {
        setCustomLookFeel(lookFeelCode);
    }

    private void jMenuItem3MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void jMenuItem4MouseClicked(ActionEvent evt) {
        new Thread(new Runnable() {
            public void run() {
                UpdateChecker updateChecker = new UpdateChecker(UIManager.getLookAndFeel());
                updateChecker.setOnTop(true);
                updateChecker.setVisible(true);
                updateChecker.updateProgress(50);
                VersionDTO latestVersion = SubtitlerUtil.readVersionInfo(UPDATE_URL);
                File f = new File(INSTALLED_APP_VERSION_FILE);
                /*VersionDTO installedVersion = null;
                if (f.exists()) {
                    installedVersion = SubtitlerUtil.readLocalVersionInfo(f);
                }*/
                if (null != latestVersion) {
                    double v1 = APP_VERSION;
                    double v2 = Double.valueOf(latestVersion.getVersion()).doubleValue();
                    updateChecker.updateProgress(100);
                    if (v2 > v1) {
                        updateChecker.updateStatus("Update Found...");
                        DownloadDialogue myDialog = new DownloadDialogue(updateChecker, true, "Update found, do you like to download?");
                        //System.err.println("After opening dialog.");
                        if (myDialog.getAnswer()) {
                            updateChecker.setOnTop(false);
                            JFileChooser fileChooser = new JFileChooser();
                            fileChooser.setMultiSelectionEnabled(false);
                            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                            int returnVal = fileChooser.showOpenDialog(Subtitler.this);
                            if (returnVal == JFileChooser.APPROVE_OPTION) {
                                updateChecker.setOnTop(true);
                                final File file = fileChooser.getSelectedFile();
                                updateChecker.updateStatus("Downloading latest installer...");
                                updateChecker.updateProgress(0);
                                SubtitlerUtil.setUpdateChecker(updateChecker);
                                String os = System.getProperty(OS_NAME);
                                System.out.println(os);
                                String url = null;
                                if (os.toLowerCase().indexOf("windows") != -1) {
                                    url = latestVersion.getUrlwin();
                                } else if (os.toLowerCase().indexOf("mac") != -1) {
                                    url = latestVersion.getUrlosx();
                                } else {
                                    url = latestVersion.getUrlother();
                                }
                                if (null != url) {
                                    try {
                                        System.out.println("URL ####### "+url);
                                        SubtitlerUtil.downloadLatestInstaller(url, file.getPath(),
                                                Integer.valueOf(latestVersion.getDlURLSize()));
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    updateChecker.updateStatus("Downloading Completed...");
                                    updateChecker.setVisible(false);
                                    updateChecker.dispose();
                                } else {
                                    updateChecker.updateStatus("Downloading Failed...");
                                }

                            }
                            //System.err.println("The answer stored in CustomDialog is 'true' (i.e. user clicked yes button.)");
                        } else {
                            //System.err.println("The answer stored in CustomDialog is 'false' (i.e. user clicked no button.)");
                        }
                    } else {
                        updateChecker.updateProgress(100);
                        updateChecker.updateStatus("Latest version is already installed.");

                    }
                } else {
                    updateChecker.updateProgress(100);
                    updateChecker.updateStatus("Latest version is already installed.");
                }
            }
        }).start();

    }

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {
        new Thread(new Runnable() {

            public void run() {
                try {
                    about.setLookFeel(UIManager.getLookAndFeel().getName());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (UnsupportedLookAndFeelException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (IllegalAccessException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                } catch (InstantiationException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                about.setVisible(true);
            }
        }).start();
    }

    private void jMenuItem6MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void jMenuItem5MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {

    }

    private void clearDVDCovers() {
        DvdCover.clearDVDCovers();
    }

    private void setDisabledFrame() {
        this.setEnabled(false);
    }

    private void setEnabledFrame() {
        this.setEnabled(true);
    }

    private void setTranperent() {
        AWTUtilities.setWindowOpacity(this, 0.8f);
    }

    private void reSetTranperent() {
        AWTUtilities.setWindowOpacity(this, 1f);
    }

    private void searchSubtitle(java.awt.event.MouseEvent evt) {
        if (!movieFilesList.isEmpty()) {
            new Thread(new Runnable() {

                public void run() {
                    SubtitlerBusyFrame subtitlerBusyFrame = new SubtitlerBusyFrame();
                    subtitlerBusyFrame.setVisible(true);
                    setTranperent();
                    setDisabledFrame();
                    try {
                        DvdCover dvdCover = new DvdCover();
                        jButton1.setEnabled(false);
                        jButton2.setEnabled(false);
                        jButton3.setEnabled(false);
                        jProgressBar1.setValue(30);
                        jLabel2.setText("Searching..");
                        root.setUserObject(VIDEO_FILES);
                        expandAll(jTree1, new TreePath(root), true);
                        jScrollPane1.setViewportView(jTree1);
                        String lang = jComboBox1.getSelectedItem().toString();
                        subtitlerWebservice.searchSubs(movieFilesList, lang);
                        movieSubMap = subtitlerWebservice.getMovieSubMap();
                        idSubMap = subtitlerWebservice.getIdSubMap();
                        if (null != movieSubMap && !movieSubMap.isEmpty()) {
                            jEditorPane2.setText("");
                            jProgressBar1.setValue(60);
                            jLabel2.setText("Subtitle Found.");
                            Set set = movieSubMap.entrySet();
                            Iterator itr = set.iterator();
                            Map.Entry entry = null;
                            File videoFile = null;
                            ArrayList subList = null;
                            html = null;
                            dvdCoverHtml = null;
                            jEditorPane2.setText("");
                            jEditorPane3.setText("");
                            while (itr.hasNext()) {
                                entry = (Map.Entry) itr.next();
                                if (entry.getKey() instanceof String) {
                                    if (null != entry.getValue()) {
                                        SubtitlerImdbDTO subtitlerImdbDTO = (SubtitlerImdbDTO) entry.getValue();
                                        String body1 = SubtitlerUtil.getImdbDetails(subtitlerImdbDTO);
                                        if (null == html || (null != html && "".equals(html))) {
                                            html = body1;
                                        } else {
                                            html = html + "<br><hr><br>" + body1;
                                        }

                                        if (null != subtitlerImdbDTO.getTitle() && !"".equals(subtitlerImdbDTO.getTitle())) {
                                            jLabel2.setText("Searching DVD Covers..");
                                            ArrayList arrayList = dvdCover.getCoverURL(subtitlerImdbDTO.getTitle());
                                            if (!arrayList.isEmpty()) {
                                                jLabel2.setText("DVD Covers Found..");
                                            } else {
                                                jLabel2.setText("No DVD Covers Found..");
                                            }
                                            LinkedHashMap hashMap = dvdCover.saveCovers(arrayList, subtitlerImdbDTO.getTitle());

                                            String body2 = SubtitlerUtil.getDVDCoverHTML(hashMap, subtitlerImdbDTO.getTitle(), subtitlerImdbDTO.getYear());
                                            //System.out.println(body2);
                                            if (null == dvdCoverHtml || (null != dvdCoverHtml && "".equals(dvdCoverHtml))) {
                                                dvdCoverHtml = body2;
                                            } else {
                                                dvdCoverHtml = dvdCoverHtml + "<br><hr><br>" + body2;
                                            }

                                        }
                                    } else {
                                        jEditorPane2.setText("");
                                    }
                                } else {
                                    videoFile = (File) entry.getKey();
                                    subList = (ArrayList) entry.getValue();
                                    int c = root.getChildCount();
                                    for (int n = 0; n < c; n++) {
                                        DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) root.getChildAt(n);
                                        if (childNode.getUserObject().toString().equalsIgnoreCase(videoFile.getParent())) {
                                            int cc = childNode.getChildCount();
                                            for (int p = 0; p < cc; p++) {
                                                DefaultMutableTreeNode childNode1 = (DefaultMutableTreeNode) childNode.getChildAt(p);
                                                if (childNode1.getUserObject().toString().equalsIgnoreCase(videoFile.getName())) {
                                                    if (null != subList && !subList.isEmpty()) {
                                                        SubtitlerDTO subtitlerDTO = null;
                                                        for (int i = 0; i < subList.size(); i++) {
                                                            subtitlerDTO = (SubtitlerDTO) subList.get(i);
                                                            CheckBoxNode checkBoxNode = new CheckBoxNode(subtitlerDTO.getSubFileName() + " : " + subtitlerDTO.getSubRating()
                                                                    + " : " + subtitlerDTO.getMovieReleaseName() + " IDSUB :" + subtitlerDTO.getIDSubtitleFile(), false);
                                                            model.insertNodeInto(checkBoxNode, childNode1, childNode1.getChildCount());
                                                            model.reload();
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    model.reload();
                                }
                            }
                            jProgressBar1.setValue(100);
                            expandAll(jTree1, new TreePath(root), true);
                            jButton2.setEnabled(true);
                            jEditorPane2.setText(LOADING_IMDB);
                        } else {
                            jLabel2.setText("No Match Found.");
                            jProgressBar1.setValue(100);
                        }
                        jButton1.setEnabled(true);
                        jButton2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jLabel2.setText("Finished Processing.");
                    } catch (Exception e) {
                        e.printStackTrace();
                        jLabel2.setText("Error. Try again.");
                        jButton1.setEnabled(true);
                        jButton2.setEnabled(true);
                        jButton3.setEnabled(true);
                        jProgressBar1.setValue(100);
                    }
                    setEnabledFrame();
                    reSetTranperent();
                    subtitlerBusyFrame.setVisible(false);
                    subtitlerBusyFrame.dispose();
                }
            }).start();
        }
    }

    private void downloadSubtitle(java.awt.event.MouseEvent evt) {
        new Thread(new Runnable() {
            public void run() {
                if (null != subDownloadList && !subDownloadList.isEmpty()) {
                    if (jButton2.isEnabled()) {
                        jButton1.setEnabled(false);
                        jLabel2.setText("Downloading Subtitle");
                        jProgressBar1.setValue(20);
                        subtitlerWebservice.downloadSub(subDownloadList);
                        DefaultTreeModel model = (DefaultTreeModel) jTree1.getModel();
                        DefaultMutableTreeNode defaultMutableTreeNode = (DefaultMutableTreeNode) model.getRoot();
                        //defaultMutableTreeNode.setUserObject("Completed.");
                        model.reload();
                        expandAll(jTree1, new TreePath(root), true);
                        jProgressBar1.setValue(100);
                        jLabel2.setText("Subtitle Downloaded.");
                        jButton1.setEnabled(true);
                    }
                }
            }
        }).start();
    }

    private void quit() {
        disposeFrame();
    }

    private void disposeFrame() {
        /*try {
            subtitlerWebservice.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlRpcException e) {
            e.printStackTrace();
        }*/
        dispose();
    }

    private void addMovies(MouseEvent mouseEvent) {
        //System.out.println("");
        if (null != mouseEvent && null != mouseEvent.getComponent() &&
                null != ((JTree) mouseEvent.getComponent()).getAnchorSelectionPath()
                && null != ((JTree) mouseEvent.getComponent()).getAnchorSelectionPath().getLastPathComponent()
                && VIDEO_FILES.equalsIgnoreCase(((JTree) mouseEvent.getComponent()).getAnchorSelectionPath().getLastPathComponent().toString())
                && mouseEvent.getClickCount() >= 2) {
            startAddMovieThread();
        }

    }

    class CheckBoxNodeRenderer implements TreeCellRenderer {
        private JCheckBox leafRenderer = new JCheckBox();

        private JLabel nonLeafRenderer = new JLabel();

        Color selectionBorderColor, selectionForeground, selectionBackground,
                textForeground, textBackground;

        protected JCheckBox getLeafRenderer() {
            return leafRenderer;
        }

        public CheckBoxNodeRenderer() {
            Font fontValue;
            fontValue = new Font("Thoma", Font.BOLD, 11);
            if (fontValue != null) {
                leafRenderer.setFont(fontValue);
            }
            Boolean booleanValue = (Boolean) UIManager
                    .get("Tree.drawsFocusBorderAroundIcon");
            leafRenderer.setFocusPainted((booleanValue != null)
                    && (booleanValue.booleanValue()));

            selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
            selectionForeground = UIManager.getColor("Tree.selectionForeground");
            selectionBackground = UIManager.getColor("Tree.selectionBackground");
            textForeground = UIManager.getColor("Tree.textForeground");
            textBackground = UIManager.getColor("Tree.textBackground");
        }

        public Component getTreeCellRendererComponent(JTree tree, Object value,
                                                      boolean selected, boolean expanded, boolean leaf, int row,
                                                      boolean hasFocus) {

            nonLeafRenderer.setText(value.toString());
            nonLeafRenderer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/video_file.png")));

            Component returnValue = null;
            boolean b = false;
            String text = "";
            if (null != value && value instanceof CheckBoxNode) {
                text = ((CheckBoxNode) value).getText();
                if (null != text && text.toLowerCase().indexOf(".srt") != -1) {
                    leaf = true;
                }
            }
            if (leaf) {
                String stringValue = tree.convertValueToText(value, selected,
                        expanded, leaf, row, false);
                leafRenderer.setText(stringValue);
                leafRenderer.setSelected(false);
                leafRenderer.setEnabled(tree.isEnabled());

                if (selected) {
                    leafRenderer.setForeground(selectionForeground);
                    leafRenderer.setBackground(selectionBackground);
                } else {
                    leafRenderer.setForeground(textForeground);
                    leafRenderer.setBackground(textBackground);
                }

                if ((value != null) && (value instanceof DefaultMutableTreeNode)) {

                    Object userObject = ((DefaultMutableTreeNode) value)
                            .getUserObject();
                    if (userObject instanceof CheckBoxNode || value instanceof CheckBoxNode) {
                        if (userObject instanceof CheckBoxNode) {
                            CheckBoxNode node = (CheckBoxNode) userObject;
                            leafRenderer.setText(node.getText());
                            leafRenderer.setSelected(node.isSelected());
                        } else if (value instanceof CheckBoxNode) {
                            CheckBoxNode node = (CheckBoxNode) value;
                            leafRenderer.setText(node.getText());
                            leafRenderer.setSelected(node.isSelected());
                        }
                    } else {
                        returnValue = nonLeafRenderer;

                        b = true;
                    }
                }
                if (!b) {
                    returnValue = leafRenderer;
                }
            } else {
                returnValue = nonLeafRenderer;
            }
            //
            return returnValue;
        }
    }

    class CheckBoxNodeEditor extends AbstractCellEditor implements TreeCellEditor {

        CheckBoxNodeRenderer renderer = new CheckBoxNodeRenderer();

        ChangeEvent changeEvent = null;

        JTree tree;

        public CheckBoxNodeEditor(JTree tree) {
            this.tree = tree;
        }

        public Object getCellEditorValue() {
            JCheckBox checkbox = renderer.getLeafRenderer();
            String idSub = checkbox.getText();
            if (checkbox.isSelected()) {
                if (idSub.indexOf("IDSUB :") != -1) {
                    idSub = idSub.substring(idSub.indexOf("IDSUB :") + "IDSUB :".length(), idSub.length());
                    idSub = idSub.trim();
                    if (null != idSubMap && idSubMap.containsKey(idSub)) {
                        SubtitlerDTO subtitlerDTO = (SubtitlerDTO) idSubMap.get(idSub);
                        if (!subDownloadList.contains(subtitlerDTO)) {
                            subDownloadList.add(subtitlerDTO);
                        }
                    }
                }
            } else {
                if (idSub.indexOf("IDSUB :") != -1) {
                    idSub = idSub.substring(idSub.indexOf("IDSUB :") + "IDSUB :".length(), idSub.length());
                    idSub = idSub.trim();
                    if (null != idSubMap && idSubMap.containsKey(idSub)) {
                        SubtitlerDTO subtitlerDTO = (SubtitlerDTO) idSubMap.get(idSub);
                        if (subDownloadList.contains(subtitlerDTO)) {
                            subDownloadList.remove(subtitlerDTO);
                        }
                    }
                }

            }
            CheckBoxNode checkBoxNode = new CheckBoxNode(checkbox.getText(),
                    checkbox.isSelected());
            return checkBoxNode;
        }

        public boolean isCellEditable(EventObject event) {
            boolean returnValue = false;
            if (event instanceof MouseEvent) {
                MouseEvent mouseEvent = (MouseEvent) event;
                TreePath path = tree.getPathForLocation(mouseEvent.getX(),
                        mouseEvent.getY());
                if (path != null) {
                    Object node = path.getLastPathComponent();
                    if ((node != null) && (node instanceof DefaultMutableTreeNode)) {
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) node;
                        Object userObject = treeNode.getUserObject();
                        returnValue = ((treeNode.isLeaf()) && (node instanceof CheckBoxNode));
                    }
                }
            }
            return returnValue;
        }

        public Component getTreeCellEditorComponent(JTree tree, Object value,
                                                    boolean selected, boolean expanded, boolean leaf, int row) {

            Component editor = renderer.getTreeCellRendererComponent(tree, value,
                    true, expanded, leaf, row, true);

            ItemListener itemListener = new ItemListener() {
                public void itemStateChanged(ItemEvent itemEvent) {
                    if (stopCellEditing()) {
                        fireEditingStopped();
                    }
                }
            };
            if (editor instanceof JCheckBox) {
                ((JCheckBox) editor).addItemListener(itemListener);
            }
            return editor;
        }
    }

    class CheckBoxNode extends DefaultMutableTreeNode {
        String text;

        boolean selected;

        public CheckBoxNode(String text, boolean selected) {
            this.text = text;
            this.selected = selected;
            super.setUserObject(text);
        }

        public boolean isSelected() {
            return selected;
        }

        public void setSelected(boolean newValue) {
            selected = newValue;
        }

        public String getText() {
            return text;
        }

        public void setText(String newValue) {
            text = newValue;
        }

        public String toString() {
            return text;
        }
    }

    private void expandAll(JTree tree, TreePath parent, boolean expand) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (node.getChildCount() >= 0) {
            for (Enumeration e = node.children(); e.hasMoreElements();) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                expandAll(tree, path, expand);
            }
        }
        if (expand) {
            tree.expandPath(parent);
        } else {
            tree.collapsePath(parent);
        }
    }

    private void jComboBox1MouseClicked(java.awt.event.MouseEvent evt) {
    }

    private void setCustomLookFeel(int val) {
        try {
            if (0 == val) {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        jCheckBoxMenuItem1.setSelected(true);
                        jCheckBoxMenuItem2.setSelected(false);
                        jCheckBoxMenuItem3.setSelected(false);
                        jCheckBoxMenuItem4.setSelected(false);
                        jPanel1.setBackground(DARK_GRAY);
                        break;
                    }
                }
            } else if (1 == val) {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("Metal".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        jCheckBoxMenuItem1.setSelected(false);
                        jCheckBoxMenuItem2.setSelected(true);
                        jCheckBoxMenuItem3.setSelected(false);
                        jCheckBoxMenuItem4.setSelected(false);
                        jPanel1.setBackground(DARK_GRAY_1);
                        break;
                    }
                }

            } else if (2 == val) {
                for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                    if ("CDE/Motif".equals(info.getName())) {
                        javax.swing.UIManager.setLookAndFeel(info.getClassName());
                        jCheckBoxMenuItem1.setSelected(false);
                        jCheckBoxMenuItem2.setSelected(false);
                        jCheckBoxMenuItem3.setSelected(true);
                        jCheckBoxMenuItem4.setSelected(false);
                        jPanel1.setBackground(DARK_GREEN);
                        break;
                    }
                }

            } else if (3 == val) {
                javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                jCheckBoxMenuItem1.setSelected(false);
                jCheckBoxMenuItem2.setSelected(false);
                jCheckBoxMenuItem3.setSelected(false);
                jCheckBoxMenuItem4.setSelected(true);
                jPanel1.setBackground(DARK_BLUE);
            }
            SwingUtilities.updateComponentTreeUI(this);
            //System.out.println(UIManager.getLookAndFeel().getName());

        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    }

    public static void main(String args[]) {
        final String dirPath = args.length > 0 ? args[0] : null;
        try {
            javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Subtitler.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Subtitler(dirPath).setVisible(true);
            }
        });
    }


    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem2;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem3;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem4;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JEditorPane jEditorPane3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private static ArrayList movieFilesList = new ArrayList();
    private javax.swing.JTree jTree1;
    private DefaultMutableTreeNode root;
    private DefaultTreeModel model;
    private static HashMap idSubMap = null;
    private static HashMap movieSubMap = null;
    private static ArrayList subDownloadList = new ArrayList();
    public static final String header = "<html><head><title></title></head><body bgcolor=white>";
    public static final String footer = "</body></html>";
    private String html = "";
    private String dvdCoverHtml = "";
    private JPopupMenu m_popup;
}
