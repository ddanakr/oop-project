package universitysystem.models.academic;

import src.universitysystem.models.users.Student;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Enrollment {

    /**
     * Default constructor
     */
    public Enrollment() {
    }

    /**
     * 
     */
    private Student student;

    /**
     * 
     */
    private  Course course ;

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
    public void isRetakeAllowed() : boolean() {
        // TODO implement here
    }

    /**
     * 
     */
    public void setMark(mark: Mark): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getMark(): Mark() {
        // TODO implement here
    }

}