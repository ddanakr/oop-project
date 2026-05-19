package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.exceptions.CourseFullException;
import universitysystem.exceptions.MaxCreditsException;
import universitysystem.exceptions.MaxRetakesException;
import universitysystem.enums.Semester;
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
 * Student-facing academic service.
 * Methods return objects or printable strings, so console views/controllers can decide how to display them.
 */
public class StudentService {
    private final Database database;
    private final EnrollmentService enrollmentService;

    public StudentService() {
        this(Database.getInstance(), new EnrollmentService());
    }

    public StudentService(Database database, EnrollmentService enrollmentService) {
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
     * Registers a managed Student for a managed Course and updates Database enrollments.
     */
    public Enrollment registerForCourse(Student student, Course course, Semester semester, int year)
            throws CourseFullException, MaxCreditsException, MaxRetakesException {
        return enrollmentService.createEnrollment(student, course, semester, year);
    }

    /**
     * Checks whether a student is enrolled in a course.
     */
    public boolean isEnrolled(Student student, Course course) {
        return enrollmentService.isEnrolled(student, course);
    }

    /**
     * Returns the student's mark for one course, or null if not found or not graded.
     */
    public Mark viewMark(Student student, Course course) {
        Enrollment enrollment = enrollmentService.findEnrollment(student, course);
        return enrollment == null ? null : enrollment.getMark();
    }

    /**
     * Returns all graded enrollments for a student sorted from highest to lowest final score.
     */
    public List<Enrollment> viewMarks(Student student) {
        if (student == null) {
            return Collections.emptyList();
        }
        List<Enrollment> graded = new ArrayList<Enrollment>();
        for (Enrollment enrollment : enrollmentService.getEnrollmentsByStudent(student)) {
            if (enrollment.getMark() != null) {
                graded.add(enrollment);
            }
        }
        graded.sort(Comparator.comparing(
                (Enrollment enrollment) -> enrollment.getMark(),
                Comparator.nullsLast(Comparator.reverseOrder())
        ));
        return graded;
    }

    /**
     * Builds a console-friendly transcript text without printing directly.
     */
    public String printTranscript(Student student) {
        if (student == null) {
            return "No student selected.";
        }
        StringBuilder transcript = new StringBuilder();
        transcript.append("Transcript for ")
                .append(student.getName()).append(" ")
                .append(student.getLastName()).append(" (ID: ")
                .append(student.getId()).append(")")
                .append(System.lineSeparator());

        List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(student);
        if (enrollments.isEmpty()) {
            transcript.append("No enrollments found.");
            return transcript.toString();
        }

        double totalGradePoints = 0.0;
        int totalCredits = 0;
        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            Mark mark = enrollment.getMark();
            double finalScore = mark == null ? 0.0 : mark.calculateFinal();
            int credits = course == null ? 0 : course.getCredits();

            transcript.append(course == null ? "Unknown course" : course.getCourseCode())
                    .append(" | ")
                    .append(course == null ? "" : course.getTitle())
                    .append(" | credits: ").append(credits)
                    .append(" | final: ").append(mark == null ? "N/A" : String.format("%.2f", finalScore))
                    .append(" | attempt: ").append(enrollment.getAttemptNumber())
                    .append(System.lineSeparator());

            if (mark != null && credits > 0) {
                totalGradePoints += toGpaPoint(finalScore) * credits;
                totalCredits += credits;
            }
        }

        transcript.append("GPA: ");
        transcript.append(totalCredits == 0 ? "N/A" : String.format("%.2f", totalGradePoints / totalCredits));
        return transcript.toString();
    }

    /**
     * Returns courses opened by managers for registration.
     * Capacity, credit limit, and retake rules are still enforced when registration is attempted.
     */
    public List<Course> getAvailableCourses(Student student) {
        if (student == null || database.getCourses() == null) {
            return Collections.emptyList();
        }
        List<Course> courses = new ArrayList<Course>();
        for (Course course : database.getCourses()) {
            if (course != null
                    && course.isRegistrationOpen()
                    && !enrollmentService.hasOpenEnrollment(student, course)
                    && !enrollmentService.hasPassedCourse(student, course)) {
                courses.add(course);
            }
        }
        courses.sort(Comparator.comparing(Course::getCourseCode, Comparator.nullsLast(String::compareTo)));
        return courses;
    }

    /**
     * Finds a student by id in Database users.
     */
    public Student findStudentById(int studentId) {
        if (database.getUsers() == null) {
            return null;
        }
        for (User user : database.getUsers()) {
            if (user instanceof Student && user.getId() == studentId) {
                return (Student) user;
            }
        }
        return null;
    }

    /**
     * Returns true when adding a course would keep the student at or under 21 credits.
     */
    public boolean canAddCourse(Student student, Course course) {
        return student != null
                && course != null
                && enrollmentService.calculateCurrentCredits(student) + course.getCredits() <= EnrollmentService.MAX_CREDITS;
    }

    public List<Course> getRegisteredCourses(Student student) {
        if (student == null) {
            return Collections.emptyList();
        }
        List<Course> courses = new ArrayList<Course>();
        for (Enrollment enrollment : enrollmentService.getEnrollmentsByStudent(student)) {
            if (enrollment.getCourse() != null && !courses.contains(enrollment.getCourse())) {
                courses.add(enrollment.getCourse());
            }
        }
        courses.sort(Comparator.comparing(Course::getCourseCode, Comparator.nullsLast(String::compareTo)));
        return courses;
    }

    public List<Teacher> getTeachersForCourse(Course course) {
        if (course == null) {
            return Collections.emptyList();
        }
        List<Teacher> teachers = new ArrayList<Teacher>();
        if (course.getLectureTeachers() != null) {
            teachers.addAll(course.getLectureTeachers());
        }
        if (course.getPracticeTeachers() != null) {
            for (Teacher teacher : course.getPracticeTeachers()) {
                if (!teachers.contains(teacher)) {
                    teachers.add(teacher);
                }
            }
        }
        teachers.sort(Comparator.comparing(Teacher::getLastName, Comparator.nullsLast(String::compareTo)));
        return teachers;
    }

    public boolean rateTeacher(Student student, Teacher teacher, double rating) {
        if (student == null || teacher == null || rating < 0.0 || rating > 5.0) {
            return false;
        }
        teacher.setRate((teacher.getRate() + rating) / 2.0);
        database.save();
        return true;
    }

    private double toGpaPoint(double score) {
        if (score >= 95) return 4.0;
        if (score >= 90) return 3.67;
        if (score >= 85) return 3.33;
        if (score >= 80) return 3.0;
        if (score >= 75) return 2.67;
        if (score >= 70) return 2.33;
        if (score >= 65) return 2.0;
        if (score >= 60) return 1.67;
        if (score >= 55) return 1.33;
        if (score >= 50) return 1.0;
        return 0.0;
    }
}
