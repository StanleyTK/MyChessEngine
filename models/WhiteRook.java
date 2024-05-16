package models;

import java.awt.*;
import java.util.ArrayList;

public class WhiteRook extends ChessPiece {
    private boolean hasMoved;

    public WhiteRook() {
        super(false);
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public String getImagePath() {
        return "public/wR.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }
        if (rowDiff != 0 && colDiff != 0) {
            return false;
        } else if (rowDiff == 0) {
            int colStep = (endCol - startCol) / colDiff;
            int currentCol = startCol + colStep;
            while (currentCol != endCol) {
                if (boardState[startRow][currentCol] != null) {
                    return false;
                }
                currentCol += colStep;
            }
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        } else {
            int rowStep = (endRow - startRow) / rowDiff;
            int currentRow = startRow + rowStep;
            while (currentRow != endRow) {
                if (boardState[currentRow][startCol] != null) {
                    return false;
                }
                currentRow += rowStep;
            }
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        }
    }

    @Override
    public WhiteRook clone() {
        WhiteRook cloned = (WhiteRook) super.clone();
        cloned.hasMoved = this.hasMoved;
        return cloned;
    }

    @Override
    public ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState) {
        ArrayList<Point> validMoves = new ArrayList<>();
        int[] directions = {-1, 1}; // Directions for movement along rows and columns

        // Check along each column and row from the current position
        for (int d : directions) {
            for (int r = row + d; r >= 0 && r < 8; r += d) {
                if (boardState[r][col] != null) {
                    if (!boardState[r][col].isBlack()) break;
                    validMoves.add(new Point(r, col));
                    break;
                }
                validMoves.add(new Point(r, col));
            }
            for (int c = col + d; c >= 0 && c < 8; c += d) {
                if (boardState[row][c] != null) {
                    if (!boardState[row][c].isBlack()) break;
                    validMoves.add(new Point(row, c));
                    break;
                }
                validMoves.add(new Point(row, c));
            }
        }
        return validMoves;
    }

}
