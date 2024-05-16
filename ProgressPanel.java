import javax.swing.*;
import java.awt.*;
import models.*;

public class ProgressPanel extends JPanel {
    private JLabel statusLabel;
    private JLabel bestWhiteMoveLabel;
    private JLabel bestBlackMoveLabel;
    private BaseBoardPanel baseBoardPanel;

    public ProgressPanel(BaseBoardPanel baseBoardPanel) {
        this.baseBoardPanel = baseBoardPanel;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(40, 44, 52));  // Updated to a darker shade similar to the other panels

        // Apply consistent font and color settings
        Font labelFont = new Font("Segoe UI", Font.BOLD, 16);
        Color labelColor = Color.WHITE;

        statusLabel = createStyledLabel("Evaluation: 0", labelFont, labelColor);
        bestWhiteMoveLabel = createStyledLabel("<html>Best White Move: <br>None</html>", labelFont, labelColor);
        bestBlackMoveLabel = createStyledLabel("<html>Best Black Move: <br>None</html>", labelFont, labelColor);

        add(statusLabel);
        add(Box.createVerticalStrut(20));
        add(bestWhiteMoveLabel);
        add(Box.createVerticalStrut(20));
        add(bestBlackMoveLabel);

        Timer timer = new Timer(300, e -> updateStatusLabel());
        timer.start();
    }

    private JLabel createStyledLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        label.setFont(font);
        label.setForeground(color);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private void updateStatusLabel() {
        int evaluation = baseBoardPanel.getBoardEvaluation();
        statusLabel.setText("Evaluation: " + evaluation);

        Move bestWhiteMove = Evaluator.getBestMove(baseBoardPanel.getBoardState(), true);
        Move bestBlackMove = Evaluator.getBestMove(baseBoardPanel.getBoardState(), false);

        bestWhiteMoveLabel.setText("<html>Best White Move: <br>" + (bestWhiteMove != null ? bestWhiteMove.toChessNotation() : "None") + "</html>");
        bestBlackMoveLabel.setText("<html>Best Black Move: <br>" + (bestBlackMove != null ? bestBlackMove.toChessNotation() : "None") + "</html>");
    }
}
