import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class DSS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter DSA parameters:");

        System.out.print("Enter prime number p: ");
        BigInteger p = new BigInteger(scanner.nextLine());

        System.out.print("Enter prime number q (divisor of p-1): ");
        BigInteger q = new BigInteger(scanner.nextLine());

        System.out.print("Enter h (1 < h < p-1): ");
        BigInteger h = new BigInteger(scanner.nextLine());

        System.out.print("Enter original message: ");
        String message = scanner.nextLine();

        System.out.print("Enter private key x (x < q): ");
        BigInteger x = new BigInteger(scanner.nextLine());

        System.out.print("Enter random integer k (k < q): ");
        BigInteger k = new BigInteger(scanner.nextLine());

        // Calculate g = h^((p-1)/q) mod p
        BigInteger g = h.modPow(p.subtract(BigInteger.ONE).divide(q), p);

        // Calculate public key y = g^x mod p
        BigInteger y = g.modPow(x, p);

        // Hash the message
        BigInteger H;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(message.getBytes());
            H = new BigInteger(1, messageDigest).mod(q);
        } catch (NoSuchAlgorithmException e) {
            // For simplicity, if SHA-1 is not available, use a simple hash
            H = new BigInteger(message.getBytes()).mod(q);
        }

        // Signature Generation (Sender side)
        // Calculate r = (g^k mod p) mod q
        BigInteger r = g.modPow(k, p).mod(q);

        // Calculate k^-1 mod q
        BigInteger kInverse = k.modInverse(q);

        // Calculate s = (k^-1 * (H + x*r)) mod q
        BigInteger s = kInverse.multiply(H.add(x.multiply(r))).mod(q);

        // Signature Verification (Receiver side)
        // Calculate w = s^-1 mod q
        BigInteger w = s.modInverse(q);

        // Calculate u1 = (H * w) mod q
        BigInteger u1 = H.multiply(w).mod(q);

        // Calculate u2 = (r * w) mod q
        BigInteger u2 = r.multiply(w).mod(q);

        // Calculate v = ((g^u1 * y^u2) mod p) mod q
        BigInteger v = g.modPow(u1, p).multiply(y.modPow(u2, p)).mod(p).mod(q);

        // Output results
        System.out.println("\n----- Sender Side -----");
        System.out.println("g = " + g);
        System.out.println("Public key (y) = " + y);
        System.out.println("r = " + r);
        System.out.println("s = " + s);

        System.out.println("\n----- Receiver Side -----");
        System.out.println("w = " + w);
        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
        System.out.println("v = " + v);

        System.out.println("\n----- Verification -----");
        System.out.println("v = " + v);
        System.out.println("r = " + r);
        System.out.println("Signature is " + (v.equals(r) ? "valid" : "invalid"));

        scanner.close();
    }
}
