package assignment2;
import java.io.*;
import java.util.*;

// Reads CSV with student data
public class MyCSVLoader extends MyFileHandler {
    private List<MyStudent> allStudents;
    private String unit;
    private int validCount;

    public MyCSVLoader() {
        allStudents = new ArrayList<>();
        unit = "";
        validCount = 0;
    }

    @Override
    public boolean loadFile(String fname) {
        if (fname == null || fname.trim().equals("")) {
            System.out.println("File name is blank!");
            return false;
        }
        try (BufferedReader buf = new BufferedReader(new FileReader(fname))) {
            unit = buf.readLine();
            if (unit == null || unit.trim().equals("")) {
                System.out.println("No unit name - file broken?");
                return false;
            }
            buf.readLine(); // skip header
            String ln;
            while ((ln = buf.readLine()) != null) {
                if (ln.trim().equals("") || ln.trim().startsWith("#")) continue;
                String[] d = ln.split(",");
                if (d.length < 6) {
                    System.out.println("Bad line - too short, skipped");
                    continue;
                }
                String sn = d[0].trim();
                String fn = d[1].trim();
                String sid = d[2].trim();
                // check for missing details
                if (sn.equals("") || fn.equals("") || sid.equals("")) {
                    System.out.println("Missing name or ID, skipping student");
                    continue;
                }
                float m1 = parseNum(d[3]);
                float m2 = parseNum(d[4]);
                float m3 = parseNum(d[5]);
                if (m1 < 0 || m2 < 0 || m3 < 0 || m1 > 30 || m2 > 30 || m3 > 30) {
                    System.out.println("Weird mark value for " + fn + " " + sn);
                    continue;
                }
                allStudents.add(new MyStudent(sn, fn, sid, m1, m2, m3));
                validCount++;
            }
        } catch (IOException e) {
            System.out.println("Can't open file: " + fname);
            return false;
        }
        return true;
    }

    // helper for mark conversion, returns 0 if empty/wrong
    private float parseNum(String v) {
        try {
            if (v == null || v.trim().equals("")) return 0f;
            return Float.parseFloat(v.trim());
        } catch (Exception e) {
            return 0f;
        }
    }

    public List<MyStudent> getAllStudents() { return allStudents; }
    public String getUnit() { return unit; }
    public int getValidStudentCount() { return validCount; }
}
