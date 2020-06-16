package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class Graphic
{
    protected Image image;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected boolean isAnimating;

    public Graphic(Image image, int x, int y, int width, int height)
    {
        this.image = image;
        
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        isAnimating = false;
    }

    public final Image getImage()
    {
        return image;
    }

    public final void setImage(Image image)
    {
        this.image = image;
    }

    public final int getX()
    {
        return x;
    }

    public final void setX(int x)
    {
        this.x = x;
    }

    public final int getY()
    {
        return y;
    }

    public final void setY(int y)
    {
        this.y = y;
    }

    public final int getWidth()
    {
        return width;
    }

    public final void setWidth(int width)
    {
        this.width = width;
    }

    public final int getHeight()
    {
        return height;
    }

    public final void setHeight(int height)
    {
        this.height = height;
    }

    public final boolean isAnimating()
    {
        return isAnimating;
    }

    public final void setAnimating(boolean isAnimating)
    {
        this.isAnimating = isAnimating;
    }
}