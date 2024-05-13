import java.awt.*;
import javax.swing.*;

public class Chess {
    private JFrame frame;
    private JPanel boardPanel;

    public Chess() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create the chess board panel
        boardPanel = new JPanel(new GridLayout(8, 8));
        initializeBoard();

        // Set preferred size for the board panel
        boardPanel.setPreferredSize(new Dimension(600, 600));

        // Set the initial size of the JFrame
        frame.setSize(800, 800); // Adjust the size as needed
        
        frame.add(boardPanel);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        // Loop to create the squares of the chess board
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel();
            boardPanel.add(square);

            // Alternate colors for the squares
            Color color = (i / 8 + i) % 2 == 0 ? Color.WHITE : Color.GRAY;
            square.setBackground(color);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Chess();
        });
    }
}
