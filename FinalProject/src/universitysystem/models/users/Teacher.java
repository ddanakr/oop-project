package universitysystem.models.users;

import src.universitysystem.models.research.Researcher;

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
    public void viewStudentInfo(studentId : int) : Student() {
        // TODO implement here
    }

    /**
     * 
     */
    public void putMark(enrollment: Enrollment, mark: Mark): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void viewStudents(course : Course) : List<Student>() {
        // TODO implement here
    }

    /**
     * 
     */
    public void updateMark(enrollment: Enrollment, mark: Mark): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void publishPaper(paper : ResearchPaper) : void() {
        // TODO implement Researcher.publishPaper(paper : ResearchPaper) : void() here
    }

    /**
     * 
     */
    public void getHIndex() : int() {
        // TODO implement Researcher.getHIndex() : int() here
    }

    /**
     * 
     */
    public void printPapers(comp : Comparator<ResearchPaper>) : void() {
        // TODO implement Researcher.printPapers(comp : Comparator<ResearchPaper>) : void() here
    }

    /**
     * 
     */
    public void joinProject(project : ResearchProject) : void() {
        // TODO implement Researcher.joinProject(project : ResearchProject) : void() here
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

}