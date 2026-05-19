package universitysystem.models.users;

import universitysystem.models.academic.Course;

import java.util.List;

public interface CourseManager {
    void assignCourseToTeacher(Course course, Teacher teacher);

    List<Student> getStudentsInfo();

    List<Teacher> getTeacherInfo();

    void openCourseRegistration(Course course);

    void closeCourseRegistration(Course course);
}
