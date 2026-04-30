package universitysystem.models.users;

import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.research.Researcher;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Teacher extends Employee implements Researcher {

    /**
     * Default constructor
     */
    public Teacher() {
        this.courses = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * Full constructor
     */
    public Teacher(TeacherPosition position, List<Course> courses, double rate) {
        this.position = position;
        this.courses = courses != null ? courses : new ArrayList<>();
        this.rate = rate;
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    /**
     * 
     */
    private TeacherPosition position;

    /**
     * 
     */
    private List<Course> courses;

    /**
     * 
     */
    private double rate;

    /**
     * 
     */
    private List<ResearchPaper> papers;

    /**
     * 
     */
    private List<ResearchProject> projects;

    public TeacherPosition getPosition() {
        return position;
    }

    public void setPosition(TeacherPosition position) {
        this.position = position;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers != null ? papers : new ArrayList<>();
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects != null ? projects : new ArrayList<>();
    }

    /**
     * 
     */
    public Student viewStudentInfo(int studentId) {
        Database db = Database.getInstance();
        if (db != null && db.getUsers() != null) {
            for (User user : db.getUsers()) {
                if (user instanceof Student && user.getId() == studentId) {
                    return (Student) user;
                }
            }
        }
        return null;
    }

    /**
     * 
     */
    public void putMark(Enrollment enrollment, Mark mark) {
        if (enrollment == null || mark == null) {
            return;
        }
        if (courses == null || !courses.contains(enrollment.getCourse())) {
            throw new IllegalArgumentException("Teacher is not assigned to this course.");
        }
        enrollment.setMark(mark);
    }

    /**
     * 
     */
    public List<Student> viewStudents(Course course) {
        List<Student> students = new ArrayList<>();
        if (course == null || course.getEnrollments() == null) {
            return students;
        }
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() != null) {
                students.add(enrollment.getStudent());
            }
        }
        return students;
    }

    /**
     * 
     */
    public void updateMark(Enrollment enrollment, Mark mark) {
        putMark(enrollment, mark);
    }

    /**
     * 
     */
    public void publishPaper(ResearchPaper paper) {
        if (this.papers == null) {
            this.papers = new ArrayList<>();
        }
        if (paper != null) {
            this.papers.add(paper);
        }
    }

    /**
     * 
     */
    public int getHIndex() {
        if (this.papers == null) {
            return 0;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        sorted.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int h = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    /**
     * 
     */
    public void printPapers(Comparator<ResearchPaper> comp) {
        if (this.papers == null) {
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        if (comp != null) {
            sorted.sort(comp);
        }
        for (ResearchPaper paper : sorted) {
            System.out.println(paper);
        }
    }

    /**
     * 
     */
    public void joinProject(ResearchProject project) {
        if (project != null) {
            project.addParticipant(this);
        }
    }

    /**
     * 
     */
    public enum TeacherPosition {
        TUTOR,
        LECTURER,
        SENIORLECTURER,
        LECTOR,
        PROFESSOR,
        ASSISTANT
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "position=" + position +
                ", rate=" + rate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher teacher = (Teacher) o;
        return Objects.equals(getLogin(), teacher.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}