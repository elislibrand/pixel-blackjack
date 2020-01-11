package com.elislibrand.pixelblackjack;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.awt.Font;
import java.awt.FontFormatException;

public class Screen
{
    private final static GraphicsEnvironment ENVIRONMENT = GraphicsEnvironment.getLocalGraphicsEnvironment();
    private final static GraphicsDevice DEVICE = ENVIRONMENT.getDefaultScreenDevice();

    public final static Dimension RESOLUTION;
    public final static int WIDTH;
    public final static int HEIGHT;
    public final static int REFRESH_RATE;
    public final static int BIT_DEPTH;
    public final static int SCALE;
    public final static Font FONT;

    static
    {
        RESOLUTION = new Dimension(DEVICE.getDisplayMode().getWidth(), DEVICE.getDisplayMode().getHeight());
        REFRESH_RATE = DEVICE.getDisplayMode().getRefreshRate(); // equals 0 if not found
        BIT_DEPTH = DEVICE.getDisplayMode().getBitDepth();
        SCALE = getScale();
        WIDTH = 480 * SCALE;
        HEIGHT = 270 * SCALE;
        FONT = getFont();

        System.out.println(FONT);
    }

    private final static int getScale()
    {
        int scaleWidth = (int)(RESOLUTION.width / 480);
        int scaleHeight = (int)(RESOLUTION.height / 270);

        int scale = (scaleWidth < scaleHeight) ? scaleWidth : scaleHeight;
        
        return scale;
    }

    private final static Font getFont()
    {
        try
        {
            Font font = Font.createFont(Font.TRUETYPE_FONT, Main.class.getResourceAsStream("/assets/fonts/PixelBlackjack.ttf"));
            ENVIRONMENT.registerFont(font);

            font = new Font("PixelBlackjack", Font.PLAIN, 6 * SCALE);

            return font;
        }
        catch (FontFormatException e)
        {
            System.out.println("FontFormatException when loading font!");
        }
        catch (IOException e)
        {
            System.out.println("IOException when loading font!");
        }

        return new Font("Arial", Font.PLAIN, 6 * SCALE);
    }
}