import java.util.Scanner;
import java.math.BigInteger;
//RSA code


public class RSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Get prime inputs
        System.out.println("Enter first prime (p):");
        int p = sc.nextInt();
        System.out.println("Enter second prime (q):");
        int q = sc.nextInt();

        // Calculate base parameters
        int n = p * q;
        int phi = (p - 1) * (q - 1);

        // Find public exponent
        int e = 2;
        while (e < phi && gcd(e, phi) != 1)
            e++;

        // Find private exponent
        int d = 1;
        while ((d * e) % phi != 1)
            d++;

        // Display cryptographic parameters
        System.out.println("\nCalculated Values:");
        System.out.println("n = p*q = " + n);
        System.out.println("φ(n) = (p-1)(q-1) = " + phi);
        System.out.println("Public Key (e) = " + e);
        System.out.println("Private Key (d) = " + d);

        // Message processing
        sc.nextLine(); // Clear buffer
        System.out.println("\nEnter message (letters only):");
        String message = sc.nextLine().toUpperCase();

        // Encrypt message
        String cipherText = encrypt(message, e, n);
        System.out.println("\nEncrypted Message: " + cipherText);

        // Decrypt message
        String decryptedText = decrypt(cipherText, d, n);
        System.out.println("Decrypted Message: " + decryptedText);
    }

    private static String encrypt(String message, int e, int n) {
        StringBuilder cipher = new StringBuilder();
        for (char ch : message.toCharArray()) {
            int m = ch - 'A' + 1; // Convert to 1-26
            BigInteger c = BigInteger.valueOf(m).modPow(BigInteger.valueOf(e), BigInteger.valueOf(n));
            cipher.append(c).append(" ");
        }
        return cipher.toString().trim();
    }

    private static String decrypt(String cipherText, int d, int n) {
        StringBuilder plain = new StringBuilder();
        String[] numbers = cipherText.split(" ");
        for (String num : numbers) {
            BigInteger c = new BigInteger(num);
            int m = c.modPow(BigInteger.valueOf(d),
                    BigInteger.valueOf(n))
                    .intValue();
            plain.append((char) (m + 'A' - 1));
        }
        return plain.toString();
    }

    private static int gcd(int a, int b) {
        return b == 0 ? a : gcd(b, a % b);
    }
}
