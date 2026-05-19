package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.enums.Degree;
import universitysystem.models.core.DateTime;
import universitysystem.models.core.LogFile;
import universitysystem.models.users.GraduateStudent;
import universitysystem.models.users.Student;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public List<User> getAllUsers() {
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) return Collections.emptyList();
        return db.getUsers();
    }

    @Override
    public User findUserById(int userId) {
        if (userId <= 0) return null;
        for (User user : getAllUsers()) {
            if (user != null && user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean addUser(User user, User actor) {
        if (user == null) return false;
        Database db = Database.getInstance();
        if (db == null) return false;

        if (db.getUsers() == null) {
            db.setUsers(new ArrayList<>());
        }
        db.getUsers().add(user);
        log(actor, "Created user: " + safeLogin(user));
        db.save();
        return true;
    }

    @Override
    public boolean updateUser(User user, User actor) {
        if (user == null || user.getId() <= 0) return false;
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) return false;

        for (int i = 0; i < db.getUsers().size(); i++) {
            User existing = db.getUsers().get(i);
            if (existing != null && existing.getId() == user.getId()) {
                db.getUsers().set(i, normalizeUser(user));
                log(actor, "Updated user: " + safeLogin(user) + " (id=" + user.getId() + ")");
                db.save();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean deleteUser(int userId, User actor) {
        if (userId <= 0) return false;
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) return false;

        User existing = findUserById(userId);
        boolean removed = db.getUsers().removeIf(u -> u != null && u.getId() == userId);
        if (removed) {
            log(actor, "Deleted user: " + safeLogin(existing) + " (id=" + userId + ")");
            db.save();
        }
        return removed;
    }

    @Override
    public boolean resetUserPassword(int userId, User actor) {
        User user = findUserById(userId);
        if (user == null) return false;
        user.setPassword("");
        log(actor, "Reset password for user: " + safeLogin(user) + " (id=" + userId + ")");
        Database.getInstance().save();
        return true;
    }

    @Override
    public boolean makeUserResearcher(int userId, User actor) {
        User user = findUserById(userId);
        if (user == null) return false;
        boolean created = new ResearchService().makeResearcher(user);
        if (created) {
            log(actor, "Made user researcher: " + safeLogin(user) + " (id=" + userId + ")");
        }
        return created;
    }

    @Override
    public List<LogFile> getLogs() {
        Database db = Database.getInstance();
        if (db == null || db.getLogFiles() == null) return Collections.emptyList();
        return db.getLogFiles();
    }

    private void log(User actor, String action) {
        if (actor == null || action == null || action.trim().isEmpty()) return;
        Database db = Database.getInstance();
        if (db == null) return;

        if (db.getLogFiles() == null) {
            db.setLogFiles(new ArrayList<>());
        }
        int id = db.getLogFiles().size() + 1;
        db.getLogFiles().add(new LogFile(id, actor, action, DateTime.now()));
        db.save();
    }

    private String safeLogin(User user) {
        if (user == null) return "(unknown)";
        String login = user.getLogin();
        return login == null || login.trim().isEmpty() ? "(no login)" : login;
    }

    private User normalizeUser(User user) {
        if (user instanceof Student
                && !(user instanceof GraduateStudent)
                && isGraduateDegree(((Student) user).getDegree())) {
            return toGraduateStudent((Student) user);
        }
        return user;
    }

    private boolean isGraduateDegree(Degree degree) {
        return degree == Degree.MASTER || degree == Degree.PHD;
    }

    private GraduateStudent toGraduateStudent(Student student) {
        GraduateStudent graduateStudent = new GraduateStudent();
        graduateStudent.setId(student.getId());
        graduateStudent.setName(student.getName());
        graduateStudent.setLastName(student.getLastName());
        graduateStudent.setLogin(student.getLogin());
        graduateStudent.setPassword(student.getPassword());
        graduateStudent.setAge(student.getAge());
        graduateStudent.setEmail(student.getEmail());
        graduateStudent.setPhoneNumber(student.getPhoneNumber());
        graduateStudent.setGender(student.getGender());
        graduateStudent.setYear(student.getYear());
        graduateStudent.setDegree(student.getDegree());
        graduateStudent.setSpeciality(student.getSpeciality());
        graduateStudent.setGpa(student.getGpa());
        graduateStudent.setCredits(student.getCredits());
        return graduateStudent;
    }
}
