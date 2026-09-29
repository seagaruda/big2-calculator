package com.big2.calculator.models;

import java.util.*;

/**
 * 锄大地计分会话。
 *
 * 采用中国大陆主流计分规则（忽略黑桃2惩罚与逃跑扣分）：
 * 1) 每局按剩余牌数 n 折算「牌分」：n&lt;8→n；8≤n&lt;10→2n；10≤n&lt;13→3n；n=13→4n；
 * 2) 每人该局得分 = Σ(其他玩家牌分 − 自己牌分)，全场零和。
 */
public class GameSession {
    private final Map<String, Player> players;
    private final List<Round> rounds;
    private int currentRound;

    public GameSession(List<String> playerNames) {
        this.players = new LinkedHashMap<>();
        for (String name : playerNames) {
            players.put(name, new Player(name));
        }
        this.rounds = new ArrayList<>();
        this.currentRound = 0;
    }

    /**
     * 记录一局，并返回本局每人得分（零和）。
     * cardsLeft 需给出每位玩家的剩余牌数（获胜者通常为 0 张）。
     */
    public Map<String, Integer> addRound(Map<String, Integer> cardsLeft) {
        currentRound++;
        Round round = new Round(currentRound, cardsLeft);
        Map<String, Integer> scores = calculateRoundScore(cardsLeft);

        for (Map.Entry<String, Integer> entry : scores.entrySet()) {
            Player player = players.get(entry.getKey());
            if (player != null) {
                player.addToBalance(entry.getValue());
            }
        }

        rounds.add(round);
        return scores;
    }

    /**
     * 牌分折算：
     *   n < 8      → n
     *   8 ≤ n < 10 → 2n
     *   10 ≤ n < 13 → 3n
     *   n = 13     → 4n
     */
    static int cardsScore(int cardsLeft) {
        if (cardsLeft < 8) return cardsLeft;
        if (cardsLeft < 10) return 2 * cardsLeft;
        if (cardsLeft < 13) return 3 * cardsLeft;
        return 4 * cardsLeft; // n == 13
    }

    /** 每人得分 = Σ(他人牌分 − 自己牌分)，全场零和。 */
    private Map<String, Integer> calculateRoundScore(Map<String, Integer> cardsLeft) {
        Map<String, Integer> cardScore = new LinkedHashMap<>();
        for (String name : players.keySet()) {
            cardScore.put(name, cardsScore(cardsLeft.getOrDefault(name, 0)));
        }

        Map<String, Integer> scores = new LinkedHashMap<>();
        for (String name : players.keySet()) {
            int my = cardScore.get(name);
            int score = 0;
            for (Map.Entry<String, Integer> e : cardScore.entrySet()) {
                if (!e.getKey().equals(name)) {
                    score += e.getValue() - my;
                }
            }
            scores.put(name, score);
        }
        return scores;
    }

    public Map<String, Integer> getCurrentStandings() {
        Map<String, Integer> standings = new LinkedHashMap<>();
        for (Map.Entry<String, Player> e : players.entrySet()) {
            standings.put(e.getKey(), e.getValue().getBalance());
        }
        return standings;
    }

    public List<Player> getPlayersSorted() {
        List<Player> playerList = new ArrayList<>(players.values());
        playerList.sort((p1, p2) -> Integer.compare(p2.getBalance(), p1.getBalance()));
        return playerList;
    }

    public List<Round> getRounds() {
        return new ArrayList<>(rounds);
    }

    public int getCurrentRoundNumber() {
        return currentRound;
    }

    public List<String> getPlayerNames() {
        return new ArrayList<>(players.keySet());
    }
}