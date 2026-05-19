package universitysystem.views;

import universitysystem.controllers.AuthController;
import universitysystem.controllers.AuthSession;
import universitysystem.controllers.UserRole;
import universitysystem.models.users.User;
import universitysystem.utils.ConsoleUtils;

public class LoginView {
    private final AuthController authController;

    public LoginView(AuthController authController) {
        this.authController = authController;
    }

    public User show() {
        while (true) {
            ConsoleUtils.printMenu("Login", "Sign in", "Exit");
            int choice = ConsoleUtils.getIntInput("Choose option: ");

            if (choice == 2) {
                return null;
            }
            if (choice != 1) {
                System.out.println("Invalid choice.");
                continue;
            }

            String login = ConsoleUtils.getInput("Login: ").trim();
            String password = ConsoleUtils.getInput("Password: ");

            AuthSession session = authController == null ? null : authController.loginSession(login, password);
            if (session == null || session.getUser() == null) {
                System.out.println("Invalid login or password.");
                continue;
            }

            User user = session.getUser();
            UserRole role = session.getRole();
            System.out.println("Logged in as: " + role);
            return user;
        }
    }
}
