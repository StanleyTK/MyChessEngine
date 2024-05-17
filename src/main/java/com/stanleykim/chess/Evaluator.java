package com.stanleykim.chess;

import com.stanleykim.chess.models.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class Evaluator {
    private static final int DEPTH = 4;
    private static ChessPiece[][] cachedBoardState;
    private static double cachedEvaluation;

    public static double evaluateBoard(ChessPiece[][] boardState) {
        if (cachedBoardState != null && Arrays.deepEquals(boardState, cachedBoardState)) {
            return cachedEvaluation;
        }

        double totalWhiteScore = 0;
        double totalBlackScore = 0;

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

        double evaluation = totalWhiteScore - totalBlackScore;
        cachedBoardState = copyBoardState(boardState);
        cachedEvaluation = evaluation;

        return evaluation;
    }

    private static double getPieceValue(ChessPiece piece, int row, int col, boolean isWhite) {
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

    private static double calculateScore(double pieceValue, double positionValue) {
        return (pieceValue + (pieceValue * positionValue));
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
        ArrayList<ChessPiece[][]> allPossibleMoves = generateAllPossibleMoves(baseBoardPanel, baseBoardPanel.getBoardState(), isWhite);

        if (allPossibleMoves.isEmpty()) {
            return null;
        }

        ChessPiece[][] bestMove = allPossibleMoves.get(0);
        double bestScore = isWhite ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        int i = 0;
        System.out.println("\nStart analyzing: ");
        for (ChessPiece[][] move : allPossibleMoves) {
            double score = minmax(baseBoardPanel, move, DEPTH, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, !isWhite);
            System.out.println(++i + ". Score: " + score);
            if ((isWhite && score > bestScore) || (!isWhite && score < bestScore)) {
                bestScore = score;
                bestMove = move;
            }
        }
        return bestMove;
    }

    private static double minmax(BaseBoardPanel baseBoardPanel, ChessPiece[][] board, int depth, double alpha, double beta, boolean isWhite) {
        if (depth == 0) {
            return evaluateBoard(board);
        }
        ArrayList<ChessPiece[][]> allPossibleMoves = generateAllPossibleMoves(baseBoardPanel, board, isWhite);

        if (isWhite) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (ChessPiece[][] move : allPossibleMoves) {
                double eval = minmax(baseBoardPanel, move, depth - 1, alpha, beta, false);
                maxEval = Math.max(maxEval, eval);
                alpha = Math.max(alpha, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxEval;
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (ChessPiece[][] move : allPossibleMoves) {
                double eval = minmax(baseBoardPanel, move, depth - 1, alpha, beta, true);
                minEval = Math.min(minEval, eval);
                beta = Math.min(beta, eval);
                if (beta <= alpha) {
                    break;
                }
            }
            return minEval;
        }
    }

    public static ArrayList<ChessPiece[][]> generateAllPossibleMoves(BaseBoardPanel baseBoardPanel, ChessPiece[][] board, boolean isWhiteTurn) {
        ArrayList<ChessPiece[][]> allPossibleMoves = new ArrayList<>();

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.isBlack() == !isWhiteTurn) {
                    ArrayList<Point> validMoves = piece.getValidMoves(row, col, board);
                    for (Point move : validMoves) {
                        ChessPiece[][] newBoardState = Evaluator.copyBoardState(board);
                        if (!Utils.isMoveLegal(newBoardState, isWhiteTurn, row, col, move.x, move.y)) {
                            continue;
                        }
                        newBoardState = baseBoardPanel.movePiece(newBoardState, row, col, move.x, move.y, isWhiteTurn);
                        if (baseBoardPanel.isPromotion(newBoardState, move.x, move.y)) {
                            addPromotedMoves(allPossibleMoves, newBoardState, move, isWhiteTurn);
                        } else {
                            allPossibleMoves.add(newBoardState);
                        }
                    }
                }
            }
        }
        return allPossibleMoves;
    }

    private static void addPromotedMoves(ArrayList<ChessPiece[][]> allPossibleMoves, ChessPiece[][] boardState, Point move, boolean isWhiteTurn) {
        ChessPiece[][] newBoardState1 = Evaluator.copyBoardState(boardState);
        newBoardState1[move.x][move.y] = isWhiteTurn ? new WhiteQueen() : new BlackQueen();
        allPossibleMoves.add(newBoardState1);

        ChessPiece[][] newBoardState2 = Evaluator.copyBoardState(boardState);
        newBoardState2[move.x][move.y] = isWhiteTurn ? new WhiteRook() : new BlackRook();
        allPossibleMoves.add(newBoardState2);

        ChessPiece[][] newBoardState3 = Evaluator.copyBoardState(boardState);
        newBoardState3[move.x][move.y] = isWhiteTurn ? new WhiteBishop() : new BlackBishop();
        allPossibleMoves.add(newBoardState3);

        ChessPiece[][] newBoardState4 = Evaluator.copyBoardState(boardState);
        newBoardState4[move.x][move.y] = isWhiteTurn ? new WhiteKnight() : new BlackKnight();
        allPossibleMoves.add(newBoardState4);
    }
}
