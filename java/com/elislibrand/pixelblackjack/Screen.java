package com.elislibrand.pixelblackjack;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

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

    static
    {
        RESOLUTION = new Dimension(DEVICE.getDisplayMode().getWidth(), DEVICE.getDisplayMode().getHeight());
        REFRESH_RATE = DEVICE.getDisplayMode().getRefreshRate(); // equals 0 if not found
        BIT_DEPTH = DEVICE.getDisplayMode().getBitDepth();
        SCALE = calculateScale();
        WIDTH = 480 * SCALE;
        HEIGHT = 270 * SCALE;
    }

    private final static int calculateScale()
    {
        int scaleWidth = (int)Math.floor(RESOLUTION.width / 480);
        int scaleHeight = (int)Math.floor(RESOLUTION.height / 270);

        int scale = (scaleWidth < scaleHeight) ? scaleWidth : scaleHeight;
        
        return scale;
    }
}