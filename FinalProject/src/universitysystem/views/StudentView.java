package universitysystem.views;

import universitysystem.enums.Semester;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.utils.ConsoleUtils;

import java.util.List;

/**
 * Console view for student academic actions.
 * This class only reads input and prints output; business rules live in services/controllers.
 */
public class StudentView {
    /**
     * Shows the main student menu and returns the selected option.
     */
    public int showMenu(Student student) {
        ConsoleUtils.printHeader("Student Menu");
        System.out.println("Logged in as: " + formatStudentName(student));
        System.out.println("1. View available courses and register");
        System.out.println("2. View marks for enrolled courses");
        System.out.println("3. Print transcript with GPA");
        System.out.println("4. View teachers for my course");
        System.out.println("5. Rate teacher");
        System.out.println("6. News");
        System.out.println("7. Journals");
        System.out.println("8. Messages");
        System.out.println("9. Research");
        System.out.println("0. Back / Logout");
        return readInt("Choose an option: ", 0, 9);
    }

    /**
     * Menu option 1 display: prints available courses in a readable table.
     */
    public void displayAvailableCourses(List<Course> courses) {
        System.out.println();
        System.out.println("Available courses");
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses are currently available for registration.");
            return;
        }
        System.out.printf("%-5s %-12s %-30s %-8s %-10s%n", "No.", "Code", "Title", "Credits", "Capacity");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            int enrolled = course.getEnrollments() == null ? 0 : course.getEnrollments().size();
            System.out.printf(
                    "%-5d %-12s %-30s %-8d %-10s%n",
                    i + 1,
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getCredits(),
                    enrolled + "/" + course.getCapacity()
            );
        }
    }

    public void displayCourses(List<Course> courses) {
        System.out.println();
        System.out.println("Courses");
        if (courses == null || courses.isEmpty()) {
            System.out.println("No courses found.");
            return;
        }
        System.out.printf("%-5s %-12s %-30s %-8s%n", "No.", "Code", "Title", "Credits");
        for (int i = 0; i < courses.size(); i++) {
            Course course = courses.get(i);
            System.out.printf(
                    "%-5d %-12s %-30s %-8d%n",
                    i + 1,
                    course.getCourseCode(),
                    course.getTitle(),
                    course.getCredits()
            );
        }
    }

    /**
     * Menu option 1 input: asks which course the student wants to register for.
     */
    public int askCourseSelection(int courseCount) {
        if (courseCount <= 0) {
            return -1;
        }
        System.out.println("Enter 0 to cancel.");
        int selected = readInt("Select course number: ", 0, courseCount);
        return selected == 0 ? -1 : selected - 1;
    }

    public void displayTeachers(List<Teacher> teachers) {
        System.out.println();
        System.out.println("Teachers");
        if (teachers == null || teachers.isEmpty()) {
            System.out.println("No teachers found for this course.");
            return;
        }
        System.out.printf("%-5s %-8s %-20s %-20s %-16s %-8s%n", "No.", "ID", "Name", "Last name", "Position", "Rate");
        for (int i = 0; i < teachers.size(); i++) {
            Teacher teacher = teachers.get(i);
            System.out.printf(
                    "%-5d %-8d %-20s %-20s %-16s %-8.2f%n",
                    i + 1,
                    teacher.getId(),
                    teacher.getName(),
                    teacher.getLastName(),
                    teacher.getPosition() == null ? "N/A" : teacher.getPosition().name(),
                    teacher.getRate()
            );
        }
    }

    public int askTeacherSelection(int teacherCount) {
        if (teacherCount <= 0) {
            return -1;
        }
        System.out.println("Enter 0 to cancel.");
        int selected = readInt("Select teacher number: ", 0, teacherCount);
        return selected == 0 ? -1 : selected - 1;
    }

    public double askTeacherRating() {
        return readDouble("Rating from 0 to 5: ", 0.0, 5.0);
    }

    /**
     * Menu option 1 input: asks semester for the new enrollment.
     */
    public Semester askSemester() {
        ConsoleUtils.printHeader("Choose Semester");
        Semester[] semesters = Semester.values();
        for (int i = 0; i < semesters.length; i++) {
            System.out.println((i + 1) + ". " + semesters[i]);
        }
        int selected = readInt("Semester: ", 1, semesters.length);
        return semesters[selected - 1];
    }

    /**
     * Menu option 1 input: asks academic year for the new enrollment.
     */
    public int askYear() {
        return readInt("Academic year: ", 2000, 2100);
    }

    /**
     * Menu option 2 display: prints graded enrollments and final scores.
     */
    public void displayMarks(List<Enrollment> enrollments) {
        System.out.println();
        System.out.println("Marks");
        if (enrollments == null || enrollments.isEmpty()) {
            System.out.println("No marks have been added yet.");
            return;
        }
        System.out.printf("%-12s %-30s %-8s %-8s %-8s %-8s%n", "Code", "Course", "Att1", "Att2", "Final", "Total");
        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            Mark mark = enrollment.getMark();
            System.out.printf(
                    "%-12s %-30s %-8.2f %-8.2f %-8.2f %-8.2f%n",
                    course == null ? "N/A" : course.getCourseCode(),
                    course == null ? "Unknown" : course.getTitle(),
                    mark == null ? 0.0 : mark.getAtt1(),
                    mark == null ? 0.0 : mark.getAtt2(),
                    mark == null ? 0.0 : mark.getFinalExam(),
                    mark == null ? 0.0 : mark.calculateFinal()
            );
        }
    }

    /**
     * Menu option 3 display: prints transcript text prepared by the service.
     */
    public void displayTranscript(String transcript) {
        ConsoleUtils.printHeader("Transcript");
        System.out.println(transcript == null ? "Transcript is unavailable." : transcript);
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

    private String formatStudentName(Student student) {
        if (student == null) {
            return "Unknown student";
        }
        return student.getName() + " " + student.getLastName() + " (ID: " + student.getId() + ")";
    }
}
