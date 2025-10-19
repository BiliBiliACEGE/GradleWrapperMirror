# Gradle Wrapper Mirror 插件
中文 | [English](readme.md)

一键把 Gradle 官方下载地址切换成国内镜像，告别 10 KB/s，**极速下载**！

## ⚡ 核心功能
| 功能             | 说明                                       |
|----------------|------------------------------------------|
| 🚀 **自动替换**    | 打开项目瞬间完成镜像地址替换，无需手动改文件                   |
| 🔄 **一键切换**    | `Ctrl + Shift + G` 随时换镜像，支持阿里云、腾讯、清华、自定义 |
| 🛠️ **配置缓存安全** | 完全兼容 Gradle Configuration Cache，无构建报错    |
| 🌐 **多语言**     | 中英文界面自动跟随 IDE                            |

## 📦 安装（30 秒）
1. IDEA 内打开 **Plugins** 面板
2. 搜索 **"Gradle Wrapper Mirror"** → **Install**
3. 重启 IDE 即可生效

## 🎯 使用（零配置）
| 场景     | 效果                                                     |
|--------|--------------------------------------------------------|
| 首次打开项目 | 自动把 `services.gradle.org` 替换成所选镜像                      |
| 想换镜像   | `File → Settings → Gradle Wrapper Mirror` 下拉选择 → Apply |
| 手动触发   | 按 `Ctrl + Shift + G` 立即重新替换                            |

## 🛠️ 自行编译
```bash
git clone https://github.com/YOUR_NAME/GradleWrapperMirror.git
cd GradleWrapperMirror
./gradlew buildPlugin