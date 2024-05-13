package models;

public class BlackQueen extends ChessPiece {
    public BlackQueen() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bQ.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        return false;
    }
}
