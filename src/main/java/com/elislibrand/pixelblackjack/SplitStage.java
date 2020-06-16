package com.elislibrand.pixelblackjack;

public enum SplitStage
{
    INITIATE{
        @Override
        public SplitStage previous()
        {
            return this;
        };
    },
    MOVE_HANDS,
    MOVE_UPPER_VISUAL_CARD,
    MOVE_LOWER_VISUAL_CARD,
    SETUP_NEW_HANDS, // Needs better name
    DRAW_FIRST_CARD,
    DRAW_SECOND_CARD,
    INCREMENT_CURRENT_HAND_INDEX,
    DISPLAY_ARROW,
    CHANGE_GAME_STATE {
        @Override
        public SplitStage next()
        {
            return this;
        };
    };

    public SplitStage next()
    {
        return values()[ordinal() + 1];
    }

    public SplitStage previous()
    {
        return values()[ordinal() - 1];
    }
}