package universitysystem.models.users;

import universitysystem.enums.Language;
import universitysystem.models.core.Message;
import universitysystem.models.news.Subscriber;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class User implements Subscriber, Serializable {


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
     * Default constructor
     */
    public User() {
    }


    protected User(
            String name,
            String lastName,
            int id,
            String login,
            String password,
            int age,
            String email,
            String phoneNumber,
            String gender
    ) {
        this.name = name;
        this.lastName = lastName;
        this.id = id;
        this.login = login;
        this.password = password;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
    }
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



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
        // later
    }

    /**
     * 
     */
    public List<Message> getMessage() {
    	return new ArrayList<>();
    }

    /**
     * 
     */
    public void switchLanguage(Language language) {
        // later
    }

    @Override
    public void update(String notification) {
        System.out.println(notification);
    }
    
    
    @Override
    public String toString() {
        return "User{login='" + login + "', id=" + id + ", name='" + name + "', lastName='" + lastName + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login);
    }

}
