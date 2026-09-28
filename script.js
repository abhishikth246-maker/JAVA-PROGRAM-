let students =
    JSON.parse(localStorage.getItem("students")) || [];

let courses =
    JSON.parse(localStorage.getItem("courses")) || [];

let registrations =
    JSON.parse(localStorage.getItem("registrations")) || [];


// Save data

function saveData() {

    localStorage.setItem(
        "students",
        JSON.stringify(students)
    );

    localStorage.setItem(
        "courses",
        JSON.stringify(courses)
    );

    localStorage.setItem(
        "registrations",
        JSON.stringify(registrations)
    );
}


// Navigation

function showSection(section) {

    document.querySelectorAll(".section")
        .forEach(s => s.classList.remove("active"));

    document.getElementById(section)
        .classList.add("active");

    displayStudents();
    displayCourses();
    displayRegistrations();
    updateDashboard();
}


// Dashboard

function updateDashboard() {

    document.getElementById("studentCount")
        .innerText = students.length;

    document.getElementById("courseCount")
        .innerText = courses.length;

    document.getElementById("registrationCount")
        .innerText = registrations.length;
}


// Add Student

function addStudent() {

    let id =
        document.getElementById("studentId").value.trim();

    let name =
        document.getElementById("studentName").value.trim();

    let email =
        document.getElementById("studentEmail").value.trim();

    let maxCredits =
        Number(document.getElementById("maxCredits").value);

    if (!id || !name || !email || !maxCredits) {
        alert("Please fill all fields.");
        return;
    }

    if (students.some(s =>
        s.id.toLowerCase() === id.toLowerCase())) {

        alert("Student ID already exists.");
        return;
    }

    students.push({
        id: id,
        name: name,
        email: email,
        maxCredits: maxCredits,
        registeredCourses: [],
        completedCourses: []
    });

    saveData();
    displayStudents();
    updateDashboard();

    document.getElementById("studentId").value = "";
    document.getElementById("studentName").value = "";
    document.getElementById("studentEmail").value = "";
    document.getElementById("maxCredits").value = "";

    alert("Student added successfully.");
}


// Display Students

function displayStudents(list = students) {

    let table =
        document.getElementById("studentTable");

    table.innerHTML = "";

    list.forEach(student => {

        table.innerHTML += `
            <tr>
                <td>${student.id}</td>
                <td>${student.name}</td>
                <td>${student.email}</td>
                <td>${student.maxCredits}</td>
            </tr>
        `;
    });
}


// Search Students

function searchStudents() {

    let value =
        document.getElementById("studentSearch")
        .value.toLowerCase();

    let result = students.filter(s =>
        s.id.toLowerCase().includes(value) ||
        s.name.toLowerCase().includes(value)
    );

    displayStudents(result);
}


// Add Course

function addCourse() {

    let code =
        document.getElementById("courseCode").value.trim();

    let title =
        document.getElementById("courseTitle").value.trim();

    let credits =
        Number(document.getElementById("courseCredits").value);

    let instructor =
        document.getElementById("courseInstructor").value.trim();

    let capacity =
        Number(document.getElementById("courseCapacity").value);

    let prerequisite =
        document.getElementById("coursePrerequisite")
        .value.trim();

    if (!code || !title || !credits ||
        !instructor || !capacity) {

        alert("Please fill all fields.");
        return;
    }

    if (courses.some(c =>
        c.code.toLowerCase() === code.toLowerCase())) {

        alert("Course code already exists.");
        return;
    }

    courses.push({
        code: code,
        title: title,
        credits: credits,
        instructor: instructor,
        capacity: capacity,
        prerequisite:
            prerequisite.toUpperCase() === "NONE"
                ? ""
                : prerequisite
    });

    saveData();
    displayCourses();
    updateDashboard();

    document.getElementById("courseCode").value = "";
    document.getElementById("courseTitle").value = "";
    document.getElementById("courseCredits").value = "";
    document.getElementById("courseInstructor").value = "";
    document.getElementById("courseCapacity").value = "";
    document.getElementById("coursePrerequisite").value = "";

    alert("Course added successfully.");
}


// Display Courses

function displayCourses(list = courses) {

    let table =
        document.getElementById("courseTable");

    table.innerHTML = "";

    list.forEach(course => {

        table.innerHTML += `
            <tr>

                <td>${course.code}</td>

                <td>${course.title}</td>

                <td>${course.credits}</td>

                <td>${course.instructor}</td>

                <td>${course.capacity}</td>

                <td>
                    ${course.prerequisite || "None"}
                </td>

                <td>

                    <button
                        onclick="editCourse('${course.code}')">
                        Update
                    </button>

                    <button
                        class="delete-btn"
                        onclick="removeCourse('${course.code}')">
                        Delete
                    </button>

                </td>

            </tr>
        `;
    });
}


