import java.util.Scanner;

public class VigenereCipher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the plaintext:");
        String plaintext = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        System.out.println("Enter the key:");
        String key = scanner.nextLine().toUpperCase().replaceAll("[^A-Z]", "");

        String ciphertext = encrypt(plaintext, key);
        String decryptedtext = decrypt(ciphertext, key);

        System.out.println("Ciphertext: " + ciphertext);
        System.out.println("Decrypted Text: " + decryptedtext);
    }

    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            int plainChar = plaintext.charAt(i) - 'A';
            int keyChar = key.charAt(i % key.length()) - 'A';
            char encryptedChar = (char) ((plainChar + keyChar) % 26 + 'A');
            ciphertext.append(encryptedChar);
        }
        return ciphertext.toString();
    }

    public static String decrypt(String ciphertext, String key) {
        StringBuilder decryptedtext = new StringBuilder();
        for (int i = 0; i < ciphertext.length(); i++) {
            int cipherChar = ciphertext.charAt(i) - 'A';
            int keyChar = key.charAt(i % key.length()) - 'A';
            char decryptedChar = (char) ((cipherChar - keyChar + 26) % 26 + 'A');
            decryptedtext.append(decryptedChar);
        }
        return decryptedtext.toString();
    }
}
