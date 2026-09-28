import java.util.*;

class Student extends User {
    private int maxCredits;
    private ArrayList<Course> registeredCourses = new ArrayList<>();
    private ArrayList<String> completedCourses = new ArrayList<>();

    Student(String id, String name, String email, int maxCredits) {
        super(id, name, email);
        this.maxCredits = maxCredits;
    }

    public int getTotalCredits() {
        int total = 0;
        for (Course c : registeredCourses)
            total += c.getCredits();
        return total;
    }

    public boolean hasCourse(String code) {
        for (Course c : registeredCourses)
            if (c.getCode().equalsIgnoreCase(code))
                return true;
        return false;
    }

    public boolean registerCourse(Course course) {
        if (hasCourse(course.getCode())) {
            System.out.println("Already registered.");
            return false;
        }

        if (course.getCapacity() <= 0) {
            System.out.println("Course capacity is full.");
            return false;
        }

        if (getTotalCredits() + course.getCredits() > maxCredits) {
            System.out.println("Credit limit exceeded.");
            return false;
        }

        for (String p : course.getPrerequisites()) {
            if (!completedCourses.contains(p)) {
                System.out.println("Prerequisite not completed: " + p);
                return false;
            }
        }

        registeredCourses.add(course);
        course.reduceCapacity();
        System.out.println("Course registered successfully.");
        return true;
    }

    public void dropCourse(String code) {
        for (Course c : registeredCourses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                registeredCourses.remove(c);
                c.increaseCapacity();
                System.out.println("Course dropped successfully.");
                return;
            }
        }
        System.out.println("Course not found.");
    }

    public void displayCourses() {
        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }

        for (Course c : registeredCourses)
            System.out.println(c.getCode() + " | " +
                    c.getTitle() + " | Credits: " + c.getCredits());

        System.out.println("Total Credits: " + getTotalCredits());
    }

    public void displayStudent() {
        System.out.println(id + " | " + name + " | " +
                email + " | Max Credits: " + maxCredits);
    }
}
