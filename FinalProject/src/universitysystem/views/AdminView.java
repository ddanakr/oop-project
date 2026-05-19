package universitysystem.views;

import universitysystem.controllers.AuthController;
import universitysystem.controllers.AdminController;
import universitysystem.models.users.Admin;
import universitysystem.models.users.Degree;
import universitysystem.models.users.Manager;
import universitysystem.models.users.Student;
import universitysystem.models.users.TechSupport;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;
import universitysystem.models.users.LogFile;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminView {
    private final AdminController adminController;
    private final AuthController authController;
    private final User currentUser;
    private final MessageView messageView;

    public AdminView(AdminController adminController) {
        this(adminController, null, null, null);
    }

    public AdminView(AdminController adminController, AuthController authController, User currentUser, MessageView messageView) {
        this.adminController = adminController;
        this.authController = authController;
        this.currentUser = currentUser;
        this.messageView = messageView;
    }

    public void show() {
        while (true) {
            ConsoleUtils.printMenu(
                    "Admin Menu",
                    "List users",
                    "Create user",
                    "Update user",
                    "Delete user",
                    "Reset user password",
                    "View logs",
                    "Change my password",
                    "Messages",
                    "Logout"
            );

            int choice = ConsoleUtils.getIntInput("Choose option: ");
            if (choice == 9) {
                return;
            }

            switch (choice) {
                case 1:
                    listUsers();
                    break;
                case 2:
                    createUser();
                    break;
                case 3:
                    updateUser();
                    break;
                case 4:
                    deleteUser();
                    break;
                case 5:
                    resetPassword();
                    break;
                case 6:
                    viewLogs();
                    break;
                case 7:
                    changeMyPassword();
                    break;
                case 8:
                    openMessages();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void listUsers() {
        List<User> users = adminController.getAllUsers();
        List<String> headers = Arrays.asList("ID", "Login", "Type", "Name", "Last name");
        List<List<String>> rows = new ArrayList<>();
        for (User user : users) {
            if (user == null) continue;
            rows.add(Arrays.asList(
                    String.valueOf(user.getId()),
                    String.valueOf(user.getLogin()),
                    user.getClass().getSimpleName(),
                    String.valueOf(user.getName()),
                    String.valueOf(user.getLastName())
            ));
        }
        ConsoleUtils.printTable(headers, rows);
    }

    private void createUser() {
        User user = promptNewUser();
        if (user == null) {
            System.out.println("User creation cancelled.");
            return;
        }
        boolean ok = adminController.createUser(user);
        System.out.println(ok ? "User created." : "Failed to create user.");
    }

    private void updateUser() {
        int userId = ConsoleUtils.getIntInput("Enter user id to update: ");
        User user = adminController.findUserById(userId);
        if (user == null) {
            System.out.println("User not found.");
            return;
        }

        System.out.println("Press Enter to keep current value.");

        String name = ConsoleUtils.getInput("Name (" + safe(user.getName()) + "): ");
        if (!name.trim().isEmpty()) user.setName(name);

        String lastName = ConsoleUtils.getInput("Last name (" + safe(user.getLastName()) + "): ");
        if (!lastName.trim().isEmpty()) user.setLastName(lastName);

        String login = ConsoleUtils.getInput("Login (" + safe(user.getLogin()) + "): ");
        if (!login.trim().isEmpty()) user.setLogin(login);

        String password = ConsoleUtils.getInput("Password (" + (user.getPassword() == null ? "" : "***") + "): ");
        if (!password.trim().isEmpty()) user.setPassword(password);

        String ageInput = ConsoleUtils.getInput("Age (" + user.getAge() + "): ");
        if (!ageInput.trim().isEmpty()) {
            try {
                user.setAge(Integer.parseInt(ageInput.trim()));
            } catch (NumberFormatException ex) {
                System.out.println("Invalid age, keeping old value.");
            }
        }

        String email = ConsoleUtils.getInput("Email (" + safe(user.getEmail()) + "): ");
        if (!email.trim().isEmpty()) user.setEmail(email);

        String phone = ConsoleUtils.getInput("Phone (" + safe(user.getPhoneNumber()) + "): ");
        if (!phone.trim().isEmpty()) user.setPhoneNumber(phone);

        String gender = ConsoleUtils.getInput("Gender (" + safe(user.getGender()) + "): ");
        if (!gender.trim().isEmpty()) user.setGender(gender);

        boolean ok = adminController.updateUser(user);
        System.out.println(ok ? "User updated." : "Failed to update user.");
    }

    private void deleteUser() {
        int userId = ConsoleUtils.getIntInput("Enter user id to delete: ");
        boolean ok = adminController.deleteUser(userId);
        System.out.println(ok ? "User deleted." : "User not found.");
    }

    private void resetPassword() {
        int userId = ConsoleUtils.getIntInput("Enter user id to reset password: ");
        boolean ok = adminController.resetPassword(userId);
        System.out.println(ok ? "Password reset (empty)." : "User not found.");
    }

    private void viewLogs() {
        List<LogFile> logs = adminController.getLogs();
        List<String> headers = Arrays.asList("ID", "Time", "Actor", "Action");
        List<List<String>> rows = new ArrayList<>();
        for (LogFile log : logs) {
            if (log == null) continue;
            rows.add(Arrays.asList(
                    String.valueOf(log.getId()),
                    String.valueOf(log.getTimestamp()),
                    log.getUser() == null ? "" : String.valueOf(log.getUser().getLogin()),
                    String.valueOf(log.getAction())
            ));
        }
        ConsoleUtils.printTable(headers, rows);
    }

    private void changeMyPassword() {
        if (authController == null || currentUser == null) {
            System.out.println("Auth is not configured for this view.");
            return;
        }
        String oldPassword = ConsoleUtils.getInput("Old password: ");
        String newPassword = ConsoleUtils.getInput("New password: ");
        boolean ok = authController.changePassword(currentUser, oldPassword, newPassword);
        System.out.println(ok ? "Password changed." : "Failed to change password.");
    }

    private void openMessages() {
        if (messageView == null || currentUser == null) {
            System.out.println("Messages are not configured for this view.");
            return;
        }
        messageView.show(currentUser, adminController.getAllUsers());
    }

    private User promptNewUser() {
        ConsoleUtils.printMenu(
                "User type",
                "Student",
                "Teacher",
                "Manager",
                "Admin",
                "TechSupport",
                "Cancel"
        );
        int typeChoice = ConsoleUtils.getIntInput("Choose user type: ");
        if (typeChoice == 6) return null;

        User user;
        switch (typeChoice) {
            case 1:
                user = new Student();
                break;
            case 2:
                user = new Teacher();
                break;
            case 3:
                user = new Manager();
                break;
            case 4:
                user = new Admin();
                break;
            case 5:
                user = new TechSupport();
                break;
            default:
                user = null;
        }
        if (user == null) return null;

        user.setId(ConsoleUtils.getIntInput("ID: "));
        user.setName(ConsoleUtils.getInput("Name: "));
        user.setLastName(ConsoleUtils.getInput("Last name: "));
        user.setLogin(ConsoleUtils.getInput("Login: "));
        user.setPassword(ConsoleUtils.getInput("Password: "));
        user.setAge(ConsoleUtils.getIntInput("Age: "));
        user.setEmail(ConsoleUtils.getInput("Email: "));
        user.setPhoneNumber(ConsoleUtils.getInput("Phone: "));
        user.setGender(ConsoleUtils.getInput("Gender: "));

        if (user instanceof Student) {
            Student student = (Student) user;
            student.setYear(ConsoleUtils.getIntInput("Year: "));
            ConsoleUtils.printMenu("Degree", "BACHELOR", "MASTER", "PHD");
            int degreeChoice = ConsoleUtils.getIntInput("Degree: ");
            if (degreeChoice >= 1 && degreeChoice <= Degree.values().length) {
                student.setDegree(Degree.values()[degreeChoice - 1]);
            }
            student.setSpeciality(ConsoleUtils.getInput("Speciality: "));
        }

        return user;
    }

    private String safe(String value) {
        return value == null ? "" : value;
    }
}
