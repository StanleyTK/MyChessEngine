package models;

public class BlackQueen extends ChessPiece {
    public BlackQueen() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bQ.png";
    }
}
