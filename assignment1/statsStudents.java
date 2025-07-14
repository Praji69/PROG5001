import java.util.Scanner;

public class NumberOfMarksInput {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numberOfMarks;

        // Prompt user until a valid number is entered
        do {
            System.out.print("Enter the number of marks: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a positive integer.");
                scanner.next(); // discard invalid input
                System.out.print("Enter the number of marks: ");
            }

            numberOfMarks = scanner.nextInt();

            if (numberOfMarks <= 0) {
                System.out.println("Number must be greater than zero.");
            }

        } while (numberOfMarks <= 0);

        System.out.println("You will enter " + numberOfMarks + " marks.");
    }
}