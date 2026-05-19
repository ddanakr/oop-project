package universitysystem.models.academic;

import universitysystem.enums.Semester;
import universitysystem.models.users.Student;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Enrollment implements Serializable {

    /**
     * 
     */
    private Student student;

    /**
     * 
     */
    private Course course;

    /**
     * 
     */
    private Semester semester;

    /**
     * 
     */
    private int year;

    /**
     * 
     */
    private Mark mark;

    /**
     * 
     */
    private int attemptNumber;
    
    /**
     * Default constructor
     */
    public Enrollment() {
    }

    public Enrollment(Student student, Course course, Semester semester, int year, Mark mark, int attemptNumber) {
        this.student = student;
        this.course = course;
        this.semester = semester;
        this.year = year;
        this.mark = mark;
        this.attemptNumber = attemptNumber;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public int getAttemptNumber() {
        return attemptNumber;
    }

    public void setAttemptNumber(int attemptNumber) {
        this.attemptNumber = attemptNumber;
    }






    /**
     * 
     */
    public boolean isRetakeAllowed() {
        return attemptNumber < 3;
    }

    @Override
    public String toString() {
        return "Enrollment{" +
                "student=" + student +
                ", course=" + course +
                ", semester=" + semester +
                ", year=" + year +
                ", mark=" + mark +
                ", attemptNumber=" + attemptNumber +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Enrollment)) return false;
        Enrollment that = (Enrollment) o;
        return year == that.year && attemptNumber == that.attemptNumber &&
                Objects.equals(student, that.student) &&
                Objects.equals(course, that.course) &&
                semester == that.semester &&
                Objects.equals(mark, that.mark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course, semester, year, mark, attemptNumber);
    }

}
