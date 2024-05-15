package models;

import java.util.Arrays;
import java.awt.Point;

public class BoardState {
    private ChessPiece[][] boardState;
    private boolean whiteTurn;
    private boolean whiteKingMoved;
    private boolean blackKingMoved;
    private boolean[] whiteRookMoved;
    private boolean[] blackRookMoved;
    private Point lastMoveStart;
    private Point lastMoveEnd;

    public BoardState(ChessPiece[][] boardState, boolean whiteTurn, boolean whiteKingMoved, boolean blackKingMoved, boolean[] whiteRookMoved, boolean[] blackRookMoved, Point lastMoveStart, Point lastMoveEnd) {
        this.boardState = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            this.boardState[i] = Arrays.copyOf(boardState[i], 8);
        }
        this.whiteTurn = whiteTurn;
        this.whiteKingMoved = whiteKingMoved;
        this.blackKingMoved = blackKingMoved;
        this.whiteRookMoved = Arrays.copyOf(whiteRookMoved, 2);
        this.blackRookMoved = Arrays.copyOf(blackRookMoved, 2);
        this.lastMoveStart = lastMoveStart != null ? new Point(lastMoveStart) : null;
        this.lastMoveEnd = lastMoveEnd != null ? new Point(lastMoveEnd) : null;
    }

    public ChessPiece[][] getBoardState() {
        ChessPiece[][] copy = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            copy[i] = Arrays.copyOf(this.boardState[i], 8);
        }
        return copy;
    }

    public boolean isWhiteTurn() {
        return whiteTurn;
    }

    public boolean isWhiteKingMoved() {
        return whiteKingMoved;
    }

    public boolean isBlackKingMoved() {
        return blackKingMoved;
    }

    public boolean[] getWhiteRookMoved() {
        return Arrays.copyOf(whiteRookMoved, 2);
    }

    public boolean[] getBlackRookMoved() {
        return Arrays.copyOf(blackRookMoved, 2);
    }

    public Point getLastMoveStart() {
        return lastMoveStart != null ? new Point(lastMoveStart) : null;
    }

    public Point getLastMoveEnd() {
        return lastMoveEnd != null ? new Point(lastMoveEnd) : null;
    }
}
