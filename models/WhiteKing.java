package models;

public class WhiteKing extends ChessPiece {

    private boolean whiteKingMoved;

    public WhiteKing() {
        super(false);
        whiteKingMoved = false;
    }

    public boolean isWhiteKingMoved() {
        return whiteKingMoved;
    }

    public void setWhiteKingMoved(boolean whiteKingMoved) {
        this.whiteKingMoved = whiteKingMoved;
    }

    @Override
    public String getImagePath() {
        return "public/wK.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);

        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // Check for regular moves
        if ((rowDiff == 1 && colDiff == 1) || (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        }

        // Check for castling
        if (!whiteKingMoved && startRow == 7 && startCol == 4 && endRow == 7) {
            if (endCol == 2 && boardState[7][0] instanceof WhiteRook && !((WhiteRook) boardState[7][0]).hasMoved()) {
                // Left castling
                if (boardState[7][1] == null && boardState[7][2] == null && boardState[7][3] == null) {
                    return true;
                }
            } else if (endCol == 6 && boardState[7][7] instanceof WhiteRook && !((WhiteRook) boardState[7][7]).hasMoved()) {
                // Right castling
                if (boardState[7][5] == null && boardState[7][6] == null) {
                    return true;
                }
            }
        }

        return false;
    }
    @Override
    public WhiteKing clone() {
        WhiteKing clone = new WhiteKing();
        clone.whiteKingMoved = this.whiteKingMoved;
        return clone;
    }



}
