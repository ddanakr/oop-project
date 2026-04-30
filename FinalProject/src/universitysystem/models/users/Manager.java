package universitysystem.models.users;

import universitysystem.models.academic.Course;
import universitysystem.models.news.News;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Manager extends Employee implements ReportManager, CourseManager, RequestManager {

    /**
     * Default constructor
     */
    public Manager() {
    }

    /**
     * 
     */
    private ManagerType type;

    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    /**
     * 
     */
    public void createPerformanceReport() {
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) {
            System.out.println("No data to generate report.");
            return;
        }
        double totalGpa = 0;
        int count = 0;
        for (User user : db.getUsers()) {
            if (user instanceof Student) {
                Student student = (Student) user;
                totalGpa += student.getGpa();
                count++;
            }
        }
        double average = count == 0 ? 0 : totalGpa / count;
        System.out.println("Average GPA of all students: " + average);
    }

    /**
     * 
     */
    public void manageNews(News news) {
        Database db = Database.getInstance();
        if (db != null) {
            if (db.getNews() == null) {
                db.setNews(new ArrayList<>());
            }
            db.getNews().add(news);
        }
    }

    /**
     * 
     */
    public List<News> generateTopResearcherNews() {
        Database db = Database.getInstance();
        if (db == null || db.getResearchPapers() == null) {
            return Collections.emptyList();
        }
        Map<String, Integer> citationTotals = new HashMap<>();
        for (ResearchPaper paper : db.getResearchPapers()) {
            for (Researcher author : paper.getAuthors()) {
                citationTotals.merge(author.toString(), paper.getCitations(), Integer::sum);
            }
        }
        String topResearcher = null;
        int maxCitations = 0;
        for (Map.Entry<String, Integer> entry : citationTotals.entrySet()) {
            if (entry.getValue() > maxCitations) {
                maxCitations = entry.getValue();
                topResearcher = entry.getKey();
            }
        }
        if (topResearcher == null) {
            return Collections.emptyList();
        }
        News news = new News();
        news.setTitle("Top Researcher: " + topResearcher);
        news.setBody("Top researcher has " + maxCitations + " citations.");
        if (db.getNews() == null) {
            db.setNews(new ArrayList<>());
        }
        db.getNews().add(news);
        return Collections.singletonList(news);
    }

    /**
     * 
     */
    public void assignCourseToTeacher(Course course, Teacher teacher) {
        if (course == null || teacher == null) {
            return;
        }
        if (teacher.getCourses() == null) {
            teacher.setCourses(new ArrayList<>());
        }
        teacher.getCourses().add(course);
    }

    /**
     * 
     */
    public List<Student> getStudentsInfo() {
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) {
            return Collections.emptyList();
        }
        List<Student> students = new ArrayList<>();
        for (User user : db.getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }

    /**
     * 
     */
    public List<Teacher> getTeacherInfo() {
        Database db = Database.getInstance();
        if (db == null || db.getUsers() == null) {
            return Collections.emptyList();
        }
        List<Teacher> teachers = new ArrayList<>();
        for (User user : db.getUsers()) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }
        return teachers;
    }

    /**
     * 
     */
    public void openCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(true);
        }
    }

    /**
     * 
     */
    public void closeCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(false);
        }
    }

    /**
     * 
     */
    public void approveRequest(Request request) {
        if (request != null) {
            request.setStatus(RequestStatus.ACCEPTED);
        }
    }

    /**
     * 
     */
    public List<Request> viewRequests() {
        Database db = Database.getInstance();
        return db != null && db.getRequests() != null ? db.getRequests() : Collections.emptyList();
    }

    /**
     * 
     */
    public void rejectRequest(Request request) {
        if (request != null) {
            request.setStatus(RequestStatus.REJECTED);
        }
    }

    /**
     * 
     */
    public enum ManagerType {
        OR,
        DEPARTMENT,
        DEAN
    }

    @Override
    public String toString() {
        return "Manager{" +
                "type=" + type +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Manager)) return false;
        Manager manager = (Manager) o;
        return Objects.equals(getLogin(), manager.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}