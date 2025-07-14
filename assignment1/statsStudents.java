import java.util.Scanner;

public class statsStudents {
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
        
        scanner.close(); // Always good to close Scanner
    }
}
public class HighLowCalculator {
    public static void main(String[] args) {
        double[] marks = {28.5, 18.0, 30.0, 22.7, 16.3}; // Example input
        double highest = marks[0];
        double lowest = marks[0];

        for (int i = 1; i < marks.length; i++) {
            if (marks[i] > highest) {
                highest = marks[i];
            }
            if (marks[i] < lowest) {
                lowest = marks[i];
            }
        }

        System.out.println("Highest mark: " + highest);
        System.out.println("Lowest mark: " + lowest);
    }
}
