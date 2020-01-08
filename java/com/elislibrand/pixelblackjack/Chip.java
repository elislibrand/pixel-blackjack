package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class Chip
{
    private Image topDownImage;
    private Image[] images = new Image[11];
    private int value;
    private int quantity;

    public Chip(Image topDownImage, Image[] images, int value)
    {
        this.topDownImage = topDownImage;
        this.images = images;
        this.value = value;
    }

    public Image getTopDownImage()
    {
        return this.topDownImage;
    }

    public Image getImage(int imageIndex)
    {
        return this.images[imageIndex];        
    }

    public int getNumberOfImages()
    {
        return this.images.length;
    }

    public int getValue()
    {
        return this.value;
    }

    public int getQuantity()
    {
        return this.quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public void addQuantity(int quantity)
    {
        this.quantity += quantity;
    }
}