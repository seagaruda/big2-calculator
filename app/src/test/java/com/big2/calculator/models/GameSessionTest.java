package com.big2.calculator.models;

import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class GameSessionTest {

    @Test
    public void cardsScoreTiers() {
        assertEquals(0, GameSession.cardsScore(0));
        assertEquals(5, GameSession.cardsScore(5));
        assertEquals(7, GameSession.cardsScore(7));
        assertEquals(16, GameSession.cardsScore(8));   // 2×8
        assertEquals(18, GameSession.cardsScore(9));   // 2×9
        assertEquals(30, GameSession.cardsScore(10));  // 3×10
        assertEquals(36, GameSession.cardsScore(12));  // 3×12
        assertEquals(52, GameSession.cardsScore(13));  // 4×13
    }

    @Test
    public void goldenExampleMatchesOfficialRule() {
        // 官方示例：A剩5、B剩8、C剩0、D剩12
        GameSession session = new GameSession(Arrays.asList("A", "B", "C", "D"));
        Map<String, Integer> cards = new LinkedHashMap<>();
        cards.put("A", 5);
        cards.put("B", 8);
        cards.put("C", 0);
        cards.put("D", 12);

        Map<String, Integer> scores = session.addRound(cards);

        assertEquals(Integer.valueOf(37), scores.get("A"));
        assertEquals(Integer.valueOf(-7), scores.get("B"));
        assertEquals(Integer.valueOf(57), scores.get("C"));
        assertEquals(Integer.valueOf(-87), scores.get("D"));
    }

    @Test
    public void everyRoundIsZeroSum() {
        GameSession session = new GameSession(Arrays.asList("P1", "P2", "P3", "P4"));
        int[][] rounds = {
                {5, 8, 0, 12},
                {13, 2, 1, 0},
                {3, 0, 10, 7},
                {0, 13, 13, 13}
        };
        for (int[] r : rounds) {
            List<String> names = session.getPlayerNames();
            Map<String, Integer> cards = new LinkedHashMap<>();
            for (int i = 0; i < names.size(); i++) {
                cards.put(names.get(i), r[i]);
            }
            Map<String, Integer> scores = session.addRound(cards);
            int sum = 0;
            for (int v : scores.values()) {
                sum += v;
            }
            assertEquals("每局总分应为 0", 0, sum);
        }
    }

    @Test
    public void balanceAccumulatesAcrossRounds() {
        GameSession session = new GameSession(Arrays.asList("A", "B", "C", "D"));

        Map<String, Integer> r1 = new LinkedHashMap<>();
        r1.put("A", 5);
        r1.put("B", 8);
        r1.put("C", 0);
        r1.put("D", 12);
        session.addRound(r1);

        Map<String, Integer> standings = session.getCurrentStandings();
        assertEquals(Integer.valueOf(37), standings.get("A"));
        assertEquals(Integer.valueOf(-7), standings.get("B"));
        assertEquals(Integer.valueOf(57), standings.get("C"));
        assertEquals(Integer.valueOf(-87), standings.get("D"));
    }
}