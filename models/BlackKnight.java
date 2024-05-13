package models;

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
        return false;
    }
}
