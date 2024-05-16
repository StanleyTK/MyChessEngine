package models;

public class BlackKing extends ChessPiece {

    private boolean blackKingMoved;

    public BlackKing() {
        super(true);
        blackKingMoved = false;
    }

    public boolean isBlackKingMoved() {
        return blackKingMoved;
    }

    public void setBlackKingMoved(boolean blackKingMoved) {
        this.blackKingMoved = blackKingMoved;
    }

    @Override
    public String getImagePath() {
        return "public/bK.png";
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
            return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
        }

        // Check for castling
        if (!blackKingMoved && startRow == 0 && startCol == 4 && endRow == 0) {
            if (endCol == 2 && boardState[0][0] instanceof BlackRook && !((BlackRook) boardState[0][0]).hasMoved()) {
                // Left castling
                if (boardState[0][1] == null && boardState[0][2] == null && boardState[0][3] == null) {
                    return true;
                }
            } else if (endCol == 6 && boardState[0][7] instanceof BlackRook && !((BlackRook) boardState[0][7]).hasMoved()) {
                // Right castling
                if (boardState[0][5] == null && boardState[0][6] == null) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public BlackKing clone() {
        BlackKing cloned = (BlackKing) super.clone();
        cloned.blackKingMoved = this.blackKingMoved;
        return cloned;
    }
}
