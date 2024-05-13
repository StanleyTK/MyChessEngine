package models;

public class BlackBishop extends ChessPiece {
    public BlackBishop() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bB.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
