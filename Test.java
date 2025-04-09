public class Test {
    private static char[][] matrix = new char[5][5];
    private static String key;

    public static void setKey(String k) {
        key = k.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        String alphabet = "ABCDEFGHIKLMNOPQRSTUVWXYZ";
        String matrixInput = key + alphabet;
        int index = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                while (key.indexOf(matrixInput.charAt(index)) != index) {
                    index++;
                }
                matrix[i][j] = matrixInput.charAt(index++);
            }
        }
    }

    public static String encrypt(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = (i + 1 < plaintext.length()) ? plaintext.charAt(i + 1) : 'X';
            if (a == b)
                b = 'X';
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);
            if (posA[0] == posB[0]) {
                ciphertext.append(matrix[posA[0]][(posA[1] + 1) % 5]);
                ciphertext.append(matrix[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                ciphertext.append(matrix[(posA[0] + 1) % 5][posA[1]]);
                ciphertext.append(matrix[(posB[0] + 1) % 5][posB[1]]);
            } else {
                ciphertext.append(matrix[posA[0]][posB[1]]);
                ciphertext.append(matrix[posB[0]][posA[1]]);
            }
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);
            if (posA[0] == posB[0]) {
                plaintext.append(matrix[posA[0]][(posA[1] + 4) % 5]);
                plaintext.append(matrix[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) {
                plaintext.append(matrix[(posA[0] + 4) % 5][posA[1]]);
                plaintext.append(matrix[(posB[0] + 4) % 5][posB[1]]);
            } else {
                plaintext.append(matrix[posA[0]][posB[1]]);
                plaintext.append(matrix[posB[0]][posA[1]]);
            }
        }
        return plaintext.toString();
    }

    private static int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (matrix[i][j] == c) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }
}
