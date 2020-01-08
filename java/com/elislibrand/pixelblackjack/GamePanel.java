package com.elislibrand.pixelblackjack;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable
{
    private static final long serialVersionUID = 1L;

    private GameState gameState;
    private Animator animator = new Animator();
    private AudioManager audioManager = new AudioManager();
    
    private Thread thread;

    private List<VisualCard> visualCards = new ArrayList<VisualCard>();
    private List<VisualChip> visualChips;

    private final int scale = Screen.SCALE;

    private final Player player = new Player();

    private final Dimension screenSize = new Dimension(Screen.WIDTH, Screen.HEIGHT);
    private final Dimension cardSize = new Dimension(33 * scale, 49 * scale);
    private final Dimension cardShadowSize = new Dimension(35 * scale, 51 * scale);
    private final Dimension chipSize = new Dimension(21 * scale, 21 * scale);
    private final Dimension chipInChipTraySize = new Dimension(21 * scale, 2 * scale);
    private final Dimension chipShadowSize = new Dimension(23 * scale, 23 * scale);
    private final Dimension betSquareSize = new Dimension(44 * scale, 58 * scale);
    private final Dimension chipTraySize = new Dimension(121 * scale, 58 * scale);
    private final Dimension chipTrayShadowSize = new Dimension(123 * scale, 60 * scale);

    private final Point playerCardStartingPos = new Point((screenSize.width / 2) - ((int)((cardSize.width / scale) / 2) * scale) - (1 * scale), // There is room for a number under the card, displaying the hand's value
                                                          screenSize.height - cardSize.height - betSquareSize.height - (21 * scale));           // (3 * scale) pixels margin top and bottom from the number ?
    private final Point dealerCardStartingPos = new Point((screenSize.width / 2) + (2 * scale), 10 * scale);
    private final Point betSquarePos = new Point((screenSize.width / 2) - (betSquareSize.width / 2), screenSize.height - (10 * scale) - betSquareSize.height);
    private final Point leftChipTrayPos = new Point(20 * scale, screenSize.height - (10 * scale) - betSquareSize.height);
    private final Point rightChipTrayPos = new Point(screenSize.width - (20 * scale) - chipTraySize.width, screenSize.height - (10 * scale) - betSquareSize.height);
    private final Point leftChipTrayShadowPos = new Point(leftChipTrayPos.x - scale, leftChipTrayPos.y - scale);
    private final Point rightChipTrayShadowPos = new Point(rightChipTrayPos.x - scale, rightChipTrayPos.y - scale);
    private final Point chipStartingPos = new Point((screenSize.width / 2) - (chipSize.width / 2) - (6 * scale),
                                                    screenSize.height - (betSquareSize.height / 2) - (chipSize.height / 2) - ((1 * scale) - 1) + (2 * scale)); // Top-left
    private final Point chipShadowStartingPos = new Point(chipStartingPos.x - scale, chipStartingPos.y - scale);
    private final Point cardHolderPos = new Point(screenSize.width - cardSize.width, -cardSize.height);
    private final Point playerCardOffset = new Point(8 * scale, -(10 * scale));
    private final Point dealerCardOffset = new Point(-(cardSize.width + (4 * scale)), 0);
    private final Point chipSplitOffset = new Point((2 * cardSize.width) - (3 * scale), 0);
    private final Point chipDoubleDownOffset = new Point(chipSize.width + (3 * scale), 0);

    private final int splitOffsetX = (2 * cardSize.width) + (1 * scale);
    private final int splitMarginX = (screenSize.width - (splitOffsetX * (player.getMaxNumberOfSplitDecks() - 1) + cardSize.width)) / 2;
    
    private final int minBet = 1;
    private final int maxBet = 100000;

    private int numberOfDecks = 6;
    private int splitStageIndex = 0;

    private boolean canClearBoard = false;

    private boolean debugMode = false;

    private Image cardFaceDown;
    private Image cardShadow;
    private Image chipShadow;
    private Image betSquare;
    private Image chipTrayImg;
    private Image chipTrayShadowImg;
    private Image chipInBetSquare;
    
    private final ChipTray chipTray = new ChipTray(player.getChips());;

    private final Deck playingDeck = new Deck();
    private final Deck usedDeck = new Deck();
    private final Deck dealerDeck = new Deck();

    public GamePanel()
    {
        initializeBoard();
    }

    private final void initializeBoard()
    {
        System.out.println("\nScreen Resolution: " + screenSize.width + "x" + screenSize.height +
                           "\nScreen Refresh Rate: " + Screen.REFRESH_RATE +
                           "\nScreen Bit Depth: " + Screen.BIT_DEPTH);

        addKeyListener(new TAdapter());
        setOpaque(false);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        setPreferredSize(new Dimension(Screen.WIDTH, Screen.HEIGHT));
        
        loadImages();
        initializeGame();
    }

    private final void loadImages()
    {
        cardFaceDown = new ImageIcon(getClass().getResource("/assets/cards/facedown.png")).getImage();
        cardShadow = new ImageIcon(getClass().getResource("/assets/cards/shadow.png")).getImage();
        chipShadow = new ImageIcon(getClass().getResource("/assets/chips/shadow.png")).getImage();
        betSquare = new ImageIcon(getClass().getResource("/assets/props/bet_square.png")).getImage();
        chipTrayImg = new ImageIcon(getClass().getResource("/assets/props/chip_tray.png")).getImage();
        chipTrayShadowImg = new ImageIcon(getClass().getResource("/assets/props/chip_tray_shadow.png")).getImage();
    }

    private final void initializeGame()
    {
        player.setActiveDeckIndex((player.getMaxNumberOfSplitDecks() / 2) - 1);

        player.setBet(minBet);

        createAllPlayerDecks();
        initiateDecks();
        createPlayingDeck();
        sortChipTray();

        gameState = GameState.SHUFFLE_DECK;
    }

    private final void createAllPlayerDecks()
    {
        for (int i = 0; i < player.getMaxNumberOfSplitDecks(); i++)
        {
            player.addDeck(i, new Deck());
        }
    }

    private final void initiateDecks()
    {
        dealerDeck.initiate();
        player.getDeck(player.getActiveDeckIndex()).initiate();
    }

    private final void createPlayingDeck()
    {
        for (int i = 0; i < numberOfDecks; i++)
        {
            playingDeck.createFullDeck();
        }
    }

    private void placeBet()
    {
        audioManager.play(Audio.CHIPS_SINGLE_DROP);

        chipInBetSquare = chipTray.getTopDownImage(player.getBet());
        
        player.placeBet();
        player.setPlacedBet(true);

        sortChipTray(); //removeFromChipTray

        gameState = GameState.RECEIVE_CARDS;
    }

    private void receiveCards()
    {
        if (!animator.isPlaying())
        {
            switch (visualCards.size())
            {
                case 0:
                    drawCard(player.getDeck(player.getActiveDeckIndex()), playerCardStartingPos, false, false);
                    break;
                case 1:
                    drawCard(dealerDeck, dealerCardStartingPos, true, false);
                    break;
                case 2:
                    drawCard(player.getDeck(player.getActiveDeckIndex()), getNextPlayerCardPosition(playerCardStartingPos, player.getActiveDeckIndex()), false, false);
                    break;
                case 3:
                    drawCard(dealerDeck, new Point(dealerCardStartingPos.x + dealerCardOffset.x, dealerCardStartingPos.y + dealerCardOffset.y), false, false);
                    break;
                case 4:
                    checkForDealerBlackjack();
                    break;
                default:
                    break;
            }
        }
    }

    private Point getNextPlayerCardPosition(Point startingPos, int deckIndex)
    {
        return new Point(startingPos.x + (playerCardOffset.x * player.getSizeOfDeck(deckIndex)), startingPos.y + (playerCardOffset.y * player.getSizeOfDeck(deckIndex)));
    }

    private Point getNextSplitPlayerCardPosition(int deckIndex)
    {
        return new Point(splitMarginX + (splitOffsetX * deckIndex) + (playerCardOffset.x * player.getSizeOfDeck(deckIndex)), playerCardStartingPos.y + (playerCardOffset.y * player.getSizeOfDeck(deckIndex)));
    }

    private Point getNextDealerCardPosition()
    {
        return new Point(dealerCardStartingPos.x + (dealerCardOffset.x * dealerDeck.getDeckSize()), dealerCardStartingPos.y + (dealerCardOffset.y * dealerDeck.getDeckSize()));
    }

    private void drawCard(Deck destinationDeck, Point destinationPos, boolean faceDown, boolean isRotated)
    {
        destinationDeck.drawCardFromDeck(playingDeck);
        
        Card drawnCard = destinationDeck.getCard(destinationDeck.getDeckSize() - 1);
        Image cardImage = drawnCard.getImage();

        if (faceDown)
        {
            cardImage = cardFaceDown;
        }

        visualCards.add(new VisualCard(cardImage, destinationPos.x, destinationPos.y, isRotated));

        animator.start(cardHolderPos, destinationPos, visualCards.size() - 1);
        audioManager.play(Audio.CARD_DRAW);
    }

    private void checkForDealerBlackjack()
    {
        if (hasBlackjackDealer())
        {
            gameState = GameState.DEALER_DRAW;
        }
        else
        {
            gameState = GameState.PLAYER_CHOOSE;
        }
    }

    private boolean hasBlackjackDealer()
    {        
        if (dealerDeck.getValueOfCards() == 21)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    private void checkForPlayerBlackjack(int deckIndex)
    {
        player.checkForBlackjackInDeck(deckIndex);

        if (player.hasBlackjackInDeck(deckIndex) || player.checkForAutoStandInDeck(deckIndex))
        {
            if (player.isAnotherDeck())
            {    
                goToNextDeck();
            }
            else
            {
                gameState = GameState.DEALER_DRAW;
            }
        }
    }

    private void hit()
    {
        if (player.getNumberOfActiveDecks() > 1)
        {
            drawCard(player.getDeck(player.getActiveDeckIndex()), getNextSplitPlayerCardPosition(player.getActiveDeckIndex()), false, false);
        }
        else
        {
            drawCard(player.getDeck(player.getActiveDeckIndex()), getNextPlayerCardPosition(playerCardStartingPos, player.getActiveDeckIndex()), false, false);
        }

        checkForPlayerBust(player.getActiveDeckIndex());
    }

    private void stand()
    {
        if (player.isAnotherDeck())
        {    
            goToNextDeck();
        }
        else
        {
            gameState = GameState.DEALER_DRAW;
        }
    }

    private void doubleDown()
    {
        if (canDoubleDown())
        {
            doubleBet();
            player.setDoubledDownToTrueInDeck(player.getActiveDeckIndex());
            
            if (player.getNumberOfActiveDecks() > 1)
            {
                drawCard(player.getDeck(player.getActiveDeckIndex()), getNextSplitPlayerCardPosition(player.getActiveDeckIndex()), false, true);
            }
            else
            {
                drawCard(player.getDeck(player.getActiveDeckIndex()), getNextPlayerCardPosition(playerCardStartingPos, player.getActiveDeckIndex()), false, true);
            }

            checkForPlayerBust(player.getActiveDeckIndex());
        }
        else
        {
            gameState = GameState.PLAYER_CHOOSE;
        }
    }

    private void split()
    {
        if (!animator.isPlaying())
        {
            switch (splitStageIndex)
            {
                case 0:
                    initiateSplit();
                    moveSplitCard(player.getActiveDeckIndex(), 2);
                    break;
                case 1:
                    moveSplitCard(player.getActiveDeckIndex() - 1, 0);
                    break;
                case 2:
                    drawCard(player.getDeck(player.getActiveDeckIndex()), getNextSplitPlayerCardPosition(player.getActiveDeckIndex()), false, false);
                    break;
                case 3:
                    drawCard(player.getDeck(player.getActiveDeckIndex() - 1), getNextSplitPlayerCardPosition(player.getActiveDeckIndex() - 1), false, false);
                    break;
                case 4:
                    gameState = GameState.PLAYER_CHOOSE;
                    break;
                default:
                    break;
            }

            splitStageIndex++;
        }
    }

    private void initiateSplit() // Needs to be changed for multiple splits
    {
        doubleBet();

        player.getDeck(player.getActiveDeckIndex() + 1).initiate();
        player.incrementNumberOfActiveDecks();
        player.getDeck(player.getActiveDeckIndex()).giveLastCardToDeck(player.getDeck((player.getActiveDeckIndex() + 1)));

        player.incrementActiveDeckIndex();
    }

    private void moveSplitCard(int deckIndex, int visualCardIndex)
    {
        VisualCard visualCard = visualCards.get(visualCardIndex);

        Point startingPos = new Point(visualCard.getXPos(), visualCard.getYPos());
        Point destinationPos = new Point(splitMarginX + (splitOffsetX * deckIndex), playerCardStartingPos.y);

        animator.start(startingPos, destinationPos, visualCardIndex);
        audioManager.play(Audio.CARD_SLIDE);
    }

    private boolean canDoubleDown()
    {
        if (player.canDoubleBet() && player.canDoubleDownDeck(player.getActiveDeckIndex()))
        {
            return true;
        }
        
        return false;
    }

    private boolean canSplit()
    {
        if (player.canDoubleBet() && player.canSplitDeck(player.getActiveDeckIndex()))
        {
            return true;
        }
        
        return false;
    }

    private void doubleBet()
    {
        player.doubleBet();
        
        sortChipTray();
    }

    private void goToNextDeck()
    {
        player.decrementActiveDeckIndex();
        gameState = GameState.PLAYER_CHOOSE;
    }

    private void checkForPlayerBust(int index)
    {
        player.checkForBustInDeck(index);

        if (player.hasBustedInDeck(index))
        {
            if (player.isAnotherDeck())
            {
                goToNextDeck();
            }
            else
            {
                gameState = GameState.DEALER_DRAW;
            }
        }
        else
        {
            if (gameState == GameState.PLAYER_DOUBLE_DOWN)
            {
                if (player.isAnotherDeck())
                {
                    goToNextDeck();
                }
                else
                {
                    gameState = GameState.DEALER_DRAW;
                }
            }
            else
            {
                gameState = GameState.PLAYER_CHOOSE;
            }
        }
    }

    private void dealerDraw()
    {
        if (!animator.isPlaying())
        {
            revealFaceDownCard();
            
            if (dealerDeck.getValueOfCards() < 17 && !player.hasBustedInAllDecks() && !player.hasBlackjackInAllDecks())
            {
                drawCard(dealerDeck, getNextDealerCardPosition(), false, false);
            }
            else
            {
                endRound();
            }
        }
    }

    private void revealFaceDownCard()
    {
        VisualCard visualCardWithFaceDownImage = visualCards.get(1);
        visualCardWithFaceDownImage.setImage(dealerDeck.getCard(0).getImage());
    }

    private void endRound()
    {
        determineWinner();
        addToChipTray(player.getWinnings());
        
        System.out.println("\nInitial Player Chips: " + (player.getChips() - player.getWinnings() + player.getBet()) + "\nInitial Player Bet: " + player.getInitialBet() + "\nPlayer Bet: " + player.getBet() + "\nPlayer Chips After Bet: " + (player.getChips() - player.getWinnings()) + "\nPlayer Winnings: " + player.getWinnings() + "\nPlayer Chips: " + player.getChips());

        gameState = GameState.CLEAR_BOARD;
    }

    private void determineWinner()
    {
        int winnings = 0;

        for (int i = 0; i < player.getMaxNumberOfSplitDecks(); i++)
        {
            if (player.getDeck(i).isInitiated())
            {
                if (player.hasBustedInDeck(i))
                {
                    winnings += 0;
                }
                else if (dealerDeck.getValueOfCards() > 21)
                {
                    if (player.hasBlackjackInDeck(i))
                    {
                        winnings += (int)(player.getInitialBet() * 2.5);
                    }
                    else if (player.hasDoubledDownInDeck(i))
                    {
                        winnings += (player.getInitialBet() * 2) * 2;
                    }
                    else
                    {
                        winnings += player.getInitialBet() * 2;
                    }
                }
                else if (dealerDeck.getValueOfCards() > player.getValueOfCardsInDeck(i))
                {
                    winnings += 0;
                }
                else if (player.getValueOfCardsInDeck(i) == dealerDeck.getValueOfCards())
                {
                    if (player.hasDoubledDownInDeck(i))
                    {
                        winnings += player.getInitialBet() * 2;
                    }
                    else
                    {
                        winnings += player.getInitialBet();
                    }
                }
                else if (player.getValueOfCardsInDeck(i) > dealerDeck.getValueOfCards())
                {
                    if (player.hasBlackjackInDeck(i))
                    {
                        winnings += (int)(player.getInitialBet() * 2.5);
                    }
                    else if (player.hasDoubledDownInDeck(i))
                    {
                        winnings += (player.getInitialBet() * 2) * 2;
                    }
                    else
                    {
                        winnings += player.getInitialBet() * 2;
                    }
                }
                else
                {
                    winnings += 0;
                }
            }
        }

        player.addChips(winnings);
        player.setWinnings(winnings);
    }

    private void clearBoard()
    {
        if (canClearBoard)
        {
            clearCards();
            reset();
            checkForShuffle();
        }
    }
    
    private void clearCards()
    {
        for (Deck deck : player.getDecks())
        {
            deck.moveAllToDeck(usedDeck);
        }

        dealerDeck.moveAllToDeck(usedDeck);
        visualCards.clear();
    }

    private void checkForShuffle()
    {
        if (playingDeck.getDeckSize() < 52)
        {
            gameState = GameState.SHUFFLE_DECK;
        }
        else
        {
            gameState = GameState.PLAYER_BET;
        }
    }

    private void shuffleDeck()
    {
        System.out.println("\nShuffling!");
        
        usedDeck.moveAllToDeck(playingDeck);
        playingDeck.shuffle();

        gameState = GameState.PLAYER_BET;
    }

    private void sortChipTray()
    {
        chipTray.sort(player.getChips());
        visualChips = chipTray.getVisualChips();
    }

    private void addToChipTray(int valueOfChipsToAdd)
    {
        chipTray.add(valueOfChipsToAdd);
        visualChips = chipTray.getVisualChips();
    }

    /* private void removeFromChipTray(int valueOfChipsToRemove)
    {
        chipTray.remove(valueOfChipsToRemove);
        visualChips = chipTray.getVisualChips();
    } */

    private void reset()
    {
        adjustPlayerBet();
        player.reset();
        
        canClearBoard = false;
    }

    private void adjustPlayerBet()
    {
        if (player.getChips() >= minBet)
        {
            player.resetBet();
        }
        else
        {
            System.exit(1);
        }
    }

    // Called after the JPanel has been added to the JFrame component (initialize)
    @Override
    public void addNotify()
    {
        super.addNotify();

        thread = new Thread(this);
        thread.start();
    }

    private void gameLoop()
    {
        switch (gameState)
        {
            case PLAYER_BET:
                break;
            case PLACE_BET:
                placeBet();
                break;
            case RECEIVE_CARDS:
                receiveCards();
                break;
            case PLAYER_CHOOSE:
                checkForPlayerBlackjack(player.getActiveDeckIndex());
                break;
            case PLAYER_HIT:
                hit();
                break;
            case PLAYER_STAND:
                stand();
                break;
            case PLAYER_DOUBLE_DOWN:
                doubleDown();
                break;
            case PLAYER_SPLIT:
                split();
                break;
            case DEALER_DRAW:
                dealerDraw();
                break;
            case CLEAR_BOARD:
                clearBoard();
                break;
            case SHUFFLE_DECK:
                shuffleDeck();
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        draw(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void draw(Graphics g)
    {
        Graphics2D g2d = (Graphics2D)g;

        if (debugMode)
        {
            g.setColor(Color.WHITE);
            g.drawString("Pixel Blackjack v2.1.1 [major restructuring and cleaning up]", 12, 24);
            g.drawString("Player chips: " + player.getChips(), 12, 48);
            g.drawString("Player bet: " + player.getBet(), 12, 72);
            g.drawString("Player winnings: " + player.getWinnings(), 12, 96);
        
            for (int i = 0; i < player.getMaxNumberOfSplitDecks(); i++)
            {
                g2d.drawRect(splitMarginX + (i * splitOffsetX), playerCardStartingPos.y, cardSize.width, cardSize.height);
            }
        }

        g2d.drawImage(betSquare, betSquarePos.x, betSquarePos.y, betSquareSize.width, betSquareSize.height, null);

        g2d.drawImage(chipTrayShadowImg, leftChipTrayShadowPos.x, leftChipTrayShadowPos.y, chipTrayShadowSize.width, chipTrayShadowSize.height, null);
        g2d.drawImage(chipTrayImg, leftChipTrayPos.x, leftChipTrayPos.y, chipTraySize.width, chipTraySize.height, null);

        g2d.drawImage(chipTrayShadowImg, rightChipTrayShadowPos.x, rightChipTrayShadowPos.y, chipTrayShadowSize.width, chipTrayShadowSize.height, null);
        g2d.drawImage(chipTrayImg, rightChipTrayPos.x, rightChipTrayPos.y, chipTraySize.width, chipTraySize.height, null);

        if (player.hasPlacedBet())
        {
            for (int i = 0; i < (player.getNumberOfDecks()); i++) // CHANGE CHIP POSITIONS BELOW
            {
                if (player.getDeck(i).isInitiated())
                {
                    if (player.hasDoubledDownInDeck(i))
                    {
                        g2d.drawImage(chipShadow, chipShadowStartingPos.x + chipDoubleDownOffset.x + (chipSplitOffset.x * i), chipShadowStartingPos.y, chipShadowSize.width, chipShadowSize.height, null);
                        g2d.drawImage(chipInBetSquare, chipStartingPos.x + chipDoubleDownOffset.x + (chipSplitOffset.x * i), chipStartingPos.y, chipSize.width, chipSize.height, null);
                    }
                    
                    g2d.drawImage(chipShadow, chipShadowStartingPos.x + (chipSplitOffset.x * i), chipShadowStartingPos.y, chipShadowSize.width, chipShadowSize.height, null);
                    g2d.drawImage(chipInBetSquare, chipStartingPos.x + (chipSplitOffset.x * i), chipStartingPos.y, chipSize.width, chipSize.height, null);
                }
            }
        }

        for (VisualCard visualCard : visualCards)
        {   
            if (visualCard.isRotated())
            {
                AffineTransform rotatedCard = AffineTransform.getTranslateInstance(visualCard.getXPos() + cardSize.height, visualCard.getYPos());
                AffineTransform rotatedShadow = AffineTransform.getTranslateInstance(visualCard.getXPos() + cardSize.height + scale, visualCard.getYPos() - scale);
                rotatedCard.rotate(Math.PI / 2);
                rotatedShadow.rotate(Math.PI / 2);
                rotatedCard.scale(scale, scale);
                rotatedShadow.scale(scale, scale);

                g2d.drawImage(cardShadow, rotatedShadow, null);
                g2d.drawImage(visualCard.getImage(), rotatedCard, null);
            }
            else
            {
                g2d.drawImage(cardShadow, visualCard.getXPos() - scale, visualCard.getYPos() - scale, cardShadowSize.width, cardShadowSize.height, null);
                g2d.drawImage(visualCard.getImage(), visualCard.getXPos(), visualCard.getYPos(), cardSize.width, cardSize.height, null);
            }
        }

        for (VisualChip visualChip : visualChips)
        {
            g2d.drawImage(visualChip.getImage(), visualChip.getXPos(), visualChip.getYPos(), chipInChipTraySize.width, chipInChipTraySize.height, null);
        }
    }

    @Override
    public void run()
    {
        long timeBefore, timeDifference, sleep, delay;

        timeBefore = System.currentTimeMillis();
        delay = 1000 / Screen.REFRESH_RATE;

        while (true)
        {
            gameLoop();
            animator.play();
            repaint();

            timeDifference = System.currentTimeMillis() - timeBefore;
            sleep = delay - timeDifference;

            if (sleep < 0)
            {
                sleep = 2;
            }

            try
            {
                Thread.sleep(sleep);
            }
            catch (InterruptedException e)
            {
                String msg = String.format("\nThread interrupted: %s", e.getMessage());
                System.out.println(msg);
            }

            timeBefore = System.currentTimeMillis();
        }
    }

    private class TAdapter extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            int key = e.getKeyCode();

            if (!animator.isPlaying())
            {
                switch (key)
                {
                    case KeyEvent.VK_ENTER:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            gameState = GameState.PLACE_BET;
                        }

                        if (gameState == GameState.PLAYER_CHOOSE)
                        {
                            gameState = GameState.PLAYER_HIT;
                        }

                        break;
                    case KeyEvent.VK_SPACE:
                        if (gameState == GameState.PLAYER_CHOOSE)
                        {
                            gameState = GameState.PLAYER_STAND;
                        }

                        if (gameState == GameState.CLEAR_BOARD)
                        {
                            canClearBoard = true;
                        }

                        break;
                    case KeyEvent.VK_TAB:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            player.setBetToMax();

                            if (player.getBet() > maxBet)
                            {
                                player.setBet(maxBet);
                            }
                        }

                        if (gameState == GameState.PLAYER_CHOOSE)
                        {
                            gameState = GameState.PLAYER_DOUBLE_DOWN;
                        }

                        break;
                    case KeyEvent.VK_SHIFT:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            if (player.getChips() >= (minBet * 2))
                            {
                                player.setBetToHalf();
                            }
                        }

                        break;
                    case KeyEvent.VK_E:
                        if (gameState == GameState.PLAYER_CHOOSE && canSplit())
                        {
                            splitStageIndex = 0;
                            gameState = GameState.PLAYER_SPLIT;
                        }

                        break;
                    case KeyEvent.VK_UP:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            if (player.getBet() < maxBet && player.hasEnoughMoneyToIncreaseBet())
                            {
                                player.increaseBet(1);
                            }
                            else
                            {
                                if (!player.hasEnoughMoneyToIncreaseBet())
                                {
                                    player.setBet(player.getChips());
                                }
                                else
                                {
                                    player.setBet(maxBet);
                                }
                            }
                        }

                        break;
                    case KeyEvent.VK_DOWN:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            if (player.getBet() > minBet)
                            {
                                player.decreaseBet(1);
                            }
                            else
                            {
                                player.setBet(minBet);
                            }
                        }

                        break;
                    case KeyEvent.VK_D:
                        debugMode = !debugMode;

                        break;
                    case KeyEvent.VK_ESCAPE:
                        if (gameState == GameState.PLAYER_BET)
                        {
                            System.exit(1);
                        }

                        break;
                    default:
                        break;
                }
            }
        }
    }

    private class Animator
    {
        private boolean isPlaying = false;

        private Point startingPos;
        private Point destinationPos;
        private Point currentPos;
        private double newPosX;
        private double newPosY;

        private int indexInArrayList;

        private int count;
        private double progress;
        private double durationInSeconds = 0.435;

        public boolean isPlaying()
        {
            return isPlaying;
        }

        public void play()
        {
            if (isPlaying)
            {
                if (progress < 1)
                {
                    count++;
                    progress = count / (durationInSeconds * Screen.REFRESH_RATE);
                }
                
                if (!isCloseToDestination())
                {
                    newPosX = easeInOut((float)(progress * durationInSeconds), startingPos.x, getDistance().x, (float)durationInSeconds);
                    newPosY = easeInOut((float)(progress * durationInSeconds), startingPos.y, getDistance().y, (float)durationInSeconds);
                
                    currentPos.setLocation((int)newPosX, (int)newPosY);

                    visualCards.get(indexInArrayList).setXPos(currentPos.x);
                    visualCards.get(indexInArrayList).setYPos(currentPos.y);
                }
                else
                {
                    currentPos.setLocation(destinationPos);

                    isPlaying = false;
                }
            }
        }

        public void start(Point startingPos, Point destinationPos, int indexInArrayList)
        {
            count = 0;
            progress = 0;

            this.startingPos = new Point(startingPos.x, startingPos.y);
            this.destinationPos = new Point(destinationPos.x, destinationPos.y); // destinationPos !!
            currentPos = new Point(startingPos.x, startingPos.y);

            this.indexInArrayList = indexInArrayList;

            isPlaying = true;
        }

        private float easeInOut(float timeElapsedInSeconds, float startingPos, float distance, float durationInSeconds)
        {
            if ((timeElapsedInSeconds /= (durationInSeconds / 2)) < 1)
            {
                return (distance / 2) * timeElapsedInSeconds * timeElapsedInSeconds * timeElapsedInSeconds + startingPos;
            }
            
            return (distance / 2) * ((timeElapsedInSeconds -= 2) * timeElapsedInSeconds * timeElapsedInSeconds + 2) + startingPos;
        }

        private boolean isCloseToDestination()
        {
            return progress >= 1;
        }

        private Point getDistance()
        {
            return new Point(destinationPos.x - startingPos.x, destinationPos.y - startingPos.y);
        }
    }
}