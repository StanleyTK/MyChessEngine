package src.models;

import java.util.Arrays;

public class BoardState {
    private ChessPiece[][] boardState;
    private boolean whiteTurn;


    public BoardState(ChessPiece[][] boardState, boolean whiteTurn) {
        this.boardState = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece original = boardState[i][j];
                if (original != null) {
                    this.boardState[i][j] = original.clone();
                }
            }
        }
        this.whiteTurn = whiteTurn;

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
}
