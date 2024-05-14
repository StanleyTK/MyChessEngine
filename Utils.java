import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.*;

public class Utils {

    public static void loadImage(JPanel square, String imagePath) {
        try {
            Image img = ImageIO.read(new File(imagePath));
            square.setLayout(new BorderLayout());
            JLabel label = new JLabel(new ImageIcon(img));
            square.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMoveLegal(ChessPiece[][] boardState, boolean whiteTurn, int startRow, int startCol, int endRow, int endCol) {
        // Save the original state of the destination cell
        ChessPiece originalPiece = boardState[endRow][endCol];

        // Move the piece to the new position temporarily
        boardState[endRow][endCol] = boardState[startRow][startCol];
        boardState[startRow][startCol] = null;

        // Check if this move puts the current player's king in check
        boolean inCheck = isCheck(boardState, !whiteTurn);

        // Restore the original state of the board
        boardState[startRow][startCol] = boardState[endRow][endCol];
        boardState[endRow][endCol] = originalPiece;

        return !inCheck;
    }

    public static boolean isCheck(ChessPiece[][] boardState, boolean whiteTurn) {
        int kingRow = -1, kingCol = -1;
        outerloop:
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] instanceof WhiteKing && !whiteTurn) {
                    kingRow = row;
                    kingCol = col;
                    break outerloop;
                } else if (boardState[row][col] instanceof BlackKing && whiteTurn) {
                    kingRow = row;
                    kingCol = col;
                    break outerloop;
                }
            }
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] != null && boardState[row][col].isBlack() != whiteTurn &&
                        boardState[row][col].isValidMove(row, col, kingRow, kingCol, boardState)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isInCheck(ChessPiece[][] boardState, boolean whiteTurn, int kingRow, int kingCol, int moveToRow, int moveToCol) {
        ChessPiece originalPiece = boardState[moveToRow][moveToCol];
        boardState[moveToRow][moveToCol] = boardState[kingRow][kingCol];
        boardState[kingRow][kingCol] = null;

        boolean isInCheck = isCheck(boardState, whiteTurn);

        boardState[kingRow][kingCol] = boardState[moveToRow][moveToCol];
        boardState[moveToRow][moveToCol] = originalPiece;

        return isInCheck;
    }

    public static boolean isCheckmate(ChessPiece[][] boardState, boolean whiteTurn) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] != null && boardState[row][col].isBlack() == whiteTurn) {
                    for (int moveToRow = 0; moveToRow < 8; moveToRow++) {
                        for (int moveToCol = 0; moveToCol < 8; moveToCol++) {
                            if (boardState[row][col].isValidMove(row, col, moveToRow, moveToCol, boardState) &&
                                    !isInCheck(boardState, whiteTurn, row, col, moveToRow, moveToCol)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
    public static int[] findKing(ChessPiece[][] boardState, boolean whiteTurn) {
        int[] king = new int[2];
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] instanceof WhiteKing && !whiteTurn) {
                    king[0] = row;
                    king[1] = col;
                    break;
                } else if (boardState[row][col] instanceof BlackKing && whiteTurn) {
                    king[0] = row;
                    king[1] = col;
                    break;
                }
            }
        }
        return king;
    }


    public static void blinkRed(JPanel[][] boardCells, ChessPiece[][] boardState, int row, int col) {

        Timer timer = new Timer(200, null);
        int[] blinkCount = {0};

        timer.addActionListener(e -> {
            Color currentColor = boardCells[row][col].getBackground();
            boardCells[row][col].setBackground(currentColor == Color.RED ? (row + col) % 2 == 0 ? Color.WHITE : Color.GRAY : Color.RED);
            blinkCount[0]++;
            if (blinkCount[0] >= 4) {
                timer.stop();
                boardCells[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    public static void setKingCellRed(JPanel[][] boardCells, ChessPiece[][] boardState, boolean whiteTurn) {
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] instanceof WhiteKing && !whiteTurn) {
                    kingRow = row;
                    kingCol = col;
                    break;
                } else if (boardState[row][col] instanceof BlackKing && whiteTurn) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }
        boardCells[kingRow][kingCol].setBackground(Color.RED);
    }
}
