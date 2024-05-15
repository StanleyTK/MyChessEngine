package models;

public abstract class ChessPiece {
    private boolean isBlack;
    private boolean firstMove;

    public ChessPiece(boolean isBlack) {
        this.isBlack = isBlack;
        this.firstMove = true;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }

    public abstract String getImagePath();

    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState);
}
