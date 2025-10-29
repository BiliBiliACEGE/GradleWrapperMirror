# Gradle Wrapper Mirror

English | [中文](Readme.md)

Automatically replace `services.gradle.org` with fast domestic mirrors (Aliyun, Tencent, Tsinghua) when opening a Gradle project.

## ✅ What it does
- Auto-detects official Gradle distribution URL in `gradle-wrapper.properties`
- Replaces it with the selected mirror **on project open** (can be disabled)
- Manual trigger via **Ctrl + Shift + G** (customizable)
- Supports **custom mirror** endpoint

## ⚙️ Requirements
- IntelliJ IDEA 2023.1+ 

## 📦 Install
Install from Marketplace:  
IDE → Settings → Plugins → Marketplace → search **"Gradle Wrapper Mirror"** → Install

Install from disk:  
Releases → Download the jar file → IDE → Settings → Plugins → Gear → **Install Plugin from Disk** → Select the **downloaded jar** to install

## 🚀 Use
1. Open any Gradle project
2. Mirror URL is applied automatically
3. Change mirror:  
   File → Settings → Gradle Wrapper Mirror → choose mirror → Apply

## 🛠️ Build
```bash
./gradlew buildPlugin
