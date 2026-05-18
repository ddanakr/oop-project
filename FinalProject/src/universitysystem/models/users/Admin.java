package universitysystem.models.users;

import universitysystem.database.Database;
import universitysystem.models.core.LogFile;

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
        this.users = new ArrayList<>();
    }

    /**
     * 
     */
    private List<User> users;

    public Admin(
            String name,
            String lastName,
            int id,
            String login,
            String password,
            int age,
            String email,
            String phoneNumber,
            String gender,
            double salary,
            java.util.Date hireDate,
            List<User> users
    ) {
        super(name, lastName, id, login, password, age, email, phoneNumber, gender, salary, hireDate);
        this.users = users == null ? new ArrayList<>() : new ArrayList<>(users);
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users == null ? new ArrayList<>() : new ArrayList<>(users);
    }
    
    
    
    /**
     * 
     */
    public void addUser(User user) {
        if (user == null) return;
        if (users == null) users = new ArrayList<>();
        users.add(user);
        Database.getInstance().getUsers().add(user);
    }

    /**
     * 
     */
    public void deleteUser(int userId) {
        if (users != null) {
            users.removeIf(u -> u != null && u.getId() == userId);
        }
        Database.getInstance().getUsers().removeIf(u -> u != null && u.getId() == userId);
    }

    /**
     * 
     */
    public void updateUser(User user) {
        if (user == null) return;
        List<User> dbUsers = Database.getInstance().getUsers();
        for (int i = 0; i < dbUsers.size(); i++) {
            User existing = dbUsers.get(i);
            if (existing != null && existing.getId() == user.getId()) {
                dbUsers.set(i, user);
                return;
            }
        }
        dbUsers.add(user);
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
    
    
    @Override
    public String toString() {
        return "Admin{login='" + getLogin() + "', id=" + getId() + ", users=" + (users == null ? 0 : users.size()) + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Admin admin = (Admin) o;
        return Objects.equals(getLogin(), admin.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}
