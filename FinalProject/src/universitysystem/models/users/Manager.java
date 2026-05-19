package universitysystem.models.users;

import universitysystem.database.Database;
import universitysystem.enums.RequestStatus;
import universitysystem.models.academic.Course;
import universitysystem.models.news.News;
import universitysystem.models.requests.Request;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.Researcher;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Manager extends Employee implements ReportManager, CourseManager, RequestManager {
    private ManagerType type;

    public Manager() {
    }

    public ManagerType getType() {
        return type;
    }

    public void setType(ManagerType type) {
        this.type = type;
    }

    public void createPerformanceReport() {
        List<Student> students = getStudentsInfo();
        double totalGpa = 0;
        for (Student student : students) {
            totalGpa += student.getGpa();
        }

        double average = students.isEmpty() ? 0 : totalGpa / students.size();
        System.out.println("Average GPA of all students: " + average);
    }

    public void manageNews(News news) {
        if (news != null) {
            Database.getInstance().getNews().add(news);
        }
    }

    public List<News> generateTopResearcherNews() {
        Database db = Database.getInstance();
        Map<String, Integer> citationTotals = new HashMap<>();

        for (ResearchPaper paper : db.getResearchPapers()) {
            if (paper.getAuthors() == null) {
                continue;
            }
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
        news.setTopic("Research");
        news.setBody("Top researcher has " + maxCitations + " citations.");
        news.setPinned(true);
        db.getNews().add(news);
        return Collections.singletonList(news);
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
        }
    }

    public List<Student> getStudentsInfo() {
        List<Student> students = new ArrayList<>();
        for (User user : Database.getInstance().getUsers()) {
            if (user instanceof Student) {
                students.add((Student) user);
            }
        }
        return students;
    }

    public List<Teacher> getTeacherInfo() {
        List<Teacher> teachers = new ArrayList<>();
        for (User user : Database.getInstance().getUsers()) {
            if (user instanceof Teacher) {
                teachers.add((Teacher) user);
            }
        }
        return teachers;
    }

    public void openCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(true);
        }
    }

    public void closeCourseRegistration(Course course) {
        if (course != null) {
            course.setRegistrationOpen(false);
        }
    }

    public void approveRequest(Request request) {
        if (request != null) {
            request.setStatus(RequestStatus.ACCEPTED);
        }
    }

    public List<Request> viewRequests() {
        return Database.getInstance().getRequests();
    }

    public void rejectRequest(Request request) {
        if (request != null) {
            request.setStatus(RequestStatus.REJECTED);
        }
    }

    public enum ManagerType {
        OR,
        DEPARTMENT,
        DEAN,
        RECTOR
    }

    @Override
    public String toString() {
        return "Manager{" +
                "type=" + type +
                ", login='" + getLogin() + '\'' +
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
