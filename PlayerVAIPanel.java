import models.*;

import javax.swing.*;
import java.awt.*;

public class PlayerVAIPanel extends BaseBoardPanel {

    @Override
    protected void handleCellClick(int row, int col) {
        if (whiteTurn) {
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
                        Utils.blinkRed(boardCells, selectedPiece.x, selectedPiece.y);
                } else {
                    moveHistory.push(new BoardState(boardState, whiteTurn, whiteKingMoved, blackKingMoved, whiteRookMoved, blackRookMoved, lastMoveStart, lastMoveEnd));
                    movePiece(selectedPiece.x, selectedPiece.y, row, col, boardState[row][col]);
                    if (isPromotion(row, col)) {
                        promotePawn(row, col);
                    }
                    if (Utils.isCheck(boardState, whiteTurn)) {
                        if (Utils.isCheckmate(boardState, whiteTurn)) {
                            Utils.setKingCellRed(boardCells, boardState, whiteTurn);
                        } else {
                            int[] king = Utils.findKing(boardState, whiteTurn);
                            Utils.blinkRed(boardCells, king[0], king[1]);
                        }
                    }
                    whiteTurn = !whiteTurn;
                    handleAIMove();
                }
            }
            selectedPiece = null;
        }
    }

    private void handleAIMove() {
        // Simple AI logic to choose the best move
        Move bestMove = Evaluator.getBestMove(boardState, false);
        if (bestMove != null) {
            moveHistory.push(new BoardState(boardState, whiteTurn, whiteKingMoved, blackKingMoved, whiteRookMoved, blackRookMoved, lastMoveStart, lastMoveEnd));
            movePiece(bestMove.getStartRow(), bestMove.getStartCol(), bestMove.getEndRow(), bestMove.getEndCol(), boardState[bestMove.getEndRow()][bestMove.getEndCol()]);
            if (isPromotion(bestMove.getEndRow(), bestMove.getEndCol())) {
                promotePawn(bestMove.getEndRow(), bestMove.getEndCol());
            }
            if (Utils.isCheck(boardState, whiteTurn)) {
                if (Utils.isCheckmate(boardState, whiteTurn)) {
                    Utils.setKingCellRed(boardCells, boardState, whiteTurn);
                } else {
                    int[] king = Utils.findKing(boardState, whiteTurn);
                    Utils.blinkRed(boardCells, king[0], king[1]);
                }
            }
            whiteTurn = !whiteTurn;
        }
    }


}

