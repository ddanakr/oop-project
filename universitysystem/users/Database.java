package universitySystem.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Database {

    /**
     * Default constructor
     */
    public Database() {
    }

    /**
     * 
     */
    private static Database instance;

    /**
     * 
     */
    private List<Course> courses;

    /**
     * 
     */
    private List<User> users;

    /**
     * 
     */
    private List<Enrollment> enrollments;

    /**
     * 
     */
    private List<Request> requests;

    /**
     * 
     */
    private List<News> news;

    /**
     * 
     */
    private List<ResearchPaper> researchPapers;

    /**
     * 
     */
    private List<ResearchProject> researchProjects;

    /**
     * 
     */
    private List<LogFile> logFiles;

    /**
     * 
     */
    public static void getInstance() : Database() {
        // TODO implement here
    }

}