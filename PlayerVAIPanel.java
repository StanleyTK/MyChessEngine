import models.*;

import java.awt.*;
import javax.swing.Timer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
            resetBoardColors();  // Reset colors before updating the move

            if (boardState[selectedPiece.x][selectedPiece.y].isValidMove(selectedPiece.x, selectedPiece.y, row, col, boardState)) {
                if (Utils.isMoveLegal(boardState, whiteTurn, selectedPiece.x, selectedPiece.y, row, col)) {
                    moveHistory.push(new BoardState(boardState, whiteTurn));
                    boardState = movePiece(boardState, selectedPiece.x, selectedPiece.y, row, col, whiteTurn);

                    if (isPromotion(boardState, row, col)) {
                        boardState = promotePawn(boardState, row, col);
                    }

                    updateBoard();
                    checkGameStatus();

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
        }
    }

    private void performAIMove() {
        ChessPiece[][] newBoard = Evaluator.getBestMove(this, whiteTurn);
        if (newBoard != null) {
            boardState = newBoard;
            updateBoard();
            checkGameStatus();
            endTurn();
        } else {
            System.out.println("Game over or no moves available.");
        }
    }

    private void checkGameStatus() {
        if (Utils.isCheck(boardState, whiteTurn)) {
            if (Utils.isCheckmate(boardState, whiteTurn)) {
                Utils.setKingCellRed(boardCells, boardState, whiteTurn); // Set the king cell red on checkmate
                JOptionPane.showMessageDialog(this, "Checkmate! Game over.");
            } else {
                int[] kingPosition = Utils.findKing(boardState, whiteTurn);
                Utils.blinkRed(boardCells, kingPosition[0], kingPosition[1]); // Blink king position on check
            }
        }
    }

    private void resetBoardColors() {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Color color = (row + col) % 2 == 0 ? Color.WHITE : Color.GRAY;
                boardCells[row][col].setBackground(color);
            }
        }
    }
}
