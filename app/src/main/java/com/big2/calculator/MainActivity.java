package com.big2.calculator;

import android.os.Bundle;
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
    private EditText etCardsP1;
    private EditText etCardsP2;
    private EditText etCardsP3;
    private EditText etCardsP4;
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
        etCardsP1 = findViewById(R.id.etCardsP1);
        etCardsP2 = findViewById(R.id.etCardsP2);
        etCardsP3 = findViewById(R.id.etCardsP3);
        etCardsP4 = findViewById(R.id.etCardsP4);
        btnAddRound = findViewById(R.id.btnAddRound);
        btnNewGame = findViewById(R.id.btnNewGame);

        btnAddRound.setOnClickListener(v -> addRound());
        btnNewGame.setOnClickListener(v -> showNewGameDialog());
    }

    private void startNewGame() {
        // 默认 4 人
        gameSession = new GameSession(Arrays.asList("玩家1", "玩家2", "玩家3", "玩家4"));
        clearInputs();
        updateUI();
    }

    private void clearInputs() {
        etCardsP1.getText().clear();
        etCardsP2.getText().clear();
        etCardsP3.getText().clear();
        etCardsP4.getText().clear();
    }

    private void showNewGameDialog() {
        new AlertDialog.Builder(this)
                .setTitle("开始新游戏")
                .setMessage("确定要开始新游戏吗？当前进度将丢失。")
                .setPositiveButton("确定", (d, w) -> startNewGame())
                .setNegativeButton("取消", null)
                .show();
    }

    private int parseCards(EditText et) {
        try {
            int v = Integer.parseInt(et.getText().toString().trim());
            return (v < 0 || v > 13) ? -1 : v;
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void addRound() {
        List<String> names = gameSession.getPlayerNames();
        EditText[] fields = {etCardsP1, etCardsP2, etCardsP3, etCardsP4};
        if (names.size() != fields.length) return;

        Map<String, Integer> cardsLeft = new LinkedHashMap<>();
        for (int i = 0; i < names.size(); i++) {
            int v = parseCards(fields[i]);
            if (v < 0) {
                Toast.makeText(this, "请为 " + names.get(i) + " 输入 0-13 的剩余牌数",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            cardsLeft.put(names.get(i), v);
        }

        gameSession.addRound(cardsLeft);
        clearInputs();
        updateUI();
        Toast.makeText(this, "已记录第 " + gameSession.getCurrentRoundNumber() + " 局",
                Toast.LENGTH_SHORT).show();
    }

    private void updateUI() {
        tvCurrentRound.setText("第 " + gameSession.getCurrentRoundNumber() + " 局");

        llPlayerScores.removeAllViews();
        for (Player player : gameSession.getPlayersSorted()) {
            TextView tv = new TextView(this);
            tv.setText(player.toString());
            tv.setTextSize(18);
            tv.setPadding(16, 16, 16, 16);
            int balance = player.getBalance();
            if (balance > 0) {
                tv.setTextColor(0xFF4CAF50); // 绿
            } else if (balance < 0) {
                tv.setTextColor(0xFFF44336); // 红
            } else {
                tv.setTextColor(0xFF9E9E9E); // 灰
            }
            llPlayerScores.addView(tv);
        }

        llRoundHistory.removeAllViews();
        List<Round> rounds = gameSession.getRounds();
        for (int i = rounds.size() - 1; i >= 0; i--) { // 最新在前
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