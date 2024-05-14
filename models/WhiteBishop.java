package models;

public class WhiteBishop extends ChessPiece {
    public WhiteBishop() {
        super(false);
    }

    @Override
    public String getImagePath() {
        return "public/wB.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (colDiff != rowDiff) return false;

        int rowStep = (endRow - startRow) / rowDiff;
        int colStep = (endCol - startCol) / colDiff;

        int currentRow = startRow + rowStep;
        int currentCol = startCol + colStep;
        while (currentRow != endRow) {
            if (boardState[currentRow][currentCol] != null) {
                return false;
            }
            currentRow += rowStep;
            currentCol += colStep;
        }
        return boardState[endRow][endCol] == null || boardState[endRow][endCol].isBlack();
    }
}
