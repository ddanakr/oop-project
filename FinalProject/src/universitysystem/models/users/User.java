package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class User  implements Subcriber {

    /**
     * Default constructor
     */
    public User() {
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String lastName;

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String login;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private int age;

    /**
     * 
     */
    private String email;

    /**
     * 
     */
    private String phoneNumber;

    /**
     * 
     */
    private String gender;







    /**
     * @return
     */
    public boolean login() {
        // TODO implement here
        return false;
    }

    /**
     * @param email 
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     */
    public void changePassword(String newPassword) {
        if (newPassword != null && !newPassword.isEmpty()) {
            this.password = newPassword;
        }
    }

    /**
     * 
     */
    public void sendMessage(Message message) {
        // Messaging implementation can be added later.
    }

    /**
     * 
     */
    public List<Message> getMessage() {
        return Collections.emptyList();
    }

    /**
     * 
     */
    public void switchLanguage(Language language) {
        // Language switching not modeled yet.
    }

}