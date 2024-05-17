package src;


import src.models.BoardState;

import java.awt.*;

public class PlayerVPlayerPanel extends BaseBoardPanel {

    @Override
    protected void handleCellClick(int row, int col) {
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
                    Utils.blinkRed(boardCells, selectedPiece.x, selectedPiece.y);
                } else {
                    moveHistory.push(new BoardState(boardState, whiteTurn));
                    boardState = movePiece(boardState, selectedPiece.x, selectedPiece.y, row, col, whiteTurn);
                    // handle promotion if pawn gets to the end rank
                    if (isPromotion(boardState, row, col)) {
                        boardState = promotePawn(boardState, row, col);
                    }
                    updateBoard();

                    if (Utils.isCheck(boardState, whiteTurn)) {
                         if (Utils.isCheckmate(boardState, whiteTurn)) {
                            Utils.setKingCellRed(boardCells, boardState, whiteTurn);
                            System.out.println("Checkmate");
                            // TODO frontend for checkmate
                        } else {
                            int[] king = Utils.findKing(boardState, whiteTurn);
                            Utils.blinkRed(boardCells, king[0], king[1]);
                        }
                    }

                    endTurn();
                }
            }
            selectedPiece = null;
        }
    }


}
