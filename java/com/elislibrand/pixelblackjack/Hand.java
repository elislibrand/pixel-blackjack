package com.elislibrand.pixelblackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
    private List<Card> cards;
    private List<Integer> visualCardIndexes;

    private boolean isBlackjack = false;
    private boolean isBusted = false;
    private boolean isDoubledDown = false;
    private boolean isActive = false;

    public Hand()
    {
        cards = new ArrayList<Card>();
        visualCardIndexes = new ArrayList<Integer>();
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public void drawCardFromDeck(Deck deck)
    {
        cards.add(deck.getCard(0));
        deck.removeCard(0);
    }

    public void addVisualCardIndex(int index)
    {
        visualCardIndexes.add(index);
    }

    public int getVisualCardIndex(int index)
    {
        return visualCardIndexes.get(index);
    }

    private void removeVisualCardIndex(int index)
    {
        visualCardIndexes.remove(index);
    }

    public void moveLastVisualCardIndexToHand(Hand hand)
    {
        int lastVisualCardIndex = visualCardIndexes.get(visualCardIndexes.size() - 1);

        hand.visualCardIndexes.add(lastVisualCardIndex);
        removeVisualCardIndex(visualCardIndexes.size() - 1);
    }

    public int getNumberOfCards()
    {
        return cards.size();
    }

    public int getValueOfCards()
    {
        int totalValue = 0;
        int aces = 0;

        for (Card card : this.cards)
        {
            switch (card.getValue())
            {
                case ACE: aces += 1; break;
                case TWO: totalValue += 2; break;
                case THREE: totalValue += 3; break;
                case FOUR: totalValue += 4; break;
                case FIVE: totalValue += 5; break;
                case SIX: totalValue += 6; break;
                case SEVEN: totalValue += 7; break;
                case EIGHT: totalValue += 8; break;
                case NINE: totalValue += 9; break;
                case TEN: totalValue += 10; break;
                case JACK: totalValue += 10; break;
                case QUEEN: totalValue += 10; break;
                case KING: totalValue += 10; break;
            }
        }

        for (int i = 0; i < aces; i++)
        {
            if (totalValue + (aces - 1) > 10)
            {
                totalValue += 1;
            }
            else
            {
                totalValue += 11;
            }
        }

        return totalValue;
    }

    public boolean canDoubleDown()
    {
        if (cards.size() == 2)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean canSplit()
    {
        if (cards.size() == 2 && isEqualValueOfStartingCards())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean isEqualValueOfStartingCards()
    {
        if (cards.get(0).getIntegerValue() == cards.get(1).getIntegerValue())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isBlackjack()
    {
        return isBlackjack;
    }

    public void setBlackjack(boolean isBlackjack)
    {
        this.isBlackjack = isBlackjack;
    }

    public boolean isBusted()
    {
        return isBusted;
    }

    public void setBusted(boolean isBusted)
    {
        this.isBusted = isBusted;
    }

    public boolean hasDoubledDown()
    {
        return isDoubledDown;
    }

    public void setDoubledDown(boolean isDoubledDown)
    {
        this.isDoubledDown = isDoubledDown;
    }

    public void reset()
    {
        isBlackjack = false;
        isBusted = false;
        isDoubledDown = false;
        isActive = false;

        visualCardIndexes.clear();
    }
}