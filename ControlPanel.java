import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel(BoardPanel boardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        JButton previousMoveButton = new JButton("Previous Move");
        previousMoveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        previousMoveButton.addActionListener(e -> boardPanel.handlePreviousMove());

        JButton resetButton = new JButton("Reset Board");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> boardPanel.handleResetBoard());


        add(Box.createVerticalStrut(20)); // Spacer
        add(previousMoveButton);
        add(Box.createVerticalStrut(10)); // Spacer
        add(resetButton);
    }
}
