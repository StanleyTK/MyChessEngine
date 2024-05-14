import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
            }
        } else {
            boardCells[selectedPiece.x][selectedPiece.y].setBackground(originalColor);
            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (!Utils.isMoveLegal(boardState, whiteTurn, selectedPiece.x, selectedPiece.y, row, col)) {
                    System.out.println("Move puts or leaves the king in check, invalid move.");
                } else {
                    movePiece(selectedPiece.x, selectedPiece.y, row, col, boardState[row][col]);
                    if (isPromotion(row, col)) {
                        promotePawn(row, col);
                    }
                    if (Utils.isCheck(boardState, whiteTurn)) {
                        if (Utils.isCheckmate(boardState, whiteTurn)) {
                            System.out.println((!whiteTurn ? "Black" : "White") + " is in checkmate!");
                            Utils.setKingCellRed(boardCells, boardState, whiteTurn);
                        } else {
                            System.out.println((whiteTurn ? "Black" : "White") + " is in check!");
                            Utils.blinkRed(boardCells, boardState, whiteTurn);
                        }
                    }
                    whiteTurn = !whiteTurn;
                }
            }
            selectedPiece = null; // Reset after move
        }
    }

    private void movePiece(int startRow, int startCol, int endRow, int endCol, ChessPiece chessPiece) {
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

    private boolean isPromotion(int row, int col) {
        return (boardState[row][col] instanceof WhitePawn && row == 0) ||
                (boardState[row][col] instanceof BlackPawn && row == 7);
    }

    private void promotePawn(int row, int col) {
        String[] options = {"Queen", "Rook", "Bishop", "Knight"};
        int choice = JOptionPane.showOptionDialog(this,
                "Promote pawn to:",
                "Pawn Promotion",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        switch (choice) {
            case 0:
                boardState[row][col] = whiteTurn ? new WhiteQueen() : new BlackQueen();
                break;
            case 1:
                boardState[row][col] = whiteTurn ? new WhiteRook() : new BlackRook();
                break;
            case 2:
                boardState[row][col] = whiteTurn ? new WhiteBishop() : new BlackBishop();
                break;
            case 3:
                boardState[row][col] = whiteTurn ? new WhiteKnight() : new BlackKnight();
                break;
            default:
                boardState[row][col] = whiteTurn ? new WhiteQueen() : new BlackQueen();
                break;
        }
        updateBoard();
    }

    private void updateBoard() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                boardCells[row][col].removeAll();
                if (boardState[row][col] != null) {
                    String imagePath = boardState[row][col].getImagePath();
                    Utils.loadImage(boardCells[row][col], imagePath);
                }
                boardCells[row][col].revalidate();
                boardCells[row][col].repaint();
            }
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
            Utils.loadImage(boardCells[row][col], boardState[row][col].getImagePath());
        }
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
}
