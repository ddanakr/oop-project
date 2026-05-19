package universitysystem.utils;

import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;

import java.util.Comparator;

public final class UserComparators {
    public static final Comparator<Student> STUDENT_BY_GPA =
            Comparator.comparing(Student::getGpa).reversed();

    public static final Comparator<Student> STUDENT_ALPHABETICALLY =
            Comparator.comparing(Student::getLastName, Comparator.nullsLast(String::compareToIgnoreCase))
                    .thenComparing(Student::getName, Comparator.nullsLast(String::compareToIgnoreCase));

    public static final Comparator<Teacher> TEACHER_ALPHABETICALLY =
            Comparator.comparing(Teacher::getLastName, Comparator.nullsLast(String::compareToIgnoreCase))
                    .thenComparing(Teacher::getName, Comparator.nullsLast(String::compareToIgnoreCase));

    public static final Comparator<User> USER_ALPHABETICALLY =
            Comparator.comparing(User::getLastName, Comparator.nullsLast(String::compareToIgnoreCase))
                    .thenComparing(User::getName, Comparator.nullsLast(String::compareToIgnoreCase));

    private UserComparators() {
    }
}
