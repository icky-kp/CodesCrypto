import java.util.Scanner;

public class SBox {
    // S-Box 1 (as an example)
    private static final int[][] S1 = {
            { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
            { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
            { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
            { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
    };

    // S-Box 5
    private static final int[][] S5 = {
            { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
            { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
            { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
            { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
    };

    // S-Box 8
    private static final int[][] S8 = {
            { 13, 1, 2, 15, 8, 13, 4, 14, 6, 11, 11, 8, 1, 4, 10, 7 },
            { 10, 7, 6, 1, 9, 3, 12, 10, 11, 6, 7, 12, 0, 5, 14, 9 },
            { 3, 10, 13, 0, 6, 9, 10, 0, 12, 11, 7, 13, 15, 1, 3, 14 },
            { 9, 4, 14, 3, 15, 5, 2, 12, 8, 2, 4, 7, 14, 15, 5, 8 }
    };

    public static String sBoxSubstitution(String input, int[][] sBox) {
        if (input.length() != 6 || !input.matches("[01]+")) {
            throw new IllegalArgumentException("Input must be a 6-bit binary string.");
        }

        // Determine row index: First and last bits
        int row = Integer.parseInt("" + input.charAt(0) + input.charAt(5), 2);

        // Determine column index: Middle 4 bits
        int col = Integer.parseInt(input.substring(1, 5), 2);

        // Substitute using S-Box
        int output = sBox[row][col];

        // Convert to 4-bit binary string
        return String.format("%4s", Integer.toBinaryString(output)).replace(' ', '0');
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a 6-bit binary input:");
        String input = scanner.nextLine();

        if (input.length() != 6 || !input.matches("[01]+")) {
            System.out.println("Invalid input. Please enter exactly 6 bits of binary data.");
            return;
        }

        System.out.println("Choose an S-Box: 1, 5, or 8:");
        int choice = scanner.nextInt();

        String output;
        switch (choice) {
            case 1:
                output = sBoxSubstitution(input, S1);
                System.out.println("S-Box 1 Output: " + output);
                break;
            case 5:
                output = sBoxSubstitution(input, S5);
                System.out.println("S-Box 5 Output: " + output);
                break;
            case 8:
                output = sBoxSubstitution(input, S8);
                System.out.println("S-Box 8 Output: " + output);
                break;
            default:
                System.out.println("Invalid choice. Please select 1, 5, or 8.");
        }

        scanner.close();
    }
}
