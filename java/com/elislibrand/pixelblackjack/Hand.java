package com.elislibrand.pixelblackjack;

import java.util.ArrayList;
import java.util.List;

public class Hand
{
    private List<Card> cards;
    private List<Integer> graphicsIndexes;

    private int valueOfCards = 0;

    private boolean isAutoStand = false;
    private boolean isDoubledDown = false;
    private boolean isActive = false;
    private boolean isSoft = false;

    public Hand()
    {
        cards = new ArrayList<Card>();
        graphicsIndexes = new ArrayList<Integer>();
    }

    public boolean isActive()
    {
        return isActive;
    }

    public void setActive(boolean isActive)
    {
        this.isActive = isActive;
    }

    public Card getCard(int index)
    {
        return cards.get(index);
    }

    public void removeCard(int index)
    {
        cards.remove(index);
    }

    public void addCard(Card card)
    {
        cards.add(card);
    }

    public void drawCardFromDeck(Deck deck)
    {
        cards.add(deck.getCard(0));
        deck.removeCard(0);
    }

    public void addGraphicsIndex(int index)
    {
        graphicsIndexes.add(index);
    }

    public int getGraphicsIndex(int index)
    {
        return graphicsIndexes.get(index);
    }

    private void removeGraphicsIndex(int index)
    {
        graphicsIndexes.remove(index);
    }

    public void moveLastCardToHand(Hand hand) // REMAKE
    {
        hand.addCard(getCard(1));
        removeCard(1);
    }

    public void moveLastGraphicsIndexToHand(Hand hand)
    {
        int lastGraphicsIndex = graphicsIndexes.get(graphicsIndexes.size() - 1);

        hand.addGraphicsIndex(lastGraphicsIndex);
        removeGraphicsIndex(graphicsIndexes.size() - 1);
    }

    public int getNumberOfCards()
    {
        return cards.size();
    }

    public int getValueOfCards()
    {
        return valueOfCards;
    }

    public void calculateValueOfCards()
    {
        isSoft = false;

        int totalValue = 0;
        int aces = 0;

        for (Card card : cards)
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
                
                isSoft = true;
            }
        }

        valueOfCards = totalValue;
    }

    public String getValueOfCardsToString()
    {
        int totalValue = getValueOfCards();
        
        if ((isSoft && totalValue == 21) || !isSoft || isDoubledDown)
        {
            return Integer.toString(totalValue);
        }

        return Integer.toString(totalValue - 10) + "/" + Integer.toString(totalValue);
    }

    public void moveAllCardsToDeck(Deck deck)
    {
        for (int i = 0; i < cards.size(); i++)
        {
            deck.addCard(getCard(i));
        }

        for (int i = 0; i < cards.size(); i++)
        {
            removeCard(0);
        }
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
    
    public boolean isAutoStand()
    {
        return isAutoStand;
    }

    public void setAutoStand(boolean isAutoStand)
    {
        this.isAutoStand = isAutoStand;
    }

    public boolean isDoubledDown()
    {
        return isDoubledDown;
    }

    public void setDoubledDown(boolean isDoubledDown)
    {
        this.isDoubledDown = isDoubledDown;
    }

    public void setSoft(boolean isSoft)
    {
        this.isSoft = isSoft;
    }

    public void reset()
    {
        valueOfCards = 0;

        isAutoStand = false;
        isDoubledDown = false;
        isSoft = false;
        isActive = false;

        cards.clear();
        graphicsIndexes.clear();
    }
}