package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class WhitePawn extends ChessPiece {
    private boolean lastMoved;

    public WhitePawn() {
        super(false);  // Assuming white pieces are false
        lastMoved = false;
    }

    public boolean getLastMoved() {
        return lastMoved;
    }

    public void setLastMoved(boolean lastMoved) {
        this.lastMoved = lastMoved;
    }

    @Override
    public String getImagePath() {
        return "public/wP.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        if (startCol == endCol && boardState[endRow][endCol] == null) {
            if (startRow == 6 && endRow == 4 && boardState[5][endCol] == null) {
                return true;
            }
            return endRow == startRow - 1;
        } else if (Math.abs(startCol - endCol) == 1 && endRow == startRow - 1) {
            if (boardState[endRow][endCol] == null && boardState[startRow][endCol] instanceof BlackPawn) {
                BlackPawn targetPawn = (BlackPawn) boardState[startRow][endCol];
                if (targetPawn.getLastMoved()) {
                    return true;
                }
            }
            return boardState[endRow][endCol] != null && boardState[endRow][endCol].isBlack();
        }
        return false;
    }

    @Override
    public WhitePawn clone() {
        WhitePawn clone = new WhitePawn();
        clone.lastMoved = this.lastMoved;
        return clone;
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        if (row > 0 && boardState[row - 1][col] == null) {
            validMoves.add(new Point(row - 1, col));
            if (row == 6 && boardState[row - 2][col] == null) {
                validMoves.add(new Point(row - 2, col));
            }
        }
        // Captures
        if (row > 0 && col > 0 && boardState[row - 1][col - 1] != null && boardState[row - 1][col - 1].isBlack()) {
            validMoves.add(new Point(row - 1, col - 1));
        }
        if (row > 0 && col < 7 && boardState[row - 1][col + 1] != null && boardState[row - 1][col + 1].isBlack()) {
            validMoves.add(new Point(row - 1, col + 1));
        }
        // En Passant
        if (row == 3) {
            if (col > 0 && boardState[row][col - 1] instanceof BlackPawn && ((BlackPawn)boardState[row][col - 1]).getLastMoved()) {
                validMoves.add(new Point(row - 1, col - 1));
            }
            if (col < 7 && boardState[row][col + 1] instanceof BlackPawn && ((BlackPawn)boardState[row][col + 1]).getLastMoved()) {
                validMoves.add(new Point(row - 1, col + 1));
            }
        }
        return validMoves;
    }

}
