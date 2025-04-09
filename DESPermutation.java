import java.util.Scanner;

public class DESPermutation {
    // Predefined Initial Permutation (IP) table
    private static final int[] IP_TABLE = {
            58, 50, 42, 34, 26, 18, 10, 2,
            60, 52, 44, 36, 28, 20, 12, 4,
            62, 54, 46, 38, 30, 22, 14, 6,
            64, 56, 48, 40, 32, 24, 16, 8,
            57, 49, 41, 33, 25, 17, 9, 1,
            59, 51, 43, 35, 27, 19, 11, 3,
            61, 53, 45, 37, 29, 21, 13, 5,
            63, 55, 47, 39, 31, 23, 15, 7
    };

    // Predefined Final Permutation (FP) table
    private static final int[] FP_TABLE = {
            40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25
    };

    // Function to perform permutation
    public static String permute(String input, int[] table) {
        StringBuilder output = new StringBuilder();
        for (int position : table) {
            output.append(input.charAt(position - 1));
        }
        return output.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Accept 64-bit plaintext input
        System.out.println("Enter 64-bit binary plaintext:");
        String plaintext = scanner.nextLine();

        // Validate input
        if (plaintext.length() != 64 || !plaintext.matches("[01]+")) {
            System.out.println("Invalid input. Please enter exactly 64 bits of binary data.");
            return;
        }

        // Perform initial permutation
        String initialPermutationResult = permute(plaintext, IP_TABLE);
        System.out.println("Result after Initial Permutation (IP):");
        System.out.println(initialPermutationResult);

        // Perform final permutation
        String finalPermutationResult = permute(initialPermutationResult, FP_TABLE);
        System.out.println("Result after Final Permutation (FP):");
        System.out.println(finalPermutationResult);

        scanner.close();
    }
}
