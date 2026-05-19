package universitysystem.controllers;

import universitysystem.models.users.Manager;
import universitysystem.models.academic.Course;
import universitysystem.models.users.Teacher;
import universitysystem.services.ManagerService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.ManagerView;

public class ManagerController {
    private final ManagerService managerService;
    private final ManagerView managerView;
    private final ManagerNewsController managerNewsController;
    private final ManagerJournalController managerJournalController;
    private Manager currentManager;

    public ManagerController(Manager currentManager) {
        this(new ManagerService(), new ManagerView(), new ManagerNewsController(currentManager), new ManagerJournalController(), currentManager);
    }

    public ManagerController(
            ManagerService managerService,
            ManagerView managerView,
            ManagerNewsController managerNewsController,
            ManagerJournalController managerJournalController,
            Manager currentManager
    ) {
        this.managerService = managerService;
        this.managerView = managerView;
        this.managerNewsController = managerNewsController;
        this.managerJournalController = managerJournalController;
        this.currentManager = currentManager;
    }

    public void setCurrentManager(Manager currentManager) {
        this.currentManager = currentManager;
        this.managerNewsController.setCurrentManager(currentManager);
    }

    public void run() {
        boolean running = true;

        while (running) {
            managerView.showMenu();
            int choice = managerView.readMenuChoice();

            try {
                running = handleChoice(choice);
            } catch (IllegalArgumentException | IllegalStateException | SecurityException e) {
                managerView.showError(e.getMessage());
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                managerNewsController.run();
                return true;
            case 2:
                managerJournalController.run();
                return true;
            case 3:
                handleStudents();
                return true;
            case 4:
                showTeachers();
                return true;
            case 5:
                showAcademicPerformanceReport();
                return true;
            case 6:
                handleRequests();
                return true;
            case 7:
                handleCourseManagement();
                return true;
            case 0:
                managerView.showMessage("Back to previous menu.");
                return false;
            default:
                managerView.showError("Unknown option.");
                return true;
        }
    }

    private void handleStudents() {
        boolean running = true;

        while (running) {
            managerView.showStudentsMenu();
            int choice = managerView.readMenuChoice();

            switch (choice) {
                case 1:
                    managerView.showStudents(managerService.getStudentsSortedByGpa());
                    break;
                case 2:
                    managerView.showStudents(managerService.getStudentsSortedAlphabetically());
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    managerView.showError("Unknown option.");
                    break;
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private void showTeachers() {
        managerView.showTeachers(managerService.getTeachersSortedAlphabetically());
    }

    private void showAcademicPerformanceReport() {
        managerView.showAverageGpa(managerService.getAverageGpa());
    }

    private void handleRequests() {
        boolean running = true;

        while (running) {
            managerView.showRequests(managerService.getRequests());
            managerView.showRequestsMenu();
            int choice = managerView.readMenuChoice();

            switch (choice) {
                case 1:
                    signRequest();
                    break;
                case 2:
                    approveRequest();
                    break;
                case 3:
                    rejectRequest();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    managerView.showError("Unknown option.");
                    break;
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private void handleCourseManagement() {
        boolean running = true;

        while (running) {
            managerView.showCourseManagementMenu();
            int choice = managerView.readMenuChoice();

            switch (choice) {
                case 1:
                    managerView.showCourses(managerService.getCourses());
                    break;
                case 2:
                    assignCourseToTeacher();
                    break;
                case 3:
                    setCourseRegistration(true);
                    break;
                case 4:
                    setCourseRegistration(false);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    managerView.showError("Unknown option.");
                    break;
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private void assignCourseToTeacher() {
        managerView.showCourses(managerService.getCourses());
        String courseCode = managerView.readCourseCode();
        managerView.showTeachers(managerService.getTeachersSortedAlphabetically());
        int teacherId = managerView.readTeacherId();

        Course course = managerService.getCourseByCode(courseCode);
        Teacher teacher = managerService.getTeacherById(teacherId);
        if (course == null || teacher == null) {
            managerView.showError("Course or teacher was not found.");
            return;
        }
        managerService.assignCourseToTeacher(course, teacher);
        managerView.showMessage("Course assigned.");
    }

    private void setCourseRegistration(boolean open) {
        managerView.showCourses(managerService.getCourses());
        Course course = managerService.getCourseByCode(managerView.readCourseCode());
        if (course == null) {
            managerView.showError("Course was not found.");
            return;
        }
        if (open) {
            managerService.openCourseRegistration(course);
            managerView.showMessage("Registration opened.");
        } else {
            managerService.closeCourseRegistration(course);
            managerView.showMessage("Registration closed.");
        }
    }

    private void approveRequest() {
        int requestId = managerView.readRequestId();
        if (managerService.approveRequest(requestId, currentManager)) {
            managerView.showMessage("Request approved.");
        } else {
            managerView.showError("Request was not found.");
        }
    }

    private void rejectRequest() {
        int requestId = managerView.readRequestId();
        if (managerService.rejectRequest(requestId, currentManager)) {
            managerView.showMessage("Request rejected.");
        } else {
            managerView.showError("Request was not found.");
        }
    }

    private void signRequest() {
        int requestId = managerView.readRequestId();
        if (managerService.signRequest(requestId, currentManager)) {
            managerView.showMessage("Request signed.");
        } else {
            managerView.showError("Request was not found.");
        }
    }
}
