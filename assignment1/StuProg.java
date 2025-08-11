import java.util.*;

class Stu {
    String nm;
    int tot;

    Stu(String nm, int m1, int m2, int m3) {
        this.nm = nm;
        this.tot = m1 + m2 + m3;
    }

    void show() {
        System.out.println(nm + " - Total: " + tot);
    }
}

public class StuProg {
    public static void main(String[] args) {
        ArrayList<Stu> list = new ArrayList<>();
        list.add(new Stu("Aki", 25, 28, 27));
        list.add(new Stu("Ben", 15, 18, 20));
        list.add(new Stu("Cal", 30, 30, 30));
        list.add(new Stu("Dee", 10, 15, 12));

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter limit: ");
        int lim = sc.nextInt();

        System.out.println("Below " + lim + ":");
        for (Stu s : list) {
            if (s.tot < lim) s.show();
        }

        // Sort high to low
        for (int i = 0; i < list.size() - 1; i++) {
            for (int j = 0; j < list.size() - i - 1; j++) {
                if (list.get(j).tot < list.get(j + 1).tot) {
                    Stu tmp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }
            }
        }

        System.out.println("Top:");
        list.get(0).show();

        sc.close();
    }
}
