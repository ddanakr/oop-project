package universitysystem.controllers;

import universitysystem.models.users.LogFile;
import universitysystem.models.users.User;
import universitysystem.services.AdminService;

import java.util.Collections;
import java.util.List;

public class AdminController {
    private final AdminService adminService;
    private final User actor;

    public AdminController(AdminService adminService, User actor) {
        this.adminService = adminService;
        this.actor = actor;
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
