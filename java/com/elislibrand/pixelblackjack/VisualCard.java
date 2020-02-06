package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class VisualCard extends Graphic
{
    private boolean isRotated;

    public VisualCard(Image image, int x, int y, int width, int height, boolean isRotated)
    {
        super(image, x, y, width, height);

        this.isRotated = isRotated;
    }

    public boolean isRotated()
    {
        return isRotated;
    }
}