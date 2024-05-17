package src.models;

import java.awt.*;
import java.util.ArrayList;

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
            throw new RuntimeException("clone error :(", e);
        }
    }

    public abstract ArrayList<Point> getValidMoves(int row, int col, ChessPiece[][] boardState);

}
