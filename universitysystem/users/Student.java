package universitySystem.users;

import java.io.*;
import java.util.*;

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
     * 
     */
    public void viewMark(course: Course): Mark() {
        // TODO implement here
    }

    /**
     * 
     */
    public void dropCourse(course : Course) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void viewCourses() : List<Course>() {
        // TODO implement here
    }

    /**
     * 
     */
    public void viewTeachersInfo(course : Course) : List<Teacher>() {
        // TODO implement here
    }

    /**
     * 
     */
    public void viewTranscript() : Transcript() {
        // TODO implement here
    }

    /**
     * 
     */
    public void rateTeacher(teacher : Teacher) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void makeRequest(requestType: RequestType): Request() {
        // TODO implement here
    }

    /**
     * 
     */
    public void requestRegistration(course: Course): Enrollment() {
        // TODO implement here
    }

}