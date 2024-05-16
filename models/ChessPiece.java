package models;

public abstract class ChessPiece implements Cloneable {
    private boolean isBlack;

    public ChessPiece(boolean isBlack) {
        this.isBlack = isBlack;
    }

    public boolean isBlack() {
        return isBlack;
    }

    public abstract String getImagePath();
    public abstract boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState);

    @Override
    public ChessPiece clone() {
        try {
            return (ChessPiece) super.clone();
        } catch (CloneNotSupportedException e) {
            // This should not happen since we are Cloneable
            throw new RuntimeException("Clone not supported", e);
        }
    }
}
