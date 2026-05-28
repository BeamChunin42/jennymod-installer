package com.jennymod.installer;

import javax.swing.*;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;

public class Installer extends SwingWorker<Boolean, String> {

    private static final String MOD_FILENAME = "jenny-mod-1.12.2.jar";

    private final String minecraftPath;
    private final JProgressBar progressBar;
    private final JLabel statusLabel;

    public Installer(String minecraftPath, JProgressBar progressBar, JLabel statusLabel) {
        this.minecraftPath = minecraftPath;
        this.progressBar   = progressBar;
        this.statusLabel   = statusLabel;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        publish("Verifying Java...");
        setProgress(10);

        JavaDetector.Result java = JavaDetector.detect();
        if (!java.found) {
            publish("ERROR: Java not found. Please install Java 8 or higher.");
            return false;
        }
        publish("Java " + java.version + " detected at: " + java.path);
        setProgress(25);

        publish("Locating mods folder...");
        File modsDir = new File(minecraftPath, "mods");
        if (!modsDir.exists() && !modsDir.mkdirs()) {
            publish("ERROR: Cannot create mods directory.");
            return false;
        }
        setProgress(40);

        publish("Downloading mod...");
        Path dest = modsDir.toPath().resolve(MOD_FILENAME);
        ModDownloader downloader = new ModDownloader();
        boolean downloaded = downloader.download(dest, p -> setProgress(40 + (int)(p * 0.4)));
        if (!downloaded) {
            publish("ERROR: Download failed.");
            return false;
        }
        setProgress(80);

        publish("Applying configuration...");
        Config config = Config.load(minecraftPath);
        config.ensureModEnabled(MOD_FILENAME);
        config.save();
        setProgress(95);

        publish("Done! Launch Minecraft to play.");
        setProgress(100);
        return true;
    }

    @Override
    protected void process(java.util.List<String> chunks) {
        String last = chunks.get(chunks.size() - 1);
        statusLabel.setText(last);
        if (last.startsWith("ERROR")) {
            statusLabel.setForeground(new Color(0xf7, 0x81, 0x66));
        } else if (last.startsWith("Done")) {
            statusLabel.setForeground(new Color(0x39, 0xd3, 0x53));
        }
    }

    @Override
    protected void done() {
        progressBar.setValue(progressBar.getValue());
    }
}
