package universitysystem.controllers;

import universitysystem.models.users.User;

public class AuthSession {
    private final User user;
    private final UserRole role;

    public AuthSession(User user, UserRole role) {
        this.user = user;
        this.role = role == null ? UserRole.UNKNOWN : role;
    }

    public User getUser() {
        return user;
    }

    public UserRole getRole() {
        return role;
    }
}
