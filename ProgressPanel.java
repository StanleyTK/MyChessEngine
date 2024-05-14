import javax.swing.*;
import java.awt.*;

public class ProgressPanel extends JPanel {
    public ProgressPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        JLabel statusLabel = new JLabel("");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JProgressBar progressBar = new JProgressBar(SwingConstants.VERTICAL, 0, 100);
        progressBar.setValue(50); // Placeholder value, update with game logic
        progressBar.setForeground(Color.GREEN);
        progressBar.setBackground(Color.RED);
        progressBar.setPreferredSize(new Dimension(50, 400));
        progressBar.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(statusLabel);
        add(Box.createVerticalStrut(20)); // Spacer
        add(progressBar);
    }
}
