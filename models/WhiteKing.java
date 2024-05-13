package models;

public class WhiteKing extends ChessPiece {
    public WhiteKing() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wK.png";
    }
}
