package com.stanleykim.chess;

import javax.swing.*;
import java.awt.*;

public class ModePanel extends JPanel {
    public ModePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 700));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(40, 44, 52));

        // Title
        JLabel titleLabel = new JLabel("MyChessEngine");
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        titleLabel.setForeground(new Color(255, 255, 255));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // About section
        JTextArea aboutTextArea = new JTextArea(
                "MyChessEngine is my Summer 2024 project designed to deepen understanding of chess and to explore chess engine algorithms such as the MinMax algorithm and various weighting systems. This project serves as both an educational tool and a foundation for developing advanced chess algorithms."
        );
        aboutTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        aboutTextArea.setForeground(new Color(255, 255, 255));
        aboutTextArea.setBackground(new Color(40, 44, 52));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);
        aboutTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Author section
        JLabel authorLabel = new JLabel("Author: Stanley Kim");
        authorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        authorLabel.setForeground(new Color(200, 200, 200));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Note section
        JLabel noteLabel = new JLabel("NOTE: AI vs AI is not available at the moment");
        noteLabel.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        noteLabel.setForeground(new Color(200, 0, 0));
        noteLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        noteLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // AI Depth Info
        JLabel depthLabel = new JLabel("AI depth is set to 4 for optimal speed.");
        depthLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        depthLabel.setForeground(new Color(200, 200, 200));
        depthLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        depthLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Buttons
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Color buttonColor = new Color(30, 35, 40);
        Color buttonHoverColor = new Color(60, 70, 80);

        JButton humanVsHumanButton = createStyledButton("Human vs Human", buttonFont, buttonColor, buttonHoverColor);
        humanVsHumanButton.addActionListener(e -> setGameMode(ChessGame.GameMode.HUMAN_VS_HUMAN));

        JButton humanVsAIButton = createStyledButton("Human vs AI", buttonFont, buttonColor, buttonHoverColor);
        humanVsAIButton.addActionListener(e -> setGameMode(ChessGame.GameMode.HUMAN_VS_AI));

        JButton aiVsAIButton = createStyledButton("AI vs AI", buttonFont, buttonColor, buttonHoverColor);
        aiVsAIButton.addActionListener(e -> setGameMode(ChessGame.GameMode.AI_VS_AI));

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(aboutTextArea);
        add(Box.createVerticalStrut(20));
        add(authorLabel);
        add(Box.createVerticalStrut(10));
        add(noteLabel);
        add(Box.createVerticalStrut(10));
        add(depthLabel);
        add(Box.createVerticalStrut(30));
        add(humanVsHumanButton);
        add(Box.createVerticalStrut(20));
        add(humanVsAIButton);
        add(Box.createVerticalStrut(20));
        add(aiVsAIButton);
    }

    private JButton createStyledButton(String text, Font font, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setGameMode(ChessGame.GameMode gameMode) {
        ChessGame.getInstance().setGameMode(gameMode);
    }
}
