package models;

public class WhiteRook extends ChessPiece {
    public WhiteRook() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wR.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
