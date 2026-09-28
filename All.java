import java.util.*;

class User {
    protected String id;
    protected String name;
    protected String email;

    User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}

class Course {
    private String code;
    private String title;
    private int credits;
    private String instructor;
    private int capacity;
    private ArrayList<String> prerequisites;

    Course(String code, String title, int credits, String instructor, int capacity) {
        this.code = code;
        this.title = title;
        this.credits = credits;
        this.instructor = instructor;
        this.capacity = capacity;
        this.prerequisites = new ArrayList<>();
    }

    public void addPrerequisite(String prerequisite) {
        prerequisites.add(prerequisite);
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public int getCredits() {
        return credits;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<String> getPrerequisites() {
        return prerequisites;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void reduceCapacity() {
        capacity--;
    }

    public void increaseCapacity() {
        capacity++;
    }

    public void display() {
        System.out.println(
                code + " | " + title +
                " | Credits: " + credits +
                " | Instructor: " + instructor +
                " | Seats: " + capacity
        );

        if (prerequisites.isEmpty()) {
            System.out.println("Prerequisites: None");
        } else {
            System.out.println("Prerequisites: " + String.join(", ", prerequisites));
        }
    }
}

class Student extends User {
    private int maxCredits;
    private ArrayList<Course> registeredCourses;
    private ArrayList<String> completedCourses;

    Student(String id, String name, String email, int maxCredits) {
        super(id, name, email);
        this.maxCredits = maxCredits;
        registeredCourses = new ArrayList<>();
        completedCourses = new ArrayList<>();
    }

    public int getTotalCredits() {
        int total = 0;

        for (Course c : registeredCourses) {
            total += c.getCredits();
        }

        return total;
    }

    public boolean hasCourse(String code) {
        for (Course c : registeredCourses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public void addCompletedCourse(String code) {
        completedCourses.add(code);
    }

    public boolean registerCourse(Course course) {

        if (hasCourse(course.getCode())) {
            System.out.println("Already registered for this course.");
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

        for (String prerequisite : course.getPrerequisites()) {
            if (!completedCourses.contains(prerequisite)) {
                System.out.println("Prerequisite not completed: " + prerequisite);
                return false;
            }
        }

        registeredCourses.add(course);
        course.reduceCapacity();

        System.out.println("Course registered successfully.");
        return true;
    }

    public boolean dropCourse(String code) {

        for (Course c : registeredCourses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                registeredCourses.remove(c);
                c.increaseCapacity();

                System.out.println("Course dropped successfully.");
                return true;
            }
        }

        System.out.println("Course not found in registered list.");
        return false;
    }

    public void displayCourses() {

        if (registeredCourses.isEmpty()) {
            System.out.println("No courses registered.");
            return;
        }

        System.out.println("\nRegistered Courses:");

        for (Course c : registeredCourses) {
            System.out.println(
                    c.getCode() + " | " +
                    c.getTitle() + " | Credits: " +
                    c.getCredits()
            );
        }

        System.out.println("Total Credits: " + getTotalCredits());
    }

    public void displayStudent() {
        System.out.println(
                id + " | " + name +
                " | " + email +
                " | Max Credits: " + maxCredits
        );
    }
}

class Registration {
    private String registrationId;
    private Student student;
    private Course course;
    private String registrationDate;

    Registration(String registrationId, Student student,
                 Course course, String registrationDate) {
        this.registrationId = registrationId;
        this.student = student;
        this.course = course;
        this.registrationDate = registrationDate;
    }

    public void display() {
        System.out.println(
                registrationId +
                " | Student: " + student.name +
                " | Course: " + course.getCode() +
                " | Date: " + registrationDate
        );
    }
}

class Registrar extends User {

    private ArrayList<Student> students;
    private ArrayList<Course> courses;
    private ArrayList<Registration> registrations;

    Registrar(String id, String name, String email) {
        super(id, name, email);

        students = new ArrayList<>();
        courses = new ArrayList<>();
        registrations = new ArrayList<>();
    }

    public void addStudent(Student student) {

        if (searchStudent(student.id) != null) {
            System.out.println("Student ID already exists.");
            return;
        }

        students.add(student);
        System.out.println("Student added successfully.");
    }

    public void addCourse(Course course) {

        if (searchCourse(course.getCode()) != null) {
            System.out.println("Course code already exists.");
            return;
        }

        courses.add(course);
        System.out.println("Course added successfully.");
    }

    public void removeCourse(String code) {

        Course course = searchCourse(code);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        courses.remove(course);
        System.out.println("Course removed successfully.");
    }

    public Student searchStudent(String id) {

        for (Student s : students) {
            if (s.id.equalsIgnoreCase(id)) {
                return s;
            }
        }

        return null;
    }

    public Student searchStudentByName(String name) {

        for (Student s : students) {
            if (s.name.equalsIgnoreCase(name)) {
                return s;
            }
        }

        return null;
    }

    public Course searchCourse(String code) {

        for (Course c : courses) {
            if (c.getCode().equalsIgnoreCase(code)) {
                return c;
            }
        }

        return null;
    }

    public Course searchCourseByTitle(String title) {

        for (Course c : courses) {
            if (c.getTitle().equalsIgnoreCase(title)) {
                return c;
            }
        }

        return null;
    }

    public void updateCourse(String code, Scanner sc) {

        Course course = searchCourse(code);

        if (course == null) {
            System.out.println("Course not found.");
            return;
        }

        System.out.print("Enter new title: ");
        String title = sc.nextLine();

        System.out.print("Enter new credits: ");
        int credits = Integer.parseInt(sc.nextLine());

        System.out.print("Enter new instructor: ");
        String instructor = sc.nextLine();

        System.out.print("Enter new capacity: ");
        int capacity = Integer.parseInt(sc.nextLine());

        course.setTitle(title);
        course.setCredits(credits);
        course.setInstructor(instructor);
        course.setCapacity(capacity);

        System.out.println("Course updated successfully.");
    }

    public void register(Student student, Course course) {

        boolean success = student.registerCourse(course);

        if (success) {

            String registrationId = "R" +
                    String.format("%03d", registrations.size() + 1);

            Registration registration =
                    new Registration(
                            registrationId,
                            student,
                            course,
                            new Date().toString()
                    );

            registrations.add(registration);
        }
    }

    public void displayStudents() {

        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        System.out.println("\nStudents:");

        for (Student s : students) {
            s.displayStudent();
        }
    }

    public void displayCourses() {

        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        System.out.println("\nCourses:");

        for (Course c : courses) {
            c.display();
        }
    }

    public void displayRegistrations() {

        if (registrations.isEmpty()) {
            System.out.println("No registrations available.");
            return;
        }

        System.out.println("\nRegistrations:");

        for (Registration r : registrations) {
            r.display();
        }
    }
}

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Registrar registrar =
                new Registrar("R001", "Registrar", "registrar@gmail.com");

        while (true) {

            System.out.println("\n==================================");
            System.out.println("     STUDENT MANAGEMENT SYSTEM");
            System.out.println("==================================");

            System.out.println("1. Add Student");
            System.out.println("2. Add Course");
            System.out.println("3. Display Students");
            System.out.println("4. Display Courses");
            System.out.println("5. Search Student");
            System.out.println("6. Search Course");
            System.out.println("7. Register Course");
            System.out.println("8. Drop Course");
            System.out.println("9. View Student Courses");
            System.out.println("10. View Registrations");
            System.out.println("11. Update Course");
            System.out.println("12. Remove Course");
            System.out.println("0. Exit");

            System.out.print("\nEnter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:

                    System.out.print("Enter student ID: ");
                    String studentId = sc.nextLine();

                    System.out.print("Enter student name: ");
                    String studentName = sc.nextLine();

                    System.out.print("Enter email: ");
                    String email = sc.nextLine();

                    System.out.print("Enter maximum credits allowed: ");
                    int maxCredits = Integer.parseInt(sc.nextLine());

                    Student student =
                            new Student(
                                    studentId,
                                    studentName,
                                    email,
                                    maxCredits
                            );

                    registrar.addStudent(student);

                    break;

                case 2:

                    System.out.print("Enter course code: ");
                    String code = sc.nextLine();

                    System.out.print("Enter course title: ");
                    String title = sc.nextLine();

                    System.out.print("Enter credits: ");
                    int credits = Integer.parseInt(sc.nextLine());

                    System.out.print("Enter instructor name: ");
                    String instructor = sc.nextLine();

                    System.out.print("Enter capacity: ");
                    int capacity = Integer.parseInt(sc.nextLine());

                    Course course =
                            new Course(
                                    code,
                                    title,
                                    credits,
                                    instructor,
                                    capacity
                            );

                    System.out.print(
                            "Enter prerequisite course code (or NONE): "
                    );

                    String prerequisite = sc.nextLine();

                    if (!prerequisite.equalsIgnoreCase("NONE")) {
                        course.addPrerequisite(prerequisite);
                    }

                    registrar.addCourse(course);

                    break;

                case 3:

                    registrar.displayStudents();

                    break;

                case 4:

                    registrar.displayCourses();

                    break;

                case 5:

                    System.out.println("\n1. Search by ID");
                    System.out.println("2. Search by Name");

                    System.out.print("Enter choice: ");
                    int studentSearchChoice =
                            Integer.parseInt(sc.nextLine());

                    if (studentSearchChoice == 1) {

                        System.out.print("Enter student ID: ");
                        String id = sc.nextLine();

                        Student found =
                                registrar.searchStudent(id);

                        if (found != null) {
                            found.displayStudent();
                        } else {
                            System.out.println("Student not found.");
                        }

                    } else if (studentSearchChoice == 2) {

                        System.out.print("Enter student name: ");
                        String name = sc.nextLine();

                        Student found =
                                registrar.searchStudentByName(name);

                        if (found != null) {
                            found.displayStudent();
                        } else {
                            System.out.println("Student not found.");
                        }

                    } else {
                        System.out.println("Invalid choice.");
                    }

                    break;

                case 6:

                    System.out.println("\n1. Search by Code");
                    System.out.println("2. Search by Title");

                    System.out.print("Enter choice: ");
                    int courseSearchChoice =
                            Integer.parseInt(sc.nextLine());

                    if (courseSearchChoice == 1) {

                        System.out.print("Enter course code: ");
                        String searchCode = sc.nextLine();

                        Course found =
                                registrar.searchCourse(searchCode);

                        if (found != null) {
                            found.display();
                        } else {
                            System.out.println("Course not found.");
                        }

                    } else if (courseSearchChoice == 2) {

                        System.out.print("Enter course title: ");
                        String searchTitle = sc.nextLine();

                        Course found =
                                registrar.searchCourseByTitle(searchTitle);

                        if (found != null) {
                            found.display();
                        } else {
                            System.out.println("Course not found.");
                        }

                    } else {
                        System.out.println("Invalid choice.");
                    }

                    break;

                case 7:

                    System.out.print("Enter student ID: ");
                    String regStudentId = sc.nextLine();

                    Student regStudent =
                            registrar.searchStudent(regStudentId);

                    if (regStudent == null) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.print("Enter course code: ");
                    String regCourseCode = sc.nextLine();

                    Course regCourse =
                            registrar.searchCourse(regCourseCode);

                    if (regCourse == null) {
                        System.out.println("Course not found.");
                        break;
                    }

                    registrar.register(regStudent, regCourse);

                    break;

                case 8:

                    System.out.print("Enter student ID: ");
                    String dropStudentId = sc.nextLine();

                    Student dropStudent =
                            registrar.searchStudent(dropStudentId);

                    if (dropStudent == null) {
                        System.out.println("Student not found.");
                        break;
                    }

                    System.out.print("Enter course code: ");
                    String dropCourseCode = sc.nextLine();

                    dropStudent.dropCourse(dropCourseCode);

                    break;

                case 9:

                    System.out.print("Enter student ID: ");
                    String viewStudentId = sc.nextLine();

                    Student viewStudent =
                            registrar.searchStudent(viewStudentId);

                    if (viewStudent == null) {
                        System.out.println("Student not found.");
                    } else {
                        viewStudent.displayCourses();
                    }

                    break;

                case 10:

                    registrar.displayRegistrations();

                    break;

                case 11:

                    System.out.print("Enter course code to update: ");
                    String updateCode = sc.nextLine();

                    registrar.updateCourse(updateCode, sc);

                    break;

                case 12:

                    System.out.print("Enter course code to remove: ");
                    String removeCode = sc.nextLine();

                    registrar.removeCourse(removeCode);

                    break;

                case 0:

                    System.out.println("Thank you!");
                    sc.close();
                    return;

                default:

                    System.out.println("Invalid choice.");
            }
        }
    }
}

