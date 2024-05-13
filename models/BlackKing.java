package models;

public class BlackKing extends ChessPiece {
    public BlackKing() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bK.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
