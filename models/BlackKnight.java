package models;

public class BlackKnight extends ChessPiece {
    public BlackKnight() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bN.png";
    }
}
