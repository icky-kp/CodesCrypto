import java.util.Scanner;

public class MD5MessagePreparation {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get original message
        System.out.print("Enter the message: ");
        String message = scanner.nextLine();

        // Convert to binary
        StringBuilder binaryMessage = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            String binary = Integer.toBinaryString(message.charAt(i));
            // Ensure each character is represented by 8 bits
            while (binary.length() < 8) {
                binary = "0" + binary;
            }
            binaryMessage.append(binary);
        }

        // Calculate original message length in bits
        int originalLengthBits = message.length() * 8;

        // Calculate padding
        int paddingLength = 448 - (originalLengthBits % 512);
        if (paddingLength <= 0) {
            paddingLength += 512;
        }

        // Create padding string (1 followed by zeros)
        StringBuilder padding = new StringBuilder();
        padding.append("1");
        for (int i = 1; i < paddingLength; i++) {
            padding.append("0");
        }

        // Convert original length to 64-bit binary
        String lengthBinary = Long.toBinaryString(originalLengthBits);
        while (lengthBinary.length() < 64) {
            lengthBinary = "0" + lengthBinary;
        }

        // Display results
        System.out.println("\n1. Original message in binary bits:");
        System.out.println(binaryMessage.toString());

        System.out.println("\n2. Total number of padding bits: " + paddingLength);

        System.out.println("\n3. Padding bits in binary:");
        System.out.println(padding.toString());

        System.out.println("\n4. Length of the original message: " + originalLengthBits + " bits");

        System.out.println("\n5. Length of the original message in binary:");
        System.out.println(lengthBinary);

        scanner.close();
    }
}
