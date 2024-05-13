package models;

public class WhitePawn extends ChessPiece {
    public WhitePawn() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wP.png";
    }
}
