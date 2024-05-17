package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class BlackBishop extends ChessPiece {
    public BlackBishop() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "src/main/resources/public/bB.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // Bishops move diagonally, so the absolute difference between rows and columns must be the same
        if (rowDiff != colDiff) {
            return false;
        }


        // Determine the direction of movement
        int rowStep = (endRow - startRow) / rowDiff; // This will be 1 or -1
        int colStep = (endCol - startCol) / colDiff; // This will be 1 or -1

        // Check if there are any pieces in the way
        int currentRow = startRow + rowStep;
        int currentCol = startCol + colStep;
        while (currentRow != endRow && currentCol != endCol) {
            if (boardState[currentRow][currentCol] != null) {
                return false; // There is a piece blocking the path
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
    }

    @Override
    public BlackBishop clone() {
        return (BlackBishop) super.clone();
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Diagonal movements

        for (int[] d : directions) {
            int r = row + d[0];
            int c = col + d[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (boardState[r][c] != null) {
                    if (boardState[r][c].isBlack()) break;
                    validMoves.add(new Point(r, c));
                    break;
                }
                validMoves.add(new Point(r, c));
                r += d[0];
                c += d[1];
            }
        }
        return validMoves;
    }

}
