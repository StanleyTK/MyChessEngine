package models;

public abstract class ChessPiece {
    private final boolean isBlack;

    public ChessPiece(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public abstract String getImagePath();

    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState);
}
