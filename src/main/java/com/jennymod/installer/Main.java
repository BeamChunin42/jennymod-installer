package com.jennymod.installer;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}

            InstallerWindow window = new InstallerWindow();
            window.setVisible(true);
        });
    }

    public static String getVersion() {
        return "1.0.0";
    }
}
