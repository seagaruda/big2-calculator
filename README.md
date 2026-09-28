# 锄大D计分器 (Big2 Calculator)

## 功能特性

- ✅ 多人计分（2-4人）
- ✅ 自定义底注和炒牌规则
- ✅ 逐局记录输赢
- ✅ 实时排名显示
- ✅ 多场次管理
- ✅ 完全离线运行
- ✅ 无需 Google Play 服务

## 技术栈

- Java 8+
- Android SDK 21+ (Android 5.0+)
- SQLite 本地存储
- Material Design UI

## 构建说明

1. 安装 Android Studio
2. 打开项目
3. Build -> Build Bundle(s) / APK(s) -> Build APK(s)
4. 生成的 APK 在 `app/build/outputs/apk/release/`

## 或使用命令行构建

```bash
./gradlew assembleRelease
```

## 项目结构

```
Big2Calculator/
├── app/
│   ├── src/
│   │   └── main/
│   │       ├── java/com/big2/calculator/
│   │       │   ├── MainActivity.java
│   │       │   ├── models/
│   │       │   ├── database/
│   │       │   └── utils/
│   │       ├── res/
│   │       │   ├── layout/
│   │       │   ├── values/
│   │       │   └── drawable/
│   │       └── AndroidManifest.xml
│   └── build.gradle
├── gradle/
└── build.gradle
```

## 许可证

MIT License - 自由使用、修改、分发
