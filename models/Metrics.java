package models;

public class Metrics {
    private static final int PAWN_VAL = 100;
    private static final int BISHOP_VAL = 330;
    private static final int KNIGHT_VAL = 330;
    private static final int ROOK_VAL = 500;
    private static final int QUEEN_VAL = 900;
    private static final int KING_VAL = 20000;

    private static final double[][] PAWN_W_EVAL = {
            {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00},
            {0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50, 0.50},
            {0.10, 0.10, 0.20, 0.30, 0.30, 0.20, 0.10, 0.10},
            {0.05, 0.05, 0.10, 0.25, 0.25, 0.10, 0.05, 0.05},
            {0.00, 0.00, 0.00, 0.20, 0.20, 0.00, 0.00, 0.00},
            {0.05, -0.05, -0.10, 0.00, 0.00, -0.10, -0.05, 0.05},
            {0.05, 0.10, 0.10, -0.20, -0.20, 0.10, 0.10, 0.05},
            {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00},
    };

    private static final double[][] KNIGHT_W_EVAL = {
            {-0.50, -0.40, -0.30, -0.30, -0.30, -0.30, -0.40, -0.50},
            {-0.40, -0.20, 0.00, 0.00, 0.00, 0.00, -0.20, -0.40},
            {-0.30, 0.00, 0.10, 0.15, 0.15, 0.10, 0.00, -0.30},
            {-0.30, 0.05, 0.15, 0.20, 0.20, 0.15, 0.05, -0.30},
            {-0.30, 0.00, 0.15, 0.20, 0.20, 0.15, 0.00, -0.30},
            {-0.30, 0.05, 0.10, 0.15, 0.15, 0.10, 0.05, -0.30},
            {-0.40, -0.20, 0.00, 0.05, 0.05, 0.00, -0.20, -0.40},
            {-0.50, -0.40, -0.30, -0.30, -0.30, -0.30, -0.40, -0.50},
    };

    private static final double[][] BISHOP_W_EVAL = {
            {-0.20, -0.10, -0.10, -0.10, -0.10, -0.10, -0.10, -0.20},
            {-0.10, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.10},
            {-0.10, 0.00, 0.05, 0.10, 0.10, 0.05, 0.00, -0.10},
            {-0.10, 0.05, 0.05, 0.10, 0.10, 0.05, 0.05, -0.10},
            {-0.10, 0.00, 0.10, 0.10, 0.10, 0.10, 0.00, -0.10},
            {-0.10, 0.10, 0.10, 0.10, 0.10, 0.10, 0.10, -0.10},
            {-0.10, 0.05, 0.00, 0.00, 0.00, 0.00, 0.05, -0.10},
            {-0.20, -0.10, -0.10, -0.10, -0.10, -0.10, -0.10, -0.20},
    };

    private static final double[][] ROOK_W_EVAL = {
            {0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00},
            {0.05, 0.10, 0.10, 0.10, 0.10, 0.10, 0.10, 0.05},
            {-0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.05},
            {-0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.05},
            {-0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.05},
            {-0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.05},
            {-0.05, 0.00, 0.00, 0.00, 0.00, 0.00, 0.00, -0.05},
            {0.00, 0.00, 0.00, 0.05, 0.05, 0.00, 0.00, 0.00},
    };

    private static final double[][] QUEEN_W_EVAL = {
            {-0.20, -0.10, -0.10, -0.05, -0.05, -0.10, -0.10, -0.20},
            {-0.10, 0.00, 0.05, 0.00, 0.00, 0.00, 0.00, -0.10},
            {-0.10, 0.05, 0.05, 0.05, 0.05, 0.05, 0.00, -0.10},
            {0.00, 0.00, 0.05, 0.05, 0.05, 0.05, 0.00, -0.10},
            {0.00, 0.00, 0.05, 0.05, 0.05, 0.05, 0.00, -0.10},
            {-0.10, 0.05, 0.05, 0.05, 0.05, 0.05, 0.00, -0.10},
            {-0.10, 0.00, 0.05, 0.00, 0.00, 0.00, 0.00, -0.10},
            {-0.20, -0.10, -0.10, -0.05, -0.05, -0.10, -0.10, -0.20},
    };

    private static final double[][] KING_W_EVAL = {
            {-0.30, -0.40, -0.40, -0.50, -0.50, -0.40, -0.40, -0.30},
            {-0.30, -0.40, -0.40, -0.50, -0.50, -0.40, -0.40, -0.30},
            {-0.30, -0.40, -0.40, -0.50, -0.50, -0.40, -0.40, -0.30},
            {-0.30, -0.40, -0.40, -0.50, -0.50, -0.40, -0.40, -0.30},
            {-0.20, -0.30, -0.30, -0.40, -0.40, -0.30, -0.30, -0.20},
            {-0.10, -0.20, -0.20, -0.20, -0.20, -0.20, -0.20, -0.10},
            {0.20, 0.20, 0.00, 0.00, 0.00, 0.00, 0.20, 0.20},
            {0.20, 0.30, 0.10, 0.00, 0.00, 0.10, 0.30, 0.20},
    };

    private static final double[][] PAWN_B_EVAL = reverseArray(PAWN_W_EVAL);
    private static final double[][] KNIGHT_B_EVAL = reverseArray(KNIGHT_W_EVAL);
    private static final double[][] BISHOP_B_EVAL = reverseArray(BISHOP_W_EVAL);
    private static final double[][] ROOK_B_EVAL = reverseArray(ROOK_W_EVAL);
    private static final double[][] QUEEN_B_EVAL = reverseArray(QUEEN_W_EVAL);
    private static final double[][] KING_B_EVAL = reverseArray(KING_W_EVAL);

    private static double[][] reverseArray(double[][] array) {
        double[][] reversedArray = new double[array.length][];
        for (int i = 0; i < array.length; i++) {
            reversedArray[i] = array[array.length - 1 - i].clone();
        }
        return reversedArray;
    }

}
