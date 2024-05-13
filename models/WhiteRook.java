package models;

public class WhiteRook extends ChessPiece {
    public WhiteRook() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wR.png";
    }
}
