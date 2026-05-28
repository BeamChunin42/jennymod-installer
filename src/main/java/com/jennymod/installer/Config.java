package com.jennymod.installer;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class Config {

    private static final String CONFIG_FILE    = "jennymod-installer.properties";
    private static final String DEFAULT_URL    =
        "https://example.com/mods/jenny-mod-1.12.2.jar";

    private final Properties props;
    private final Path       configPath;

    private Config(Path configPath, Properties props) {
        this.configPath = configPath;
        this.props      = props;
    }

    public static Config load(String minecraftPath) {
        Path path  = Path.of(minecraftPath, CONFIG_FILE);
        Properties p = new Properties();
        if (Files.exists(path)) {
            try (InputStream in = Files.newInputStream(path)) {
                p.load(in);
            } catch (IOException ignored) {}
        }
        return new Config(path, p);
    }

    public void ensureModEnabled(String modFilename) {
        props.setProperty("mod.filename",    modFilename);
        props.setProperty("mod.enabled",     "true");
        props.setProperty("install.version", Main.getVersion());
        props.setProperty("install.date",    new Date().toString());
    }

    public void save() {
        try (OutputStream out = Files.newOutputStream(configPath,
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            props.store(out, "jennyMod Installer config — do not edit manually");
        } catch (IOException e) {
            System.err.println("Failed to save config: " + e.getMessage());
        }
    }

    public static String getDownloadUrl() {
        String env = System.getenv("JENNYMOD_URL");
        return (env != null && !env.isEmpty()) ? env : DEFAULT_URL;
    }

    public String get(String key, String fallback) {
        return props.getProperty(key, fallback);
    }
}
