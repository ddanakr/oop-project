package universitysystem.models.users;

import universitysystem.models.academic.Course;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface CourseManager {

    /**
     * 
     */
    void assignCourseToTeacher(Course course, Teacher teacher);

    /**
     * 
     */
    List<Student> getStudentsInfo();

    /**
     * 
     */
    List<Teacher> getTeacherInfo();

    /**
     * 
     */
    void openCourseRegistration(Course course);

    /**
     * 
     */
    void closeCourseRegistration(Course course);

}
