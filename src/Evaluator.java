package src;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Evaluator {

    private static src.models.ChessPiece[][] cachedBoardState;
    private static int cachedEvaluation;

    public static int evaluateBoard(src.models.ChessPiece[][] boardState) {
        if (cachedBoardState != null && Arrays.deepEquals(boardState, cachedBoardState)) {
            return cachedEvaluation;
        }

        int totalWhiteScore = 0;
        int totalBlackScore = 0;

        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                src.models.ChessPiece piece = boardState[i][j];
                if (piece != null) {
                    if (!piece.isBlack()) {
                        totalWhiteScore += getPieceValue(piece, i, j, true);
                    } else {
                        totalBlackScore += getPieceValue(piece, i, j, false);
                    }
                }
            }
        }

        int evaluation = totalWhiteScore - totalBlackScore;
        cachedBoardState = copyBoardState(boardState);
        cachedEvaluation = evaluation;

        return evaluation;
    }

    private static int getPieceValue(src.models.ChessPiece piece, int row, int col, boolean isWhite) {
        if (piece instanceof src.models.WhitePawn || piece instanceof src.models.BlackPawn) {
            return isWhite ? calculateScore(src.models.Metrics.PAWN_VAL, src.models.Metrics.PAWN_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.PAWN_VAL, src.models.Metrics.PAWN_B_EVAL[row][col]);
        }
        if (piece instanceof src.models.WhiteKnight || piece instanceof src.models.BlackKnight) {
            return isWhite ? calculateScore(src.models.Metrics.KNIGHT_VAL, src.models.Metrics.KNIGHT_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.KNIGHT_VAL, src.models.Metrics.KNIGHT_B_EVAL[row][col]);
        }
        if (piece instanceof src.models.WhiteBishop || piece instanceof src.models.BlackBishop) {
            return isWhite ? calculateScore(src.models.Metrics.BISHOP_VAL, src.models.Metrics.BISHOP_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.BISHOP_VAL, src.models.Metrics.BISHOP_B_EVAL[row][col]);
        }
        if (piece instanceof src.models.WhiteRook || piece instanceof src.models.BlackRook) {
            return isWhite ? calculateScore(src.models.Metrics.ROOK_VAL, src.models.Metrics.ROOK_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.ROOK_VAL, src.models.Metrics.ROOK_B_EVAL[row][col]);
        }
        if (piece instanceof src.models.WhiteQueen || piece instanceof src.models.BlackQueen) {
            return isWhite ? calculateScore(src.models.Metrics.QUEEN_VAL, src.models.Metrics.QUEEN_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.QUEEN_VAL, src.models.Metrics.QUEEN_B_EVAL[row][col]);
        }
        if (piece instanceof src.models.WhiteKing || piece instanceof src.models.BlackKing) {
            return isWhite ? calculateScore(src.models.Metrics.KING_VAL, src.models.Metrics.KING_W_EVAL[row][col]) :
                    calculateScore(src.models.Metrics.KING_VAL, src.models.Metrics.KING_B_EVAL[row][col]);
        }
        return 0;
    }

    private static int calculateScore(int pieceValue, double positionValue) {
        return (int) (pieceValue + (pieceValue * positionValue));
    }

    public static src.models.ChessPiece[][] copyBoardState(src.models.ChessPiece[][] boardState) {
        src.models.ChessPiece[][] newBoardState = new src.models.ChessPiece[boardState.length][];
        for (int i = 0; i < boardState.length; i++) {
            newBoardState[i] = new src.models.ChessPiece[boardState[i].length];
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] != null) {
                    newBoardState[i][j] = boardState[i][j].clone();
                }
            }
        }
        return newBoardState;
    }

    public static src.models.ChessPiece[][] getBestMove(BaseBoardPanel baseBoardPanel, boolean isWhite) {
        ArrayList<src.models.ChessPiece[][]> allPossibleMoves = baseBoardPanel.generateAllPossibleMoves(baseBoardPanel.getBoardState(), isWhite);

        if (allPossibleMoves.isEmpty()) {
            return null;  // No available moves
        }

        // Randomly select a move from the list of possible moves
        Random random = new Random();
        int randomIndex = random.nextInt(allPossibleMoves.size());
        return allPossibleMoves.get(randomIndex);
    }
}
