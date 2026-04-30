package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface CourseManager {

    /**
     * 
     */
    public void assignCourseToTeacher(course : Course, teacher : Teacher) : void();

    /**
     * 
     */
    public void getStudentsInfo() : List<Student>();

    /**
     * 
     */
    public void getTeacherInfo() : List<Teacher>();

    /**
     * 
     */
    public void openCourseRegistration(course: Course): void();

    /**
     * 
     */
    public void closeCourseRegistration(course: Course): void();

}