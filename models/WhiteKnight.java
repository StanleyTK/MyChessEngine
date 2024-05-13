package models;

public class WhiteKnight extends ChessPiece {
    public WhiteKnight() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wN.png";
    }
}
