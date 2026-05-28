package com.jennymod.installer;

import java.io.*;
import java.nio.file.*;
import java.util.regex.*;

public class JavaDetector {

    public static class Result {
        public final boolean found;
        public final String  version;
        public final String  path;

        Result(boolean found, String version, String path) {
            this.found   = found;
            this.version = version;
            this.path    = path;
        }
    }

    public static Result detect() {
        // 1. Check JAVA_HOME first
        String javaHome = System.getenv("JAVA_HOME");
        if (javaHome != null && !javaHome.isEmpty()) {
            File bin = new File(javaHome, "bin/java.exe");
            if (bin.exists()) {
                String ver = queryVersion(bin.getAbsolutePath());
                if (ver != null) return new Result(true, ver, bin.getAbsolutePath());
            }
        }

        // 2. Scan common Windows install locations
        String[] roots = {
            "C:/Program Files/Java",
            "C:/Program Files (x86)/Java",
            "C:/Program Files/Eclipse Adoptium",
            "C:/Program Files/Microsoft/jdk-17.0"
        };
        for (String root : roots) {
            File dir = new File(root);
            if (!dir.isDirectory()) continue;
            File[] subdirs = dir.listFiles(File::isDirectory);
            if (subdirs == null) continue;
            for (File sub : subdirs) {
                File bin = new File(sub, "bin/java.exe");
                if (bin.exists()) {
                    String ver = queryVersion(bin.getAbsolutePath());
                    if (ver != null) return new Result(true, ver, bin.getAbsolutePath());
                }
            }
        }

        // 3. Fall back to PATH
        String ver = queryVersion("java");
        if (ver != null) return new Result(true, ver, "java");

        return new Result(false, null, null);
    }

    private static String queryVersion(String javaBin) {
        try {
            Process p = new ProcessBuilder(javaBin, "-version")
                    .redirectErrorStream(true)
                    .start();
            String out = new String(p.getInputStream().readAllBytes());
            p.waitFor();
            Matcher m = Pattern.compile("version \"([^\"]+)\"").matcher(out);
            return m.find() ? m.group(1) : "unknown";
        } catch (Exception e) {
            return null;
        }
    }
}
