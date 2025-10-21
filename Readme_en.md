# Gradle Wrapper Mirror

English | [中文](Readme)

Automatically replace `services.gradle.org` with fast domestic mirrors (Aliyun, Tencent, Tsinghua) when opening a Gradle project.

## ✅ What it does
- Auto-detects official Gradle distribution URL in `gradle-wrapper.properties`
- Replaces it with the selected mirror **on project open** (can be disabled)
- Manual trigger via **Ctrl + Shift + G** (customizable)
- Supports **custom mirror** endpoint

## ⚙️ Requirements
- IntelliJ IDEA 2022.3+ (build 223.*)

## 📦 Install
IDE → Settings → Plugins → Marketplace → search **"Gradle Wrapper Mirror"** → Install

## 🚀 Use
1. Open any Gradle project
2. Mirror URL is applied automatically
3. Change mirror:  
   File → Settings → Gradle Wrapper Mirror → choose mirror → Apply

## 🛠️ Build
```bash
./gradlew buildPlugin