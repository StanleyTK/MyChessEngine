package models;

public class BlackRook extends ChessPiece {
    public BlackRook() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bR.png";
    }
}
