import java.util.Scanner;

public class StudentStats {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        
        // Input validation
        System.out.print("Enter number of marks (N): ");
        while (!input.hasNextInt()) {
            System.out.println("Invalid input! Enter an integer.");
            input.next();
        }
        int n = input.nextInt();
        
        double[] marks = new double[n];
        for (int i = 0; i < n; i++) {
            System.out.print("Enter mark " + (i+1) + " (0-30): ");
            while (true) {
                if (input.hasNextDouble()) {
                    double mark = input.nextDouble();
                    if (mark >= 0 && mark <= 30) {
                        marks[i] = mark;
                        break;
                    }
                }
                System.out.print("Invalid! Re-enter mark " + (i+1) + " (0-30): ");
                input.next();
            }
        }

        // Calculate statistics
        double min = marks[0], max = marks[0], sum = 0;
        for (double mark : marks) {
            if (mark < min) min = mark;
            if (mark > max) max = mark;
            sum += mark;
        }
        double mean = sum / n;
        
        double variance = 0;
        for (double mark : marks) {
            variance += Math.pow(mark - mean, 2);
        }
        variance /= n;
        
        // Output results
        System.out.println("\nStatistics:");
        System.out.printf("Minimum: %.2f\n", min);
        System.out.printf("Maximum: %.2f\n", max);
        System.out.printf("Mean: %.2f\n", mean);
        System.out.printf("Variance: %.2f\n", variance);
    }
}