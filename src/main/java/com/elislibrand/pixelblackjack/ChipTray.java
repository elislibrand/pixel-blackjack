package com.elislibrand.pixelblackjack;

import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.ImageIcon;

public class ChipTray
{
    private Random random = new Random();

    private List<Chip> chips;
    private List<VisualChip> visualChips;

    private final Point leftChipStartingPos = new Point((24 * Screen.SCALE), (206 * Screen.SCALE));
    private final Point rightChipStartingPos = new Point((228 * Screen.SCALE), (206 * Screen.SCALE));
    private final Point chipOffset = new Point((23 * Screen.SCALE), (2 * Screen.SCALE));

    public ChipTray(int playerChips)
    {
        chips = new ArrayList<Chip>();
        visualChips = new ArrayList<VisualChip>();

        create();
    }

    private void create()
    {
        for (ChipValue chipValue : ChipValue.values())
        {
            Image topDownImage;
            Image[] images = new Image[11];

            for (int i = 0; i < images.length; i++)
            {
                images[i] = new ImageIcon(getClass().getResource("/assets/chips/" + chipValue.toString().toLowerCase() + "_chip_" + (i + 1) + ".png")).getImage();
            }

            topDownImage = new ImageIcon(getClass().getResource("/assets/chips/" + chipValue.toString().toLowerCase() + "_chip_top_down.png")).getImage();

            chips.add(new Chip(topDownImage, images, chipValue.getValue()));
        }
    }

    public void sort(int totalChips)
    {
        visualChips.clear();

        int chipPosX;

        boolean addToRightChipTray;

        for (int i = (chips.size() - 1); i >= 0; i--)
        {
            Chip chip = chips.get(i);

            int chipValue = chip.getValue();
            int quantity = (totalChips - (totalChips % chipValue)) / chipValue;
            totalChips -= quantity * chipValue;

            chip.setQuantity(quantity);

            addToRightChipTray = (i >= 5) ? true : false;
            chipPosX = addToRightChipTray ? rightChipStartingPos.x + (chipOffset.x * i) : leftChipStartingPos.x + (chipOffset.x * i);

            createVisualChips(chip, 0, quantity, chipPosX);
        }
    }

    public void add(int totalChips)
    {
        int chipPosX;

        boolean addToRightChipTray;

        for (int i = (chips.size() - 1); i >= 0; i--)
        {
            Chip chip = chips.get(i);

            int chipValue = chip.getValue();
            int quantity = (totalChips - (totalChips % chipValue)) / chipValue;
            int initialQuantity = chip.getQuantity();
            int newQuantity = initialQuantity + quantity;

            totalChips -= quantity * chipValue;

            chip.addQuantity(quantity);
            
            addToRightChipTray = (i >= 5) ? true : false;
            chipPosX = addToRightChipTray ? rightChipStartingPos.x + (chipOffset.x * i) : leftChipStartingPos.x + (chipOffset.x * i);

            createVisualChips(chip, initialQuantity, newQuantity, chipPosX);
        }
    }

    public void remove(int totalChips)
    {
        
    }

    private void createVisualChips(Chip chip, int initialQuantity, int totalQuantity, int x)
    {
        if (totalQuantity == 0) return;

        int imageIndex = random.nextInt(((chip.getNumberOfImages() - 1) - 0) + 1) + 0;
        int prevImageIndex = imageIndex;

        int y = leftChipStartingPos.y /* <- CHANGE */ + (initialQuantity * chipOffset.y);

        for (int i = initialQuantity; i < totalQuantity; i++)
        {
            do
            {
                imageIndex = random.nextInt(((chip.getNumberOfImages() - 1) - 0) + 1) + 0;
            }
            while (imageIndex == prevImageIndex);

            prevImageIndex = imageIndex;

            visualChips.add(new VisualChip(chip.getImage(imageIndex), x, y));

            y += chipOffset.y;
        }
    }

    public Image getTopDownImage(int playerBet)
    {
        return chips.get(getChipIndex(playerBet)).getTopDownImage();
    }

    private int getChipIndex(int playerBet)
    {
        int chipIndex = 0;

        for (int i = (chips.size() - 1); i >= 0; i--)
        {
            Chip chip = chips.get(i);

            if (playerBet - chip.getValue() >= 0)
            {
                chipIndex = i;

                break;
            }
        }

        return chipIndex;
    }

    public List<VisualChip> getVisualChips()
    {
        return visualChips;
    }
}