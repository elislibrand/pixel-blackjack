package com.elislibrand.pixelblackjack;

public class Dealer
{
    private Hand hand;

    private boolean isBlackjack = false;

    public Dealer()
    {
        hand = new Hand();
    }

    public Hand getHand()
    {
        return hand;
    }

    public boolean isBlackjack()
    {
        return isBlackjack;
    }

    public void setBlackjack(boolean isBlackjack)
    {
        this.isBlackjack = isBlackjack;
    }

    public void checkForBlackjack()
    {    
        if (hand.getValueOfCards() == 21 && hand.getNumberOfCards() == 2)
        {
            isBlackjack = true;
        }
    }

    public void reset()
    {
        hand.reset();
        
        isBlackjack = false;
    }
}