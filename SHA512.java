import java.util.Scanner;

public class SHA512 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Example 1:");
        calculatePadding(700);

        System.out.println("\nExample 2:");
        System.out.print("Enter message length in bits (greater than 5000): ");
        long messageBits = scanner.nextLong();
        calculatePadding(messageBits);

        scanner.close();
    }

    public static void calculatePadding(long messageLengthBits) {
        System.out.println("Original message length: " + messageLengthBits + " bits");

        // Add 1 bit (the '1' bit that is always appended)
        long afterOneBit = messageLengthBits + 1;

        // Calculate padding zeros
        long paddingZeros = 896 - (afterOneBit % 1024);
        if (paddingZeros < 0) {
            paddingZeros += 1024;
        }

        // Calculate total padded length (original + 1 + zeros + 128 for length field)
        long paddedLength = messageLengthBits + 1 + paddingZeros + 128;

        // Calculate number of blocks
        long blocks = paddedLength / 1024;

        System.out.println("Padding bits: 1 bit + " + paddingZeros + " zero bits + 128 bits for length");
        System.out.println("Total padding bits: " + (1 + paddingZeros + 128) + " bits");
        System.out.println("Padded message length: " + paddedLength + " bits");
        System.out.println("Number of blocks required: " + blocks);
    }
}
