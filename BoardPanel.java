import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import models.*;
import java.util.Stack;



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
    private Stack<Move> moveHistory = new Stack<>();



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
//                System.out.println("selected piece: " + selectedPiece);
            }
        } else {
            boardCells[selectedPiece.x][selectedPiece.y].setBackground(originalColor);
            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (!isMoveLegal(selectedPiece.x, selectedPiece.y, row, col)) {
                    System.out.println("Move puts or leaves the king in check, invalid move.");
                } else {
                    if (isCastlingMove(selectedPiece.x, selectedPiece.y, row, col)) {
                        performCastling(selectedPiece.x, selectedPiece.y, row, col);
                    } else {
                        movePiece(selectedPiece.x, selectedPiece.y, row, col);
                    }
                    if (isCheck(whiteTurn)) {
                        if (isCheckmate(whiteTurn)) {
                            System.out.println((!whiteTurn ? "Black" : "White") + " is in checkmate!");
                            setKingCellRed(whiteTurn);
                        } else {
                            System.out.println((whiteTurn ? "Black" : "White") + " is in check!");
                            blinkRed(whiteTurn);
                        }
                    }
                    whiteTurn = !whiteTurn;
                }
            }
            selectedPiece = null; // Reset after move
        }
    }

    private boolean isMoveLegal(int startRow, int startCol, int endRow, int endCol) {
        // Save the original state of the destination cell
        ChessPiece originalPiece = boardState[endRow][endCol];

        // Move the piece to the new position temporarily
        boardState[endRow][endCol] = boardState[startRow][startCol];
        boardState[startRow][startCol] = null;

        // Check if this move puts the current player's king in check
        boolean inCheck = isCheck(!whiteTurn);

        // Restore the original state of the board
        boardState[startRow][startCol] = boardState[endRow][endCol];
        boardState[endRow][endCol] = originalPiece;

        return !inCheck;
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
        moveHistory.push(new Move(startRow, startCol, endRow, endCol, boardState[endRow][endCol]));

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
            if (boardState[kingRow][currentCol] != null || isInCheck(kingRow, kingCol, kingRow, currentCol)) {
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
        moveHistory.clear();

    }




    public void handlePreviousMove() {
        if (!moveHistory.isEmpty()) {
            Move lastMove = moveHistory.pop();
            boardState[lastMove.startRow][lastMove.startCol] = boardState[lastMove.endRow][lastMove.endCol];
            boardState[lastMove.endRow][lastMove.endCol] = lastMove.capturedPiece;
            if (boardState[lastMove.startRow][lastMove.startCol] instanceof WhiteKing) {
                whiteKingMoved = false;
            } else if (boardState[lastMove.startRow][lastMove.startCol] instanceof BlackKing) {
                blackKingMoved = false;
            } else if (boardState[lastMove.startRow][lastMove.startCol] instanceof WhiteRook) {
                if (lastMove.startRow == 7 && lastMove.startCol == 0) {
                    whiteRookMoved[0] = false;
                } else if (lastMove.startRow == 7 && lastMove.startCol == 7) {
                    whiteRookMoved[1] = false;
                }
            } else if (boardState[lastMove.startRow][lastMove.startCol] instanceof BlackRook) {
                if (lastMove.startRow == 0 && lastMove.startCol == 0) {
                    blackRookMoved[0] = false;
                } else if (lastMove.startRow == 0 && lastMove.startCol == 7) {
                    blackRookMoved[1] = false;
                }
            }
            whiteTurn = !whiteTurn;
            updateBoard();
        }
    }

    public void handleResetBoard() {
        resetBoard();
        System.out.println("Reset board");
    }
    private void blinkRed(boolean whiteTurn) {
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


    private void setKingCellRed(boolean whiteTurn) {
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

    private static class Move {
        int startRow, startCol, endRow, endCol;
        ChessPiece capturedPiece;

        Move(int startRow, int startCol, int endRow, int endCol, ChessPiece capturedPiece) {
            this.startRow = startRow;
            this.startCol = startCol;
            this.endRow = endRow;
            this.endCol = endCol;
            this.capturedPiece = capturedPiece;
        }
    }
}
