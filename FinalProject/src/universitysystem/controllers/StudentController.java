package universitysystem.controllers;

import universitysystem.database.Database;
import universitysystem.exceptions.CourseFullException;
import universitysystem.exceptions.MaxCreditsException;
import universitysystem.exceptions.MaxRetakesException;
import universitysystem.enums.Semester;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.users.Student;
import universitysystem.services.EnrollmentService;
import universitysystem.services.StudentService;
import universitysystem.views.StudentView;

import java.util.List;

/**
 * Connects StudentView console actions with StudentService and EnrollmentService.
 */
public class StudentController {
    private final Student student;
    private final Database database;
    private final StudentService studentService;
    private final EnrollmentService enrollmentService;
    private final StudentView studentView;

    public StudentController(Student student) {
        this(
                student,
                Database.getInstance(),
                new StudentService(),
                new EnrollmentService(),
                new StudentView()
        );
    }

    public StudentController(
            Student student,
            Database database,
            StudentService studentService,
            EnrollmentService enrollmentService,
            StudentView studentView
    ) {
        this.student = student;
        this.database = database;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.studentView = studentView;
    }

    /**
     * Starts the menu loop for the logged-in student.
     */
    public void start() {
        if (student == null) {
            studentView.showError("No student is logged in.");
            return;
        }
        boolean running = true;
        while (running) {
            int option = studentView.showMenu(student);
            switch (option) {
                case 1:
                    handleCourseRegistration();
                    break;
                case 2:
                    handleViewMarks();
                    break;
                case 3:
                    handlePrintTranscript();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    studentView.showError("Unknown menu option.");
            }
        }
    }

    /**
     * Menu option 1: shows available courses and registers the student for the selected course.
     */
    public void handleCourseRegistration() {
        List<Course> courses = studentService.getAvailableCourses(student);
        studentView.displayAvailableCourses(courses);
        int selectedIndex = studentView.askCourseSelection(courses.size());
        if (selectedIndex < 0) {
            return;
        }

        Course selectedCourse = courses.get(selectedIndex);
        Semester semester = studentView.askSemester();
        int year = studentView.askYear();

        try {
            Enrollment enrollment = studentService.registerForCourse(student, selectedCourse, semester, year);
            studentView.showSuccess("Registered for " + enrollment.getCourse().getCourseCode() + ".");
        } catch (CourseFullException | MaxCreditsException | MaxRetakesException e) {
            studentView.showError(e.getMessage());
        } catch (IllegalArgumentException | IllegalStateException e) {
            studentView.showError(e.getMessage());
        }
        studentView.waitForEnter();
    }

    /**
     * Menu option 2: displays marks for the logged-in student's enrolled courses.
     */
    public void handleViewMarks() {
        studentView.displayMarks(studentService.viewMarks(student));
        studentView.waitForEnter();
    }

    /**
     * Menu option 3: prints a service-generated transcript with GPA.
     */
    public void handlePrintTranscript() {
        studentView.displayTranscript(studentService.printTranscript(student));
        studentView.waitForEnter();
    }

    /**
     * Gives future controllers/tests access to the Database singleton used here.
     */
    public Database getDatabase() {
        return database;
    }

    /**
     * Gives future controllers/tests access to enrollment helper methods.
     */
    public EnrollmentService getEnrollmentService() {
        return enrollmentService;
    }
}
