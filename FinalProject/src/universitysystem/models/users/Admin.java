package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Admin extends Employee {

    /**
     * Default constructor
     */
    public Admin() {
    }

    /**
     * 
     */
    private List<User> users;

    /**
     * 
     */
    public void addUser(User user) {
        if (this.users == null) {
            this.users = new ArrayList<>();
        }
        if (user != null) {
            this.users.add(user);
        }
    }

    /**
     * 
     */
    public void deleteUser(int userId) {
        if (this.users != null) {
            this.users.removeIf(user -> user != null && user.getId() == userId);
        }
    }

    /**
     * 
     */
    public void updateUser(User user) {
        if (user == null || this.users == null) {
            return;
        }
        for (int i = 0; i < this.users.size(); i++) {
            User existing = this.users.get(i);
            if (existing != null && existing.getId() == user.getId()) {
                this.users.set(i, user);
                return;
            }
        }
        this.users.add(user);
    }

    /**
     * 
     */
    public void dropUserPassword(int userId) {
        if (this.users == null) {
            return;
        }
        for (User user : this.users) {
            if (user != null && user.getId() == userId) {
                user.setPassword("");
                return;
            }
        }
    }

    /**
     * 
     */
    public List<LogFile> viewLogs() {
        Database db = Database.getInstance();
        return db != null && db.getLogFiles() != null ? db.getLogFiles() : Collections.emptyList();
    }

}