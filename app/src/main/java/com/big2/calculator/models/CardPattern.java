package com.big2.calculator.models;

public enum CardPattern {
    NORMAL("普通", 1),
    THREE_OF_KIND("3条", 2),
    FOUR_OF_KIND("4条", 4),
    FIVE_OF_KIND("5条", 8),
    STRAIGHT("顺子", 1),
    FLUSH("同花", 1),
    FULL_HOUSE("葫芦", 1),
    STRAIGHT_FLUSH("同花顺", 8);

    private final String displayName;
    private final int defaultMultiplier;

    CardPattern(String displayName, int defaultMultiplier) {
        this.displayName = displayName;
        this.defaultMultiplier = defaultMultiplier;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getDefaultMultiplier() {
        return defaultMultiplier;
    }
}
