package com.jennymod.installer;

import java.io.File;

public class MinecraftFinder {

    public static String findDefaultPath() {
        String appData = System.getenv("APPDATA");
        if (appData != null) {
            File mc = new File(appData, ".minecraft");
            if (mc.exists()) return mc.getAbsolutePath();
        }

        // MultiMC / Prism Launcher locations
        String localAppData = System.getenv("LOCALAPPDATA");
        if (localAppData != null) {
            String[] multiMcPaths = {
                "Programs/MultiMC/instances",
                "Packages/Prism Launcher"
            };
            for (String rel : multiMcPaths) {
                File f = new File(localAppData, rel);
                if (f.exists()) return f.getAbsolutePath();
            }
        }

        // Default user home fallback
        return System.getProperty("user.home") + "\\.minecraft";
    }

    public static boolean isValidMinecraftDir(String path) {
        if (path == null || path.isEmpty()) return false;
        File dir = new File(path);
        return dir.isDirectory() &&
               (new File(dir, "versions").exists() ||
                new File(dir, "mods").exists() ||
                new File(dir, "saves").exists());
    }

    public static String getModsPath(String minecraftPath) {
        return minecraftPath + File.separator + "mods";
    }
}
