package com.elislibrand.pixelblackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Player
{
    private List<Deck> decks;

    private int chips = 50;
    private int bet;
    private int initialBet;
    private int winnings;

    private final int maxNumberOfSplitDecks = 6; // Must be an even number
    private int numberOfActiveDecks = 1;
    private int activeDeckIndex;

    private boolean hasPlacedBet = false;

    public Player()
    {
        decks = new ArrayList<Deck>();
    }

    public List<Deck> getDecks()
    {
        return decks;
    }

    public Deck getDeck(int index)
    {
        return decks.get(index);
    }

    public void addDeck(int index, Deck deck)
    {
        decks.add(index, deck);
    }

    public int getNumberOfDecks()
    {
        return decks.size();
    }

    public int getIndexOfDeck(Deck deck)
    {
        return decks.indexOf(deck);
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

    public int getMaxNumberOfSplitDecks()
    {
        return maxNumberOfSplitDecks;
    }

    public int getNumberOfActiveDecks()
    {
        return numberOfActiveDecks;
    }

    public void incrementNumberOfActiveDecks()
    {
        numberOfActiveDecks++;
    }

    public int getActiveDeckIndex()
    {
        return activeDeckIndex;
    }

    public void setActiveDeckIndex(int activeDeckIndex)
    {
        this.activeDeckIndex = activeDeckIndex;
    }

    public void incrementActiveDeckIndex()
    {
        activeDeckIndex++;
    }

    public void decrementActiveDeckIndex()
    {
        activeDeckIndex--;
    }

    public boolean isAnotherDeck()
    {
        if (activeDeckIndex == 0) return false;

        if (decks.get(activeDeckIndex - 1).isInitiated())
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
        Deck deck = decks.get(deckIndex);

        if (deck.getValueOfCards() == 21 && deck.getDeckSize() == 2)
        {
            deck.setBlackjack(true);
        }
    }

    public boolean hasBlackjackInDeck(int deckIndex)
    {
        return decks.get(deckIndex).hasBlackjack();
    }

    public boolean hasBlackjackInAllDecks()
    {
        for (Deck deck : decks)
        {
            if (deck.isInitiated())
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
        Deck deck = decks.get(deckIndex);

        if (deck.getValueOfCards() == 21 && deck.getDeckSize() > 2)
        {
            return true;
        }

        return false;
    }

    public boolean hasDoubledDownInDeck(int deckIndex)
    {
        return decks.get(deckIndex).hasDoubledDown();
    }

    public void checkForBustInDeck(int deckIndex)
    {
        Deck deck = decks.get(deckIndex);

        if (deck.getValueOfCards() > 21)
        {
            deck.setBusted(true);
        }
    }

    public boolean hasBustedInDeck(int deckIndex)
    {
        return decks.get(deckIndex).hasBusted();
    }

    public boolean hasBustedInAllDecks()
    {
        for (Deck deck : decks)
        {
            if (deck.isInitiated())
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
        return decks.get(deckIndex).getValueOfCards();
    }

    public int getSizeOfDeck(int deckIndex)
    {
        return decks.get(deckIndex).getDeckSize();
    }

    public void setDoubledDownToTrueInDeck(int deckIndex)
    {
        decks.get(deckIndex).setDoubledDown(true);
    }

    public boolean canDoubleDownDeck(int deckIndex)
    {
        return decks.get(deckIndex).canDoubleDown();
    }

    public boolean canSplitDeck(int deckIndex)
    {
        if (decks.get(deckIndex).canSplit() && getNumberOfActiveDecks() < maxNumberOfSplitDecks)
        {
            return true;
        }

        return false;
    }

    public void swapDecks(int indexOfFirstDeck, int indexOfSecondDeck) // hands
    {
        Collections.swap(decks, indexOfFirstDeck, indexOfSecondDeck);
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
        for (Deck deck : decks)
        {
            deck.reset();
        }

        activeDeckIndex = getMaxNumberOfSplitDecks() / 2 - 1;
        decks.get(activeDeckIndex).initiate();
        
        numberOfActiveDecks = 1;
        hasPlacedBet = false;
        winnings = 0;
    }
}