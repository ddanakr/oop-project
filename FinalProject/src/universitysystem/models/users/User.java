package universitysystem.models.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class User {

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
     * @return
     */
    public void setEmail(String email) {
        // TODO implement here
        return null;
    }

    /**
     * 
     */
    public void changePassword(newPassword : String) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void sendMessage(message : Message) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getMessage() : List<Message>() {
        // TODO implement here
    }

    /**
     * 
     */
    public void switchLanguage(language : Language) : void() {
        // TODO implement here
    }

}