package com.jennymod.installer;

import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.function.DoubleConsumer;

public class ModDownloader {

    private static final int  CONNECT_TIMEOUT_MS = 10_000;
    private static final int  READ_TIMEOUT_MS    = 30_000;
    private static final int  BUFFER_SIZE        = 8192;

    public boolean download(Path destination, DoubleConsumer progressCallback) {
        String downloadUrl = Config.getDownloadUrl();
        try {
            URL url = URI.create(downloadUrl).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(CONNECT_TIMEOUT_MS);
            conn.setReadTimeout(READ_TIMEOUT_MS);
            conn.setRequestProperty("User-Agent",
                "jennymod-installer/" + Main.getVersion());
            conn.connect();

            int code = conn.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                System.err.println("Download HTTP error: " + code);
                return false;
            }

            long total = conn.getContentLengthLong();
            Files.createDirectories(destination.getParent());

            try (InputStream  in  = conn.getInputStream();
                 OutputStream out = Files.newOutputStream(destination)) {
                byte[] buf  = new byte[BUFFER_SIZE];
                long   read = 0;
                int    n;
                while ((n = in.read(buf)) != -1) {
                    out.write(buf, 0, n);
                    read += n;
                    if (total > 0 && progressCallback != null) {
                        progressCallback.accept((double) read / total);
                    }
                }
            }
            return true;

        } catch (IOException e) {
            System.err.println("Download failed: " + e.getMessage());
            return false;
        }
    }
}
