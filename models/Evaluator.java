package models;

public class Evaluator {

    public static int evaluateBoard(ChessPiece[][] boardState) {
        int totalWhiteScore = 0;
        int totalBlackScore = 0;

        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                ChessPiece piece = boardState[i][j];
                if (piece != null) {
                    if (!piece.isBlack()) {
                        totalWhiteScore += getPieceValue(piece, i, j, true);
                    } else {
                        totalBlackScore += getPieceValue(piece, i, j, false);
                    }
                }
            }
        }

        return totalWhiteScore - totalBlackScore;
    }

    private static int getPieceValue(ChessPiece piece, int row, int col, boolean isWhite) {
        if (piece instanceof WhitePawn || piece instanceof BlackPawn) {
            return isWhite ? calculateScore(Metrics.PAWN_VAL, Metrics.PAWN_W_EVAL[row][col]) :
                    calculateScore(Metrics.PAWN_VAL, Metrics.PAWN_B_EVAL[row][col]);
        }
        if (piece instanceof WhiteKnight || piece instanceof BlackKnight) {
            return isWhite ? calculateScore(Metrics.KNIGHT_VAL, Metrics.KNIGHT_W_EVAL[row][col]) :
                    calculateScore(Metrics.KNIGHT_VAL, Metrics.KNIGHT_B_EVAL[row][col]);
        }
        if (piece instanceof WhiteBishop || piece instanceof BlackBishop) {
            return isWhite ? calculateScore(Metrics.BISHOP_VAL, Metrics.BISHOP_W_EVAL[row][col]) :
                    calculateScore(Metrics.BISHOP_VAL, Metrics.BISHOP_B_EVAL[row][col]);
        }
        if (piece instanceof WhiteRook || piece instanceof BlackRook) {
            return isWhite ? calculateScore(Metrics.ROOK_VAL, Metrics.ROOK_W_EVAL[row][col]) :
                    calculateScore(Metrics.ROOK_VAL, Metrics.ROOK_B_EVAL[row][col]);
        }
        if (piece instanceof WhiteQueen || piece instanceof BlackQueen) {
            return isWhite ? calculateScore(Metrics.QUEEN_VAL, Metrics.QUEEN_W_EVAL[row][col]) :
                    calculateScore(Metrics.QUEEN_VAL, Metrics.QUEEN_B_EVAL[row][col]);
        }
        if (piece instanceof WhiteKing || piece instanceof BlackKing) {
            return isWhite ? calculateScore(Metrics.KING_VAL, Metrics.KING_W_EVAL[row][col]) :
                    calculateScore(Metrics.KING_VAL, Metrics.KING_B_EVAL[row][col]);
        }
        return 0;
    }

    private static int calculateScore(int pieceValue, double positionValue) {
        return (int) (pieceValue + (pieceValue * positionValue));
    }
}
