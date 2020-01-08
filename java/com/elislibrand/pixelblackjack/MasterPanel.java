package com.elislibrand.pixelblackjack;

import java.awt.Color;
import java.awt.GridBagConstraints;
//import java.awt.Graphics;
//import java.awt.Graphics2D;
//import java.awt.Toolkit;
import java.awt.GridBagLayout;

//import java.awt.Image;
//import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MasterPanel extends JPanel
{
    private static final long serialVersionUID = 1L;
 
    //private Image background;

    public MasterPanel()
    {
        initializeBoard();
    }

    private final void initializeBoard()
    {
        setLayout(new GridBagLayout());
        setBackground(new Color(48, 102, 60));
        setPreferredSize(Screen.RESOLUTION);
        add(new GamePanel(), getGridBagConstraints());

        //loadImages();
    }

    private final GridBagConstraints getGridBagConstraints()
    {
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        //gridBagConstraints.weighty = 1;

        return gridBagConstraints;
    }

    /*private final void loadImages()
    {
        //background = new ImageIcon(getClass().getResource("/assets/props/background.png")).getImage();
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        draw(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        g2d.drawImage(background, 0, 0, Screen.RESOLUTION.width, Screen.RESOLUTION.height, null);
    }*/
}