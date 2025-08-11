import java.io.*;
import java.util.*;

// === Person (Abstract Class) ===
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
    @Override
    public void displayDetails() {
        System.out.println(firstName + " " + lastName +
                ", Student ID: " + id +
                ", Total Marks: " + totalMarks);
    }
}

// === Main Class with All Logic ===
public class FullStudentProgram {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Filter students below a total marks threshold");
            System.out.println("2. Display top 5 and bottom 5 students");
            System.out.println("3. Exit");
            System.out.print("Enter choice (1-3): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Error: Enter a number between 1 and 3");
                continue;
            }

            if (choice == 3) {
                System.out.println("Exiting program.");
                break;
            }
            if (choice != 1 && choice != 2) {
                System.out.println("Invalid choice.");
                continue;
            }

            System.out.print("Enter CSV filename: ");
            String filename = scanner.nextLine();
            List<Student> records = readCSV(filename);
            if (records.isEmpty()) {
                System.out.println("No valid records.");
                continue;
            }

            if (choice == 1) {
                System.out.print("Enter threshold: ");
                float th;
                try {
                    th = Float.parseFloat(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Threshold must be a number.");
                    continue;
                }
                int printed = 0;
                for (Student s : records) {
                    if (s.getTotalMarks() < th) {
                        s.displayDetails();
                        printed++;
                    }
                }
                if (printed == 0) System.out.println("No students below threshold.");
                else if (printed == records.size()) System.out.println("All students below threshold.");
            }

            if (choice == 2) {
                // bubble sort descending
                for (int i = 0; i < records.size() - 1; i++) {
                    for (int j = 0; j < records.size() - i - 1; j++) {
                        if (records.get(j).getTotalMarks() < records.get(j + 1).getTotalMarks()) {
                            Student tmp = records.get(j);
                            records.set(j, records.get(j + 1));
                            records.set(j + 1, tmp);
                        }
                    }
                }
                // Top 5
                System.out.println("Top 5 Students:");
                for (int i = 0; i < Math.min(5, records.size()); i++) {
                    records.get(i).displayDetails();
                }
                // Bottom 5
                System.out.println("Bottom 5 Students:");
                for (int i = Math.max(records.size() - 5, 0); i < records.size(); i++) {
                    records.get(i).displayDetails();
                }
            }
        }
        scanner.close();
    }

    // === Reads the CSV and returns a de-duped List of Students ===
    private static List<Student> readCSV(String filename) {
        List<Student> list = new ArrayList<>();
        Set<String> seenIDs = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String unitLine = br.readLine();
            if (unitLine == null) {
                System.out.println("Empty file");
                return list;
            }
            String header = br.readLine(); // skip header

            String line;
            int lineNum = 2;
            while ((line = br.readLine()) != null) {
                lineNum++;
                if (line.trim().isEmpty() || line.startsWith("#")) continue;
                String[] data = line.split(",");
                if (data.length < 6) {
                    System.out.println("Skipping short record at line " + lineNum);
                    continue;
                }
                String lastName = data[0].trim();
                String firstName = data[1].trim();
                String id = data[2].trim();
                if (seenIDs.contains(id)) {
                    System.out.println("Duplicate ID [" + id + "] at line " + lineNum + " skipped.");
                    continue;
                }
                try {
                    float m1 = data[3].isEmpty() ? 0 : Float.parseFloat(data[3].trim());
                    float m2 = data[4].isEmpty() ? 0 : Float.parseFloat(data[4].trim());
                    float m3 = data[5].isEmpty() ? 0 : Float.parseFloat(data[5].trim());
                    if (m1 < 0 || m1 > 30 || m2 < 0 || m2 > 30 || m3 < 0 || m3 > 30) {
                        System.out.println("Invalid marks at line " + lineNum + ", skipping.");
                        continue;
                    }
                    list.add(new Student(lastName, firstName, id, m1, m2, m3));
                    seenIDs.add(id);
                } catch (NumberFormatException e) {
                    System.out.println("Invalid mark format at line " + lineNum + ", skipping.");
                }
            }
        } catch (IOException e) {
            System.out.println("File not found: " + filename);
        }
        return list;
    }
}
