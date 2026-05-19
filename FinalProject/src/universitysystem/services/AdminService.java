package universitysystem.services;

import universitysystem.models.core.LogFile;
import universitysystem.models.users.User;

import java.util.List;

public interface AdminService {
    List<User> getAllUsers();

    User findUserById(int userId);

    boolean addUser(User user, User actor);

    boolean updateUser(User user, User actor);

    boolean deleteUser(int userId, User actor);

    boolean resetUserPassword(int userId, User actor);

    boolean makeUserResearcher(int userId, User actor);

    List<LogFile> getLogs();
}
