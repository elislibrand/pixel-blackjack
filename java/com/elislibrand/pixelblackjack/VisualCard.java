package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class VisualCard
{
    private Image image;
    private int x;
    private int y;
    private boolean isRotated;

    public VisualCard(Image image, int x, int y, boolean isRotated)
    {
        this.image = image;
        this.x = x;
        this.y = y;
        this.isRotated = isRotated;
    }

    public Image getImage()
    {
        return image;
    }

    public void setImage(Image image)
    {
        this.image = image;
    }

    public boolean isRotated()
    {
        return isRotated;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}