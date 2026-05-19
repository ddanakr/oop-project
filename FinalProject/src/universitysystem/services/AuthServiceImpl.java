package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.users.User;

import java.util.List;
import java.util.Objects;

public class AuthServiceImpl implements AuthService {
    @Override
    public User login(String login, String password) {
        if (login == null || login.trim().isEmpty() || password == null) {
            return null;
        }

        Database db = Database.getInstance();
        List<User> users = db == null ? null : db.getUsers();
        if (users == null) {
            return null;
        }

        for (User user : users) {
            if (user == null) continue;
            if (!Objects.equals(login, user.getLogin())) continue;
            if (!Objects.equals(password, user.getPassword())) continue;
            return user;
        }
        return null;
    }

    @Override
    public boolean changePassword(User user, String oldPassword, String newPassword) {
        if (user == null || newPassword == null || newPassword.trim().isEmpty()) {
            return false;
        }
        if (oldPassword == null || !Objects.equals(oldPassword, user.getPassword())) {
            return false;
        }
        user.changePassword(newPassword);
        Database.getInstance().save();
        return true;
    }
}
