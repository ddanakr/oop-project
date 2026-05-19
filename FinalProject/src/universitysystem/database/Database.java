package universitysystem.database;

import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.news.Journal;
import universitysystem.models.news.News;
import universitysystem.models.core.Message;
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
public class Database implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String DEFAULT_DATA_FILE = resolveDefaultDataFile();

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
        this.journals = new ArrayList<>();
        this.logFiles = new ArrayList<>();
        this.messages = new ArrayList<>();
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
    private List<Journal> journals;

    /**
     * 
     */
    private List<LogFile> logFiles;

    /**
     *
     */
    private List<Message> messages;

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    private static String resolveDefaultDataFile() {
        File nestedProject = new File("FinalProject");
        if (nestedProject.isDirectory() && new File(nestedProject, "src").isDirectory()) {
            return "FinalProject/database.ser";
        }
        return "database.ser";
    }

    public static Database load() {
        return load(DEFAULT_DATA_FILE);
    }

    public static Database loadData() {
        return load();
    }

    public static Database deserialize() {
        return load();
    }

    public static Database load(String filePath) {
        File file = new File(filePath == null || filePath.trim().isEmpty() ? DEFAULT_DATA_FILE : filePath);
        if (!file.exists()) {
            instance = new Database();
            return instance;
        }

        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(file))) {
            Object loaded = input.readObject();
            if (!(loaded instanceof Database)) {
                throw new IllegalStateException("Saved data is not a Database.");
            }
            instance = (Database) loaded;
            instance.ensureLists();
            return instance;
        } catch (IOException | ClassNotFoundException e) {
            throw new IllegalStateException("Could not load database from " + file.getPath(), e);
        }
    }

    public void save() {
        save(DEFAULT_DATA_FILE);
    }

    public void saveData() {
        save();
    }

    public void serialize() {
        save();
    }

    public void save(String filePath) {
        ensureLists();
        File file = new File(filePath == null || filePath.trim().isEmpty() ? DEFAULT_DATA_FILE : filePath);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new IllegalStateException("Could not create directory " + parent.getPath());
        }

        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file))) {
            output.writeObject(this);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save database to " + file.getPath(), e);
        }
    }

    private void ensureLists() {
        setCourses(courses);
        setUsers(users);
        setEnrollments(enrollments);
        setRequests(requests);
        setNews(news);
        setResearchPapers(researchPapers);
        setResearchProjects(researchProjects);
        setJournals(journals);
        setLogFiles(logFiles);
        setMessages(messages);
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

    public List<Journal> getJournals() {
        return journals;
    }

    public void setJournals(List<Journal> journals) {
        this.journals = journals != null ? journals : new ArrayList<>();
    }

    public List<LogFile> getLogFiles() {
        return logFiles;
    }

    public void setLogFiles(List<LogFile> logFiles) {
        this.logFiles = logFiles != null ? logFiles : new ArrayList<>();
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages != null ? messages : new ArrayList<>();
    }

}
