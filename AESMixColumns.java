import java.util.Arrays;

public class AESMixColumns {
    private static final int[][] MIX_COLUMNS_MATRIX = {
            { 0x02, 0x03, 0x01, 0x01 },
            { 0x01, 0x02, 0x03, 0x01 },
            { 0x01, 0x01, 0x02, 0x03 },
            { 0x03, 0x01, 0x01, 0x02 }
    };

    public static void main(String[] args) {
        int[][] state = {
                { 0x4a, 0xd6, 0x6b, 0x97 },
                { 0x1a, 0x4c, 0x27, 0x5f },
                { 0x60, 0x44, 0x50, 0xe7 },
                { 0x7d, 0x4f, 0xe0, 0x2e }
        };

        int[][] mixedState = mixColumns(state);
        System.out.println(Arrays.deepToString(mixedState));
    }

    private static int[][] mixColumns(int[][] state) {
        int[][] result = new int[4][4];

        for (int col = 0; col < 4; col++) {
            for (int row = 0; row < 4; row++) {
                result[row][col] = galoisMultiplication(state[0][col], MIX_COLUMNS_MATRIX[row][0]) ^
                        galoisMultiplication(state[1][col], MIX_COLUMNS_MATRIX[row][1]) ^
                        galoisMultiplication(state[2][col], MIX_COLUMNS_MATRIX[row][2]) ^
                        galoisMultiplication(state[3][col], MIX_COLUMNS_MATRIX[row][3]);
            }
        }

        return result;
    }

    private static int galoisMultiplication(int a, int b) {
        int p = 0;
        for (int counter = 0; counter < 8; counter++) {
            if ((b & 1) != 0) {
                p ^= a;
            }
            boolean highBitSet = (a & 0x80) != 0;
            a <<= 1;
            if (highBitSet) {
                a ^= 0x1B;
            }
            b >>= 1;
        }
        return p & 0xFF;
    }
}
