package models;

public class WhiteKing extends ChessPiece {
    public WhiteKing() {
        super(false);
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

        if ((rowDiff == 1 && colDiff == 1) || (rowDiff == 1 && colDiff == 0) || (rowDiff == 0 && colDiff == 1)) {
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        }
        return false;
    }
}
