package models;

public class Move {

    public int startRow;
    public int startCol;
    public int endRow;
    public int endCol;
    public ChessPiece capturedPiece;

    public Move(int startRow, int startCol, int endRow, int endCol, ChessPiece capturedPiece) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.capturedPiece = capturedPiece;
    }

}
