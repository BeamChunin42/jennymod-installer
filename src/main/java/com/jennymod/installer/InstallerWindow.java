package com.jennymod.installer;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;

public class InstallerWindow extends JFrame {

    private JLabel statusLabel;
    private JProgressBar progressBar;
    private JButton installButton;
    private JTextField pathField;

    public InstallerWindow() {
        setTitle("jennyMod Installer v" + Main.getVersion());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(620, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
    }

    private void buildUI() {
        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(new Color(0x0d, 0x11, 0x17));
        root.setBorder(new EmptyBorder(24, 32, 24, 32));

        JLabel title = new JLabel("jennyMod Installer");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(Color.WHITE);

        JLabel subtitle = new JLabel("One-click setup for Windows");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitle.setForeground(new Color(0x8b, 0x94, 0x9e));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setOpaque(false);
        header.add(title);
        header.add(Box.createVerticalStrut(4));
        header.add(subtitle);

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 0, 6, 0);

        String detected = MinecraftFinder.findDefaultPath();
        pathField = new JTextField(detected);
        pathField.setFont(new Font("Consolas", Font.PLAIN, 13));
        pathField.setBackground(new Color(0x21, 0x26, 0x2d));
        pathField.setForeground(Color.WHITE);
        pathField.setCaretColor(Color.WHITE);
        pathField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x30, 0x36, 0x3d)),
                new EmptyBorder(6, 10, 6, 10)));

        JLabel pathLabel = new JLabel("Minecraft installation path:");
        pathLabel.setForeground(new Color(0x8b, 0x94, 0x9e));
        pathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        progressBar = new JProgressBar(0, 100);
        progressBar.setStringPainted(true);
        progressBar.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        progressBar.setBackground(new Color(0x30, 0x36, 0x3d));
        progressBar.setForeground(new Color(0x39, 0xd3, 0x53));

        statusLabel = new JLabel("Ready to install.");
        statusLabel.setForeground(new Color(0x8b, 0x94, 0x9e));
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        c.gridy = 0; center.add(pathLabel,   c);
        c.gridy = 1; center.add(pathField,   c);
        c.gridy = 2; center.add(progressBar, c);
        c.gridy = 3; center.add(statusLabel, c);

        installButton = new JButton("Install JennyMod");
        installButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        installButton.setBackground(new Color(0x23, 0x8b, 0x36));
        installButton.setForeground(Color.WHITE);
        installButton.setFocusPainted(false);
        installButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        installButton.addActionListener(this::onInstallClicked);

        root.add(header,        BorderLayout.NORTH);
        root.add(center,        BorderLayout.CENTER);
        root.add(installButton, BorderLayout.SOUTH);
        add(root);
    }

    private void onInstallClicked(ActionEvent e) {
        String path = pathField.getText().trim();
        if (path.isEmpty() || !new File(path).exists()) {
            statusLabel.setText("Error: invalid Minecraft path.");
            statusLabel.setForeground(new Color(0xf7, 0x81, 0x66));
            return;
        }
        installButton.setEnabled(false);
        Installer installer = new Installer(path, progressBar, statusLabel);
        installer.execute();
    }
}
