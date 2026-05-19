package universitysystem.app;

import universitysystem.database.Database;
import universitysystem.enums.CourseType;
import universitysystem.enums.Degree;
import universitysystem.enums.RequestType;
import universitysystem.enums.Urgency;
import universitysystem.models.academic.Course;
import universitysystem.models.requests.Request;
import universitysystem.models.users.Admin;
import universitysystem.models.users.Manager;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.TechSupport;
import universitysystem.models.users.User;
import universitysystem.services.RequestService;
import universitysystem.services.RequestServiceImpl;

import java.util.ArrayList;

public final class DemoDataSeeder {
    private DemoDataSeeder() {
    }

    public static void seedIfEmpty(Database database) {
        if (database == null || database.getUsers() == null || !database.getUsers().isEmpty()) {
            return;
        }

        Admin admin = new Admin();
        fillUser(admin, 1, "System", "Admin", "admin", "admin", "admin@uni.kz");

        Student student = new Student();
        fillUser(student, 2, "Test", "Student", "student", "student", "student@uni.kz");
        student.setDegree(Degree.BACHELOR);
        student.setYear(2);
        student.setSpeciality("Computer Science");
        student.setGpa(3.4);

        Teacher teacher = new Teacher();
        fillUser(teacher, 3, "Test", "Teacher", "teacher", "teacher", "teacher@uni.kz");
        teacher.setPosition(Teacher.TeacherPosition.LECTURER);
        teacher.setRate(4.7);

        Manager manager = new Manager();
        fillUser(manager, 4, "Test", "Manager", "manager", "manager", "manager@uni.kz");
        manager.setType(Manager.ManagerType.DEAN);

        TechSupport techSupport = new TechSupport();
        fillUser(techSupport, 5, "Tech", "Support", "support", "support", "support@uni.kz");

        Course oop = new Course(
                "OOP101",
                "Object Oriented Programming",
                CourseType.MAJOR,
                5,
                30,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
        oop.setRegistrationOpen(true);
        oop.getLectureTeachers().add(teacher);
        teacher.getCourses().add(oop);

        database.getUsers().add(admin);
        database.getUsers().add(student);
        database.getUsers().add(teacher);
        database.getUsers().add(manager);
        database.getUsers().add(techSupport);
        database.getCourses().add(oop);

        RequestService requestService = new RequestServiceImpl();
        Request request = requestService.createRequest(
                student,
                RequestType.TRANSCRIPT,
                "Need transcript for testing.",
                admin,
                Urgency.MEDIUM
        );
        if (request != null && techSupport.getRequests() != null) {
            techSupport.getRequests().add(request);
        }

        database.save();
    }

    private static void fillUser(User user, int id, String name, String lastName, String login, String password, String email) {
        user.setId(id);
        user.setName(name);
        user.setLastName(lastName);
        user.setLogin(login);
        user.setPassword(password);
        user.setAge(20 + id);
        user.setEmail(email);
        user.setPhoneNumber("+7700000000" + id);
        user.setGender("N/A");
    }
}
