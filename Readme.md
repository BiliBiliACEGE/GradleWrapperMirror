# Gradle Wrapper Mirror

English | [中文](Readme_zh)

Make Gradle distribution downloads **fast & stable** in China by one-click switching among major mirrors (Aliyun, Tencent, Tsinghua ...).

## 🚀 Features
- Auto-detects official `services.gradle.org` URL in `gradle-wrapper.properties`
- Replace with mirror URL **on project open** (can be disabled)
- Manual trigger via **Ctrl + Shift + G** (customizable)
- Support **custom mirror** endpoint
- Compatible with **Configuration Cache**

## 🛠️ Requirements
- IntelliJ IDEA 2022.3+ (build 223.*)
- Gradle 7.0+

## 📦 Installation
Install from JetBrains Marketplace inside IDE:
`Settings / Preferences → Plugins → Marketplace → search "Gradle Wrapper Mirror" → Install`

## 📖 Usage
1. Open any project which uses Gradle Wrapper
2. The plugin **automatically** replaces distribution URL with selected mirror
3. If you want to switch mirror:  
   `File → Settings → Gradle Wrapper Mirror → choose mirror → Apply`

## ⚙️ Build
```bash
./gradlew buildPlugin