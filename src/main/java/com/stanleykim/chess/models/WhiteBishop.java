package com.stanleykim.chess.models;

import java.awt.*;
import java.util.ArrayList;

public class WhiteBishop extends ChessPiece {
    public WhiteBishop() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wB.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }
        if (colDiff != rowDiff) return false;

        int rowStep = (endRow - startRow) / rowDiff;
        int colStep = (endCol - startCol) / colDiff;

        int currentRow = startRow + rowStep;
        int currentCol = startCol + colStep;
        while (currentRow != endRow) {
            if (boardState[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
    }

    @Override
    public WhiteBishop clone() {
        return (WhiteBishop) super.clone();
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        int[][] directions = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};

        for (int[] d : directions) {
            int r = row + d[0];
            int c = col + d[1];
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (boardState[r][c] != null) {
                    if (!boardState[r][c].isBlack()) break;
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
