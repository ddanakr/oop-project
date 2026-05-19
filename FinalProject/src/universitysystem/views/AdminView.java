package universitysystem.views;

import universitysystem.enums.Degree;
import universitysystem.models.core.LogFile;
import universitysystem.models.users.Admin;
import universitysystem.models.users.Manager;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.TechSupport;
import universitysystem.models.users.User;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminView {
    public void showMenu() {
        ConsoleUtils.printHeader("Admin Menu");
        System.out.println("1. List users");
        System.out.println("2. Create user");
        System.out.println("3. Update user");
        System.out.println("4. Delete user");
        System.out.println("5. Reset user password");
        System.out.println("6. View logs");
        System.out.println("7. Change my password");
        System.out.println("8. Messages");
        System.out.println("9. News");
        System.out.println("10. Journals");
        System.out.println("0. Logout");
    }

    public int readMenuChoice() {
        return ConsoleUtils.getIntInput("Choose option: ");
    }

    public int readUserId(String prompt) {
        return ConsoleUtils.getIntInput(prompt);
    }

    public User readNewUser() {
        ConsoleUtils.printHeader("User Type");
        System.out.println("1. Student");
        System.out.println("2. Teacher");
        System.out.println("3. Manager");
        System.out.println("4. Admin");
        System.out.println("5. TechSupport");
        System.out.println("0. Cancel");
        int typeChoice = ConsoleUtils.getIntInput("Choose user type: ");
        if (typeChoice == 0) {
            return null;
        }

        User user = createUserByChoice(typeChoice);
        if (user == null) {
            return null;
        }

        readRequiredUserFields(user);
        if (user instanceof Student) {
            readStudentFields((Student) user);
        }
        return user;
    }

    public void readUserUpdates(User user) {
        showMessage("Press Enter to keep current value.");

        String name = ConsoleUtils.getInput("Name (" + safe(user.getName()) + "): ");
        if (!name.trim().isEmpty()) {
            user.setName(name);
        }

        String lastName = ConsoleUtils.getInput("Last name (" + safe(user.getLastName()) + "): ");
        if (!lastName.trim().isEmpty()) {
            user.setLastName(lastName);
        }

        String login = ConsoleUtils.getInput("Login (" + safe(user.getLogin()) + "): ");
        if (!login.trim().isEmpty()) {
            user.setLogin(login);
        }

        String password = ConsoleUtils.getInput("Password (" + (user.getPassword() == null ? "" : "***") + "): ");
        if (!password.trim().isEmpty()) {
            user.setPassword(password);
        }

        String ageInput = ConsoleUtils.getInput("Age (" + user.getAge() + "): ");
        if (!ageInput.trim().isEmpty()) {
            try {
                user.setAge(Integer.parseInt(ageInput.trim()));
            } catch (NumberFormatException ex) {
                showError("Invalid age, keeping old value.");
            }
        }

        String email = ConsoleUtils.getInput("Email (" + safe(user.getEmail()) + "): ");
        if (!email.trim().isEmpty()) {
            user.setEmail(email);
        }

        String phone = ConsoleUtils.getInput("Phone (" + safe(user.getPhoneNumber()) + "): ");
        if (!phone.trim().isEmpty()) {
            user.setPhoneNumber(phone);
        }

        String gender = ConsoleUtils.getInput("Gender (" + safe(user.getGender()) + "): ");
        if (!gender.trim().isEmpty()) {
            user.setGender(gender);
        }
    }

    public PasswordInput readPasswordInput() {
        String oldPassword = ConsoleUtils.getInput("Old password: ");
        String newPassword = ConsoleUtils.getInput("New password: ");
        return new PasswordInput(oldPassword, newPassword);
    }

    public void showUsers(List<User> users) {
        List<String> headers = Arrays.asList("ID", "Login", "Type", "Name", "Last name");
        List<List<String>> rows = new ArrayList<>();
        if (users != null) {
            for (User user : users) {
                if (user == null) {
                    continue;
                }
                rows.add(Arrays.asList(
                        String.valueOf(user.getId()),
                        String.valueOf(user.getLogin()),
                        user.getClass().getSimpleName(),
                        String.valueOf(user.getName()),
                        String.valueOf(user.getLastName())
                ));
            }
        }
        ConsoleUtils.printTable(headers, rows);
    }

    public void showLogs(List<LogFile> logs) {
        List<String> headers = Arrays.asList("ID", "Time", "Actor", "Action");
        List<List<String>> rows = new ArrayList<>();
        if (logs != null) {
            for (LogFile log : logs) {
                if (log == null) {
                    continue;
                }
                rows.add(Arrays.asList(
                        String.valueOf(log.getId()),
                        String.valueOf(log.getTimestamp()),
                        log.getUser() == null ? "" : String.valueOf(log.getUser().getLogin()),
                        String.valueOf(log.getAction())
                ));
            }
        }
        ConsoleUtils.printTable(headers, rows);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    private User createUserByChoice(int typeChoice) {
        switch (typeChoice) {
            case 1:
                return new Student();
            case 2:
                return new Teacher();
            case 3:
                return new Manager();
            case 4:
                return new Admin();
            case 5:
                return new TechSupport();
            default:
                return null;
        }
    }

    private void readRequiredUserFields(User user) {
        user.setId(ConsoleUtils.getIntInput("ID: "));
        user.setName(ConsoleUtils.getInput("Name: "));
        user.setLastName(ConsoleUtils.getInput("Last name: "));
        user.setLogin(ConsoleUtils.getInput("Login: "));
        user.setPassword(ConsoleUtils.getInput("Password: "));
        user.setAge(ConsoleUtils.getIntInput("Age: "));
        user.setEmail(ConsoleUtils.getInput("Email: "));
        user.setPhoneNumber(ConsoleUtils.getInput("Phone: "));
        user.setGender(ConsoleUtils.getInput("Gender: "));
    }

    private void readStudentFields(Student student) {
        student.setYear(ConsoleUtils.getIntInput("Year: "));
        ConsoleUtils.printHeader("Degree");
        System.out.println("1. BACHELOR");
        System.out.println("2. MASTER");
        System.out.println("3. PHD");
        int degreeChoice = ConsoleUtils.getIntInput("Degree: ");
        if (degreeChoice >= 1 && degreeChoice <= Degree.values().length) {
            student.setDegree(Degree.values()[degreeChoice - 1]);
        }
        student.setSpeciality(ConsoleUtils.getInput("Speciality: "));
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }

    public static class PasswordInput {
        private final String oldPassword;
        private final String newPassword;

        public PasswordInput(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}
