import models.*;

import java.awt.*;
import javax.swing.Timer;

public class PlayerVAIPanel extends BaseBoardPanel {

    public PlayerVAIPanel() {
        super();
    }

    @Override
    protected void handleCellClick(int row, int col) {
        if (selectedPiece == null) {
            if (boardState[row][col] != null && boardState[row][col].isBlack() != whiteTurn) {
                selectedPiece = new Point(row, col);
                originalColor = boardCells[row][col].getBackground();
                boardCells[row][col].setBackground(Color.YELLOW);
            }
        } else {
            boardCells[selectedPiece.x][selectedPiece.y].setBackground(originalColor);

            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (Utils.isMoveLegal(boardState, whiteTurn, selectedPiece.x, selectedPiece.y, row, col)) {
                    moveHistory.push(new BoardState(boardState, whiteTurn));
                    boardState = movePiece(boardState, selectedPiece.x, selectedPiece.y, row, col, whiteTurn);

                    if (isPromotion(boardState, row, col)) {
                        boardState = promotePawn(boardState, row, col);
                    }

                    updateBoard();

                    endTurn();  // End player's turn

                    // AI makes a move after a delay
                    Timer timer = new Timer(500, e -> performAIMove());
                    timer.setRepeats(false);
                    timer.start();
                } else {
                    Utils.blinkRed(boardCells, selectedPiece.x, selectedPiece.y);
                }
            }
            selectedPiece = null;
            boardCells[row][col].setBackground(originalColor);
        }
    }

    private void performAIMove() {
        ChessPiece[][] newBoard = Evaluator.getBestMove(this, whiteTurn);
        if (newBoard != null) {
            boardState = newBoard;
            updateBoard();
            endTurn();
        } else {
            // Handle game over or no moves available
            System.out.println("Game over or no moves available.");
        }
    }
}
