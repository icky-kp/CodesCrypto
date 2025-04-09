import java.util.Scanner;

public class HillCipher {
    private static int[][] keyMatrix;
    private static int[] messageVector;
    private static int[] cipherMatrix;
    private static int[] plainMatrix;
    private static int[][] inverseMatrix;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the message to encrypt (e.g., sathish):");
        String message = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        // Ensure the message length is even by adding an 'A' if needed
        if (message.length() % 2 != 0) {
            message += "A";
        }

        keyMatrix = new int[2][2];
        inverseMatrix = new int[2][2];
        messageVector = new int[2];
        cipherMatrix = new int[2];
        plainMatrix = new int[2];

        // Input the key matrix from the user
        System.out.println("Enter the 2x2 key matrix (4 letters, e.g., dbgf):");
        String key = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        if (key.length() != 4) {
            System.out.println("Invalid key length. Key must be 4 letters.");
            return;
        }

        // Fill the key matrix with numerical values
        keyMatrix[0][0] = key.charAt(0) - 'A';
        keyMatrix[0][1] = key.charAt(1) - 'A';
        keyMatrix[1][0] = key.charAt(2) - 'A';
        keyMatrix[1][1] = key.charAt(3) - 'A';

        // Calculate the inverse key matrix
        calculateInverseMatrix();

        System.out.println("Original Message: " + message);

        // Encrypt the message
        String encryptedMessage = encrypt(message);
        System.out.println("Encrypted Message: " + encryptedMessage);

        // Decrypt the message
        String decryptedMessage = decrypt(encryptedMessage);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }

    public static void calculateInverseMatrix() {
        int determinant = keyMatrix[0][0] * keyMatrix[1][1] - keyMatrix[0][1] * keyMatrix[1][0];
        determinant = (determinant % 26 + 26) % 26; // Make sure determinant is positive
        int inverseDet = modInverse(determinant, 26);

        if (inverseDet == -1) {
            System.out.println("Inverse does not exist for the given key matrix.");
            System.exit(1);
        }

        inverseMatrix[0][0] = (keyMatrix[1][1] * inverseDet) % 26;
        inverseMatrix[0][1] = (-keyMatrix[0][1] * inverseDet + 26) % 26;
        inverseMatrix[1][0] = (-keyMatrix[1][0] * inverseDet + 26) % 26;
        inverseMatrix[1][1] = (keyMatrix[0][0] * inverseDet) % 26;
    }

    public static int modInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1;
    }

    public static String encrypt(String message) {
        StringBuilder encryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i += 2) {
            messageVector[0] = message.charAt(i) - 'A';
            messageVector[1] = message.charAt(i + 1) - 'A';

            cipherMatrix[0] = (keyMatrix[0][0] * messageVector[0] + keyMatrix[0][1] * messageVector[1]) % 26;
            cipherMatrix[1] = (keyMatrix[1][0] * messageVector[0] + keyMatrix[1][1] * messageVector[1]) % 26;

            encryptedMessage.append((char) (cipherMatrix[0] + 'A'));
            encryptedMessage.append((char) (cipherMatrix[1] + 'A'));
        }
        return encryptedMessage.toString();
    }

    public static String decrypt(String message) {
        StringBuilder decryptedMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i += 2) {
            cipherMatrix[0] = message.charAt(i) - 'A';
            cipherMatrix[1] = message.charAt(i + 1) - 'A';

            plainMatrix[0] = (inverseMatrix[0][0] * cipherMatrix[0] + inverseMatrix[0][1] * cipherMatrix[1]) % 26;
            plainMatrix[1] = (inverseMatrix[1][0] * cipherMatrix[0] + inverseMatrix[1][1] * cipherMatrix[1]) % 26;

            decryptedMessage.append((char) (plainMatrix[0] + 'A'));
            decryptedMessage.append((char) (plainMatrix[1] + 'A'));
        }
        return decryptedMessage.toString();
    }
}
