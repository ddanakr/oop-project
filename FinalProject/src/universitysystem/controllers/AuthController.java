package universitysystem.controllers;

import universitysystem.models.users.User;
import universitysystem.services.AuthService;
import universitysystem.views.LoginView;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public User runLogin(LoginView loginView) {
        if (loginView == null) {
            return null;
        }

        while (true) {
            loginView.showMenu();
            int choice = loginView.readMenuChoice();

            if (choice == 2 || choice == 0) {
                return null;
            }
            if (choice != 1) {
                loginView.showError("Invalid choice.");
                continue;
            }

            LoginView.LoginInput input = loginView.readLoginInput();
            AuthSession session = loginSession(input.getLogin(), input.getPassword());
            if (session == null || session.getUser() == null) {
                loginView.showError("Invalid login or password.");
                continue;
            }

            loginView.showMessage("Logged in as: " + session.getRole());
            return session.getUser();
        }
    }

    public User login(String login, String password) {
        return authService == null ? null : authService.login(login, password);
    }

    public AuthSession loginSession(String login, String password) {
        User user = login(login, password);
        if (user == null) return null;
        return new AuthSession(user, getRole(user));
    }

    public boolean changePassword(User user, String oldPassword, String newPassword) {
        return authService != null && authService.changePassword(user, oldPassword, newPassword);
    }

    public UserRole getRole(User user) {
        if (user == null) return UserRole.UNKNOWN;
        String typeName = user.getClass().getSimpleName();
        if ("Student".equals(typeName) || "GraduateStudent".equals(typeName)) return UserRole.STUDENT;
        if ("Teacher".equals(typeName)) return UserRole.TEACHER;
        if ("Manager".equals(typeName)) return UserRole.MANAGER;
        if ("Admin".equals(typeName)) return UserRole.ADMIN;
        if ("TechSupport".equals(typeName)) return UserRole.TECH_SUPPORT;
        return UserRole.UNKNOWN;
    }
}
