package universitysystem.services;

import universitysystem.exceptions.CourseFullException;
import universitysystem.exceptions.MaxCreditsException;
import universitysystem.exceptions.MaxRetakesException;
import universitysystem.enums.Semester;
import universitysystem.database.Database;
import universitysystem.models.academic.Course;
import universitysystem.models.academic.Enrollment;
import universitysystem.models.academic.Mark;
import universitysystem.models.users.Student;
import universitysystem.models.users.User;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Service responsible for creating and finding Enrollment objects.
 * It stores references to existing Student and Course objects from Database,
 * so serialized object relationships stay connected between runs.
 */
public class EnrollmentService {
    public static final int MAX_CREDITS = 21;
    public static final int MAX_FAILED_ATTEMPTS = 3;
    public static final double PASSING_FINAL_SCORE = 50.0;

    private final Database database;

    public EnrollmentService() {
        this(Database.getInstance());
    }

    public EnrollmentService(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null.");
        }
        this.database = database;
    }

    /**
     * Enrolls an existing database student into an existing database course.
     *
     * @throws CourseFullException when course capacity has already been reached.
     * @throws MaxCreditsException when adding the course would exceed 21 credits.
     * @throws MaxRetakesException when the student already has 3 failed attempts.
     */
    public Enrollment createEnrollment(Student student, Course course, Semester semester, int year)
            throws CourseFullException, MaxCreditsException, MaxRetakesException {
        Student managedStudent = requireManagedStudent(student);
        Course managedCourse = requireManagedCourse(course);

        if (!managedCourse.isRegistrationOpen()) {
            throw new IllegalStateException("Registration for course " + managedCourse.getCourseCode() + " is closed.");
        }
        if (hasOpenEnrollment(managedStudent, managedCourse)) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }
        if (hasPassedCourse(managedStudent, managedCourse)) {
            throw new IllegalStateException("Student already passed this course.");
        }
        if (managedCourse.isFull()) {
            throw new CourseFullException("Course " + managedCourse.getCourseCode() + " is full.");
        }

        int newCreditTotal = calculateCurrentCredits(managedStudent) + managedCourse.getCredits();
        if (newCreditTotal > MAX_CREDITS) {
            throw new MaxCreditsException("Student cannot exceed " + MAX_CREDITS + " credits.");
        }

        int failedAttempts = countFailedAttempts(managedStudent, managedCourse);
        if (failedAttempts >= MAX_FAILED_ATTEMPTS) {
            throw new MaxRetakesException("Student reached the maximum failed attempts for this course.");
        }

        Enrollment enrollment = new Enrollment(
                managedStudent,
                managedCourse,
                semester,
                year,
                null,
                countAttempts(managedStudent, managedCourse) + 1
        );

        getEnrollments().add(enrollment);
        managedCourse.addEnrollment(enrollment);
        managedStudent.setCredits(newCreditTotal);
        saveDatabase();
        return enrollment;
    }

    /**
     * Returns true when the student is currently linked to the course by an Enrollment.
     */
    public boolean isEnrolled(Student student, Course course) {
        return findEnrollment(student, course) != null;
    }

    /**
     * Finds one enrollment for a student and course, or null if no link exists.
     */
    public Enrollment findEnrollment(Student student, Course course) {
        if (student == null || course == null) {
            return null;
        }
        Enrollment latest = null;
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student) && sameCourse(enrollment.getCourse(), course)) {
                if (enrollment.getMark() == null) {
                    return enrollment;
                }
                if (latest == null || enrollment.getAttemptNumber() > latest.getAttemptNumber()) {
                    latest = enrollment;
                }
            }
        }
        return latest;
    }

    /**
     * Returns all enrollments for the student sorted by year, semester, then course code.
     */
    public List<Enrollment> getEnrollmentsByStudent(Student student) {
        if (student == null) {
            return Collections.emptyList();
        }
        List<Enrollment> result = new ArrayList<Enrollment>();
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student)) {
                result.add(enrollment);
            }
        }
        result.sort(Comparator
                .comparingInt(Enrollment::getYear)
                .thenComparing(enrollment -> enrollment.getSemester() == null ? "" : enrollment.getSemester().name())
                .thenComparing(enrollment -> enrollment.getCourse() == null ? "" : enrollment.getCourse().getCourseCode()));
        return result;
    }

    /**
     * Returns all enrollments for the course sorted by student last name and id.
     */
    public List<Enrollment> getEnrollmentsByCourse(Course course) {
        if (course == null) {
            return Collections.emptyList();
        }
        List<Enrollment> result = new ArrayList<Enrollment>();
        for (Enrollment enrollment : getEnrollments()) {
            if (sameCourse(enrollment.getCourse(), course)) {
                result.add(enrollment);
            }
        }
        result.sort(Comparator
                .comparing((Enrollment enrollment) -> safeString(enrollment.getStudent() == null ? null : enrollment.getStudent().getLastName()))
                .thenComparingInt(enrollment -> enrollment.getStudent() == null ? 0 : enrollment.getStudent().getId()));
        return result;
    }

    /**
     * Stores or replaces the Mark object connected to an existing Enrollment.
     */
    public void setMark(Enrollment enrollment, Mark mark) {
        if (enrollment == null) {
            throw new IllegalArgumentException("Enrollment cannot be null.");
        }
        if (mark == null) {
            throw new IllegalArgumentException("Mark cannot be null.");
        }
        if (!getEnrollments().contains(enrollment)) {
            throw new IllegalArgumentException("Enrollment must belong to Database.");
        }
        enrollment.setMark(mark);
        addMarkToDatabaseIfSupported(mark);
        saveDatabase();
    }

    /**
     * Calculates credits for courses where the student is enrolled and has not failed.
     */
    public int calculateCurrentCredits(Student student) {
        if (student == null) {
            return 0;
        }
        int total = 0;
        for (Enrollment enrollment : getEnrollmentsByStudent(student)) {
            Course course = enrollment.getCourse();
            if (course != null && !isFailed(enrollment.getMark())) {
                total += course.getCredits();
            }
        }
        return total;
    }

    /**
     * Counts failed completed attempts for the student in the given course.
     */
    public int countFailedAttempts(Student student, Course course) {
        int count = 0;
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student)
                    && sameCourse(enrollment.getCourse(), course)
                    && isFailed(enrollment.getMark())) {
                count++;
            }
        }
        return count;
    }

    /**
     * Counts all attempts for the student in the given course.
     */
    public int countAttempts(Student student, Course course) {
        int count = 0;
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student) && sameCourse(enrollment.getCourse(), course)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns true when the student has an unfinished enrollment for the course.
     */
    public boolean hasOpenEnrollment(Student student, Course course) {
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student)
                    && sameCourse(enrollment.getCourse(), course)
                    && enrollment.getMark() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true when the student has already completed the course successfully.
     */
    public boolean hasPassedCourse(Student student, Course course) {
        for (Enrollment enrollment : getEnrollments()) {
            if (sameStudent(enrollment.getStudent(), student)
                    && sameCourse(enrollment.getCourse(), course)
                    && enrollment.getMark() != null
                    && !isFailed(enrollment.getMark())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Exposes a safe mutable enrollment list from Database.
     */
    public List<Enrollment> getEnrollments() {
        if (database.getEnrollments() == null) {
            database.setEnrollments(new ArrayList<Enrollment>());
        }
        return database.getEnrollments();
    }

    private Student requireManagedStudent(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null.");
        }
        for (User user : database.getUsers()) {
            if (user instanceof Student && sameStudent((Student) user, student)) {
                return (Student) user;
            }
        }
        throw new IllegalArgumentException("Student must belong to Database.");
    }

    private Course requireManagedCourse(Course course) {
        if (course == null) {
            throw new IllegalArgumentException("Course cannot be null.");
        }
        for (Course dbCourse : database.getCourses()) {
            if (sameCourse(dbCourse, course)) {
                return dbCourse;
            }
        }
        throw new IllegalArgumentException("Course must belong to Database.");
    }

    private boolean isFailed(Mark mark) {
        return mark != null && mark.calculateFinal() < PASSING_FINAL_SCORE;
    }

    private boolean sameStudent(Student first, Student second) {
        return first != null && second != null && (first == second || first.getId() == second.getId() || first.equals(second));
    }

    private boolean sameCourse(Course first, Course second) {
        return first != null && second != null && (first == second || Objects.equals(first.getCourseCode(), second.getCourseCode()));
    }

    private String safeString(String value) {
        return value == null ? "" : value;
    }

    private void saveDatabase() {
        invokeNoArgDatabaseMethod("save");
    }

    @SuppressWarnings("unchecked")
    private void addMarkToDatabaseIfSupported(Mark mark) {
        try {
            Method method = database.getClass().getMethod("getMarks");
            Object value = method.invoke(database);
            if (value instanceof List && !((List<Mark>) value).contains(mark)) {
                ((List<Mark>) value).add(mark);
            }
        } catch (Exception ignored) {
            // Current Database skeleton keeps marks inside Enrollment only.
        }
    }

    private void invokeNoArgDatabaseMethod(String methodName) {
        try {
            Method method = database.getClass().getMethod(methodName);
            method.invoke(database);
        } catch (Exception ignored) {
            // Database persistence method is optional in the current skeleton.
        }
    }
}
