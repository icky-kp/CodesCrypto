import java.util.Scanner;

public class PlayfairCipher {
    private static char[][] keySquare = new char[5][5];
    private static String key = "SCOPE";

    public static void main(String[] args) {
        generateKeySquare(key);
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        plaintext = preparePlaintext(plaintext);
        String ciphertext = encrypt(plaintext);
        System.out.println("Ciphertext: " + ciphertext);

        System.out.println("Enter the ciphertext:");
        String inputCiphertext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");
        String decryptedText = decrypt(inputCiphertext);
        System.out.println("Decrypted Text: " + decryptedText);
    }

    private static void generateKeySquare(String key) {
        boolean[] used = new boolean[26];
        int x = 0, y = 0;

        for (char c : key.toCharArray()) {
            if (!used[c - 'A']) {
                keySquare[x][y++] = c;
                used[c - 'A'] = true;
                if (y == 5) {
                    y = 0;
                    x++;
                }
            }
        }

        for (char c = 'A'; c <= 'Z'; c++) {
            if (c == 'J')
                continue;
            if (!used[c - 'A']) {
                keySquare[x][y++] = c;
                used[c - 'A'] = true;
                if (y == 5) {
                    y = 0;
                    x++;
                }
            }
        }
    }

    // New preparePlaintext function
    private static String preparePlaintext(String plaintext) {
        StringBuilder preparedText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = (i + 1) < plaintext.length() ? plaintext.charAt(i + 1) : 'X';

            if (a == b) {
                preparedText.append(a).append('X');
                i--;
            } else if (a == 'I' && b == 'J') {
                preparedText.append('I').append('P');
            } else if (a == 'J' && b == 'I') {
                preparedText.append('P').append('I');
            } else {
                preparedText.append(a).append(b);
            }
        }

        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }

        return preparedText.toString();
    }

    private static String encrypt(String plaintext) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i += 2) {
            char a = plaintext.charAt(i);
            char b = plaintext.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                ciphertext.append(keySquare[posA[0]][(posA[1] + 1) % 5]);
                ciphertext.append(keySquare[posB[0]][(posB[1] + 1) % 5]);
            } else if (posA[1] == posB[1]) {
                ciphertext.append(keySquare[(posA[0] + 1) % 5][posA[1]]);
                ciphertext.append(keySquare[(posB[0] + 1) % 5][posB[1]]);
            } else {
                ciphertext.append(keySquare[posA[0]][posB[1]]);
                ciphertext.append(keySquare[posB[0]][posA[1]]);
            }
        }
        return ciphertext.toString();
    }

    private static String decrypt(String ciphertext) {
        StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i += 2) {
            char a = ciphertext.charAt(i);
            char b = ciphertext.charAt(i + 1);
            int[] posA = findPosition(a);
            int[] posB = findPosition(b);

            if (posA[0] == posB[0]) {
                plaintext.append(keySquare[posA[0]][(posA[1] + 4) % 5]);
                plaintext.append(keySquare[posB[0]][(posB[1] + 4) % 5]);
            } else if (posA[1] == posB[1]) {
                plaintext.append(keySquare[(posA[0] + 4) % 5][posA[1]]);
                plaintext.append(keySquare[(posB[0] + 4) % 5][posB[1]]);
            } else {
                plaintext.append(keySquare[posA[0]][posB[1]]);
                plaintext.append(keySquare[posB[0]][posA[1]]);
            }
        }
        return plaintext.toString();
    }

    private static int[] findPosition(char c) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (keySquare[i][j] == c) {
                    return new int[] { i, j };
                }
            }
        }
        return null;
    }
}
