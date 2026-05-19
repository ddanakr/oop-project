package universitysystem.services;

import universitysystem.models.users.User;

public interface AuthService {
    User login(String login, String password);

    boolean changePassword(User user, String oldPassword, String newPassword);
}
