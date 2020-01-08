package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class VisualChip
{
    private Image image;
    private int xPos;
    private int yPos;

    public VisualChip(Image image, int xPos, int yPos)
    {
        this.image = image;
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Image getImage()
    {
        return this.image;
    }

    public int getXPos()
    {
        return this.xPos;
    }

    public int getYPos()
    {
        return this.yPos;
    }
}