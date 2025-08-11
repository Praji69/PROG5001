package genai2;
    
import java.io.*;
import java.util.*;

class Studnt {
    String fname, lname, id;
    float m1, m2, m3, tot;
    Studnt(String ln, String fn, String id, float m1, float m2, float m3) {
        this.lname = ln; this.fname = fn; this.id = id;
        this.m1 = m1; this.m2 = m2; this.m3 = m3;
        this.tot = m1 + m2 + m3;
    }
    void show() {
        System.out.println(fname + " " + lname +
                " | ID: " + id +
                " | A1: " + m1 +
                " | A2: " + m2 +
                " | A3: " + m3 +
                " | Total: " + tot);
    }
}

public class StudntProg {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Filter below threshold");
            System.out.println("2. Show top 5 & bottom 5");
            System.out.println("3. Exit");
            System.out.print("Choice: ");
            int ch;
            try { ch = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { System.out.println("Invalid"); continue; }

            if (ch == 3) break;
            if (ch != 1 && ch != 2) { System.out.println("Invalid"); continue; }

            System.out.print("CSV filename: ");
            String file = sc.nextLine();
            List<Studnt> studs = load(file);
            if (studs.isEmpty()) { System.out.println("No valid data"); continue; }

            if (ch == 1) {
                System.out.print("Threshold: ");
                float th;
                try { th = Float.parseFloat(sc.nextLine()); }
                catch (NumberFormatException e) { System.out.println("Invalid"); continue; }

                int count = 0;
                for (Studnt s : studs) {
                    if (s.tot < th) { s.show(); count++; }
                }
                if (count == 0) System.out.println("None below threshold");
                if (count == studs.size()) System.out.println("All below threshold");
            }

            if (ch == 2) {
                sortDesc(studs);
                System.out.println("\nTop 5:");
                for (int i = 0; i < Math.min(5, studs.size()); i++) studs.get(i).show();
                System.out.println("\nBottom 5:");
                for (int i = Math.max(0, studs.size() - 5); i < studs.size(); i++) studs.get(i).show();
            }
        }
        sc.close();
    }

    // === Load & check CSV ===
    static List<Studnt> load(String fname) {
        List<Studnt> list = new ArrayList<>();
        Set<String> seen = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fname))) {
            br.readLine(); // skip unit line
            br.readLine(); // skip header
            String ln; int lineNum = 2;
            while ((ln = br.readLine()) != null) {
                lineNum++;
                if (ln.trim().isEmpty() || ln.startsWith("#")) continue;
                String[] p = ln.split(",");
                if (p.length < 6) { 
                    System.out.println("Bad data line " + lineNum); continue; 
                }
                String last = p[0].trim(), first = p[1].trim(), id = p[2].trim();
                if (seen.contains(id)) { 
                    System.out.println("Duplicate ID " + id + " at line " + lineNum); 
                    continue; 
                }
                try {
                    float m1 = p[3].isEmpty()?0:Float.parseFloat(p[3].trim());
                    float m2 = p[4].isEmpty()?0:Float.parseFloat(p[4].trim());
                    float m3 = p[5].isEmpty()?0:Float.parseFloat(p[5].trim());
                    if (!valid(m1) || !valid(m2) || !valid(m3)) {
                        System.out.println("Marks out of range at line " + lineNum);
                        continue;
                    }
                    list.add(new Studnt(last, first, id, m1, m2, m3));
                    seen.add(id);
                } catch (NumberFormatException e) {
                    System.out.println("Bad marks at line " + lineNum);
                }
            }
        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
        }
        return list;
    }

    static boolean valid(float m) { return m >= 0 && m <= 30; }

    static void sortDesc(List<Studnt> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).tot < list.get(j + 1).tot) {
                    Studnt tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }
    }
}
