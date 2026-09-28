package com.big2.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.big2.calculator.models.*;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    
    private GameSession gameSession;
    private TextView tvCurrentRound;
    private LinearLayout llPlayerScores;
    private LinearLayout llRoundHistory;
    private Spinner spinnerPattern;
    private Spinner spinnerWinner;
    private Button btnAddRound;
    private Button btnNewGame;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initViews();
        startNewGame();
    }
    
    private void initViews() {
        tvCurrentRound = findViewById(R.id.tvCurrentRound);
        llPlayerScores = findViewById(R.id.llPlayerScores);
        llRoundHistory = findViewById(R.id.llRoundHistory);
        spinnerPattern = findViewById(R.id.spinnerPattern);
        spinnerWinner = findViewById(R.id.spinnerWinner);
        btnAddRound = findViewById(R.id.btnAddRound);
        btnNewGame = findViewById(R.id.btnNewGame);
        
        // Setup pattern spinner
        ArrayAdapter<String> patternAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getPatternNames());
        patternAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPattern.setAdapter(patternAdapter);
        
        btnAddRound.setOnClickListener(v -> addRound());
        btnNewGame.setOnClickListener(v -> showNewGameDialog());
    }
    
    private String[] getPatternNames() {
        CardPattern[] patterns = CardPattern.values();
        String[] names = new String[patterns.length];
        for (int i = 0; i < patterns.length; i++) {
            names[i] = patterns[i].getDisplayName();
        }
        return names;
    }
    
    private void startNewGame() {
        // Default 4 players
        List<String> players = Arrays.asList("玩家1", "玩家2", "玩家3", "玩家4");
        gameSession = new GameSession(players, 10.0); // Base bet $10
        
        // Setup winner spinner
        ArrayAdapter<String> winnerAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, players);
        winnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWinner.setAdapter(winnerAdapter);
        
        updateUI();
    }
    
    private void showNewGameDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("开始新游戏");
        builder.setMessage("确定要开始新游戏吗？当前进度将丢失。");
        builder.setPositiveButton("确定", (dialog, which) -> startNewGame());
        builder.setNegativeButton("取消", null);
        builder.show();
    }
    
    private void addRound() {
        String winner = spinnerWinner.getSelectedItem().toString();
        CardPattern pattern = CardPattern.values()[spinnerPattern.getSelectedItemPosition()];
        
        // Get losers (all players except winner)
        List<String> losers = new ArrayList<>();
        for (String player : gameSession.getPlayerNames()) {
            if (!player.equals(winner)) {
                losers.add(player);
            }
        }
        
        // Add round without cards left info for now
        gameSession.addRound(winner, losers, pattern, null);
        updateUI();
        
        Toast.makeText(this, winner + " 赢了这一局！", Toast.LENGTH_SHORT).show();
    }
    
    private void updateUI() {
        // Update round number
        tvCurrentRound.setText("第 " + gameSession.getCurrentRoundNumber() + " 局");
        
        // Update player scores
        llPlayerScores.removeAllViews();
        for (Player player : gameSession.getPlayersSorted()) {
            TextView tv = new TextView(this);
            tv.setText(player.toString());
            tv.setTextSize(18);
            tv.setPadding(16, 16, 16, 16);
            
            // Color code: green for positive, red for negative
            if (player.getBalance() > 0) {
                tv.setTextColor(0xFF4CAF50); // Green
            } else if (player.getBalance() < 0) {
                tv.setTextColor(0xFFF44336); // Red
            } else {
                tv.setTextColor(0xFF9E9E9E); // Grey
            }
            
            llPlayerScores.addView(tv);
        }
        
        // Update round history
        llRoundHistory.removeAllViews();
        List<Round> rounds = gameSession.getRounds();
        for (int i = rounds.size() - 1; i >= 0; i--) { // Reverse order (newest first)
            Round round = rounds.get(i);
            TextView tv = new TextView(this);
            tv.setText(round.getSummary());
            tv.setTextSize(14);
            tv.setPadding(16, 8, 16, 8);
            tv.setTextColor(0xFF757575);
            llRoundHistory.addView(tv);
        }
    }
}
