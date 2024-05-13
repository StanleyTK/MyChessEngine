import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChessGame {
    private JFrame frame;
    private JPanel boardPanel;
    private JPanel[][] boardCells;
    private ChessPiece[][] boardState;
    private Point selectedPiece; // Track the selected piece

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
                    public void mouseClicked(MouseEvent e) {
                        if (selectedPiece == null) {
                            selectedPiece = new Point(finalRow, finalCol);
                        } else {
                            movePiece(finalRow, finalCol);
                            selectedPiece = null; // Reset after move
                        }
                    }
                });
            }
        }
    }



    private void movePiece(int targetRow, int targetCol) {
        if (selectedPiece != null && boardState[targetRow][targetCol] == null) {
            boardState[targetRow][targetCol] = boardState[selectedPiece.x][selectedPiece.y];
            boardState[selectedPiece.x][selectedPiece.y] = null;
            updateBoard();
        }
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardCells[row][col].removeAll();
                if (boardState[row][col] != null) {
                    String imagePath = getImagePathForPiece(boardState[row][col]);
                    loadImage(boardCells[row][col], imagePath);
                }
                boardCells[row][col].revalidate();
                boardCells[row][col].repaint();
            }
        }
    }

    private String getImagePathForPiece(ChessPiece piece) {
        switch (piece) {
            case BLACK_KING: return "public/bK.png";
            case BLACK_QUEEN: return "public/bQ.png";
            case BLACK_ROOK: return "public/bR.png";
            case BLACK_BISHOP: return "public/bB.png";
            case BLACK_KNIGHT: return "public/bN.png";
            case BLACK_PAWN: return "public/bP.png";
            case WHITE_KING: return "public/wK.png";
            case WHITE_QUEEN: return "public/wQ.png";
            case WHITE_ROOK: return "public/wR.png";
            case WHITE_BISHOP: return "public/wB.png";
            case WHITE_KNIGHT: return "public/wN.png";
            case WHITE_PAWN: return "public/wP.png";
            default: return null;
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
        String imagePath = "";
        if (row == 1) {
            // Place black pawns
            boardState[row][col] = ChessPiece.BLACK_PAWN;
            imagePath = "public/bP.png";
        } else if (row == 6) {
            // Place white pawns
            boardState[row][col] = ChessPiece.WHITE_PAWN;
            imagePath = "public/wP.png";
        } else if (row == 0 || row == 7) {
            // Place major pieces on the first and last rows
            ChessPiece piece = null;
            boolean isBlack = row == 0;
            switch (col) {
                case 0:
                case 7:
                    piece = isBlack ? ChessPiece.BLACK_ROOK : ChessPiece.WHITE_ROOK;
                    imagePath = isBlack ? "public/bR.png" : "public/wR.png";
                    break;
                case 1:
                case 6:
                    piece = isBlack ? ChessPiece.BLACK_KNIGHT : ChessPiece.WHITE_KNIGHT;
                    imagePath = isBlack ? "public/bN.png" : "public/wN.png";
                    break;
                case 2:
                case 5:
                    piece = isBlack ? ChessPiece.BLACK_BISHOP : ChessPiece.WHITE_BISHOP;
                    imagePath = isBlack ? "public/bB.png" : "public/wB.png";
                    break;
                case 3:
                    piece = isBlack ? ChessPiece.BLACK_QUEEN : ChessPiece.WHITE_QUEEN;
                    imagePath = isBlack ? "public/bQ.png" : "public/wQ.png";
                    break;
                case 4:
                    piece = isBlack ? ChessPiece.BLACK_KING : ChessPiece.WHITE_KING;
                    imagePath = isBlack ? "public/bK.png" : "public/wK.png";
                    break;
            }
            boardState[row][col] = piece;
        }

        // Load the image if there's a piece at the current position
        if (!imagePath.isEmpty()) {
            loadImage(boardCells[row][col], imagePath);
        }
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }

    enum ChessPiece {
        BLACK_KING, BLACK_QUEEN, BLACK_ROOK, BLACK_BISHOP, BLACK_KNIGHT, BLACK_PAWN,
        WHITE_KING, WHITE_QUEEN, WHITE_ROOK, WHITE_BISHOP, WHITE_KNIGHT, WHITE_PAWN
    }
}