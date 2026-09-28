package com.big2.calculator.models;

import java.util.*;

public class GameSession {
    private Map<String, Player> players;
    private List<Round> rounds;
    private double baseBet;
    private Map<CardPattern, Integer> multipliers;
    private int currentRound;

    public GameSession(List<String> playerNames, double baseBet) {
        this.players = new HashMap<>();
        for (String name : playerNames) {
            players.put(name, new Player(name));
        }
        this.rounds = new ArrayList<>();
        this.baseBet = baseBet;
        this.currentRound = 0;
        initializeDefaultMultipliers();
    }

    private void initializeDefaultMultipliers() {
        multipliers = new HashMap<>();
        for (CardPattern pattern : CardPattern.values()) {
            multipliers.put(pattern, pattern.getDefaultMultiplier());
        }
    }

    public void setMultiplier(CardPattern pattern, int multiplier) {
        multipliers.put(pattern, multiplier);
    }

    public Map<String, Double> addRound(String winner, List<String> losers, 
                                        CardPattern pattern, Map<String, Integer> cardsLeft) {
        currentRound++;
        Round round = new Round(currentRound, winner, losers, pattern);
        
        if (cardsLeft != null) {
            for (Map.Entry<String, Integer> entry : cardsLeft.entrySet()) {
                round.setCardsLeft(entry.getKey(), entry.getValue());
            }
        }

        Map<String, Double> scores = calculateRoundScore(round);
        
        // Update player balances
        for (Map.Entry<String, Double> entry : scores.entrySet()) {
            Player player = players.get(entry.getKey());
            if (player != null) {
                player.addToBalance(entry.getValue());
            }
        }

        rounds.add(round);
        return scores;
    }

    private Map<String, Double> calculateRoundScore(Round round) {
        double baseScore = baseBet * multipliers.get(round.getPattern());
        Map<String, Double> scores = new HashMap<>();
        
        // Initialize all players to 0
        for (String playerName : players.keySet()) {
            scores.put(playerName, 0.0);
        }

        // Calculate scores for losers
        for (String loser : round.getLosers()) {
            int cardsLeft = round.getCardsLeft().getOrDefault(loser, 0);
            double penalty = baseScore + (cardsLeft > 10 ? cardsLeft * baseBet : 0);
            scores.put(loser, -penalty);
            
            // Winner gets the penalty
            double currentWinnerScore = scores.get(round.getWinner());
            scores.put(round.getWinner(), currentWinnerScore + penalty);
        }

        return scores;
    }

    public Map<String, Double> getCurrentStandings() {
        Map<String, Double> standings = new HashMap<>();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            standings.put(entry.getKey(), entry.getValue().getBalance());
        }
        return standings;
    }

    public List<Player> getPlayersSorted() {
        List<Player> playerList = new ArrayList<>(players.values());
        playerList.sort((p1, p2) -> Double.compare(p2.getBalance(), p1.getBalance()));
        return playerList;
    }

    public List<Round> getRounds() {
        return new ArrayList<>(rounds);
    }

    public double getBaseBet() {
        return baseBet;
    }

    public int getCurrentRoundNumber() {
        return currentRound;
    }

    public List<String> getPlayerNames() {
        return new ArrayList<>(players.keySet());
    }
}
