package models;

public class BlackQueen extends ChessPiece {
    public BlackQueen() {
        super(true);
    }

    @Override
    public String getImagePath() {
        return "public/bQ.png";
    }

    @Override
    public boolean isValidMove(int startRow, int startCol, int endRow, int endCol, ChessPiece[][] boardState) {
        int rowDiff = Math.abs(endRow - startRow);
        int colDiff = Math.abs(endCol - startCol);

        if (rowDiff == colDiff) {
            // Diagonal move (like a bishop)
            int rowStep = (endRow - startRow) / rowDiff;
            int colStep = (endCol - startCol) / colDiff;

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

            // Check the final cell (must be empty or contain an opponent's piece)
            ChessPiece targetPiece = boardState[endRow][endCol];
            return targetPiece == null || !targetPiece.isBlack();
        } else if (rowDiff == 0 || colDiff == 0) {
            // Horizontal or vertical move (like a rook)
            if (rowDiff == 0) {
                // Horizontal move
                int colStep = (endCol - startCol) / colDiff;
                for (int currentCol = startCol + colStep; currentCol != endCol; currentCol += colStep) {
                    if (boardState[startRow][currentCol] != null) {
                        return false; // Path is blocked
                    }
                }
            } else {
                // Vertical move
                int rowStep = (endRow - startRow) / rowDiff;
                for (int currentRow = startRow + rowStep; currentRow != endRow; currentRow += rowStep) {
                    if (boardState[currentRow][startCol] != null) {
                        return false; // Path is blocked
                    }
                }
            }

            ChessPiece targetPiece = boardState[endRow][endCol];
            return targetPiece == null || !targetPiece.isBlack();
        }

        return false;
    }
}