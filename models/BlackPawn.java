package models;

public class BlackPawn extends ChessPiece {
    public BlackPawn() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bP.png";
    }
}
