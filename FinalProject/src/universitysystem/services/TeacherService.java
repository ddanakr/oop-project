package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Teacher-facing academic service.
 * It validates that a teacher is assigned to a course before marks are written.
 */
public class TeacherService {
    private final Database database;
    private final EnrollmentService enrollmentService;

    public TeacherService() {
        this(Database.getInstance(), new EnrollmentService());
    }

    public TeacherService(Database database, EnrollmentService enrollmentService) {
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null.");
        }
        if (enrollmentService == null) {
            throw new IllegalArgumentException("EnrollmentService cannot be null.");
        }
        this.database = database;
        this.enrollmentService = enrollmentService;
    }

    /**
     * Puts the first mark for a student enrolled in a course.
     */
    public void putMark(Teacher teacher, Student student, Course course, Mark mark) {
        Enrollment enrollment = requireEnrollment(student, course);
        putMark(teacher, enrollment, mark);
    }

    /**
     * Puts the first mark for an existing enrollment.
     */
    public void putMark(Teacher teacher, Enrollment enrollment, Mark mark) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null.");
        }
        validateTeacherAuthorization(teacher, enrollment.getCourse());
        if (enrollment.getMark() != null) {
            throw new IllegalStateException("Enrollment already has a mark. Use updateMark instead.");
        }
        enrollmentService.setMark(enrollment, requireMark(mark));
    }

    /**
     * Replaces an existing mark or sets it if it was not entered yet.
     */
    public void updateMark(Teacher teacher, Student student, Course course, Mark mark) {
        Enrollment enrollment = requireEnrollment(student, course);
        updateMark(teacher, enrollment, mark);
    }

    /**
     * Replaces an existing mark or sets it if it was not entered yet.
     */
    public void updateMark(Teacher teacher, Enrollment enrollment, Mark mark) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null.");
        }
        validateTeacherAuthorization(teacher, enrollment.getCourse());
        enrollmentService.setMark(enrollment, requireMark(mark));
    }

    /**
     * Returns students enrolled in a course if the teacher is authorized for that course.
     */
    public List<Student> viewStudentsPerCourse(Teacher teacher, Course course) {
        validateTeacherAuthorization(teacher, course);
        List<Student> students = new ArrayList<Student>();
        for (Enrollment enrollment : enrollmentService.getEnrollmentsByCourse(course)) {
            if (enrollment.getStudent() != null) {
                students.add(enrollment.getStudent());
            }
        }
        students.sort(Comparator
                .comparing((Student student) -> student.getLastName() == null ? "" : student.getLastName())
                .thenComparingInt(Student::getId));
        return students;
    }

    /**
     * Returns course enrollments with marks for teacher console views.
     */
    public List<Enrollment> viewCourseJournal(Teacher teacher, Course course) {
        validateTeacherAuthorization(teacher, course);
        return new ArrayList<Enrollment>(enrollmentService.getEnrollmentsByCourse(course));
    }

    /**
     * Returns true when the teacher is assigned to the course as lecture, practice, or general course teacher.
     */
    public boolean isTeacherAuthorized(Teacher teacher, Course course) {
        if (teacher == null || course == null) {
            return false;
        }
        if (teacher.getCourses() != null && teacher.getCourses().contains(course)) {
            return true;
        }
        if (course.getLectureTeachers() != null && course.getLectureTeachers().contains(teacher)) {
            return true;
        }
        return course.getPracticeTeachers() != null && course.getPracticeTeachers().contains(teacher);
    }

    /**
     * Finds a teacher by id in Database users.
     */
    public Teacher findTeacherById(int teacherId) {
        if (database.getUsers() == null) {
            return null;
        }
        for (User user : database.getUsers()) {
            if (user instanceof Teacher && user.getId() == teacherId) {
                return (Teacher) user;
            }
        }
        return null;
    }

    /**
     * Returns all courses assigned to a teacher, sorted by course code.
     */
    public List<Course> viewAssignedCourses(Teacher teacher) {
        if (teacher == null || teacher.getCourses() == null) {
            return Collections.emptyList();
        }
        List<Course> courses = new ArrayList<Course>(teacher.getCourses());
        courses.sort(Comparator.comparing(Course::getCourseCode, Comparator.nullsLast(String::compareTo)));
        return courses;
    }

    /**
     * Throws IllegalArgumentException when the teacher cannot grade the course.
     */
    public void validateTeacherAuthorization(Teacher teacher, Course course) {
        if (!isTeacherAuthorized(teacher, course)) {
            throw new IllegalArgumentException("Teacher is not assigned to this course.");
        }
    }

    private Enrollment requireEnrollment(Student student, Course course) {
        Enrollment enrollment = enrollmentService.findEnrollment(student, course);
        if (enrollment == null) {
            throw new IllegalArgumentException("Student is not enrolled in this course.");
        }
        return enrollment;
    }

    private Mark requireMark(Mark mark) {
        if (mark == null) {
            throw new IllegalArgumentException("Mark cannot be null.");
        }
        return mark;
    }
}
