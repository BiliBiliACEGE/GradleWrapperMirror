# Gradle Wrapper Mirror 插件

中文 | [English](Readme_en.md)

打开 Gradle 项目时，自动把 `services.gradle.org` 替换成国内高速镜像（阿里云、腾讯云、清华 TUNA）。

## ✅ 功能
- 自动识别 `gradle-wrapper.properties` 中的官方下载地址
- 项目打开瞬间完成替换（可关闭）
- 按 `Ctrl + Shift + G` 手动重新替换
- 支持自定义镜像地址

## ⚙️ 系统要求
- IntelliJ IDEA 2023.1 及以上

## 📦 安装
从Marketplace商店安装:  
IDE → 设置 → 插件 → Marketplace → 搜索 **"Gradle Wrapper Mirror"** → 安装    

从本地安装:  
Releases → 下载jar文件 → IDE → 设置 → 插件 → 齿轮 → **从磁盘安装插件** → 选择**下载好的jar**安装即可

## 🚀 使用
1. 打开任意 Gradle 项目
2. 插件自动应用镜像地址
3. 需要换镜像：  
   IDE → 设置 → 工具 → Gradle Wrapper Mirror → 选择镜像 → 应用

## 🛠️ 编译
```bash
./gradlew buildPlugin
