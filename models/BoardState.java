package models;

import java.util.Arrays;

public class BoardState {
    private ChessPiece[][] boardState;
    private boolean whiteTurn;
    private boolean whiteKingMoved;
    private boolean blackKingMoved;
    private boolean[] whiteRookMoved;
    private boolean[] blackRookMoved;

    public BoardState(ChessPiece[][] boardState, boolean whiteTurn, boolean whiteKingMoved, boolean blackKingMoved, boolean[] whiteRookMoved, boolean[] blackRookMoved) {
        this.boardState = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            this.boardState[i] = Arrays.copyOf(boardState[i], 8);
        }
        this.whiteTurn = whiteTurn;
        this.whiteKingMoved = whiteKingMoved;
        this.blackKingMoved = blackKingMoved;
        this.whiteRookMoved = Arrays.copyOf(whiteRookMoved, 2);
        this.blackRookMoved = Arrays.copyOf(blackRookMoved, 2);
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
}
