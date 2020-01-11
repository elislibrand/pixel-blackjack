package com.elislibrand.pixelblackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Player
{
    private List<Deck> hands;

    private int chips = 50;
    private int bet;
    private int initialBet;
    private int winnings;

    private final int maxNumberOfHands = 6; // Must be an even number
    private int numberOfActiveDecks = 1;
    private int currentDeckIndex;

    private boolean hasPlacedBet = false;

    public Player()
    {
        hands = new ArrayList<Deck>();
    }

    public List<Deck> getDecks()
    {
        return hands;
    }

    public Deck getDeck(int index)
    {
        return hands.get(index);
    }

    public void addDeck(int index, Deck deck)
    {
        hands.add(index, deck);
    }

    public int getNumberOfDecks()
    {
        return hands.size();
    }

    public int getIndexOfDeck(Deck deck)
    {
        return hands.indexOf(deck);
    }

    public int getChips()
    {
        return chips;
    }

    public void setChips(int chips)
    {
        this.chips = chips;
    }

    public void addChips(int chips)
    {
        this.chips += chips;
    }

    public int getBet()
    {
        return bet;
    }

    public void setBet(int bet)
    {
        this.bet = bet;
    }

    public int getInitialBet()
    {
        return initialBet;
    }
    
    public void increaseBet(int amount)
    {
        bet += amount;
    }

    public void decreaseBet(int amount)
    {
        bet -= amount;
    }

    public boolean hasEnoughMoneyToIncreaseBet()
    {
        if (bet < chips)
        {
            return true;
        }

        return false;
    }

    public boolean canDoubleBet()
    {
        if (initialBet <= chips)
        {
            return true;
        }
        
        return false;
    }

    public void doubleBet()
    {
        chips -= initialBet;
        bet += initialBet;
    }

    public void setBetToHalf()
    {
        bet = (int)(chips / 2);
    }

    public void setBetToMax()
    {
        bet = chips;
    }

    public void resetBet()
    {
        if (initialBet > chips)
        {
            bet = chips;
        }
        else
        {
            bet = initialBet;
        }
    }

    public int getWinnings()
    {
        return winnings;
    }

    public void setWinnings(int winnings)
    {
        this.winnings = winnings;
    }

    public int getMaxNumberOfHands()
    {
        return maxNumberOfHands;
    }

    public int getNumberOfActiveDecks()
    {
        return numberOfActiveDecks;
    }

    public void incrementNumberOfActiveDecks()
    {
        numberOfActiveDecks++;
    }

    public int getCurrentDeckIndex()
    {
        return currentDeckIndex;
    }

    public void setCurrentDeckIndex(int currentDeckIndex)
    {
        this.currentDeckIndex = currentDeckIndex;
    }

    public void incrementCurrentDeckIndex()
    {
        currentDeckIndex++;
    }

    public void decrementCurrentDeckIndex()
    {
        currentDeckIndex--;
    }

    public boolean isAnotherDeck()
    {
        if (currentDeckIndex == 0) return false;

        if (hands.get(currentDeckIndex - 1).isActive())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void checkForBlackjackInDeck(int deckIndex)
    {
        Deck deck = hands.get(deckIndex);

        if (deck.getValueOfCards() == 21 && deck.getDeckSize() == 2)
        {
            deck.setBlackjack(true);
        }
    }

    public boolean hasBlackjackInDeck(int deckIndex)
    {
        return hands.get(deckIndex).hasBlackjack();
    }

    public boolean hasBlackjackInAllDecks()
    {
        for (Deck deck : hands)
        {
            if (deck.isActive())
            {
                if (!deck.hasBlackjack())
                {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean checkForAutoStandInDeck(int deckIndex)
    {
        Deck deck = hands.get(deckIndex);

        if (deck.getValueOfCards() == 21 && deck.getDeckSize() > 2)
        {
            return true;
        }

        return false;
    }

    public boolean hasDoubledDownInDeck(int deckIndex)
    {
        return hands.get(deckIndex).hasDoubledDown();
    }

    public void checkForBustInDeck(int deckIndex)
    {
        Deck deck = hands.get(deckIndex);

        if (deck.getValueOfCards() > 21)
        {
            deck.setBusted(true);
        }
    }

    public boolean hasBustedInDeck(int deckIndex)
    {
        return hands.get(deckIndex).hasBusted();
    }

    public boolean hasBustedInAllDecks()
    {
        for (Deck deck : hands)
        {
            if (deck.isActive())
            {
                if (!deck.hasBusted())
                {
                    return false;
                }
            }
        }

        return true;
    }

    public int getValueOfCardsInDeck(int deckIndex)
    {
        return hands.get(deckIndex).getValueOfCards();
    }

    public int getSizeOfDeck(int deckIndex)
    {
        return hands.get(deckIndex).getDeckSize();
    }

    public void setDoubledDownToTrueInDeck(int deckIndex)
    {
        hands.get(deckIndex).setDoubledDown(true);
    }

    public boolean canDoubleDownDeck(int deckIndex)
    {
        return hands.get(deckIndex).canDoubleDown();
    }

    public boolean canSplitDeck(int deckIndex)
    {
        if (hands.get(deckIndex).canSplit() && getNumberOfActiveDecks() < maxNumberOfHands)
        {
            return true;
        }

        return false;
    }

    public void swapDecks(int indexOfFirstDeck, int indexOfSecondDeck) // hands
    {
        Collections.swap(hands, indexOfFirstDeck, indexOfSecondDeck);
    }

    public boolean hasPlacedBet()
    {
        return hasPlacedBet;
    }

    public void setPlacedBet(boolean hasPlacedBet)
    {
        this.hasPlacedBet = hasPlacedBet;
    }

    public void placeBet()
    {
        initialBet = bet;
        chips -= bet;
    }

    public void reset()
    {
        for (Deck deck : hands)
        {
            deck.reset();
        }

        currentDeckIndex = getMaxNumberOfHands() / 2 - 1;
        hands.get(currentDeckIndex).setActive(true);
        
        numberOfActiveDecks = 1;
        hasPlacedBet = false;
        winnings = 0;
    }
}