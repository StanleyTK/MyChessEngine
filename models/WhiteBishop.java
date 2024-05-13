package models;

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
        return false;
    }
}
