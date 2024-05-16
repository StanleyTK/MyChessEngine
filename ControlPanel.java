import javax.swing.*;
import java.awt.*;

public class ControlPanel extends JPanel {
    public ControlPanel(BaseBoardPanel baseBoardPanel) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(40, 44, 52));  // Dark background similar to ModePanel

        // Create styled buttons with uniform fonts and colors
        JButton previousMoveButton = createStyledButton("Previous Move");
        previousMoveButton.addActionListener(e -> baseBoardPanel.handlePreviousMove());

        JButton resetButton = createStyledButton("Reset Board");
        resetButton.addActionListener(e -> baseBoardPanel.handleResetBoard());

        JButton chooseGameModeButton = createStyledButton("Return Home");
        chooseGameModeButton.addActionListener(e -> ChessGame.getInstance().showModePanel());

        add(Box.createVerticalStrut(20)); // Spacer
        add(previousMoveButton);
        add(Box.createVerticalStrut(10)); // Spacer
        add(resetButton);
        add(Box.createVerticalStrut(10)); // Spacer
        add(chooseGameModeButton);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));  // Modern font
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(new Color(30, 35, 40));  // Dark gray base color
        button.setForeground(Color.WHITE);  // Text color
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  // Padding

        // Mouse hover effects
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 70, 80));  // Lighter gray on hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(30, 35, 40));  // Original dark gray
            }
        });

        return button;
    }
}
