package com.stanleykim.chess;


import com.stanleykim.chess.models.*;

import java.awt.*;
import java.util.concurrent.ExecutionException;
import javax.swing.Timer;
import javax.swing.*;


public class PlayerVAIPanel extends BaseBoardPanel {

    public PlayerVAIPanel() {
        super();
    }
    private ProgressPanel progressPanel;

    public void setProgressPanel(ProgressPanel progressPanel) {
        this.progressPanel = progressPanel;
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
        if (progressPanel != null) {
            System.out.println("AI starts thinking"); // Debug statement
            progressPanel.setStatusText("AI is thinking...");
        }

        // Simulating a delay for AI computation (e.g., heavy computation)
        new SwingWorker<ChessPiece[][], Void>() {
            @Override
            protected ChessPiece[][] doInBackground() throws Exception {
                // Simulate computation delay
                Thread.sleep(2000); // Simulate delay
                return Evaluator.getBestMove(PlayerVAIPanel.this, whiteTurn);
            }

            @Override
            protected void done() {
                ChessPiece[][] newBoard;
                try {
                    newBoard = get();
                    if (newBoard != null) {
                        boardState = newBoard;
                        updateBoard();
                        checkGameStatus();
                        endTurn();
                    } else {
                        System.out.println("Game over or no moves available.");
                    }

                    if (progressPanel != null) {
                        System.out.println("AI ends thinking"); // Debug statement
                        progressPanel.setStatusText("Evaluation updated");
                    }
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        }.execute();
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
