package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class BlackPawn extends ChessPiece {
    private boolean lastMoved;

    public BlackPawn() {
        super(true);  // Assuming black pieces are true
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
        return "/public/bP.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        if (startCol == endCol && boardState[endRow][endCol] == null) {
            // Double move from starting position
            if (startRow == 1 && endRow == 3 && boardState[2][endCol] == null) {
                return true;
            }
            return endRow == startRow + 1;
        } else if (Math.abs(startCol - endCol) == 1 && endRow == startRow + 1) {
            // En Passant capture
            if (boardState[endRow][endCol] == null && boardState[startRow][endCol] instanceof WhitePawn) {
                WhitePawn targetPawn = (WhitePawn) boardState[startRow][endCol];
                if (targetPawn.getLastMoved()) {
                    return true;
                }
            }
            return boardState[endRow][endCol] != null && !boardState[endRow][endCol].isBlack();
        }
        return false;
    }

    @Override
    public BlackPawn clone() {
        BlackPawn clone = new BlackPawn();
        clone.lastMoved = this.lastMoved;
        return clone;
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        // Normal move forward
        if (row < 7 && boardState[row + 1][col] == null) {
            validMoves.add(new Point(row + 1, col));
            // Initial double move
            if (row == 1 && boardState[row + 2][col] == null) {
                validMoves.add(new Point(row + 2, col));
            }
        }
        // Captures
        if (row < 7 && col > 0 && boardState[row + 1][col - 1] != null && !boardState[row + 1][col - 1].isBlack()) {
            validMoves.add(new Point(row + 1, col - 1));
        }
        if (row < 7 && col < 7 && boardState[row + 1][col + 1] != null && !boardState[row + 1][col + 1].isBlack()) {
            validMoves.add(new Point(row + 1, col + 1));
        }
        // En Passant
        if (row == 4) {
            if (col > 0 && boardState[row][col - 1] instanceof WhitePawn && ((WhitePawn)boardState[row][col - 1]).getLastMoved()) {
                validMoves.add(new Point(row + 1, col - 1));
            }
            if (col < 7 && boardState[row][col + 1] instanceof WhitePawn && ((WhitePawn)boardState[row][col + 1]).getLastMoved()) {
                validMoves.add(new Point(row + 1, col + 1));
            }
        }
        return validMoves;
    }

}