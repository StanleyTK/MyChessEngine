package models;

public class BlackKing extends ChessPiece {
    public BlackKing() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bK.png";
    }
}
