package com.stanleykim.chess;


import com.stanleykim.chess.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Evaluator {

    private static ChessPiece[][] cachedBoardState;
    private static int cachedEvaluation;

    public static int evaluateBoard(ChessPiece[][] boardState) {
        if (cachedBoardState != null && Arrays.deepEquals(boardState, cachedBoardState)) {
            return cachedEvaluation;
        }

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

        int evaluation = totalWhiteScore - totalBlackScore;
        cachedBoardState = copyBoardState(boardState);
        cachedEvaluation = evaluation;

        return evaluation;
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

    public static ChessPiece[][] copyBoardState(ChessPiece[][] boardState) {
        ChessPiece[][] newBoardState = new ChessPiece[boardState.length][];
        for (int i = 0; i < boardState.length; i++) {
            newBoardState[i] = new ChessPiece[boardState[i].length];
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] != null) {
                    newBoardState[i][j] = boardState[i][j].clone();
                }
            }
        }
        return newBoardState;
    }

    public static ChessPiece[][] getBestMove(BaseBoardPanel baseBoardPanel, boolean isWhite) {
        ArrayList<ChessPiece[][]> allPossibleMoves = baseBoardPanel.generateAllPossibleMoves(baseBoardPanel.getBoardState(), isWhite);

        if (allPossibleMoves.isEmpty()) {
            return null;  // No available moves
        }

        // Randomly select a move from the list of possible moves
        Random random = new Random();
        int randomIndex = random.nextInt(allPossibleMoves.size());
        return allPossibleMoves.get(randomIndex);
    }
}
