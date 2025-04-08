import java.util.Scanner;

public class MD5BufferCalculation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get initial buffer values
        System.out.println("Enter initial MD buffer values (hexadecimal):");
        System.out.print("A: 0x");
        int A = Integer.parseUnsignedInt(scanner.nextLine(), 16);
        System.out.print("B: 0x");
        int B = Integer.parseUnsignedInt(scanner.nextLine(), 16);
        System.out.print("C: 0x");
        int C = Integer.parseUnsignedInt(scanner.nextLine(), 16);
        System.out.print("D: 0x");
        int D = Integer.parseUnsignedInt(scanner.nextLine(), 16);

        // Get message blocks and constants
        System.out.print("Enter M0 (hexadecimal): 0x");
        int M0 = Integer.parseUnsignedInt(scanner.nextLine(), 16);
        System.out.print("Enter K1 (hexadecimal): 0x");
        int K1 = Integer.parseUnsignedInt(scanner.nextLine(), 16);

        // Step 1 of Round 1
        int temp = A;
        A = B + Integer.rotateLeft((A + F(B, C, D) + M0 + K1), 7);
        D = temp;

        System.out.println("\nOutput of Step 1 of Round 1:");
        System.out.println("A: 0x" + Integer.toHexString(A).toUpperCase());
        System.out.println("B: 0x" + Integer.toHexString(B).toUpperCase());
        System.out.println("C: 0x" + Integer.toHexString(C).toUpperCase());
        System.out.println("D: 0x" + Integer.toHexString(D).toUpperCase());

        // Get message block and constant for step 2
        System.out.print("\nEnter M1 (hexadecimal): 0x");
        int M1 = Integer.parseUnsignedInt(scanner.nextLine(), 16);
        System.out.print("Enter K2 (hexadecimal): 0x");
        int K2 = Integer.parseUnsignedInt(scanner.nextLine(), 16);

        // Step 2 of Round 1
        temp = D;
        D = A + Integer.rotateLeft((D + F(A, B, C) + M1 + K2), 12);
        C = temp;

        System.out.println("\nOutput of Step 2 of Round 1:");
        System.out.println("A: 0x" + Integer.toHexString(A).toUpperCase());
        System.out.println("B: 0x" + Integer.toHexString(B).toUpperCase());
        System.out.println("C: 0x" + Integer.toHexString(C).toUpperCase());
        System.out.println("D: 0x" + Integer.toHexString(D).toUpperCase());

        scanner.close();
    }

    // F function for Round 1
    private static int F(int x, int y, int z) {
        return (x & y) | (~x & z);
    }
}
