import java.util.*;
import java.io.*;

class SimpleStudent {
    String name;
    int total;
    SimpleStudent(String name, int a1, int a2, int a3) {
        this.name = name.trim();
        this.total = a1 + a2 + a3;
    }
    void print() {
        System.out.println(name + " - Total: " + total);
    }
}

public class SimpleStudentProgram {
    public static void main(String[] args) throws Exception {
        ArrayList<SimpleStudent> list = new ArrayList<>();

        // Read from file
        String filename = "students_marks_50.csv"; // Change as needed
        BufferedReader br = new BufferedReader(new FileReader(filename));

        br.readLine(); // skip 'Unit: ...' line
        br.readLine(); // skip header

        String line;
        while ((line = br.readLine()) != null) {
            if (line.trim().isEmpty() || line.startsWith("#")) continue;
            String[] parts = line.split(",");
            if (parts.length < 6) continue;
            String fname = parts[1].trim(), lname = parts[0].trim();
            String name = fname + " " + lname;
            int a1 = parts[3].isEmpty() ? 0 : (int)Float.parseFloat(parts[3].trim());
            int a2 = parts[4].isEmpty() ? 0 : (int)Float.parseFloat(parts[4].trim());
            int a3 = parts[5].isEmpty() ? 0 : (int)Float.parseFloat(parts[5].trim());
            list.add(new SimpleStudent(name, a1, a2, a3));
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter threshold: ");
        int th = sc.nextInt();

        System.out.println("Students below threshold (" + th + "):");
        for (SimpleStudent s : list) {
            if (s.total < th) s.print();
        }

        // Bubble sort manually (descending)
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).total < list.get(j + 1).total) {
                    SimpleStudent tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
        System.out.println("Top student:");
        list.get(0).print();

        sc.close();
    }
}
