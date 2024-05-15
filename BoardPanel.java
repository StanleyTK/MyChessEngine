import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import models.*;
import java.util.Stack;

public class BoardPanel extends JPanel {
    private JPanel[][] boardCells;
    public ChessPiece[][] boardState;
    private Point selectedPiece;
    private Color originalColor;
    private boolean whiteTurn = true;
    private boolean whiteKingMoved = false;
    private boolean blackKingMoved = false;
    private boolean[] whiteRookMoved = {false, false};
    private boolean[] blackRookMoved = {false, false};
    private Stack<BoardState> moveHistory = new Stack<>();
    private Point lastMoveStart = null;
    private Point lastMoveEnd = null;


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
            if (isCastling(selectedPiece.x, selectedPiece.y, row, col)) {
                moveHistory.push(new BoardState(boardState, whiteTurn, whiteKingMoved, blackKingMoved, whiteRookMoved, blackRookMoved, lastMoveStart, lastMoveEnd));
                handleCastling(selectedPiece.x, selectedPiece.y, row, col);
            } else if (isEnPassant(selectedPiece.x, selectedPiece.y, row, col)) {
                moveHistory.push(new BoardState(boardState, whiteTurn, whiteKingMoved, blackKingMoved, whiteRookMoved, blackRookMoved, lastMoveStart, lastMoveEnd));
                handleEnPassant(selectedPiece.x, selectedPiece.y, row, col);
            } else if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (!Utils.isMoveLegal(boardState, whiteTurn, selectedPiece.x, selectedPiece.y, row, col)) {
                    Utils.blinkRed(boardCells, selectedPiece.x, selectedPiece.y);
                } else {
                    moveHistory.push(new BoardState(boardState, whiteTurn, whiteKingMoved, blackKingMoved, whiteRookMoved, blackRookMoved, lastMoveStart, lastMoveEnd));
                    movePiece(selectedPiece.x, selectedPiece.y, row, col, boardState[row][col]);
                    if (isPromotion(row, col)) {
                        promotePawn(row, col);
                    }
                    if (Utils.isCheck(boardState, whiteTurn)) {
                        if (Utils.isCheckmate(boardState, whiteTurn)) {
//                            System.out.println((!whiteTurn ? "Black" : "White") + " is in checkmate!");
                            Utils.setKingCellRed(boardCells, boardState, whiteTurn);
                        } else {
//                            System.out.println((whiteTurn ? "Black" : "White") + " is in check!");
                            int[] king = Utils.findKing(boardState, whiteTurn);
                            Utils.blinkRed(boardCells, king[0], king[1]);
                        }
                    }
                    whiteTurn = !whiteTurn;
                }
            }
            selectedPiece = null;
        }
    }

    private boolean isEnPassant(int startX, int startY, int endX, int endY) {
        if (boardState[startX][startY] instanceof WhitePawn && startX == 3 && endX == 2) {
            return lastMoveEnd != null && lastMoveEnd.x == 3 && lastMoveEnd.y == endY &&
                    boardState[lastMoveEnd.x][lastMoveEnd.y] instanceof BlackPawn &&
                    lastMoveStart.x == 1 && lastMoveStart.y == endY;
        } else if (boardState[startX][startY] instanceof BlackPawn && startX == 4 && endX == 5) {
            return lastMoveEnd != null && lastMoveEnd.x == 4 && lastMoveEnd.y == endY &&
                    boardState[lastMoveEnd.x][lastMoveEnd.y] instanceof WhitePawn &&
                    lastMoveStart.x == 6 && lastMoveStart.y == endY;
        }
        return false;
    }

    private void handleEnPassant(int startX, int startY, int endX, int endY) {
        boardState[endX][endY] = boardState[startX][startY];
        boardState[startX][startY] = null;

        if (endX == 2) {
            boardState[3][endY] = null; // Capturing black pawn
        } else if (endX == 5) {
            boardState[4][endY] = null; // Capturing white pawn
        }
        updateBoard();
        whiteTurn = !whiteTurn;
    }




    private void handleCastling(int x, int y, int row, int col) {
        if (row == 7 && col == 6) {
            // White kingside castling
            boardState[7][6] = boardState[7][4];
            boardState[7][4] = null;
            boardState[7][5] = boardState[7][7];
            boardState[7][7] = null;
            whiteKingMoved = true;
            whiteRookMoved[1] = true;
        } else if (row == 7 && col == 2) {
            // White queenside castling
            boardState[7][2] = boardState[7][4];
            boardState[7][4] = null;
            boardState[7][3] = boardState[7][0];
            boardState[7][0] = null;
            whiteKingMoved = true;
            whiteRookMoved[0] = true;
        } else if (row == 0 && col == 6) {
            // Black kingside castling
            boardState[0][6] = boardState[0][4];
            boardState[0][4] = null;
            boardState[0][5] = boardState[0][7];
            boardState[0][7] = null;
            blackKingMoved = true;
            blackRookMoved[1] = true;
        } else if (row == 0 && col == 2) {
            // Black queenside castling
            boardState[0][2] = boardState[0][4];
            boardState[0][4] = null;
            boardState[0][3] = boardState[0][0];
            boardState[0][0] = null;
            blackKingMoved = true;
            blackRookMoved[0] = true;
        }
        updateBoard();
        whiteTurn = !whiteTurn;
        selectedPiece = null;
    }

    private boolean isCastling(int x, int y, int row, int col) {
        ChessPiece piece = boardState[x][y];
        if (piece instanceof WhiteKing && !whiteKingMoved && x == 7 && y == 4) {
            // White kingside castling
            if (col == 6 && !whiteRookMoved[1] && boardState[7][5] == null && boardState[7][6] == null &&
                    isCastlingValid(x, y, 7, 5) && isCastlingValid(x, y, 7, 6)) {
                return true;
            }
            // White queenside castling
            if (col == 2 && !whiteRookMoved[0] && boardState[7][1] == null && boardState[7][2] == null && boardState[7][3] == null &&
                    isCastlingValid(x, y, 7, 3) && isCastlingValid(x, y, 7, 2)) {
                return true;
            }
        }
        if (piece instanceof BlackKing && !blackKingMoved && x == 0 && y == 4) {
            // Black kingside castling
            if (col == 6 && !blackRookMoved[1] && boardState[0][5] == null && boardState[0][6] == null &&
                    isCastlingValid(x, y, 0, 5) && isCastlingValid(x, y, 0, 6)) {
                return true;
            }
            // Black queenside castling
            if (col == 2 && !blackRookMoved[0] && boardState[0][1] == null && boardState[0][2] == null && boardState[0][3] == null &&
                    isCastlingValid(x, y, 0, 3) && isCastlingValid(x, y, 0, 2)) {
                return true;
            }
        }
        return false;
    }

    private boolean isCastlingValid(int startX, int startY, int endX, int endY) {
        // Temporarily make the move
        if (Utils.isCheck(boardState, !whiteTurn)) {
            return false;
        }
        ChessPiece temp = boardState[endX][endY];
        boardState[endX][endY] = boardState[startX][startY];
        boardState[startX][startY] = null;

        boolean isValid = !Utils.isCheck(boardState, !whiteTurn);

        // Revert the move
        boardState[startX][startY] = boardState[endX][endY];
        boardState[endX][endY] = temp;

        return isValid;
    }

    private void movePiece(int startRow, int startCol, int endRow, int endCol, ChessPiece chessPiece) {
        lastMoveStart = new Point(startRow, startCol);
        lastMoveEnd = new Point(endRow, endCol);

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

    public void handlePreviousMove() {
        if (!moveHistory.isEmpty()) {
            BoardState previousState = moveHistory.pop();
            this.boardState = previousState.getBoardState();
            this.whiteTurn = previousState.isWhiteTurn();
            this.whiteKingMoved = previousState.isWhiteKingMoved();
            this.blackKingMoved = previousState.isBlackKingMoved();
            this.whiteRookMoved = previousState.getWhiteRookMoved();
            this.blackRookMoved = previousState.getBlackRookMoved();
            this.lastMoveStart = previousState.getLastMoveStart();
            this.lastMoveEnd = previousState.getLastMoveEnd();
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

}
