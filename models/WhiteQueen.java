package models;

public class WhiteQueen extends ChessPiece {
    public WhiteQueen() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wQ.png";
    }
}
