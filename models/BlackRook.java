package models;

public class BlackRook extends ChessPiece {
    private boolean hasMoved;

    public BlackRook() {
        super(true);
        this.hasMoved = false;
    }

    public boolean hasMoved() {
        return hasMoved;
    }

    public void setMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public String getImagePath() {
        return "public/bR.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);
        if (rowDiff == 0 && colDiff == 0) {
            return false;
        }
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
            return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
        } else {
            int rowStep = (endRow - startRow) / rowDiff;
            int currentRow = startRow + rowStep;
            while (currentRow != endRow) {
                if (boardState[currentRow][startCol] != null) {
                    return false;
                }
                currentRow += rowStep;
            }
            return boardState[endRow][endCol] == null || !boardState[endRow][endCol].isBlack();
        }
    }

    @Override
    public BlackRook clone() {
        BlackRook cloned = (BlackRook) super.clone();
        cloned.hasMoved = this.hasMoved;
        return cloned;
    }
}
