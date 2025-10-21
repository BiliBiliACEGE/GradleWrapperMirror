# Gradle Wrapper Mirror 插件

中文 | [English](Readme_en)

打开 Gradle 项目时，自动把 `services.gradle.org` 替换成国内高速镜像（阿里云、腾讯云、清华 TUNA）。

## ✅ 实际功能
- 自动识别 `gradle-wrapper.properties` 中的官方下载地址
- 项目打开瞬间完成替换（可关闭）
- 按 `Ctrl + Shift + G` 手动重新替换
- 支持自定义镜像地址

## ⚙️ 系统要求
- IntelliJ IDEA 2022.3 及以上

## 📦 安装
IDE → Settings → Plugins → Marketplace → 搜索 **"Gradle Wrapper Mirror"** → Install

## 🚀 使用
1. 打开任意 Gradle 项目
2. 插件自动应用镜像地址
3. 需要换镜像：  
   File → Settings → Gradle Wrapper Mirror → 选择镜像 → Apply

## 🛠️ 编译
```bash
./gradlew buildPlugin