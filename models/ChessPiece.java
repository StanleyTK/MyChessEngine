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
}
