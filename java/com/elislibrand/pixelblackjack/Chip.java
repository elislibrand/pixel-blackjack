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
        return topDownImage;
    }

    public Image getImage(int imageIndex)
    {
        return images[imageIndex];        
    }

    public int getNumberOfImages()
    {
        return images.length;
    }

    public int getValue()
    {
        return value;
    }

    public int getQuantity()
    {
        return quantity;
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