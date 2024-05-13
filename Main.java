import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Main {
    private JFrame frame;
    private JPanel boardPanel;

    public Main() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        boardPanel = new JPanel(new GridLayout(8, 8));
        initializeBoard();

        boardPanel.setPreferredSize(new Dimension(600, 600));

        frame.setSize(400, 400);

        frame.add(boardPanel);

        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int i = 0; i < 64; i++) {
            JPanel square = new JPanel();
            boardPanel.add(square);

            Color color = (i / 8 + i) % 2 == 0 ? Color.WHITE : Color.GRAY;
            square.setBackground(color);

            // Load images for black pieces
            if (i == 0 || i == 7) {
                loadImage(square, "public/bR.png");
            } else if (i == 1 || i == 6) {
                loadImage(square, "public/bN.png");
            } else if (i == 2 || i == 5) {
                loadImage(square, "public/bB.png");
            } else if (i == 3) {
                loadImage(square, "public/bQ.png");
            } else if (i == 4) {
                loadImage(square, "public/bK.png");
            } else if (i >= 8 && i < 16) {
                loadImage(square, "public/bP.png");
            }
            // Load images for white pieces
            else if (i == 56 || i == 63) {
                loadImage(square, "public/wR.png");
            } else if (i == 57 || i == 62) {
                loadImage(square, "public/wN.png");
            } else if (i == 58 || i == 61) {
                loadImage(square, "public/wB.png");
            } else if (i == 59) {
                loadImage(square, "public/wQ.png");
            } else if (i == 60) {
                loadImage(square, "public/wK.png");
            } else if (i >= 48 && i < 56) {
                loadImage(square, "public/wP.png");
            }
        }
    }

    private void loadImage(JPanel square, String imagePath) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            square.setLayout(new BorderLayout());
            JLabel label = new JLabel(new ImageIcon(img));
            square.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main();
        });
    }
}
