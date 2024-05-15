import javax.swing.*;
import java.awt.*;
import models.*;

public class ProgressPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel bestWhiteMoveLabel;
    private JLabel bestBlackMoveLabel;
    private PlayerVPlayerPanel playerVPlayerPanel;

    public ProgressPanel(PlayerVPlayerPanel playerVPlayerPanel) {
        this.playerVPlayerPanel = playerVPlayerPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        statusLabel = new JLabel("Evaluation: 0");
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bestWhiteMoveLabel = new JLabel("<html>Best White Move: <br>None</html>");
        bestWhiteMoveLabel.setForeground(Color.WHITE);
        bestWhiteMoveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bestBlackMoveLabel = new JLabel("<html>Best Black Move: <br>None</html>");
        bestBlackMoveLabel.setForeground(Color.WHITE);
        bestBlackMoveLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(statusLabel);
        add(Box.createVerticalStrut(20));
        add(bestWhiteMoveLabel);
        add(Box.createVerticalStrut(20));
        add(bestBlackMoveLabel);

        Timer timer = new Timer(300, e -> updateStatusLabel());
        timer.start();
    }

    private void updateStatusLabel() {
        int evaluation = playerVPlayerPanel.getBoardEvaluation();
        statusLabel.setText("Evaluation: " + evaluation);

        Move bestWhiteMove = Evaluator.getBestMove(playerVPlayerPanel.getBoardState(), true);
        Move bestBlackMove = Evaluator.getBestMove(playerVPlayerPanel.getBoardState(), false);

        bestWhiteMoveLabel.setText("<html>Best White Move: <br>" + (bestWhiteMove != null ? bestWhiteMove.toChessNotation() : "None") + "</html>");
        bestBlackMoveLabel.setText("<html>Best Black Move: <br>" + (bestBlackMove != null ? bestBlackMove.toChessNotation() : "None") + "</html>");
    }
}
