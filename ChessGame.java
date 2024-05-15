import javax.swing.*;
import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

public class ChessGame {
    private JFrame frame;

    public ChessGame() {
        frame = new JFrame("MyChessEngine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        BoardPanel boardPanel = new BoardPanel();
        frame.add(boardPanel, BorderLayout.CENTER);

        ProgressPanel progressPanel = new ProgressPanel(boardPanel); // Pass BoardPanel instance
        frame.add(progressPanel, BorderLayout.WEST);

        ControlPanel controlPanel = new ControlPanel(boardPanel);
        frame.add(controlPanel, BorderLayout.EAST);

        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Capture memory and CPU usage before running the application
        long beforeUsedMemory = getUsedMemory();
        long beforeCPUTime = getProcessCpuTime();

        SwingUtilities.invokeLater(() -> {
            new ChessGame();
        });

        // Add a shutdown hook to capture memory and CPU usage after the application exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            long afterUsedMemory = getUsedMemory();
            long afterCPUTime = getProcessCpuTime();
            System.out.println("-----------MEM USAGE------------");
            System.out.println("Memory usage before: " + beforeUsedMemory / 1024 + " KB");
            System.out.println("Memory usage after: " + afterUsedMemory / 1024 + " KB");
            System.out.println("Memory used: " + (afterUsedMemory - beforeUsedMemory) / 1024 + " KB\n\n");
            System.out.println("-----------CPU USAGE------------");

            System.out.println("CPU time before: " + beforeCPUTime / 1_000_000 + " ms");
            System.out.println("CPU time after: " + afterCPUTime / 1_000_000 + " ms");
            System.out.println("CPU time used: " + (afterCPUTime - beforeCPUTime) / 1_000_000 + " ms");
        }));
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
