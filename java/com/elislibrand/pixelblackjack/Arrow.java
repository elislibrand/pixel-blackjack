package com.elislibrand.pixelblackjack;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public class Arrow
{
    private Image image;

    private Dimension size;
    private Point pos;

    private int marginFromTopObject;
    private boolean isActive;

    public Arrow()
    {
        image = new ImageIcon(getClass().getResource("/assets/props/arrow.png")).getImage();
        size = new Dimension(5 * Screen.SCALE, 5 * Screen.SCALE);
        
        marginFromTopObject = 3 * Screen.SCALE;
        isActive = false;
    }

    public Image getImage()
    {
        return image;
    }

    public Dimension getSize()
    {
        return size;
    }

    public Point getPos()
    {
        return pos;
    }

    public void setPos(Point pos)
    {
        this.pos = pos;
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public void calculatePos(Dimension cardSize, Point currentHandVisualCardPosition) // Rename later
    {
        setPos(new Point(currentHandVisualCardPosition.x + (((int)((cardSize.width / Screen.SCALE) / 2) - (int)((size.width / Screen.SCALE) / 2)) * Screen.SCALE), currentHandVisualCardPosition.y + cardSize.height + marginFromTopObject));
    }

    public void reset()
    {
        isActive = false;
    }
}