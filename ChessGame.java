import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.*;

public class ChessGame {
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel[][] boardCells;
    private ChessPiece[][] boardState;
    private Point selectedPiece; // Track the selected piece
    private Color originalColor; // Track the original color of the selected cell

    public ChessGame() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        boardPanel = new JPanel(new GridLayout(8, 8));
        boardCells = new JPanel[8][8];
        boardState = new ChessPiece[8][8];

        initializeBoard();

        boardPanel.setPreferredSize(new Dimension(600, 600));
        frame.setSize(600, 600);
        frame.add(boardPanel);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel();
                boardCells[row][col] = cell;
                boardPanel.add(cell);

                Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.GRAY;
                cell.setBackground(color);
                setInitialPiece(row, col);

                int finalRow = row;
                int finalCol = col;
                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        handleCellClick(finalRow, finalCol);
                    }
                });
            }
        }
    }

    private void handleCellClick(int row, int col) {
        if (selectedPiece == null) {
            if (boardState[row][col] != null) {
                selectedPiece = new Point(row, col);
                originalColor = boardCells[row][col].getBackground();
                boardCells[row][col].setBackground(Color.YELLOW);
                System.out.println("selected piece: " + selectedPiece);
            }
        } else {
            boardCells[selectedPiece.x][selectedPiece.y].setBackground(originalColor);
            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                movePiece(selectedPiece.x, selectedPiece.y, row, col);
                System.out.println("moved piece: " + selectedPiece);
            } else {
                System.out.println("unmovable");
            }
            selectedPiece = null; // Reset after move
        }
    }

    private void movePiece(int startRow, int startCol, int endRow, int endCol) {
        boardState[endRow][endCol] = boardState[startRow][startCol];
        boardState[startRow][startCol] = null;
        updateBoard();
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardCells[row][col].removeAll();
                if (boardState[row][col] != null) {
                    String imagePath = boardState[row][col].getImagePath();
                    loadImage(boardCells[row][col], imagePath);
                }
                boardCells[row][col].revalidate();
                boardCells[row][col].repaint();
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

    private void setInitialPiece(int row, int col) {
        if (row == 1) {
            boardState[row][col] = new BlackPawn();
        } else if (row == 6) {
            boardState[row][col] = new WhitePawn();
        } else if (row == 0 || row == 7) {
            boolean isBlack = row == 0;
            switch (col) {
                case 0:
                case 7:
                    boardState[row][col] = isBlack ? new BlackRook() : new WhiteRook();
                    break;
                case 1:
                case 6:
                    boardState[row][col] = isBlack ? new BlackKnight() : new WhiteKnight();
                    break;
                case 2:
                case 5:
                    boardState[row][col] = isBlack ? new BlackBishop() : new WhiteBishop();
                    break;
                case 3:
                    boardState[row][col] = isBlack ? new BlackQueen() : new WhiteQueen();
                    break;
                case 4:
                    boardState[row][col] = isBlack ? new BlackKing() : new WhiteKing();
                    break;
            }
        }

        if (boardState[row][col] != null) {
            loadImage(boardCells[row][col], boardState[row][col].getImagePath());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}
