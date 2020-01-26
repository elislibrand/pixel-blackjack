package com.elislibrand.pixelblackjack;

import java.awt.FontMetrics;

public class TextManager
{
    public int getCenteredText(String text, FontMetrics fontMetrics, int x)
    {
        return x - (fontMetrics.stringWidth(text) / 2);
    }

    public String getFormattedNumber(int number)
    {
        String string = Integer.toString(number);
        
        int numberOfDigits = string.length();
        int numberOfFirstLetters = numberOfDigits % 3;

        if (numberOfFirstLetters == 0)
        {
            return string.substring(numberOfFirstLetters, numberOfDigits).replaceAll("...", "$0 ");
        }

        return string.substring(0, numberOfFirstLetters) + " " + string.substring(numberOfFirstLetters, numberOfDigits).replaceAll("...", "$0 ");
    }
}