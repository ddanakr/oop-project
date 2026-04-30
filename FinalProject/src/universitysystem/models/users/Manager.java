package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Manager extends Employee implements ReportManager, CourseManager, RequestManager {

    /**
     * Default constructor
     */
    public Manager() {
    }

    /**
     * 
     */
    private ManagerType type;

    /**
     * 
     */
    public void createPerformanceReport() : void() {
        // TODO implement ReportManager.createPerformanceReport() : void() here
    }

    /**
     * 
     */
    public void manageNews(news : News) : void() {
        // TODO implement ReportManager.manageNews(news : News) : void() here
    }

    /**
     * 
     */
    public void generateTopResearcherNews() : List<News>() {
        // TODO implement ReportManager.generateTopResearcherNews() : List<News>() here
    }

    /**
     * 
     */
    public void assignCourseToTeacher(course : Course, teacher : Teacher) : void() {
        // TODO implement CourseManager.assignCourseToTeacher(course : Course, teacher : Teacher) : void() here
    }

    /**
     * 
     */
    public void getStudentsInfo() : List<Student>() {
        // TODO implement CourseManager.getStudentsInfo() : List<Student>() here
    }

    /**
     * 
     */
    public void getTeacherInfo() : List<Teacher>() {
        // TODO implement CourseManager.getTeacherInfo() : List<Teacher>() here
    }

    /**
     * 
     */
    public void openCourseRegistration(course: Course): void() {
        // TODO implement CourseManager.openCourseRegistration(course: Course): void() here
    }

    /**
     * 
     */
    public void closeCourseRegistration(course: Course): void() {
        // TODO implement CourseManager.closeCourseRegistration(course: Course): void() here
    }

    /**
     * 
     */
    public void approveRequest(request: Request): void() {
        // TODO implement RequestManager.approveRequest(request: Request): void() here
    }

    /**
     * 
     */
    public void viewRequests() : List<Request>() {
        // TODO implement RequestManager.viewRequests() : List<Request>() here
    }

    /**
     * 
     */
    public void rejectRequest(request: Request): void() {
        // TODO implement RequestManager.rejectRequest(request: Request): void() here
    }

    /**
     * 
     */
    public enum ManagerType {
        OR,
        DEPARTMENT,
        DEAN
    }

}