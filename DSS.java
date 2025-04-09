import java.math.BigInteger;
import java.util.Scanner;

public class DSS {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter DSA parameters:");

        System.out.print("Enter prime number p: ");
        BigInteger primeP = new BigInteger(scanner.nextLine());

        System.out.print("Enter prime number q (divisor of p-1): ");
        BigInteger primeQ = new BigInteger(scanner.nextLine());

        System.out.print("Enter h (1 < h < p-1): ");
        BigInteger baseH = new BigInteger(scanner.nextLine());

        System.out.print("Enter original message: ");
        String message = scanner.nextLine();

        System.out.print("Enter private key x (x < q): ");
        BigInteger privateKeyX = new BigInteger(scanner.nextLine());

        System.out.print("Enter random integer k (k < q): ");
        BigInteger randomK = new BigInteger(scanner.nextLine());

        // Calculate r = h^((p-1)/q) mod p
        BigInteger r = baseH.modPow(primeP.subtract(BigInteger.ONE).divide(primeQ), primeP);

        // Calculate public key y = r^x mod p
        BigInteger publicKeyY = r.modPow(privateKeyX, primeP);

        // Use the plaintext message directly as w (hash of the message)
        BigInteger hashW = new BigInteger(message.getBytes()).mod(primeQ);

        // Signature Generation (Sender side)
        // Calculate a = (r^k mod p) mod q
        BigInteger signatureA = r.modPow(randomK, primeP).mod(primeQ);

        // Calculate k^-1 mod q
        BigInteger kInverse = randomK.modInverse(primeQ);

        // Calculate b = (k^-1 * (w + x*a)) mod q
        BigInteger signatureB = kInverse.multiply(hashW.add(privateKeyX.multiply(signatureA))).mod(primeQ);

        // Signature Verification (Receiver side)
        // Calculate z = b^-1 mod q
        BigInteger z = signatureB.modInverse(primeQ);

        // Calculate u1 = (w * z) mod q
        BigInteger u1 = hashW.multiply(z).mod(primeQ);

        // Calculate u2 = (a * z) mod q
        BigInteger u2 = signatureA.multiply(z).mod(primeQ);

        // Calculate v = ((r^u1 * y^u2) mod p) mod q
        BigInteger verificationV = r.modPow(u1, primeP).multiply(publicKeyY.modPow(u2, primeP)).mod(primeP).mod(primeQ);

        // Output results
        System.out.println("\n----- Sender Side -----");
        System.out.println("r = " + r);
        System.out.println("Public key (y) = " + publicKeyY);
        System.out.println("a = " + signatureA);
        System.out.println("b = " + signatureB);

        System.out.println("\n----- Receiver Side -----");
        System.out.println("z = " + z);
        System.out.println("u1 = " + u1);
        System.out.println("u2 = " + u2);
        System.out.println("v = " + verificationV);

        System.out.println("\n----- Verification -----");
        System.out.println("v = " + verificationV);
        System.out.println("a = " + signatureA);
        System.out.println("Signature is " + (verificationV.equals(signatureA) ? "valid" : "invalid"));

        scanner.close();
    }
}
