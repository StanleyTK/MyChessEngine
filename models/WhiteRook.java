package models;

public class WhiteRook extends ChessPiece {
    public WhiteRook() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wR.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if (rowDiff != 0 && colDiff != 0) {
            return false;
        } else if (rowDiff == 0) {
            int colStep = (endCol - startCol) / colDiff;
            int currentCol = startCol + colStep;
            while (currentCol != endCol) {
                if (boardState[startRow][currentCol] != null) {
                    return false;
                }
                currentCol += colStep;
            }
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        } else {
            int rowStep = (endRow - startRow) / rowDiff;
            int currentRow = startRow + rowStep;
            while (currentRow != endRow) {
                if (boardState[currentRow][startCol] != null) {
                    return false;
                }
                currentRow += rowStep;
            }
            return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
        }


    }
}
