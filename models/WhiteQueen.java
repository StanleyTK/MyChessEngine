package models;

public class WhiteQueen extends ChessPiece {
    public WhiteQueen() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wQ.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
