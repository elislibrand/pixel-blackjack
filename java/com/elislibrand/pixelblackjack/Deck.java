package com.elislibrand.pixelblackjack;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

public class Deck
{
    private List<Card> cards = new ArrayList<Card>();
    private List<Integer> visualCardIndexes = new ArrayList<Integer>();

    private boolean hasBlackjack = false;
    private boolean hasBusted = false;
    private boolean hasDoubledDown = false;
    private boolean isInitiated = false;

    public Deck()
    {
        this.cards = new ArrayList<Card>();
    }

    public boolean isInitiated()
    {
        return isInitiated;
    }

    public void initiate()
    {
        isInitiated = true;
    }

    public void createFullDeck()
    {
        for (Suit cardSuit : Suit.values())
        {
            for (Value cardValue : Value.values())
            {
                Image cardImage = new ImageIcon(getClass().getResource("/assets/cards/" + cardValue.toString().toLowerCase() + cardSuit.toString().toLowerCase() + ".png")).getImage();

                this.cards.add(new Card(cardImage, cardSuit, cardValue));
            }
        }
    }

    public void shuffle()
    {   
        Collections.shuffle(this.cards);
    }

    public Card getCard(int cardIndex)
    {
        return this.cards.get(cardIndex);
    }

    public void removeCard(int cardIndex)
    {
        this.cards.remove(cardIndex);
    }

    public void addCard(Card card)
    {
        this.cards.add(card);
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

    public int getVisualCardIndex(int cardIndex)
    {
        return visualCardIndexes.get(cardIndex);
    }

    private void removeVisualCardIndex(int index)
    {
        visualCardIndexes.remove(index);
    }

    public void moveLastCardToDeck(Deck deck) // REMAKE
    {
        deck.cards.add(getCard(1));
        removeCard(1);
    }

    public void moveLastVisualCardIndexToDeck(Deck deck)
    {
        int lastVisualCardIndex = visualCardIndexes.get(visualCardIndexes.size() - 1);
        System.out.println("Upper card VC index: " + lastVisualCardIndex);

        deck.visualCardIndexes.add(lastVisualCardIndex);
        removeVisualCardIndex(visualCardIndexes.size() - 1);
    }

    public int getDeckSize()
    {
        return this.cards.size();
    }

    public void moveAllToDeck(Deck deckToMoveTo)
    {
        int thisDeckSize = this.cards.size();

        for (int i = 0; i < thisDeckSize; i++)
        {
            deckToMoveTo.addCard(this.getCard(i));
        }

        for (int i = 0; i < thisDeckSize; i++)
        {
            this.removeCard(0);
        }
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
        if (this.hasTwoCards())
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
        if (hasTwoCards() && isEqualValueOfStartingCards())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private boolean hasTwoCards()
    {
        if (this.cards.size() == 2)
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
        if (this.cards.get(0).getIntegerValue() == this.cards.get(1).getIntegerValue())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean hasBlackjack()
    {
        return this.hasBlackjack;
    }

    public void setBlackjack(boolean hasBlackjack)
    {
        this.hasBlackjack = hasBlackjack;
    }

    public boolean hasBusted()
    {
        return this.hasBusted;
    }

    public void setBusted(boolean hasBusted)
    {
        this.hasBusted = hasBusted;
    }

    public boolean hasDoubledDown()
    {
        return this.hasDoubledDown;
    }

    public void setDoubledDown(boolean hasDoubledDown)
    {
        this.hasDoubledDown = hasDoubledDown;
    }

    public void reset()
    {
        hasBlackjack = false;
        hasBusted = false;
        hasDoubledDown = false;
        isInitiated = false;

        visualCardIndexes.clear();
    }
}