import java.util.*;

class Course {
    private String code, title, instructor;
    private int credits, capacity;
    private ArrayList<String> prerequisites = new ArrayList<>();

    Course(String code, String title, int credits,
           String instructor, int capacity) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.capacity = capacity;
    }

    public void addPrerequisite(String p) {
        prerequisites.add(p);
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public int getCapacity() { return capacity; }
    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }

    public void setTitle(String title) { this.title = title; }
    public void setCredits(int credits) { this.credits = credits; }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void reduceCapacity() { capacity--; }
    public void increaseCapacity() { capacity++; }

    public void display() {
        System.out.println(code + " | " + title +
                " | Credits: " + credits +
                " | Instructor: " + instructor +
                " | Seats: " + capacity);

        if (prerequisites.isEmpty())
            System.out.println("Prerequisites: None");
        else
            System.out.println("Prerequisites: " +
                    String.join(", ", prerequisites));
    }
}
