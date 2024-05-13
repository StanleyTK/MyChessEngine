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
        return false;
    }

}
