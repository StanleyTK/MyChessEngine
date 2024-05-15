import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModePanel extends JPanel {
    public ModePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(200, 600));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(30, 30, 30));

        JButton humanVsHumanButton = new JButton("Human vs Human");
        humanVsHumanButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsHumanButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(ChessGame.GameMode.HUMAN_VS_HUMAN);
            }
        });

        JButton humanVsAIButton = new JButton("Human vs AI");
        humanVsAIButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        humanVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(ChessGame.GameMode.HUMAN_VS_AI);
            }
        });

        JButton aiVsAIButton = new JButton("AI vs AI");
        aiVsAIButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aiVsAIButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setGameMode(ChessGame.GameMode.AI_VS_AI);
            }
        });

        add(Box.createVerticalStrut(20));
        add(humanVsHumanButton);
        add(Box.createVerticalStrut(20));
        add(humanVsAIButton);
        add(Box.createVerticalStrut(20));
        add(aiVsAIButton);
    }

    private void setGameMode(ChessGame.GameMode gameMode) {
        ChessGame.getInstance().setGameMode(gameMode);
    }
}
