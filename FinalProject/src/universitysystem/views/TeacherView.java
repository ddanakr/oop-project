package universitysystem.views;

import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.enums.Urgency;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.utils.ConsoleUtils;

import java.util.List;

/**
 * Console view for teacher academic actions.
 * This class handles menu text, input validation, and formatted output only.
 */
public class TeacherView {
    /**
     * Shows the main teacher menu and returns the selected option.
     */
    public int showMenu(Teacher teacher) {
        ConsoleUtils.printHeader("Teacher Menu");
        System.out.println("Logged in as: " + formatTeacherName(teacher));
        System.out.println("1. View assigned courses");
        System.out.println("2. View students in a course");
        System.out.println("3. Put or update marks for students");
        System.out.println("4. Send complaint about student");
        System.out.println("5. News");
        System.out.println("6. Journals");
        System.out.println("7. Messages");
        System.out.println("8. Research");
        System.out.println("0. Back / Logout");
        return readInt("Choose an option: ", 0, 8);
    }

    /**
     * Menu options 1-3 display: prints assigned courses in a readable table.
     */
    public void displayCourses(List<Course> courses) {
        System.out.println();
        System.out.println("Assigned courses");
        if (courses == null || courses.isEmpty()) {
            System.out.println("No assigned courses found.");
            return;
        }
        System.out.printf("%-5s %-12s %-30s %-8s %-10s%n", "No.", "Code", "Title", "Credits", "Students");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            int enrolled = course.getEnrollments() == null ? 0 : course.getEnrollments().size();
            System.out.printf(
                    "%-5d %-12s %-30s %-8d %-10d%n",
                    i + 1,
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getCredits(),
                    enrolled
            );
        }
    }

    /**
     * Menu options 2-3 input: asks which assigned course should be used.
     */
    public int askCourseSelection(int courseCount) {
        if (courseCount <= 0) {
            return -1;
        }
        System.out.println("Enter 0 to cancel.");
        int selected = readInt("Select course number: ", 0, courseCount);
        return selected == 0 ? -1 : selected - 1;
    }

    /**
     * Menu option 2 display: prints students enrolled in the selected course.
     */
    public void displayStudents(List<Student> students) {
        System.out.println();
        System.out.println("Students");
        if (students == null || students.isEmpty()) {
            System.out.println("No students are enrolled in this course.");
            return;
        }
        System.out.printf("%-5s %-8s %-20s %-20s %-12s%n", "No.", "ID", "Name", "Last name", "Speciality");
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            System.out.printf(
                    "%-5d %-8d %-20s %-20s %-12s%n",
                    i + 1,
                    student.getId(),
                    student.getName(),
                    student.getLastName(),
                    student.getSpeciality()
            );
        }
    }

    /**
     * Menu option 3 display: prints enrollments before choosing a student to grade.
     */
    public void displayCourseJournal(List<Enrollment> enrollments) {
        System.out.println();
        System.out.println("Course journal");
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("No enrollments found for this course.");
            return;
        }
        System.out.printf("%-5s %-8s %-24s %-10s %-10s%n", "No.", "ID", "Student", "Attempt", "Total");
        for (int i = 0; i < enrollments.size(); i++) {
            Enrollment enrollment = enrollments.get(i);
            Student student = enrollment.getStudent();
            Mark mark = enrollment.getMark();
            System.out.printf(
                    "%-5d %-8d %-24s %-10d %-10s%n",
                    i + 1,
                    student == null ? 0 : student.getId(),
                    student == null ? "Unknown" : student.getName() + " " + student.getLastName(),
                    enrollment.getAttemptNumber(),
                    mark == null ? "N/A" : String.format("%.2f", mark.calculateFinal())
            );
        }
    }

    /**
     * Menu option 3 input: asks which enrolled student should receive a mark.
     */
    public int askEnrollmentSelection(int enrollmentCount) {
        if (enrollmentCount <= 0) {
            return -1;
        }
        System.out.println("Enter 0 to cancel.");
        int selected = readInt("Select student number: ", 0, enrollmentCount);
        return selected == 0 ? -1 : selected - 1;
    }

    /**
     * Menu option 3 input: asks whether to put a new mark or update an existing one.
     */
    public boolean askUpdateExistingMark() {
        ConsoleUtils.printHeader("Mark Action");
        System.out.println("1. Put mark");
        System.out.println("2. Update mark");
        return readInt("Choose action: ", 1, 2) == 2;
    }

    /**
     * Menu option 3 input: reads mark components and returns a Mark object.
     */
    public Mark askMark() {
        double att1 = readDouble("Attestation 1: ", 0.0, 100.0);
        double att2 = readDouble("Attestation 2: ", 0.0, 100.0);
        double finalExam = readDouble("Final exam: ", 0.0, 100.0);
        return new Mark(att1, att2, finalExam);
    }

    public String askComplaintDescription() {
        return ConsoleUtils.getInput("Complaint description: ");
    }

    public Urgency askUrgency() {
        ConsoleUtils.printHeader("Urgency");
        Urgency[] values = Urgency.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        int selected = readInt("Choose urgency: ", 1, values.length);
        return values[selected - 1];
    }

    /**
     * Prints a success message after a controller operation.
     */
    public void showSuccess(String message) {
        System.out.println("Success: " + message);
    }

    /**
     * Prints validation or exception messages without stopping the menu loop.
     */
    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    /**
     * Pauses after an action so the user can read the result.
     */
    public void waitForEnter() {
        ConsoleUtils.pressEnterToContinue();
    }

    private int readInt(String prompt, int min, int max) {
        while (true) {
            String input = ConsoleUtils.getInput(prompt).trim();
            try {
                int value = Integer.parseInt(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private double readDouble(String prompt, double min, double max) {
        while (true) {
            String input = ConsoleUtils.getInput(prompt).trim();
            try {
                double value = Double.parseDouble(input);
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Please enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private String formatTeacherName(Teacher teacher) {
        if (teacher == null) {
            return "Unknown teacher";
        }
        return teacher.getName() + " " + teacher.getLastName() + " (ID: " + teacher.getId() + ")";
    }
}
