package universitysystem.controllers;

import universitysystem.database.Database;
import universitysystem.exceptions.CourseFullException;
import universitysystem.exceptions.MaxCreditsException;
import universitysystem.exceptions.MaxRetakesException;
import universitysystem.enums.Semester;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.research.Researcher;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;
import universitysystem.services.EnrollmentService;
import universitysystem.services.InMemoryMessageService;
import universitysystem.services.ResearchService;
import universitysystem.services.StudentService;
import universitysystem.views.MessageView;
import universitysystem.views.StudentView;

import java.util.Collections;
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
    private final NewsController newsController;
    private final JournalController journalController;
    private final MessageController messageController;
    private final ResearchController researchController;

    public StudentController(Student student) {
        this(
                student,
                Database.getInstance(),
                new StudentService(),
                new EnrollmentService(),
                new StudentView(),
                new NewsController(student),
                new JournalController(student),
                new MessageController(new InMemoryMessageService(Database.getInstance()), new MessageView()),
                student instanceof Researcher ? new ResearchController((Researcher) student) : null
        );
    }

    public StudentController(
            Student student,
            Database database,
            StudentService studentService,
            EnrollmentService enrollmentService,
            StudentView studentView,
            NewsController newsController,
            JournalController journalController,
            MessageController messageController,
            ResearchController researchController
    ) {
        this.student = student;
        this.database = database;
        this.studentService = studentService;
        this.enrollmentService = enrollmentService;
        this.studentView = studentView;
        this.newsController = newsController;
        this.journalController = journalController;
        this.messageController = messageController;
        this.researchController = researchController;
    }

    public StudentController(
            Student student,
            Database database,
            StudentService studentService,
            EnrollmentService enrollmentService,
            StudentView studentView
    ) {
        this(
                student,
                database,
                studentService,
                enrollmentService,
                studentView,
                new NewsController(student),
                new JournalController(student),
                new MessageController(new InMemoryMessageService(database), new MessageView()),
                student instanceof Researcher ? new ResearchController((Researcher) student) : null
        );
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
                case 4:
                    handleViewTeachersForCourse();
                    break;
                case 5:
                    handleRateTeacher();
                    break;
                case 6:
                    newsController.run();
                    break;
                case 7:
                    journalController.run();
                    break;
                case 8:
                    openMessages();
                    break;
                case 9:
                    openResearch();
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

    public void handleViewTeachersForCourse() {
        Course course = chooseRegisteredCourse();
        if (course == null) {
            return;
        }
        studentView.displayTeachers(studentService.getTeachersForCourse(course));
        studentView.waitForEnter();
    }

    public void handleRateTeacher() {
        Course course = chooseRegisteredCourse();
        if (course == null) {
            return;
        }
        List<Teacher> teachers = studentService.getTeachersForCourse(course);
        studentView.displayTeachers(teachers);
        int selectedIndex = studentView.askTeacherSelection(teachers.size());
        if (selectedIndex < 0) {
            return;
        }
        boolean rated = studentService.rateTeacher(student, teachers.get(selectedIndex), studentView.askTeacherRating());
        studentView.showSuccess(rated ? "Teacher rated." : "Could not rate teacher.");
        studentView.waitForEnter();
    }

    private Course chooseRegisteredCourse() {
        List<Course> courses = studentService.getRegisteredCourses(student);
        studentView.displayCourses(courses);
        int selectedIndex = studentView.askCourseSelection(courses.size());
        if (selectedIndex < 0) {
            return null;
        }
        return courses.get(selectedIndex);
    }

    private void openMessages() {
        List<User> users = database == null || database.getUsers() == null ? Collections.emptyList() : database.getUsers();
        messageController.run(student, users);
    }

    private void openResearch() {
        if (researchController == null) {
            Researcher researcher = new ResearchService().getResearcherForUser(student);
            if (researcher == null) {
                studentView.showError("Current student is not a researcher.");
                studentView.waitForEnter();
                return;
            }
            new ResearchController(researcher).run();
        } else {
            researchController.run();
        }
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
