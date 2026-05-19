package universitysystem.controllers;

import universitysystem.database.Database;
import universitysystem.enums.Urgency;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.research.Researcher;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;
import universitysystem.services.EnrollmentService;
import universitysystem.services.InMemoryMessageService;
import universitysystem.services.TeacherService;
import universitysystem.views.MessageView;
import universitysystem.views.TeacherView;

import java.util.Collections;
import java.util.List;

/**
 * Connects TeacherView console actions with TeacherService and EnrollmentService.
 */
public class TeacherController {
    private final Teacher teacher;
    private final Database database;
    private final TeacherService teacherService;
    private final EnrollmentService enrollmentService;
    private final TeacherView teacherView;
    private final NewsController newsController;
    private final JournalController journalController;
    private final MessageController messageController;
    private final ResearchController researchController;

    public TeacherController(Teacher teacher) {
        this(
                teacher,
                Database.getInstance(),
                new TeacherService(),
                new EnrollmentService(),
                new TeacherView(),
                new NewsController(teacher),
                new JournalController(teacher),
                new MessageController(new InMemoryMessageService(Database.getInstance()), new MessageView()),
                new ResearchController(teacher)
        );
    }

    public TeacherController(
            Teacher teacher,
            Database database,
            TeacherService teacherService,
            EnrollmentService enrollmentService,
            TeacherView teacherView,
            NewsController newsController,
            JournalController journalController,
            MessageController messageController,
            ResearchController researchController
    ) {
        this.teacher = teacher;
        this.database = database;
        this.teacherService = teacherService;
        this.enrollmentService = enrollmentService;
        this.teacherView = teacherView;
        this.newsController = newsController;
        this.journalController = journalController;
        this.messageController = messageController;
        this.researchController = researchController;
    }

    public TeacherController(
            Teacher teacher,
            Database database,
            TeacherService teacherService,
            EnrollmentService enrollmentService,
            TeacherView teacherView
    ) {
        this(
                teacher,
                database,
                teacherService,
                enrollmentService,
                teacherView,
                new NewsController(teacher),
                new JournalController(teacher),
                new MessageController(new InMemoryMessageService(database), new MessageView()),
                new ResearchController(teacher)
        );
    }

    /**
     * Starts the menu loop for the logged-in teacher.
     */
    public void start() {
        if (teacher == null) {
            teacherView.showError("No teacher is logged in.");
            return;
        }
        boolean running = true;
        while (running) {
            int option = teacherView.showMenu(teacher);
            switch (option) {
                case 1:
                    handleViewAssignedCourses();
                    break;
                case 2:
                    handleViewStudentsInCourse();
                    break;
                case 3:
                    handlePutOrUpdateMarks();
                    break;
                case 4:
                    handleSendComplaint();
                    break;
                case 5:
                    newsController.run();
                    break;
                case 6:
                    journalController.run();
                    break;
                case 7:
                    openMessages();
                    break;
                case 8:
                    openResearch();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    teacherView.showError("Unknown menu option.");
            }
        }
    }

    /**
     * Menu option 1: displays courses assigned to the logged-in teacher.
     */
    public void handleViewAssignedCourses() {
        teacherView.displayCourses(teacherService.viewAssignedCourses(teacher));
        teacherView.waitForEnter();
    }

    /**
     * Menu option 2: asks for a course and displays enrolled students.
     */
    public void handleViewStudentsInCourse() {
        Course course = chooseAssignedCourse();
        if (course == null) {
            return;
        }
        try {
            List<Student> students = teacherService.viewStudentsPerCourse(teacher, course);
            teacherView.displayStudents(students);
        } catch (IllegalArgumentException e) {
            teacherView.showError(e.getMessage());
        }
        teacherView.waitForEnter();
    }

    /**
     * Menu option 3: asks for a course, student enrollment, and mark values, then saves the mark.
     */
    public void handlePutOrUpdateMarks() {
        Course course = chooseAssignedCourse();
        if (course == null) {
            return;
        }

        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(course);
        teacherView.displayCourseJournal(enrollments);
        int selectedIndex = teacherView.askEnrollmentSelection(enrollments.size());
        if (selectedIndex < 0) {
            return;
        }

        Enrollment enrollment = enrollments.get(selectedIndex);
        Student student = enrollment.getStudent();
        Mark mark = teacherView.askMark();
        boolean update = teacherView.askUpdateExistingMark();

        try {
            if (update) {
                teacherService.updateMark(teacher, student, course, mark);
            } else {
                teacherService.putMark(teacher, student, course, mark);
            }
            teacherView.showSuccess("Mark saved for student ID " + (student == null ? "N/A" : student.getId()) + ".");
        } catch (IllegalArgumentException | IllegalStateException e) {
            teacherView.showError(e.getMessage());
        }
        teacherView.waitForEnter();
    }

    public void handleSendComplaint() {
        Course course = chooseAssignedCourse();
        if (course == null) {
            return;
        }
        try {
            List<Student> students = teacherService.viewStudentsPerCourse(teacher, course);
            teacherView.displayStudents(students);
            int selectedIndex = teacherView.askEnrollmentSelection(students.size());
            if (selectedIndex < 0) {
                return;
            }
            String description = teacherView.askComplaintDescription();
            Urgency urgency = teacherView.askUrgency();
            teacherService.sendComplaint(teacher, students.get(selectedIndex), description, urgency);
            teacherView.showSuccess("Complaint sent.");
        } catch (IllegalArgumentException e) {
            teacherView.showError(e.getMessage());
        }
        teacherView.waitForEnter();
    }

    /**
     * Shared helper for teacher menu options that need one assigned course.
     */
    private Course chooseAssignedCourse() {
        List<Course> courses = teacherService.viewAssignedCourses(teacher);
        teacherView.displayCourses(courses);
        int selectedIndex = teacherView.askCourseSelection(courses.size());
        if (selectedIndex < 0) {
            return null;
        }
        return courses.get(selectedIndex);
    }

    private void openMessages() {
        List<User> users = database == null || database.getUsers() == null ? Collections.emptyList() : database.getUsers();
        messageController.run(teacher, users);
    }

    private void openResearch() {
        if (!(teacher instanceof Researcher)) {
            teacherView.showError("Current teacher is not a researcher.");
            teacherView.waitForEnter();
            return;
        }
        researchController.run();
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
