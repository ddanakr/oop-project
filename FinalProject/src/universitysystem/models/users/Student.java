package universitysystem.models.users;

import java.io.*;
import java.util.*;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestType;
import universitysystem.models.requests.Urgency;
import universitysystem.models.users.Teacher;

/**
 * 
 */
public class Student extends User {

    /**
     * Default constructor
     */
    public Student() {
    }

    /**
     * 
     */
    private int year;

    /**
     * 
     */
    private Degree degree;

    /**
     * 
     */
    private String speciality;

    /**
     * 
     */
    private double gpa;

    /**
     * 
     */
    private int credits;

    /**
     * Full constructor
     */
    public Student(int year, Degree degree, String speciality, double gpa, int credits) {
        this.year = year;
        this.degree = degree;
        this.speciality = speciality;
        this.gpa = gpa;
        this.credits = credits;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public double getGpa() {
        return gpa;
    }

    public void setGpa(double gpa) {
        this.gpa = gpa;
    }

    public int getCredits() {
        return credits;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    /**
     * 
     */
    public Mark viewMark(Course course) {
        for (Enrollment enrollment : course.getEnrollments()) {
            if (enrollment.getStudent() != null && enrollment.getStudent().equals(this)) {
                return enrollment.getMark();
            }
        }
        return null;
    }

    /**
     * 
     */
    public void dropCourse(Course course) {
        if (course != null && course.getEnrollments() != null) {
            course.getEnrollments().removeIf(enrollment -> enrollment.getStudent() != null && enrollment.getStudent().equals(this));
        }
    }

    /**
     * 
     */
    public List<Course> viewCourses() {
        List<Course> courses = new ArrayList<>();
        Database db = Database.getInstance();
        if (db != null && db.getEnrollments() != null) {
            for (Enrollment enrollment : db.getEnrollments()) {
                if (enrollment.getStudent() != null && enrollment.getStudent().equals(this)) {
                    courses.add(enrollment.getCourse());
                }
            }
        }
        return courses;
    }

    /**
     * 
     */
    public List<Teacher> viewTeachersInfo(Course course) {
        if (course == null) {
            return Collections.emptyList();
        }
        List<Teacher> teachers = new ArrayList<>();
        if (course.getLectureTeachers() != null) {
            teachers.addAll(course.getLectureTeachers());
        }
        if (course.getPracticeTeachers() != null) {
            teachers.addAll(course.getPracticeTeachers());
        }
        return teachers;
    }

    /**
     * 
     */
    public void rateTeacher(Teacher teacher) {
        if (teacher == null) {
            return;
        }
        double currentRate = teacher.getRate();
        teacher.setRate(Math.min(5.0, currentRate + 0.1));
    }

    /**
     * 
     */
    public Request makeRequest(RequestType requestType) {
        if (requestType == null) {
            return null;
        }
        Database db = Database.getInstance();
        if (db == null) {
            return null;
        }
        int requestId = db.getRequests() != null ? db.getRequests().size() + 1 : 1;
        Request request = new Request(requestId, this, requestType, "Request from student", null, Urgency.MEDIUM, new ArrayList<>(), new universitysystem.models.DateTime());
        if (db.getRequests() == null) {
            db.setRequests(new ArrayList<>());
        }
        db.getRequests().add(request);
        return request;
    }

    /**
     * 
     */
    public Enrollment requestRegistration(Course course) throws universitysystem.exceptions.CourseFullException, universitysystem.exceptions.MaxCreditsException, universitysystem.exceptions.MaxRetakesException {
        if (course == null) {
            return null;
        }
        if (!course.isRegistrationOpen()) {
            throw new IllegalStateException("Registration for course " + course.getCourseCode() + " is closed.");
        }
        if (course.isFull()) {
            throw new universitysystem.exceptions.CourseFullException("Course " + course.getCourseCode() + " is full.");
        }
        if (this.credits + course.getCredits() > 21) {
            throw new universitysystem.exceptions.MaxCreditsException("Student " + getLogin() + " cannot exceed 21 credits.");
        }
        int retakeCount = 0;
        if (course.getEnrollments() != null) {
            for (Enrollment enrollment : course.getEnrollments()) {
                if (enrollment.getStudent() != null && enrollment.getStudent().equals(this) && enrollment.getAttemptNumber() >= 3) {
                    retakeCount++;
                }
            }
        }
        if (retakeCount >= 3) {
            throw new universitysystem.exceptions.MaxRetakesException("Student " + getLogin() + " has reached the maximum retake limit.");
        }
        Enrollment enrollment = new Enrollment(this, course, null, 0, null, retakeCount + 1);
        course.addEnrollment(enrollment);
        return enrollment;
    }

    @Override
    public String toString() {
        return "Student{" +
                "year=" + year +
                ", degree=" + degree +
                ", speciality='" + speciality + '\'' +
                ", gpa=" + gpa +
                ", credits=" + credits +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return id == student.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}