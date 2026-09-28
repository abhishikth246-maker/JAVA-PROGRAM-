class Registration {
    private String registrationId;
    private Student student;
    private Course course;
    private String registrationDate;

    Registration(String id, Student student,
                 Course course, String date) {
        registrationId = id;
        this.student = student;
        this.course = course;
        registrationDate = date;
    }

    public void display() {
        System.out.println(registrationId +
                " | Student: " + student.name +
                " | Course: " + course.getCode() +
                " | Date: " + registrationDate);
    }
}
