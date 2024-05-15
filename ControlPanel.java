import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel(PlayerVPlayerPanel playerVPlayerPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        JButton previousMoveButton = new JButton("Previous Move");
        previousMoveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousMoveButton.addActionListener(e -> playerVPlayerPanel.handlePreviousMove());

        JButton resetButton = new JButton("Reset Board");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> playerVPlayerPanel.handleResetBoard());

        JButton chooseGameModeButton = new JButton("Choose Game Mode");
        chooseGameModeButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        chooseGameModeButton.addActionListener(e -> ChessGame.getInstance().showModePanel());

        add(Box.createVerticalStrut(20)); // Spacer
        add(previousMoveButton);
        add(Box.createVerticalStrut(10)); // Spacer
        add(resetButton);
        add(Box.createVerticalStrut(10)); // Spacer
        add(chooseGameModeButton);
    }
}
