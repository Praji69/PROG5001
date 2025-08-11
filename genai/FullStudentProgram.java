import java.io.*;
import java.util.*;

// === Abstract Superclass ===
abstract class Person {
    protected String lastName;
    protected String firstName;
    protected String id;

    public Person(String lastName, String firstName, String id) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.id = id;
    }

    public abstract void displayDetails();
}

// === Student Class ===
class Student extends Person {
    private float mark1, mark2, mark3, totalMarks;

    public Student(String lastName, String firstName, String studentID,
                   float mark1, float mark2, float mark3) {
        super(lastName, firstName, studentID);
        this.mark1 = mark1;
        this.mark2 = mark2;
        this.mark3 = mark3;
        this.totalMarks = mark1 + mark2 + mark3;
    }

    public float getTotalMarks() { return totalMarks; }
    public float getMark1() { return mark1; }
    public float getMark2() { return mark2; }
    public float getMark3() { return mark3; }

    @Override
    public void displayDetails() {
        System.out.println(firstName + " " + lastName +
                " | ID: " + id +
                " | A1: " + mark1 +
                " | A2: " + mark2 +
                " | A3: " + mark3 +
                " | Total: " + totalMarks);
    }
}

// === Main Program ===
public class FullStudentProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // === Menu ===
            System.out.println("\n----- STUDENT MARKS MENU -----");
            System.out.println("1. Filter students below a total marks threshold");
            System.out.println("2. Display top 5 and bottom 5 students");
            System.out.println("3. Exit");
            System.out.print("Enter choice (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Invalid input. Please enter a number 1-3.");
                continue;
            }

            if (choice == 3) {
                System.out.println("✅ Exiting program.");
                break;
            }
            if (choice != 1 && choice != 2) {
                System.out.println("⚠ Invalid choice.");
                continue;
            }

            // === Get filename ===
            System.out.print("Enter CSV filename: ");
            String filename = scanner.nextLine();

            List<Student> studentRecords = readCSV(filename);

            if (studentRecords.isEmpty()) {
                System.out.println("⚠ No valid student records loaded.");
                continue;
            }

            // === Option 1: Filter by threshold ===
            if (choice == 1) {
                System.out.print("Enter threshold: ");
                float threshold;
                try {
                    threshold = Float.parseFloat(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("⚠ Threshold must be a valid number.");
                    continue;
                }

                int belowCount = 0;
                for (Student s : studentRecords) {
                    if (s.getTotalMarks() < threshold) {
                        s.displayDetails();
                        belowCount++;
                    }
                }
                if (belowCount == 0) {
                    System.out.println("ℹ No students below threshold.");
                } else if (belowCount == studentRecords.size()) {
                    System.out.println("ℹ All students are below threshold.");
                }
            }

            // === Option 2: Top & Bottom 5 ===
            if (choice == 2) {
                bubbleSortDescending(studentRecords);

                System.out.println("\n🏆 Top 5 Students:");
                for (int i = 0; i < Math.min(5, studentRecords.size()); i++) {
                    studentRecords.get(i).displayDetails();
                }

                System.out.println("\n📉 Bottom 5 Students:");
                for (int i = Math.max(0, studentRecords.size() - 5); i < studentRecords.size(); i++) {
                    studentRecords.get(i).displayDetails();
                }
            }
        }

        scanner.close();
    }

    // === Reads & validates CSV file ===
    private static List<Student> readCSV(String filename) {
        List<Student> list = new ArrayList<>();
        Set<String> seenIDs = new HashSet<>(); // for duplicate check

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String unitLine = br.readLine(); // Unit: ...
            if (unitLine == null) {
                System.out.println("⚠ Empty file.");
                return list;
            }

            br.readLine(); // skip header line

            String line;
            int lineNum = 2;

            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty() || line.startsWith("#")) continue;

                String[] data = line.split(",");
                if (data.length < 6) {
                    System.out.println("⚠ Skipping invalid record at line " + lineNum);
                    continue;
                }

                String lastName = data[0].trim();
                String firstName = data[1].trim();
                String id = data[2].trim();

                // Duplicate ID check
                if (seenIDs.contains(id)) {
                    System.out.println("⚠ Duplicate ID [" + id + "] at line " + lineNum + " skipped.");
                    continue;
                }

                try {
                    float m1 = data[3].isEmpty() ? 0 : Float.parseFloat(data[3].trim());
                    float m2 = data[4].isEmpty() ? 0 : Float.parseFloat(data[4].trim());
                    float m3 = data[5].isEmpty() ? 0 : Float.parseFloat(data[5].trim());

                    if (!isValidMark(m1) || !isValidMark(m2) || !isValidMark(m3)) {
                        System.out.println("⚠ Marks out of range at line " + lineNum + ", skipping.");
                        continue;
                    }

                    list.add(new Student(lastName, firstName, id, m1, m2, m3));
                    seenIDs.add(id);

                } catch (NumberFormatException e) {
                    System.out.println("⚠ Invalid mark format at line " + lineNum + ", skipping.");
                }
            }

        } catch (IOException e) {
            System.out.println("⚠ Error reading file: " + e.getMessage());
        }

        return list;
    }

    private static boolean isValidMark(float mark) {
        return mark >= 0 && mark <= 30;
    }

    // === Bubble sort descending ===
    private static void bubbleSortDescending(List<Student> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).getTotalMarks() < list.get(j + 1).getTotalMarks()) {
                    Student tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
    }
}
