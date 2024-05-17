package com.stanleykim.chess;
import com.stanleykim.chess.models.*;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
public class Utils {

    public static void loadImage(JPanel square, String imagePath) {
        try {
            // Access the image as a resource stream
            InputStream imgStream = ChessGame.class.getResourceAsStream(imagePath);
            if (imgStream == null) {
                System.err.println("Could not find image: " + imagePath);
                return;
            }
            Image img = ImageIO.read(imgStream);

            square.setLayout(new BorderLayout());
            JLabel label = new JLabel(new ImageIcon(img));
            square.add(label);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isMoveLegal(ChessPiece[][] board, boolean whiteTurn, int startRow, int startCol, int endRow, int endCol) {
        if ((startRow == 7 && startCol == 4 && endRow == 7 && (endCol == 2 || endCol == 6)) ||
                (startRow == 0 && startCol == 4 && endRow == 0 && (endCol == 2 || endCol == 6))) {
            if (board[startRow][startCol] instanceof WhiteKing || board[startRow][startCol] instanceof BlackKing ) {
                if (isCheck(board, !whiteTurn)) {
                    return false;
                }
                if (isRookAttacked(board, !whiteTurn, startRow, startCol, endRow, endCol)) {
                    return false;
                }
            }
        }
        ChessPiece originalPiece = board[endRow][endCol];

        board[endRow][endCol] = board[startRow][startCol];
        board[startRow][startCol] = null;

        boolean inCheck = isCheck(board, !whiteTurn);

        board[startRow][startCol] = board[endRow][endCol];
        board[endRow][endCol] = originalPiece;

        return !inCheck;
    }

    public static boolean isRookAttacked(ChessPiece[][] board, boolean whiteTurn, int startRow, int startCol, int endRow, int endCol) {
        int rookStartCol = (endCol == 2) ? 0 : 7;  // 0 for queen-side castling, 7 for king-side castling
        int rookEndCol = (endCol == 2) ? 3 : 5;    // Rook moves to these columns after castling

        if (isPositionUnderAttack(board, whiteTurn, startRow, rookStartCol)) {
            return true;
        }

        ChessPiece rook = board[startRow][rookStartCol];
        board[startRow][rookStartCol] = null;
        board[startRow][rookEndCol] = rook;

        boolean isAttacked = isPositionUnderAttack(board, whiteTurn, startRow, rookEndCol);

        board[startRow][rookStartCol] = rook;
        board[startRow][rookEndCol] = null;

        return isAttacked;
    }

    /**
     * Helper method to determine if a position is under attack by any opponent's piece.
     */
    private static boolean isPositionUnderAttack(ChessPiece[][] board, boolean whiteTurn, int row, int col) {
        for (int r = 0; r < 8; r++) {
            for (int c = 0; c < 8; c++) {
                ChessPiece piece = board[r][c];
                if (piece != null && piece.isBlack() != whiteTurn && piece.isValidMove(r, c, row, col, board)) {
                    return true;
                }
            }
        }
        return false;
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

    public static void blinkRed(JPanel[][] boardCells, int row, int col) {

        Timer timer = new Timer(100, null);
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
