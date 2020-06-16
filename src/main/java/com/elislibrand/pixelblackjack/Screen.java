package com.elislibrand.pixelblackjack;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.IOException;

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
    public final static Cursor DEFAULT_CURSOR;
    public final static Cursor HIDDEN_CURSOR;

    static
    {
        RESOLUTION = new Dimension(DEVICE.getDisplayMode().getWidth(), DEVICE.getDisplayMode().getHeight());
        REFRESH_RATE = getRefreshRate();
        BIT_DEPTH = DEVICE.getDisplayMode().getBitDepth();
        SCALE = getScale();
        WIDTH = 640 * SCALE;
        HEIGHT = 360 * SCALE;
        FONT = getFont();
        DEFAULT_CURSOR = getDefaultCursor();
        HIDDEN_CURSOR = getHiddenCursor();
    }

    private final static int getRefreshRate()
    {
        int refreshRate = DEVICE.getDisplayMode().getRefreshRate();

        if (refreshRate <= 0)
        {
            refreshRate = 60;
        }

        return refreshRate;
    }

    private final static int getScale()
    {
        int scaleWidth = (int)(RESOLUTION.width / 640);
        int scaleHeight = (int)(RESOLUTION.height / 360);

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

    public final static Font getScaledFont(int scalingFactor)
    {
        return new Font("PixelBlackjack", Font.PLAIN, (6 * SCALE) * scalingFactor);
    }

    private final static Cursor getDefaultCursor()
    {
        return Cursor.getDefaultCursor();
    }

    private final static Cursor getHiddenCursor()
    {
        return Toolkit.getDefaultToolkit().createCustomCursor(Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(16, 16, new int[16 * 16], 0, 16)),
                                                              new Point(0, 0),
                                                              "Hidden");
    }
}