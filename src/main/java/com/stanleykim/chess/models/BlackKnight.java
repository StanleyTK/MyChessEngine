package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class BlackKnight extends ChessPiece {
    public BlackKnight() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bN.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        if ((rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2)) {
            return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
        }
        return false;
    }
    @Override
    public BlackKnight clone() {
        return (BlackKnight) super.clone();
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
                    (boardState[newRow][newCol] == null || !boardState[newRow][newCol].isBlack())) {
                validMoves.add(new Point(newRow, newCol));
            }
        }
        return validMoves;
    }

}
