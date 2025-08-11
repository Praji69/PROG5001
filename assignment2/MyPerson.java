package assignment2;

// Base abstract class with info all people have
public abstract class MyPerson {
    protected String surname;
    protected String givenName;
    protected String studID;

    public MyPerson(String s, String g, String i) {
        surname = s;
        givenName = g;
        studID = i;
    }

    // Must be implemented in subclasses
    public abstract void showDetails();
}
