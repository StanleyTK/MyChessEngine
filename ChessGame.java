import javax.swing.*;
import java.awt.*;

public class ChessGame {
    private JFrame frame;

    public ChessGame() {
        frame = new JFrame("MyChessEngine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        BoardPanel boardPanel = new BoardPanel();
        frame.add(boardPanel, BorderLayout.CENTER);

        ProgressPanel progressPanel = new ProgressPanel();
        frame.add(progressPanel, BorderLayout.WEST);

        ControlPanel controlPanel = new ControlPanel(boardPanel);
        frame.add(controlPanel, BorderLayout.EAST);

        frame.setSize(1000, 600);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}
