package com.elislibrand.pixelblackjack;

public enum ChipValue
{
    WHITE(1),
    RED(5),
    GREEN(25),
    BLUE(50),
    BLACK(100),
    PURPLE(500),
    YELLOW(1000),
    PINK(5000),
    ORANGE(10000),
    BROWN(50000);

    private int value;

    ChipValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}