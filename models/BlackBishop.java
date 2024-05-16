package models;

public class BlackBishop extends ChessPiece {
    public BlackBishop() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bB.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(startRow - endRow);
        int colDiff = Math.abs(startCol - endCol);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }

        // Bishops move diagonally, so the absolute difference between rows and columns must be the same
        if (rowDiff != colDiff) {
            return false;
        }


        // Determine the direction of movement
        int rowStep = (endRow - startRow) / rowDiff; // This will be 1 or -1
        int colStep = (endCol - startCol) / colDiff; // This will be 1 or -1

        // Check if there are any pieces in the way
        int currentRow = startRow + rowStep;
        int currentCol = startCol + colStep;
        while (currentRow != endRow && currentCol != endCol) {
            if (boardState[currentRow][currentCol] != null) {
                return false; // There is a piece blocking the path
            }
            currentRow += rowStep;
            currentCol += colStep;
        }

        return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
    }

    @Override
    public BlackBishop clone() {
        return (BlackBishop) super.clone();
    }
}
