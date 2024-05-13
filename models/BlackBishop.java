package models;

public class BlackBishop extends ChessPiece {
    public BlackBishop() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bB.png";
    }
}
