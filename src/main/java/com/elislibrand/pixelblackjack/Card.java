package com.elislibrand.pixelblackjack;

import java.awt.Image;

public class Card
{
    private Image image;
    private Suit suit;
    private Value value;

    public Card(Image image, Suit suit, Value value)
    {
        this.image = image;
        this.suit = suit;
        this.value = value;
    }

    public Image getImage()
    {
        return image;
    }

    public Suit getSuit()
    {
        return suit;
    }

    public Value getValue()
    {
        return value;
    }

    public int getIntegerValue()
    {
        switch (value)
        {
            case TWO: return 2;
            case THREE: return 3;
            case FOUR: return 4;
            case FIVE: return 5;
            case SIX: return 6;
            case SEVEN: return 7;
            case EIGHT: return 8;
            case NINE: return 9;
            case TEN: return 10;
            case JACK: return 10;
            case QUEEN: return 10;
            case KING: return 10;
            default: return 1;
        }
    }

    public int getCount()
    {
        switch (value)
        {
            case TWO: return 1;
            case THREE: return 1;
            case FOUR: return 1;
            case FIVE: return 1;
            case SIX: return 1;
            case SEVEN: return 0;
            case EIGHT: return 0;
            case NINE: return 0;
            case TEN: return -1;
            case JACK: return -1;
            case QUEEN: return -1;
            case KING: return -1;
            case ACE: return -1;
            default: return 0;
        }
    }
}