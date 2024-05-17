import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import models.*;

import java.util.ArrayList;
import java.util.Stack;

public abstract class BaseBoardPanel extends JPanel {
    protected JPanel[][] boardCells;
    public ChessPiece[][] boardState;
    protected Point selectedPiece;
    protected Color originalColor;
    protected boolean whiteTurn = true;
    protected Stack<BoardState> moveHistory = new Stack<>();


    public BaseBoardPanel() {
        setLayout(new GridLayout(8, 8));
        boardCells = new JPanel[8][8];
        boardState = new ChessPiece[8][8];
        initializeBoard();
        setPreferredSize(new Dimension(600, 600));
    }

    protected abstract void handleCellClick(int row, int col);

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

    protected void updateBoard() {
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

    protected ChessPiece[][] movePiece(ChessPiece[][] board, int startRow, int startCol, int endRow, int endCol, boolean whiteTurn) {
        ChessPiece piece = board[startRow][startCol];

        // for castling
        moveSpecialPiece(startRow, startCol);
        // move rook if needed
        if ((startRow == 7 && startCol == 4 && endRow == 7 && (endCol == 2 || endCol == 6)) ||
                (startRow == 0 && startCol == 4 && endRow == 0 && (endCol == 2 || endCol == 6))) {
            if (boardState[endRow][endCol] instanceof WhiteKing || boardState[endRow][endCol] instanceof BlackKing ) {
                handleCastling(startRow, startCol, endRow, endCol);
            }
        }

        // en passant logic
        handleEnPassant(startRow, startCol, endRow, endCol, whiteTurn, piece);
        board[endRow][endCol] = piece;
        board[startRow][startCol] = null;

        // handle promotion if pawn gets to the end rank
        if (isPromotion(board, endRow, endCol)) {
            promotePawn(board, endRow, endCol);
        }
        return board;


    }

    protected void handleEnPassant(int startRow, int startCol, int endRow, int endCol, boolean whiteTurn, ChessPiece piece) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (boardState[row][col] instanceof WhitePawn && whiteTurn) {
                    ((WhitePawn) boardState[row][col]).setLastMoved(false);
                }
                else if (boardState[row][col] instanceof BlackPawn && !whiteTurn) {
                    ((BlackPawn) boardState[row][col]).setLastMoved(false);
                }
            }
        }
        if (piece instanceof WhitePawn && Math.abs(startRow - endRow) == 2) {
            ((WhitePawn) piece).setLastMoved(true);
        }
        else if (piece instanceof BlackPawn && Math.abs(startRow - endRow) == 2) {
            ((BlackPawn) piece).setLastMoved(true);
        }

        if (piece instanceof WhitePawn && Math.abs(startRow - endRow) == 1 &&
                Math.abs(startCol - endCol) == 1 && boardState[endRow][endCol] == null
                && boardState[startRow][endCol] instanceof BlackPawn) {
            boardState[startRow][endCol] = null;
        }
        else if (piece instanceof BlackPawn && Math.abs(startRow - endRow) == 1 &&
                Math.abs(startCol - endCol) == 1 && boardState[endRow][endCol] == null
                && boardState[startRow][endCol] instanceof WhitePawn) {
            boardState[startRow][endCol] = null;
        }

    }


    private void moveSpecialPiece(int startRow, int startCol) {
        ChessPiece piece = boardState[startRow][startCol];

        if (piece instanceof WhiteKing) {
            ((WhiteKing) piece).setWhiteKingMoved(true);
        } else if (piece instanceof BlackKing) {
            ((BlackKing) piece).setBlackKingMoved(true);
        } else if (piece instanceof WhiteRook) {
            ((WhiteRook) piece).setMoved(true);
        } else if (piece instanceof BlackRook) {
            ((BlackRook) piece).setMoved(true);
        }
    }

    protected boolean isPromotion(ChessPiece[][] board, int row, int col) {
        return (board[row][col] instanceof WhitePawn && row == 0) ||
                (board[row][col] instanceof BlackPawn && row == 7);
    }

    protected void promotePawn(ChessPiece[][] board, int row, int col) {
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
                board[row][col] = whiteTurn ? new WhiteQueen() : new BlackQueen();
                break;
            case 1:
                board[row][col] = whiteTurn ? new WhiteRook() : new BlackRook();
                break;
            case 2:
                board[row][col] = whiteTurn ? new WhiteBishop() : new BlackBishop();
                break;
            case 3:
                board[row][col] = whiteTurn ? new WhiteKnight() : new BlackKnight();
                break;
            default:
                board[row][col] = whiteTurn ? new WhiteQueen() : new BlackQueen();
                break;
        }
        updateBoard();
    }

    public void handlePreviousMove() {
        if (!moveHistory.isEmpty()) {
            BoardState previousState = moveHistory.pop();
            this.boardState = previousState.getBoardState();

            this.whiteTurn = previousState.isWhiteTurn();
            updateBoard();
        }
    }


    public void handleResetBoard() {
        removeAll();
        whiteTurn = true;
        boardState = new ChessPiece[8][8];
        initializeBoard();
        revalidate();
        repaint();
        moveHistory.clear();
    }

    public int getBoardEvaluation() {
        return Evaluator.evaluateBoard(boardState);
    }

    public ChessPiece[][] getBoardState() {
        return boardState;
    }

    void handleCastling(int startRow, int startCol, int endRow, int endCol) {
        int rookStartCol = (endCol == 2) ? 0 : 7;
        int rookEndCol = (endCol == 2) ? 3 : 5;

        ChessPiece rook = boardState[startRow][rookStartCol];

        boardState[startRow][rookEndCol] = rook;
        boardState[startRow][rookStartCol] = null;

        if (rook instanceof WhiteRook) {
            ((WhiteRook) rook).setMoved(true);
        } else if (rook instanceof BlackRook) {
            ((BlackRook) rook).setMoved(true);
        }


    }


    protected void endTurn() {
        whiteTurn = !whiteTurn;
        updateBoard();
    }


    public ArrayList<ChessPiece[][]> generateAllPossibleMoves(boolean isWhiteTurn) {
        ArrayList<ChessPiece[][]> allPossibleMoves = new ArrayList<>();

        for (int row = 0; row < boardState.length; row++) {
            for (int col = 0; col < boardState[row].length; col++) {
                ChessPiece piece = boardState[row][col];
                if (piece != null && piece.isBlack() == !isWhiteTurn) {
                    ArrayList<Point> validMoves = piece.getValidMoves(row, col, boardState);
                    for (Point move : validMoves) {
                        ChessPiece[][] newBoardState = Evaluator.copyBoardState(boardState);

                        if (!Utils.isMoveLegal(newBoardState, whiteTurn, row, col, move.x, move.y)) {
                            continue;
                        }
                        newBoardState = movePiece(newBoardState, row, col, move.x, move.y, isWhiteTurn);

                        allPossibleMoves.add(newBoardState);
                    }
                }
            }
        }

        return allPossibleMoves;
    }

}
