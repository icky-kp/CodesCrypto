import java.util.Scanner;

public class MD5Padding {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Example 1:");
        calculatePadding(400);

        System.out.println("\nExample 2:");
        System.out.print("Enter message length in bits (greater than 500): ");
        long messageBits = scanner.nextLong();
        calculatePadding(messageBits);

        scanner.close();
    }

    public static void calculatePadding(long messageLengthBits) {
        System.out.println("Original message length: " + messageLengthBits + " bits");

        // Add 1 bit (the '1' bit that is always appended)
        long afterOneBit = messageLengthBits + 1;

        // Calculate padding zeros
        long paddingZeros = 448 - (afterOneBit % 512);
        if (paddingZeros < 0) {
            paddingZeros += 512;
        }

        // Calculate total padded length (original + 1 + zeros + 64 for length field)
        long paddedLength = messageLengthBits + 1 + paddingZeros + 64;

        // Calculate number of blocks
        long blocks = paddedLength / 512;

        System.out.println("Padding bits: 1 bit + " + paddingZeros + " zero bits + 64 bits for length");
        System.out.println("Total padding bits: " + (1 + paddingZeros + 64) + " bits");
        System.out.println("Padded message length: " + paddedLength + " bits");
        System.out.println("Number of blocks required: " + blocks);
    }
}
