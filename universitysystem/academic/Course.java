package universitySystem.academic;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Course {

    /**
     * Default constructor
     */
    public Course() {
    }

    /**
     * 
     */
    public void courseCode;

    /**
     * 
     */
    public String title;

    /**
     * 
     */
    public void type;

    /**
     * 
     */
    public void credits;

    /**
     * 
     */
    public void capacity;

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
    private List<Enrollment> enrollments;

    /**
     * 
     */
    private List<Teacher> lectureTeachers;

    /**
     * 
     */
    private List<Teacher> practiceTeachers;



    /**
     * 
     */
    public void isFull() : boolean() {
        // TODO implement here
    }

    /**
     * 
     */
    public void addEnrollment(enrollment: Enrollment): void() {
        // TODO implement here
    }

}