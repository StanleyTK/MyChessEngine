package models;

public class BlackRook extends ChessPiece {
    public BlackRook() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bR.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
