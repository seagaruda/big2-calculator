package com.big2.calculator.models;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Round {
    private int roundNumber;
    private String winner;
    private List<String> losers;
    private CardPattern pattern;
    private Map<String, Integer> cardsLeft;

    public Round(int roundNumber, String winner, List<String> losers, CardPattern pattern) {
        this.roundNumber = roundNumber;
        this.winner = winner;
        this.losers = losers;
        this.pattern = pattern;
        this.cardsLeft = new HashMap<>();
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getWinner() {
        return winner;
    }

    public List<String> getLosers() {
        return losers;
    }

    public CardPattern getPattern() {
        return pattern;
    }

    public Map<String, Integer> getCardsLeft() {
        return cardsLeft;
    }

    public void setCardsLeft(String player, int cards) {
        cardsLeft.put(player, cards);
    }

    public String getSummary() {
        return "第" + roundNumber + "局: " + winner + " 赢 (" + 
               pattern.getDisplayName() + ") - 输家: " + String.join(", ", losers);
    }
}
