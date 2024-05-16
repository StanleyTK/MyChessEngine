package models;

public class WhitePawn extends ChessPiece {
    boolean lastMoved;
    public WhitePawn() {
        super(false);
        lastMoved = false;
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
        } else if (Math.abs(startCol - endCol) == 1 && endRow == startRow - 1 && boardState[endRow][endCol] != null && boardState[endRow][endCol].isBlack()) {
            return true;
        }
        return false;
    }

    @Override
    public WhitePawn clone() {
        return (WhitePawn) super.clone();
    }
}
