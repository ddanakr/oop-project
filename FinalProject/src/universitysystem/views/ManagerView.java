package universitysystem.views;

import universitysystem.models.requests.Request;
import universitysystem.models.academic.Course;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManagerView {
    public void showMenu() {
        ConsoleUtils.printHeader("Manager Menu");
        System.out.println("1. News");
        System.out.println("2. Journals");
        System.out.println("3. Students");
        System.out.println("4. Teachers");
        System.out.println("5. Academic performance report");
        System.out.println("6. Requests");
        System.out.println("7. Course management");
        System.out.println("0. Back");
    }

    public void showStudentsMenu() {
        ConsoleUtils.printHeader("Students");
        System.out.println("1. Sort by GPA");
        System.out.println("2. Sort alphabetically");
        System.out.println("0. Back");
    }

    public void showRequestsMenu() {
        ConsoleUtils.printHeader("Requests");
        System.out.println("1. Sign request");
        System.out.println("2. Approve request");
        System.out.println("3. Reject request");
        System.out.println("0. Back");
    }

    public void showCourseManagementMenu() {
        ConsoleUtils.printHeader("Course Management");
        System.out.println("1. Show courses");
        System.out.println("2. Assign course to teacher");
        System.out.println("3. Open course registration");
        System.out.println("4. Close course registration");
        System.out.println("0. Back");
    }

    public int readMenuChoice() {
        return ConsoleUtils.readInt("Choose option: ");
    }

    public int readRequestId() {
        return ConsoleUtils.readInt("Request id: ");
    }

    public String readCourseCode() {
        return ConsoleUtils.readLine("Course code: ");
    }

    public int readTeacherId() {
        return ConsoleUtils.readInt("Teacher id: ");
    }

    public void showStudents(List<Student> students) {
        List<List<String>> rows = new ArrayList<>();
        for (Student student : students) {
            rows.add(Arrays.asList(
                    String.valueOf(student.getId()),
                    valueOrDash(student.getName()),
                    valueOrDash(student.getLastName()),
                    valueOrDash(student.getSpeciality()),
                    String.valueOf(student.getYear()),
                    String.valueOf(student.getGpa()),
                    String.valueOf(student.getCredits())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Name", "Last name", "Speciality", "Year", "GPA", "Credits"),
                rows
        );
    }

    public void showTeachers(List<Teacher> teachers) {
        List<List<String>> rows = new ArrayList<>();
        for (Teacher teacher : teachers) {
            rows.add(Arrays.asList(
                    String.valueOf(teacher.getId()),
                    valueOrDash(teacher.getName()),
                    valueOrDash(teacher.getLastName()),
                    teacher.getPosition() == null ? "-" : teacher.getPosition().name(),
                    String.valueOf(teacher.getRate())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Name", "Last name", "Position", "Rate"),
                rows
        );
    }

    public void showCourses(List<Course> courses) {
        List<List<String>> rows = new ArrayList<>();
        for (Course course : courses) {
            rows.add(Arrays.asList(
                    valueOrDash(course.getCourseCode()),
                    valueOrDash(course.getTitle()),
                    course.getType() == null ? "-" : course.getType().name(),
                    String.valueOf(course.getCredits()),
                    course.isRegistrationOpen() ? "open" : "closed",
                    String.valueOf(course.getCapacity())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("Code", "Title", "Type", "Credits", "Registration", "Capacity"),
                rows
        );
    }

    public void showRequests(List<Request> requests) {
        List<List<String>> rows = new ArrayList<>();
        for (Request request : requests) {
            rows.add(Arrays.asList(
                    String.valueOf(request.getRequestId()),
                    request.getSender() == null ? "-" : valueOrDash(request.getSender().getLogin()),
                    request.getRequestType() == null ? "-" : request.getRequestType().name(),
                    request.getUrgency() == null ? "-" : request.getUrgency().name(),
                    request.getStatus() == null ? "-" : request.getStatus().name(),
                    getSignaturesText(request),
                    valueOrDash(request.getDescription())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Sender", "Type", "Urgency", "Status", "Signatures", "Description"),
                rows
        );
    }

    public void showAverageGpa(double averageGpa) {
        ConsoleUtils.printHeader("Academic Report");
        System.out.println("Average GPA: " + averageGpa);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    private String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    private String getSignaturesText(Request request) {
        List<String> roles = new ArrayList<>();
        if (request.getSignatures() != null) {
            for (int i = 0; i < request.getSignatures().size(); i++) {
                roles.add(request.getSignatures().get(i).getSignerRole().name());
            }
        }
        return roles.isEmpty() ? "-" : String.join(",", roles);
    }
}
