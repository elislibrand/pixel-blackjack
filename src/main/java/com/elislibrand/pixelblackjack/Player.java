package com.elislibrand.pixelblackjack;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Player
{
    private List<Hand> hands;

    private int chips = 50;
    private int bet;
    private int initialBet;
    private int winnings;

    private final int maxNumberOfHands = 8; // Must be an even number
    private int numberOfActiveHands;
    private int currentHandIndex;

    private boolean hasPlacedBet = false;
    private boolean isBlackjack = false;

    public Player()
    {
        hands = new ArrayList<Hand>();
        numberOfActiveHands = 1;
    }

    public List<Hand> getHands()
    {
        return hands;
    }

    public Hand getHand(int index)
    {
        return hands.get(index);
    }

    public void addHand(Hand hand)
    {
        hands.add(hand);
    }

    public int getNumberOfHands()
    {
        return hands.size();
    }

    public int getIndexOfHand(Hand hand)
    {
        return hands.indexOf(hand);
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

    public int getNumberOfActiveHands()
    {
        return numberOfActiveHands;
    }

    public void incrementNumberOfActiveHands()
    {
        numberOfActiveHands++;
    }

    public int getCurrentHandIndex()
    {
        return currentHandIndex;
    }

    public void setCurrentHandIndex(int currentHandIndex)
    {
        this.currentHandIndex = currentHandIndex;
    }

    public void incrementCurrentHandIndex()
    {
        currentHandIndex++;
    }

    public void decrementCurrentHandIndex()
    {
        currentHandIndex--;
    }

    public boolean isAnotherHand()
    {
        if (currentHandIndex == 0) return false;

        if (hands.get(currentHandIndex - 1).isActive())
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

    public void checkForBlackjack()
    {
        if (numberOfActiveHands == 1)
        {
            Hand hand = hands.get(currentHandIndex);
        
            if (hand.getValueOfCards() == 21 && hand.getNumberOfCards() == 2)
            {
                isBlackjack = true;
            }
        }
    }

    public boolean shouldAutoStandInHand(int index)
    {
        Hand hand = hands.get(index);

        if (hand.getValueOfCards() == 21)
        {
            return true;
        }

        return false;
    }

    public boolean isDoubledDownInHand(int index)
    {
        return hands.get(index).isDoubledDown();
    }

    public void checkForAutoStandInHand(int index)
    {
        Hand hand = hands.get(index);

        if (hand.getValueOfCards() >= 21 || hand.isDoubledDown())
        {
            hand.setAutoStand(true);
        }
    }

    public boolean isAutoStandInHand(int index)
    {
        return hands.get(index).isAutoStand();
    }

    public boolean isBlackjackOrBustedInAllHands()
    {
        if (isBlackjack)
        {
            return true;
        }

        for (Hand hand : hands)
        {
            if (hand.isActive())
            {
                if (hand.getValueOfCards() <= 21)
                {
                    return false;
                }
            }
        }

        return true;
    }

    public int getValueOfCardsInHand(int index)
    {
        return hands.get(index).getValueOfCards();
    }

    public int getNumberOfCardsInHand(int index)
    {
        return hands.get(index).getNumberOfCards();
    }

    public void setDoubledDownToTrueInHand(int index)
    {
        hands.get(index).setDoubledDown(true);
    }

    public boolean canDoubleDownInHand(int index)
    {
        return hands.get(index).canDoubleDown();
    }

    public boolean canSplitHand(int index)
    {
        if (hands.get(index).canSplit() && getNumberOfActiveHands() < maxNumberOfHands)
        {
            return true;
        }

        return false;
    }

    public void swapHands(int indexOfFirstHand, int indexOfSecondHand)
    {
        Collections.swap(hands, indexOfFirstHand, indexOfSecondHand);
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
        for (Hand hand : hands)
        {
            hand.reset();
        }

        currentHandIndex = getMaxNumberOfHands() / 2 - 1;
        hands.get(currentHandIndex).setActive(true);
        
        numberOfActiveHands = 1;
        hasPlacedBet = false;
        isBlackjack = false;
        winnings = 0;
    }
}