package models;

public class WhiteBishop extends ChessPiece {
    public WhiteBishop() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wB.png";
    }
}
