package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class WhiteKnight extends ChessPiece {
    public WhiteKnight() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wN.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        }
        return false;
    }

    @Override
    public WhiteKnight clone() {
        return (WhiteKnight) super.clone();
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        int[][] moves = {
                {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
                {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
        };

        for (int[] move : moves) {
            int newRow = row + move[0];
            int newCol = col + move[1];
            if (newRow >= 0 && newRow < 8 && newCol >= 0 && newCol < 8 &&
                    (boardState[newRow][newCol] == null || boardState[newRow][newCol].isBlack())) {
                validMoves.add(new Point(newRow, newCol));
            }
        }
        return validMoves;
    }


}
