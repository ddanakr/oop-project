package universitysystem.controllers;

import universitysystem.models.core.LogFile;
import universitysystem.models.users.User;
import universitysystem.services.AdminService;
import universitysystem.views.AdminView;

import java.util.Collections;
import java.util.List;

public class AdminController {
    private final AdminService adminService;
    private final AdminView adminView;
    private final AuthController authController;
    private final MessageController messageController;
    private final NewsController newsController;
    private final JournalController journalController;
    private final User actor;

    public AdminController(AdminService adminService, User actor) {
        this(adminService, new AdminView(), null, null, actor);
    }

    public AdminController(
            AdminService adminService,
            AdminView adminView,
            AuthController authController,
            MessageController messageController,
            User actor
    ) {
        this.adminService = adminService;
        this.adminView = adminView;
        this.authController = authController;
        this.messageController = messageController;
        this.newsController = new NewsController(actor);
        this.journalController = new JournalController(actor);
        this.actor = actor;
    }

    public void run() {
        boolean running = true;
        while (running) {
            adminView.showMenu();
            int choice = adminView.readMenuChoice();
            running = handleChoice(choice);
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                adminView.showUsers(getAllUsers());
                return true;
            case 2:
                createUser();
                return true;
            case 3:
                updateUser();
                return true;
            case 4:
                deleteUser();
                return true;
            case 5:
                resetPassword();
                return true;
            case 6:
                adminView.showLogs(getLogs());
                return true;
            case 7:
                changeMyPassword();
                return true;
            case 8:
                openMessages();
                return true;
            case 9:
                newsController.run();
                return true;
            case 10:
                journalController.run();
                return true;
            case 0:
                adminView.showMessage("Logout.");
                return false;
            default:
                adminView.showError("Invalid choice.");
                return true;
        }
    }

    private void createUser() {
        User user = adminView.readNewUser();
        if (user == null) {
            adminView.showMessage("User creation cancelled.");
            return;
        }
        adminView.showMessage(createUser(user) ? "User created." : "Failed to create user.");
    }

    private void updateUser() {
        int userId = adminView.readUserId("Enter user id to update: ");
        User user = findUserById(userId);
        if (user == null) {
            adminView.showError("User not found.");
            return;
        }
        adminView.readUserUpdates(user);
        adminView.showMessage(updateUser(user) ? "User updated." : "Failed to update user.");
    }

    private void deleteUser() {
        int userId = adminView.readUserId("Enter user id to delete: ");
        adminView.showMessage(deleteUser(userId) ? "User deleted." : "User not found.");
    }

    private void resetPassword() {
        int userId = adminView.readUserId("Enter user id to reset password: ");
        adminView.showMessage(resetPassword(userId) ? "Password reset (empty)." : "User not found.");
    }

    private void changeMyPassword() {
        if (authController == null || actor == null) {
            adminView.showError("Auth is not configured.");
            return;
        }
        AdminView.PasswordInput input = adminView.readPasswordInput();
        boolean changed = authController.changePassword(actor, input.getOldPassword(), input.getNewPassword());
        adminView.showMessage(changed ? "Password changed." : "Failed to change password.");
    }

    private void openMessages() {
        if (messageController == null || actor == null) {
            adminView.showError("Messages are not configured.");
            return;
        }
        messageController.run(actor, getAllUsers());
    }

    public List<User> getAllUsers() {
        return adminService == null ? Collections.emptyList() : adminService.getAllUsers();
    }

    public User findUserById(int userId) {
        return adminService == null ? null : adminService.findUserById(userId);
    }

    public boolean createUser(User user) {
        return adminService != null && adminService.addUser(user, actor);
    }

    public boolean updateUser(User user) {
        return adminService != null && adminService.updateUser(user, actor);
    }

    public boolean deleteUser(int userId) {
        return adminService != null && adminService.deleteUser(userId, actor);
    }

    public boolean resetPassword(int userId) {
        return adminService != null && adminService.resetUserPassword(userId, actor);
    }

    public List<LogFile> getLogs() {
        return adminService == null ? Collections.emptyList() : adminService.getLogs();
    }
}
