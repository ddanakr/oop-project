package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.core.DateTime;
import universitysystem.models.academic.Course;
import universitysystem.models.news.News;
import universitysystem.models.requests.Request;
import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Signature;
import universitysystem.models.users.Manager;
import universitysystem.models.users.RequestManager;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;
import universitysystem.utils.UserComparators;

import java.util.ArrayList;
import java.util.List;

public class ManagerService {
    private final Database database;
    private final NewsService newsService;
    private final ResearchService researchService;

    public ManagerService() {
        this.database = Database.getInstance();
        this.newsService = new NewsService();
        this.researchService = new ResearchService();
    }

    public News createNews(String title, String topic, String body, Manager manager) {
        return newsService.createNews(title, topic, body, manager);
    }

    public boolean updateNews(int newsId, String title, String topic, String body) {
        return newsService.updateNews(newsId, title, topic, body);
    }

    public boolean deleteNews(int newsId) {
        return newsService.deleteNews(newsId);
    }

    public boolean pinNews(int newsId) {
        return newsService.pinNews(newsId);
    }

    public boolean unpinNews(int newsId) {
        return newsService.unpinNews(newsId);
    }

    public boolean generateTopCitedResearcherNews() {
        return researchService.generateTopCitedResearcherNews();
    }

    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            return;
        }
        if (teacher.getCourses() == null) {
            teacher.setCourses(new ArrayList<>());
        }
        if (!teacher.getCourses().contains(course)) {
            teacher.getCourses().add(course);
            database.save();
        }
    }

    public void openCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(true);
            database.save();
        }
    }

    public void closeCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(false);
            database.save();
        }
    }

    public List<Student> getStudentsSortedByGpa() {
        List<Student> students = getStudents();
        students.sort(UserComparators.STUDENT_BY_GPA);
        return students;
    }

    public List<Student> getStudentsSortedAlphabetically() {
        List<Student> students = getStudents();
        students.sort(UserComparators.STUDENT_ALPHABETICALLY);
        return students;
    }

    public List<Teacher> getTeachersSortedAlphabetically() {
        List<Teacher> teachers = getTeachers();
        teachers.sort(UserComparators.TEACHER_ALPHABETICALLY);
        return teachers;
    }

    public double getAverageGpa() {
        List<Student> students = getStudents();
        if (students.isEmpty()) {
            return 0;
        }

        double total = 0;
        for (Student student : students) {
            total += student.getGpa();
        }
        return total / students.size();
    }

    public List<Request> getRequests() {
        return new ArrayList<>(database.getRequests());
    }

    public boolean signRequest(int requestId, Manager manager) {
        validateRequestManager(manager);

        Request request = getRequestById(requestId);
        if (request == null) {
            return false;
        }

        Signature.SignerRole signerRole = getSignerRole(manager);
        request.addSignature(new Signature(manager, signerRole, DateTime.now()));
        database.save();
        return true;
    }

    public boolean approveRequest(int requestId, Manager manager) {
        validateRequestManager(manager);

        Request request = getRequestById(requestId);
        if (request == null) {
            return false;
        }
        if (!request.checkApprovalRequirement()) {
            throw new IllegalStateException("Request must be signed by dean and rector before approval.");
        }

        request.setStatus(RequestStatus.ACCEPTED);
        database.save();
        return true;
    }

    public boolean rejectRequest(int requestId, Manager manager) {
        validateRequestManager(manager);

        Request request = getRequestById(requestId);
        if (request == null) {
            return false;
        }
        request.setStatus(RequestStatus.REJECTED);
        database.save();
        return true;
    }

    private Request getRequestById(int requestId) {
        for (Request request : database.getRequests()) {
            if (request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

    private void validateRequestManager(Manager manager) {
        if (!(manager instanceof RequestManager)) {
            throw new SecurityException("Current manager does not have permission to manage requests.");
        }
    }

    private Signature.SignerRole getSignerRole(Manager manager) {
        if (manager == null || manager.getType() == null) {
            throw new SecurityException("Manager type is required to sign requests.");
        }
        if (manager.getType() == Manager.ManagerType.DEAN) {
            return Signature.SignerRole.DEAN;
        }
        if (manager.getType() == Manager.ManagerType.RECTOR) {
            return Signature.SignerRole.RECTOR;
        }
        throw new SecurityException("Only dean or rector can sign requests.");
    }

    private List<Student> getStudents() {
        List<Student> students = new ArrayList<>();
        for (User user : database.getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }

    private List<Teacher> getTeachers() {
        List<Teacher> teachers = new ArrayList<>();
        for (User user : database.getUsers()) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }
        return teachers;
    }
}
