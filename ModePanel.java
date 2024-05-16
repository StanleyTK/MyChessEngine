import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModePanel extends JPanel {
    public ModePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 700));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(new Color(40, 44, 52));  // Updated to a darker shade

        // Title
        JLabel titleLabel = new JLabel("MyChessEngine");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Updated font
        titleLabel.setForeground(new Color(255, 255, 255));  // Pure white for better visibility
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // About section
        JTextArea aboutTextArea = new JTextArea(
                "MyChessEngine is my Summer 2024 project designed to deepen understanding of chess and to explore chess engine algorithms such as the MinMax algorithm and various weighting systems. This project serves as both an educational tool and a foundation for developing advanced chess algorithms."
        );
        aboutTextArea.setFont(new Font("Segoe UI", Font.PLAIN, 16));  // Updated font
        aboutTextArea.setForeground(new Color(255, 255, 255));
        aboutTextArea.setBackground(new Color(40, 44, 52));
        aboutTextArea.setLineWrap(true);
        aboutTextArea.setWrapStyleWord(true);
        aboutTextArea.setEditable(false);
        aboutTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutTextArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Buttons
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Color buttonColor = new Color(30, 35, 40);  // Darker for button base
        Color buttonHoverColor = new Color(60, 70, 80);  // Dark gray for hover

        JButton humanVsHumanButton = createStyledButton("Human vs Human", buttonFont, buttonColor, buttonHoverColor);
        humanVsHumanButton.addActionListener(e -> setGameMode(ChessGame.GameMode.HUMAN_VS_HUMAN));

        JButton humanVsAIButton = createStyledButton("Human vs AI", buttonFont, buttonColor, buttonHoverColor);
        humanVsAIButton.addActionListener(e -> setGameMode(ChessGame.GameMode.HUMAN_VS_AI));

        JButton aiVsAIButton = createStyledButton("AI vs AI", buttonFont, buttonColor, buttonHoverColor);
        aiVsAIButton.addActionListener(e -> setGameMode(ChessGame.GameMode.AI_VS_AI));

        add(Box.createVerticalStrut(10));
        add(titleLabel);
        add(Box.createVerticalStrut(20));
        add(aboutTextArea);
        add(Box.createVerticalStrut(30));
        add(humanVsHumanButton);
        add(Box.createVerticalStrut(20));
        add(humanVsAIButton);
        add(Box.createVerticalStrut(20));
        add(aiVsAIButton);
    }

    private JButton createStyledButton(String text, Font font, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void setGameMode(ChessGame.GameMode gameMode) {
        ChessGame.getInstance().setGameMode(gameMode);
    }
}
