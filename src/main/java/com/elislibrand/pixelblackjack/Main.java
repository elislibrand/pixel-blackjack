package com.elislibrand.pixelblackjack;

import java.awt.EventQueue;

//import java.awt.Image;
//import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main extends JFrame
{
    /* 
     * Create class with variables accessable for every class (SCALING, SCREEN_SIZE, etc.) 
     * If player splits and busts only on last hand, dealer will only reveal reveal face-down card and not draw more cards 
     * 
     */

    private static final long serialVersionUID = 1L;

    public Main()
    {
        initializeUI();
    }

    private void initializeUI()
    {
        add(new MasterPanel());

        setResizable(false);
        setUndecorated(true);
        
        pack();

        setTitle("Pixel Blackjack");
        setLocationRelativeTo(null);
        //setIconImage(new ImageIcon("./assets/logo.png").getImage());
        setCursor(Screen.HIDDEN_CURSOR);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args)
    {
        EventQueue.invokeLater(() ->
        {
            JFrame frame = new Main();
            frame.setVisible(true);
        });
    }
}