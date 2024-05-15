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
    private BoardPanel boardPanel;

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
        boardPanel = new BoardPanel();

        ProgressPanel progressPanel = new ProgressPanel(boardPanel);
        ControlPanel controlPanel = new ControlPanel(boardPanel);

        frame.add(boardPanel, BorderLayout.CENTER);
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
            // Capture memory and CPU usage before running the application
            long beforeUsedMemory = getUsedMemory();
            long beforeCPUTime = getProcessCpuTime();

            new ChessGame();

            // Add a shutdown hook to capture memory and CPU usage after the application exits
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
