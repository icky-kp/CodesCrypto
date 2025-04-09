import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AESKeyExpansion {
        private static SecretKeySpec secretKey;
        private static byte[] key;

        public static void setKey(String myKey) {
                try {
                        key = myKey.getBytes("UTF-8");
                        MessageDigest sha = MessageDigest.getInstance("SHA-1");
                        key = sha.digest(key);
                        key = Arrays.copyOf(key, 16); // Use 16 bytes (128 bits) for AES-128
                        secretKey = new SecretKeySpec(key, "AES");
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        public static int[] createKeyExpansion(byte[] key) {
                // AES-128 parameters
                int Nb = 4; // Number of columns in state
                int Nr = 10; // Number of rounds for AES-128
                int Nk = 4; // Key length in words (4 for AES-128)

                // For AES-256, use:
                // if key.length == 32 (256 bits), then Nk = 8, Nr = 14
                if (key.length == 32) {
                        Nk = 8;
                        Nr = 14;
                }

                // Initialize key expansion array
                int[] w = new int[Nb * (Nr + 1)];

                // Rcon values for key expansion
                int[] Rcon = {
                                0x00000000, 0x01000000, 0x02000000, 0x04000000, 0x08000000,
                                0x10000000, 0x20000000, 0x40000000, 0x80000000, 0x1b000000,
                                0x36000000
                };

                // First Nk words are the original key
                for (int i = 0; i < Nk; i++) {
                        w[i] = ((key[4 * i] & 0xFF) << 24) |
                                        ((key[4 * i + 1] & 0xFF) << 16) |
                                        ((key[4 * i + 2] & 0xFF) << 8) |
                                        (key[4 * i + 3] & 0xFF);
                }

                // Generate the rest of the key schedule
                for (int i = Nk; i < Nb * (Nr + 1); i++) {
                        int temp = w[i - 1];
                        if (i % Nk == 0) {
                                // RotWord and SubWord operations
                                temp = subWord(rotWord(temp)) ^ Rcon[i / Nk];
                        } else if (Nk > 6 && i % Nk == 4) {
                                // Additional transformation for AES-256
                                temp = subWord(temp);
                        }
                        w[i] = w[i - Nk] ^ temp;
                }

                return w;
        }

        private static int rotWord(int word) {
                return ((word << 8) | ((word >>> 24) & 0xFF));
        }

        private static int subWord(int word) {
                // S-box lookup for each byte
                return (sBox[(word >>> 24) & 0xFF] << 24) |
                                (sBox[(word >>> 16) & 0xFF] << 16) |
                                (sBox[(word >>> 8) & 0xFF] << 8) |
                                sBox[word & 0xFF];
        }

        // S-box table (partial - first few values)
        private static final byte[] sBox = {
                        (byte) 0x63, (byte) 0x7c, (byte) 0x77, (byte) 0x7b, (byte) 0xf2, (byte) 0x6b, (byte) 0x6f,
                        (byte) 0xc5,
                        (byte) 0x30, (byte) 0x01, (byte) 0x67, (byte) 0x2b, (byte) 0xfe, (byte) 0xd7, (byte) 0xab,
                        (byte) 0x76
                        // Full S-box would have 256 entries
        };

        public static void main(String[] args) {
                Scanner scanner = new Scanner(System.in);

                System.out.println("Enter your key for AES encryption:");
                String userKey = scanner.nextLine();

                // Set the key
                setKey(userKey);

                // Create key expansion
                int[] expandedKey = createKeyExpansion(key);

                // Display the first few round keys
                System.out.println("\nKey Expansion (first 3 round keys):");
                for (int i = 0; i < 12 && i < expandedKey.length; i++) {
                        if (i % 4 == 0) {
                                System.out.println("\nRound " + (i / 4) + ":");
                        }
                        System.out.printf("0x%08X ", expandedKey[i]);
                }

                scanner.close();
        }
}
