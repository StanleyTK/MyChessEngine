
import javax.swing.*;
import java.awt.*;

public class ProgressPanel extends JPanel {
    private JLabel statusLabel;
    private BoardPanel boardPanel;

    public ProgressPanel(BoardPanel boardPanel) {
        this.boardPanel = boardPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        statusLabel = new JLabel("Evaluation: 0");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(statusLabel);
        add(Box.createVerticalStrut(20));

        Timer timer = new Timer(300, e -> updateStatusLabel());
        timer.start();
    }

    private void updateStatusLabel() {
        int evaluation = boardPanel.getBoardEvaluation();
        statusLabel.setText("eval: " + evaluation);
    }
}
