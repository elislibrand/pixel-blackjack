package com.elislibrand.pixelblackjack;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

public class Deck
{
    private List<Card> cards;

    public Deck()
    {
        cards = new ArrayList<Card>();
    }

    public void createFullDeck()
    {
        for (Suit cardSuit : Suit.values())
        {
            for (Value cardValue : Value.values())
            {
                Image cardImage = new ImageIcon(getClass().getResource("/assets/cards/" + cardValue.toString().toLowerCase() + cardSuit.toString().toLowerCase() + ".png")).getImage();

                cards.add(new Card(cardImage, cardSuit, cardValue));
            }
        }
    }

    public void shuffle()
    {   
        Collections.shuffle(cards);
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

    public int getNumberOfCards()
    {
        return cards.size();
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
}