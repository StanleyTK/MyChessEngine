package models;

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

}
