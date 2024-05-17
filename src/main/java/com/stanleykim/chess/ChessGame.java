package com.stanleykim.chess;

import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class ChessGame {
    public enum GameMode {
        HUMAN_VS_HUMAN,
        HUMAN_VS_AI,
        AI_VS_AI
    }

    private JFrame frame;
    private static ChessGame instance;
    private PlayerVPlayerPanel playerVPlayerPanel;
    private PlayerVAIPanel playerVAIPanel;


    public ChessGame() {
        instance = this;
        frame = new JFrame("MyChessEngine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        showModePanel();

        frame.setSize(1000, 650);
        frame.setVisible(true);
    }

    public static ChessGame getInstance() {
        return instance;
    }
    public void setGameMode(GameMode gameMode) {
        frame.getContentPane().removeAll();
        ProgressPanel progressPanel = null;
        ControlPanel controlPanel = null;

        switch (gameMode) {
            case HUMAN_VS_HUMAN:
                playerVPlayerPanel = new PlayerVPlayerPanel();
                progressPanel = new ProgressPanel(playerVPlayerPanel);
                controlPanel = new ControlPanel(playerVPlayerPanel);
                frame.add(playerVPlayerPanel, BorderLayout.CENTER);
                break;
            case HUMAN_VS_AI:
                playerVAIPanel = new PlayerVAIPanel();
                progressPanel = new ProgressPanel(playerVAIPanel);
                controlPanel = new ControlPanel(playerVAIPanel);
                frame.add(playerVAIPanel, BorderLayout.CENTER);
                break;
            case AI_VS_AI:
                playerVPlayerPanel = new PlayerVPlayerPanel();
                progressPanel = new ProgressPanel(playerVPlayerPanel);
                controlPanel = new ControlPanel(playerVPlayerPanel);
                frame.add(playerVPlayerPanel, BorderLayout.CENTER);
                break;
        }

        frame.add(progressPanel, BorderLayout.WEST);
        frame.add(controlPanel, BorderLayout.EAST);

        frame.revalidate();
        frame.repaint();
    }

    public void showModePanel() {
        frame.getContentPane().removeAll();
        frame.add(new ModePanel(), BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            long beforeUsedMemory = getUsedMemory();
            long beforeCPUTime = getProcessCpuTime();

            new ChessGame();

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                long afterUsedMemory = getUsedMemory();
                long afterCPUTime = getProcessCpuTime();
                System.out.println("-----------MEMORY USAGE------------");
                System.out.println("Memory usage before: " + beforeUsedMemory / 1024 + " KB");
                System.out.println("Memory usage after: " + afterUsedMemory / 1024 + " KB");
                System.out.println("Memory used: " + (afterUsedMemory - beforeUsedMemory) / 1024 + " KB\n");
                System.out.println("-----------CPU USAGE------------");
                System.out.println("CPU time before: " + beforeCPUTime / 1_000_000 + " ms");
                System.out.println("CPU time after: " + afterCPUTime / 1_000_000 + " ms");
                System.out.println("CPU time used: " + (afterCPUTime - beforeCPUTime) / 1_000_000 + " ms");
            }));
        });
    }

    private static long getUsedMemory() {
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

    private static long getProcessCpuTime() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            return ((com.sun.management.OperatingSystemMXBean) osBean).getProcessCpuTime();
        }
        return 0;
    }
}