package models;

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

}
