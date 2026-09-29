# 锄大D计分器 (Big2 Calculator)

按中国大陆主流「锄大地」计分规则进行局间计分。

## 计分规则（核心）

- 每局按每人剩余牌数 n 折算「牌分」：
  - n < 8        → n
  - 8 ≤ n < 10   → 2n
  - 10 ≤ n < 13  → 3n
  - n = 13       → 4n
- 每人该局得分 = Σ(其他玩家牌分 − 自己牌分)，全场零和。
- 黑桃 2 惩罚与逃跑扣分暂不纳入。

## 功能特性

- 固定 4 人计分（玩家 1–4）
- 主题蓝（#2196F3）+ 三白柱启动图标（含各密度 PNG 与自适应图标）
- 逐局输入每人剩余牌数、自动判定赢家
- 实时排名显示（正分绿色、负分红色）
- 局历史记录（本局赢家 + 各人剩余牌数）
- 完全离线运行，无需 Google Play 服务

## 技术栈

- Java 8+
- Android SDK 21+ (Android 5.0+)
- Material Design UI

## 构建说明

1. 安装 Android Studio 打开项目
2. Build → Build APK(s)
3. APK 输出在 `app/build/outputs/apk/`

或命令行：`./gradlew assembleDebug`；单元测试：`./gradlew test`

## 项目结构

```
Big2Calculator/
├── app/
│   ├── src/
│   │   ├── main/java/com/big2/calculator/
│   │   │   ├── MainActivity.java
│   │   │   └── models/  (GameSession, Player, Round)
│   │   ├── main/res/    (layout, values, mipmap)
│   │   └── test/java/com/big2/calculator/models/GameSessionTest.java
│   └── build.gradle
├── gradle/
└── build.gradle
```

## 许可证

MIT License