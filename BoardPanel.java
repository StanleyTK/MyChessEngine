import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.*;

public class BoardPanel extends JPanel {
    private JPanel[][] boardCells;
    private ChessPiece[][] boardState;
    private Point selectedPiece; // Track the selected piece
    private Color originalColor; // Track the original color of the selected cell
    private boolean whiteTurn = true;
    private boolean whiteKingMoved = false;
    private boolean blackKingMoved = false;
    private boolean[] whiteRookMoved = {false, false}; // [left rook, right rook]
    private boolean[] blackRookMoved = {false, false}; // [left rook, right rook]

    public BoardPanel() {
        setLayout(new GridLayout(8, 8));
        boardCells = new JPanel[8][8];
        boardState = new ChessPiece[8][8];
        initializeBoard();
        setPreferredSize(new Dimension(600, 600));
    }

    private void initializeBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                JPanel cell = new JPanel();
                boardCells[row][col] = cell;
                add(cell);

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
            if (boardState[row][col] != null && boardState[row][col].isBlack() == !whiteTurn) {
                selectedPiece = new Point(row, col);
                originalColor = boardCells[row][col].getBackground();
                boardCells[row][col].setBackground(Color.YELLOW);
                System.out.println("selected piece: " + selectedPiece);
            }
        } else {
            boardCells[selectedPiece.x][selectedPiece.y].setBackground(originalColor);
            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (isCastlingMove(selectedPiece.x, selectedPiece.y, row, col)) {
                    performCastling(selectedPiece.x, selectedPiece.y, row, col);
                } else {
                    movePiece(selectedPiece.x, selectedPiece.y, row, col);
                }
                if (isCheck(whiteTurn)) {
                    if (isCheckmate(whiteTurn)) {
                        System.out.println((whiteTurn ? "Black" : "White") + " is in checkmate!");
                    } else {
                        System.out.println((whiteTurn ? "Black" : "White") + " is in check!");
                    }
                }
                whiteTurn = !whiteTurn;

                System.out.println("moved piece: " + selectedPiece);
            } else {
                System.out.println("unmovable");
            }
            selectedPiece = null; // Reset after move
        }
    }

    private void movePiece(int startRow, int startCol, int endRow, int endCol) {
        if (boardState[startRow][startCol] instanceof WhiteKing) {
            whiteKingMoved = true;
        } else if (boardState[startRow][startCol] instanceof BlackKing) {
            blackKingMoved = true;
        } else if (boardState[startRow][startCol] instanceof WhiteRook) {
            if (startRow == 7 && startCol == 0) {
                whiteRookMoved[0] = true;
            } else if (startRow == 7 && startCol == 7) {
                whiteRookMoved[1] = true;
            }
        } else if (boardState[startRow][startCol] instanceof BlackRook) {
            if (startRow == 0 && startCol == 0) {
                blackRookMoved[0] = true;
            } else if (startRow == 0 && startCol == 7) {
                blackRookMoved[1] = true;
            }
        }

        boardState[endRow][endCol] = boardState[startRow][startCol];
        boardState[startRow][startCol] = null;
        updateBoard();
    }

    private boolean isCastlingMove(int startRow, int startCol, int endRow, int endCol) {
        if (boardState[startRow][startCol] instanceof WhiteKing && startRow == 7 && startCol == 4) {
            if (endRow == 7 && endCol == 6 && !whiteKingMoved && !whiteRookMoved[1]) {
                return canCastle(7, 4, 7, 7);
            } else if (endRow == 7 && endCol == 2 && !whiteKingMoved && !whiteRookMoved[0]) {
                return canCastle(7, 4, 7, 0);
            }
        } else if (boardState[startRow][startCol] instanceof BlackKing && startRow == 0 && startCol == 4) {
            if (endRow == 0 && endCol == 6 && !blackKingMoved && !blackRookMoved[1]) {
                return canCastle(0, 4, 0, 7);
            } else if (endRow == 0 && endCol == 2 && !blackKingMoved && !blackRookMoved[0]) {
                return canCastle(0, 4, 0, 0);
            }
        }
        return false;
    }

    private boolean canCastle(int kingRow, int kingCol, int rookRow, int rookCol) {
        int colStep = (rookCol > kingCol) ? 1 : -1;
        for (int currentCol = kingCol + colStep; currentCol != rookCol; currentCol += colStep) {
            if (boardState[kingRow][currentCol] != null) {
                return false;
            }
        }
        // Ensure king is not in check and does not move through check
        if (isInCheck(kingRow, kingCol, kingRow, kingCol + colStep) || isInCheck(kingRow, kingCol, kingRow, kingCol + 2 * colStep)) {
            return false;
        }
        return true;
    }

    private void performCastling(int kingRow, int kingCol, int endRow, int endCol) {
        if (endCol == 6) {
            // King-side castling
            movePiece(kingRow, kingCol, endRow, endCol);
            movePiece(kingRow, 7, kingRow, 5);
        } else if (endCol == 2) {
            // Queen-side castling
            movePiece(kingRow, kingCol, endRow, endCol);
            movePiece(kingRow, 0, kingRow, 3);
        }
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

    private boolean isInCheck(int kingRow, int kingCol, int moveToRow, int moveToCol) {
        ChessPiece originalPiece = boardState[moveToRow][moveToCol];
        boardState[moveToRow][moveToCol] = boardState[kingRow][kingCol];
        boardState[kingRow][kingCol] = null;

        boolean isInCheck = isCheck(whiteTurn);

        boardState[kingRow][kingCol] = boardState[moveToRow][moveToCol];
        boardState[moveToRow][moveToCol] = originalPiece;

        return isInCheck;
    }

    private boolean isCheck(boolean whiteTurn) {
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

    private boolean isCheckmate(boolean whiteTurn) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] != null && boardState[row][col].isBlack() == whiteTurn) {
                    for (int moveToRow = 0; moveToRow < 8; moveToRow++) {
                        for (int moveToCol = 0; moveToCol < 8; moveToCol++) {
                            if (boardState[row][col].isValidMove(row, col, moveToRow, moveToCol, boardState) &&
                                    !isInCheck(row, col, moveToRow, moveToCol)) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public void resetBoard() {
        removeAll();
        whiteTurn = true;
        boardState = new ChessPiece[8][8];
        initializeBoard();
        revalidate();
        repaint();
    }

    public void handlePreviousMove() {
        // TODO: Implement logic to revert to the previous move
        System.out.println("Previous move");
    }

    public void handleResetBoard() {
        resetBoard();
        System.out.println("Reset board");
    }
}