// Search Courses

function searchCourses() {

    let value =
        document.getElementById("courseSearch")
        .value.toLowerCase();

    let result = courses.filter(c =>
        c.code.toLowerCase().includes(value) ||
        c.title.toLowerCase().includes(value)
    );

    displayCourses(result);
}


// Register Course

function registerCourse() {

    let studentId =
        document.getElementById("registerStudent")
        .value.trim();

    let courseCode =
        document.getElementById("registerCourse")
        .value.trim();

    let student =
        students.find(s =>
            s.id.toLowerCase() ===
            studentId.toLowerCase());

    let course =
        courses.find(c =>
            c.code.toLowerCase() ===
            courseCode.toLowerCase());

    if (!student) {
        showMessage("Student not found.");
        return;
    }

    if (!course) {
        showMessage("Course not found.");
        return;
    }

    if (student.registeredCourses.includes(course.code)) {
        showMessage("Already registered for this course.");
        return;
    }

    if (course.capacity <= 0) {
        showMessage("Course capacity is full.");
        return;
    }

    let totalCredits = 0;

    student.registeredCourses.forEach(code => {

        let c = courses.find(x => x.code === code);

        if (c)
            totalCredits += c.credits;
    });

    if (totalCredits + course.credits >
        student.maxCredits) {

        showMessage("Credit limit exceeded.");
        return;
    }

    if (course.prerequisite &&
        !student.completedCourses.includes(
            course.prerequisite)) {

        showMessage(
            "Prerequisite not completed: " +
            course.prerequisite
        );

        return;
    }

    student.registeredCourses.push(course.code);

    course.capacity--;

    let registrationId =
        "R" +
        String(registrations.length + 1)
            .padStart(3, "0");

    registrations.push({

        id: registrationId,

        studentId: student.id,

        studentName: student.name,

        courseCode: course.code,

        date: new Date().toLocaleString()
    });

    saveData();
    updateDashboard();
    displayCourses();
    displayRegistrations();

    showMessage(
        "Course registered successfully."
    );

    document.getElementById("registerStudent").value = "";
    document.getElementById("registerCourse").value = "";
}


// Message

function showMessage(message) {

    document.getElementById("message")
        .innerText = message;
}


// Display Registrations

function displayRegistrations() {

    let table =
        document.getElementById("registrationTable");

    table.innerHTML = "";

    registrations.forEach(r => {

        table.innerHTML += `
            <tr>

                <td>${r.id}</td>

                <td>
                    ${r.studentId} - ${r.studentName}
                </td>

                <td>${r.courseCode}</td>

                <td>${r.date}</td>

                <td>

                    <button
                        class="drop-btn"
                        onclick="dropCourse(
                            '${r.studentId}',
                            '${r.courseCode}'
                        )">
                        Drop
                    </button>

                </td>

            </tr>
        `;
    });
}


// Drop Course

function dropCourse(studentId, courseCode) {

    let student =
        students.find(s => s.id === studentId);

    let course =
        courses.find(c => c.code === courseCode);

    if (!student || !course)
        return;

    let index =
        student.registeredCourses
        .indexOf(courseCode);

    if (index === -1)
        return;

    student.registeredCourses.splice(index, 1);

    course.capacity++;

    registrations =
        registrations.filter(r =>
            !(r.studentId === studentId &&
              r.courseCode === courseCode)
        );

    saveData();

    displayCourses();
    displayRegistrations();
    updateDashboard();

    alert("Course dropped successfully.");
}


// Remove Course

function removeCourse(code) {

    let course =
        courses.find(c => c.code === code);

    if (!course)
        return;

    let used =
        students.some(s =>
            s.registeredCourses.includes(code));

    if (used) {

        alert(
            "Cannot remove a course that is currently registered."
        );

        return;
    }

    courses =
        courses.filter(c => c.code !== code);

    saveData();

    displayCourses();
    updateDashboard();

    alert("Course removed successfully.");
}


// Update Course

function editCourse(code) {

    let course =
        courses.find(c => c.code === code);

    if (!course)
        return;

    let title =
        prompt("Enter new title:", course.title);

    if (title === null)
        return;

    let credits =
        Number(prompt(
            "Enter new credits:",
            course.credits
        ));

    if (!credits)
        return;

    let instructor =
        prompt(
            "Enter new instructor:",
            course.instructor
        );

    if (instructor === null)
        return;

    let capacity =
        Number(prompt(
            "Enter new capacity:",
            course.capacity
        ));

    if (!capacity)
        return;

    course.title = title;
    course.credits = credits;
    course.instructor = instructor;
    course.capacity = capacity;

    saveData();

    displayCourses();

    alert("Course updated successfully.");
}


// Initial display

displayStudents();
displayCourses();
displayRegistrations();
updateDashboard();
