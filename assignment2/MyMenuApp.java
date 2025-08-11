package assignment2;
import java.util.*;

// Main menu class
public class MyMenuApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose from:");
            System.out.println("1 - List students under a marks threshold");
            System.out.println("2 - Show top 5 and bottom 5 by total marks");
            System.out.println("3 - Quit");
            System.out.print("Type your option (1/2/3): ");
            int pick;
            try {
                pick = Integer.parseInt(in.nextLine());
            } catch (Exception e) {
                System.out.println("Not a valid choice!");
                continue;
            }
            if (pick == 3) {
                System.out.println("Bye!");
                break;
            }
            if (pick != 1 && pick != 2) {
                System.out.println("Invalid. Only 1/2/3 allowed.");
                continue;
            }
            System.out.print("Enter CSV file name: ");
            String fname = in.nextLine();
            MyFileHandler fh = new MyCSVLoader();
            if (!fh.loadFile(fname)) continue;

            MyCSVLoader loader = (MyCSVLoader) fh;
            String module = loader.getUnit();
            List<MyStudent> studz = loader.getAllStudents();
            int howMany = loader.getValidStudentCount();
            if (howMany == 0) {
                System.out.println("Nobody in the list!");
                continue;
            }

            if (pick == 1) {
                System.out.print("Marks below: ");
                float th = 0;
                try { th = Float.parseFloat(in.nextLine()); }
                catch (Exception e) {
                    System.out.println("Invalid marks.");
                    continue;
                }
                int shown = 0;
                System.out.println(module + ", students below " + th);
                for (MyStudent s : studz) {
                    if (s.fetchTotal() < th) {
                        s.showDetails();
                        shown++;
                    }
                }
                if (shown == 0) System.out.println("None.");
                else if (shown == howMany) System.out.println("All students are below threshold.");
            } else if (pick == 2) {
                // Bubble sort
                for (int i = 0; i < studz.size() - 1; i++) {
                    for (int j = 0; j < studz.size() - i - 1; j++) {
                        if (studz.get(j).fetchTotal() < studz.get(j+1).fetchTotal()) {
                            MyStudent tmp = studz.get(j); studz.set(j, studz.get(j+1)); studz.set(j+1, tmp);
                        }
                    }
                }
                System.out.println(module + " -- TOP 5:");
                int T = Math.min(5, studz.size());
                for (int i = 0; i < T; i++) studz.get(i).showDetails();
                System.out.println("-- BOTTOM 5:");
                int B = Math.max(0, studz.size() - 5);
                for (int i = B; i < studz.size(); i++) studz.get(i).showDetails();
            }
        }
        in.close();
    }
}
