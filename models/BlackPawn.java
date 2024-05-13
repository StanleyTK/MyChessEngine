package models;

public class BlackPawn extends ChessPiece {
    public BlackPawn() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bP.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        if (startCol == endCol && boardState[endRow][endCol] == null) {
            if (startRow == 1 && endRow == 3 && boardState[2][endCol] == null) {
                return true; // Double move from starting position
            }
            return endRow == startRow + 1;
        }
        return false;
    }
}
