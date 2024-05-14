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

    public static void blinkRed(JPanel[][] boardCells, ChessPiece[][] boardState, boolean whiteTurn) {
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

        Timer timer = new Timer(200, null);
        int[] blinkCount = {0}; // Array to use as a counter
        int finalKingRow = kingRow;
        int finalKingCol = kingCol;

        timer.addActionListener(e -> {
            Color currentColor = boardCells[finalKingRow][finalKingCol].getBackground();
            boardCells[finalKingRow][finalKingCol].setBackground(currentColor == Color.RED ? (finalKingRow + finalKingCol) % 2 == 0 ? Color.WHITE : Color.GRAY : Color.RED);
            blinkCount[0]++;
            if (blinkCount[0] >= 4) {
                timer.stop();
                boardCells[finalKingRow][finalKingCol].setBackground((finalKingRow + finalKingCol) % 2 == 0 ? Color.WHITE : Color.GRAY);
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    public static void setKingCellRed(JPanel[][] boardCells, ChessPiece[][] boardState, boolean whiteTurn) {
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
        boardCells[kingRow][kingCol].setBackground(Color.RED);
    }
}
