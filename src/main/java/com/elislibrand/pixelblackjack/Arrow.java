package com.elislibrand.pixelblackjack;

import java.awt.Image;
import java.awt.Point;

public class Arrow extends Graphic
{
    private int graphicsIndex;
    private int marginTop;
    private boolean isActive;

    public Arrow(Image image, int x, int y, int width, int height, int graphicsIndex)
    {
        super(image, x, y, width, height);
        
        this.graphicsIndex = graphicsIndex;
        marginTop = 3 * Screen.SCALE;
        isActive = false;
    }

    public int getGraphicsIndex()
    {
        return graphicsIndex;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public Point getPosition(int x, int y, int width, int height)
    {
        return new Point(x + (((int)((width / Screen.SCALE) / 2) - (int)((this.width / Screen.SCALE) / 2)) * Screen.SCALE), y + height + marginTop);
    }

    public void reset()
    {
        isActive = false;
    }
}