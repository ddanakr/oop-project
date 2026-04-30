package universitysystem.models.academic;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Course implements Serializable{

    /**
     * Default constructor
     */
    public Course() {
        this.enrollments = new ArrayList<>();
        this.lectureTeachers = new ArrayList<>();
        this.practiceTeachers = new ArrayList<>();
    }

    /**
     * Full constructor
     */
    public Course(String courseCode, String title, CourseType type, int credits, int capacity,
                  List<Enrollment> enrollments, List<Teacher> lectureTeachers, List<Teacher> practiceTeachers) {
        this.courseCode = courseCode;
        this.title = title;
        this.type = type;
        this.credits = credits;
        this.capacity = capacity;
        this.isRegistrationOpen = false;
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
        this.lectureTeachers = lectureTeachers != null ? lectureTeachers : new ArrayList<>();
        this.practiceTeachers = practiceTeachers != null ? practiceTeachers : new ArrayList<>();
    }

    /**
     * 
     */
    private String courseCode;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private CourseType type;

    /**
     * 
     */
    private int credits;

    /**
     * 
     */
    private int capacity;

    /**
     * 
     */
    private boolean isRegistrationOpen = false;

    /**
     * 
     */
    private List<Enrollment> enrollments;

    /**
     * 
     */
    private List<Teacher> lectureTeachers;

    /**
     * 
     */
    private List<Teacher> practiceTeachers;

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public CourseType getType() {
        return type;
    }

    public void setType(CourseType type) {
        this.type = type;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isRegistrationOpen() {
        return isRegistrationOpen;
    }

    public void setRegistrationOpen(boolean registrationOpen) {
        isRegistrationOpen = registrationOpen;
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
    }

    public List<Teacher> getLectureTeachers() {
        return lectureTeachers;
    }

    public void setLectureTeachers(List<Teacher> lectureTeachers) {
        this.lectureTeachers = lectureTeachers != null ? lectureTeachers : new ArrayList<>();
    }

    public List<Teacher> getPracticeTeachers() {
        return practiceTeachers;
    }

    public void setPracticeTeachers(List<Teacher> practiceTeachers) {
        this.practiceTeachers = practiceTeachers != null ? practiceTeachers : new ArrayList<>();
    }

    /**
     * 
     */
    public boolean isFull() {
        return enrollments != null && enrollments.size() >= capacity;
    }

    /**
     * 
     */
    public void addEnrollment(Enrollment enrollment) {
        if (this.enrollments == null) {
            this.enrollments = new ArrayList<>();
        }
        this.enrollments.add(enrollment);
    }

    @Override
    public String toString() {
        return "Course{" +
                "courseCode='" + courseCode + '\'' +
                ", title='" + title + '\'' +
                ", credits=" + credits +
                ", capacity=" + capacity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return Objects.equals(courseCode, course.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseCode);
    }

}