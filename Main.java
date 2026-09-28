import java.util.*;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Registrar registrar =
                new Registrar("R001", "Registrar",
                        "registrar@gmail.com");

        while (true) {

            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
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

            System.out.print("Enter choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.print("Student ID: ");
                    String id = sc.nextLine();

                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Maximum credits: ");
                    int max = Integer.parseInt(sc.nextLine());

                    registrar.addStudent(
                            new Student(id, name, email, max));
                    break;

                case 2:
                    System.out.print("Course code: ");
                    String code = sc.nextLine();

                    System.out.print("Title: ");
                    String title = sc.nextLine();

                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(sc.nextLine());

                    System.out.print("Instructor: ");
                    String instructor = sc.nextLine();

                    System.out.print("Capacity: ");
                    int capacity = Integer.parseInt(sc.nextLine());

                    Course c = new Course(
                            code, title, credits,
                            instructor, capacity);

                    System.out.print("Prerequisite or NONE: ");
                    String p = sc.nextLine();

                    if (!p.equalsIgnoreCase("NONE"))
                        c.addPrerequisite(p);

                    registrar.addCourse(c);
                    break;

                case 3:
                    registrar.displayStudents();
                    break;

                case 4:
                    registrar.displayCourses();
                    break;

                case 5:
                    System.out.print("Enter student ID: ");
                    Student s = registrar.searchStudent(sc.nextLine());

                    if (s != null)
                        s.displayStudent();
                    else
                        System.out.println("Student not found.");
                    break;

                case 6:
                    System.out.print("Enter course code: ");
                    Course course =
                            registrar.searchCourse(sc.nextLine());

                    if (course != null)
                        course.display();
                    else
                        System.out.println("Course not found.");
                    break;

                case 7:
                    System.out.print("Student ID: ");
                    Student rs =
                            registrar.searchStudent(sc.nextLine());

                    System.out.print("Course code: ");
                    Course rc =
                            registrar.searchCourse(sc.nextLine());

                    if (rs != null && rc != null)
                        registrar.register(rs, rc);
                    else
                        System.out.println("Student or course not found.");
                    break;

                case 8:
                    System.out.print("Student ID: ");
                    Student ds =
                            registrar.searchStudent(sc.nextLine());

                    System.out.print("Course code: ");
                    String dc = sc.nextLine();

                    if (ds != null)
                        ds.dropCourse(dc);
                    else
                        System.out.println("Student not found.");
                    break;

                case 9:
                    System.out.print("Student ID: ");
                    Student vs =
                            registrar.searchStudent(sc.nextLine());

                    if (vs != null)
                        vs.displayCourses();
                    else
                        System.out.println("Student not found.");
                    break;

                case 10:
                    registrar.displayRegistrations();
                    break;

                case 12:
                    System.out.print("Course code: ");
                    registrar.removeCourse(sc.nextLine());
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
