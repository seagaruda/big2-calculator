package com.big2.calculator.models;

import java.util.LinkedHashMap;
import java.util.Map;

public class Round {
    private final int roundNumber;
    private final Map<String, Integer> cardsLeft; // 每位玩家剩余牌数
    private final String winner;                  // 剩余牌数最少的玩家（正常为 0 张）

    public Round(int roundNumber, Map<String, Integer> cardsLeft) {
        this.roundNumber = roundNumber;
        this.cardsLeft = new LinkedHashMap<>(cardsLeft);
        this.winner = findWinner(cardsLeft);
    }

    private static String findWinner(Map<String, Integer> cardsLeft) {
        String winner = null;
        int min = Integer.MAX_VALUE;
        for (Map.Entry<String, Integer> e : cardsLeft.entrySet()) {
            if (e.getValue() < min) {
                min = e.getValue();
                winner = e.getKey();
            }
        }
        return winner == null ? "?" : winner;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public String getWinner() {
        return winner;
    }

    public Map<String, Integer> getCardsLeft() {
        return new LinkedHashMap<>(cardsLeft);
    }

    public String getSummary() {
        StringBuilder sb = new StringBuilder();
        sb.append("第").append(roundNumber).append("局: ").append(winner).append(" 赢 | 剩余: ");
        boolean first = true;
        for (Map.Entry<String, Integer> e : cardsLeft.entrySet()) {
            if (!first) sb.append("，");
            sb.append(e.getKey()).append("剩").append(e.getValue()).append("张");
            first = false;
        }
        return sb.toString();
    }
}