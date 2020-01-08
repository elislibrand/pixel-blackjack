package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class VisualCard
{
    private Image image;
    //private int indexInArrayList;
    private int x;
    private int y;
    private boolean isRotated;

    public VisualCard(Image image, /* int indexInArrayList, */ int xPos, int yPos, boolean isRotated)
    {
        this.image = image;
        //this.indexInArrayList = indexInArrayList;
        this.x = xPos;
        this.y = yPos;
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

    /*public int getIndexInArrayList()
    {
        return indexInArrayList;
    }*/

    public boolean isRotated()
    {
        return isRotated;
    }

    public int getXPos()
    {
        return x;
    }

    public void setXPos(int xPos)
    {
        this.x = xPos;
    }

    public int getYPos()
    {
        return y;
    }

    public void setYPos(int yPos)
    {
        this.y = yPos;
    }
}