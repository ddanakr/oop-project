package universitysystem.models.users;

import universitysystem.models.DateTime;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class LogFile implements Serializable {

    /**
     * Default constructor
     */
    public LogFile() {
    }

    public LogFile(int id, User user, String action, DateTime timestamp) {
        this.id = id;
        this.user = user;
        this.action = action;
        this.timestamp = timestamp;
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private User user;

    /**
     * 
     */
    private String action;

    /**
     * 
     */
    private DateTime timestamp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public DateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(DateTime timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 
     */
    public String getLog() {
        return "[" + timestamp + "] User: " + user.getLogin() + " | Action: " + action;
    }

    @Override
    public String toString() {
        return "LogFile{" +
                "id=" + id +
                ", user=" + user +
                ", action='" + action + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogFile)) return false;
        LogFile logFile = (LogFile) o;
        return id == logFile.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}