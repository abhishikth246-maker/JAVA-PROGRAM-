import java.util.*;

class Registrar extends User {
    private ArrayList<Student> students = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    private ArrayList<Registration> registrations = new ArrayList<>();

    Registrar(String id, String name, String email) {
        super(id, name, email);
    }

    public void addStudent(Student s) {
        if (searchStudent(s.id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }
        students.add(s);
        System.out.println("Student added successfully.");
    }

    public void addCourse(Course c) {
        if (searchCourse(c.getCode()) != null) {
            System.out.println("Course code already exists.");
            return;
        }
        courses.add(c);
        System.out.println("Course added successfully.");
    }

    public Student searchStudent(String id) {
        for (Student s : students)
            if (s.id.equalsIgnoreCase(id)) return s;
        return null;
    }

    public Student searchStudentByName(String name) {
        for (Student s : students)
            if (s.name.equalsIgnoreCase(name)) return s;
        return null;
    }

    public Course searchCourse(String code) {
        for (Course c : courses)
            if (c.getCode().equalsIgnoreCase(code)) return c;
        return null;
    }

    public Course searchCourseByTitle(String title) {
        for (Course c : courses)
            if (c.getTitle().equalsIgnoreCase(title)) return c;
        return null;
    }

    public void removeCourse(String code) {
        Course c = searchCourse(code);
        if (c == null)
            System.out.println("Course not found.");
        else {
            courses.remove(c);
            System.out.println("Course removed successfully.");
        }
    }

    public void register(Student s, Course c) {
        if (s.registerCourse(c)) {
            String id = "R" +
                    String.format("%03d", registrations.size() + 1);

            registrations.add(new Registration(
                    id, s, c, new Date().toString()));

        }
    }

    public void displayStudents() {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        for (Student s : students)
            s.displayStudent();
    }

    public void displayCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course c : courses)
            c.display();
    }

    public void displayRegistrations() {
        if (registrations.isEmpty()) {
            System.out.println("No registrations available.");
            return;
        }

        for (Registration r : registrations)
            r.display();
    }
}
