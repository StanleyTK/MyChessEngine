package models;

public class WhiteKing extends ChessPiece {
    public WhiteKing() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wK.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
