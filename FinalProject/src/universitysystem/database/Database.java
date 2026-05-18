package universitysystem.database;

import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.news.News;
import universitysystem.models.requests.Request;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.core.LogFile;
import universitysystem.models.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Database {

    /**
     * Default constructor
     */
    private Database() {
        this.courses = new ArrayList<>();
        this.users = new ArrayList<>();
        this.enrollments = new ArrayList<>();
        this.requests = new ArrayList<>();
        this.news = new ArrayList<>();
        this.researchPapers = new ArrayList<>();
        this.researchProjects = new ArrayList<>();
        this.logFiles = new ArrayList<>();
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

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses != null ? courses : new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users != null ? users : new ArrayList<>();
    }

    public List<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(List<Enrollment> enrollments) {
        this.enrollments = enrollments != null ? enrollments : new ArrayList<>();
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests != null ? requests : new ArrayList<>();
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news != null ? news : new ArrayList<>();
    }

    public List<ResearchPaper> getResearchPapers() {
        return researchPapers;
    }

    public void setResearchPapers(List<ResearchPaper> researchPapers) {
        this.researchPapers = researchPapers != null ? researchPapers : new ArrayList<>();
    }

    public List<ResearchProject> getResearchProjects() {
        return researchProjects;
    }

    public void setResearchProjects(List<ResearchProject> researchProjects) {
        this.researchProjects = researchProjects != null ? researchProjects : new ArrayList<>();
    }

    public List<LogFile> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(List<LogFile> logFiles) {
        this.logFiles = logFiles != null ? logFiles : new ArrayList<>();
    }

}
