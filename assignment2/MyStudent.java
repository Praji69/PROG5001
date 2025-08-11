package assignment2;

// Subclass for normal student, extends MyPerson
public class MyStudent extends MyPerson {
    private float test1, test2, test3;
    private float totalScore;

    public MyStudent(String last, String first, String id, float m1, float m2, float m3) {
        super(last, first, id);
        test1 = m1;
        test2 = m2;
        test3 = m3;
        totalScore = test1 + test2 + test3;
    }

    public float fetchTotal() {
        return totalScore;
    }

    // For menu display, a bit simpler
    @Override
    public void showDetails() {
        System.out.println(givenName + " " + surname + ", ID: " + studID + ", Marks: " + totalScore);
    }
}
