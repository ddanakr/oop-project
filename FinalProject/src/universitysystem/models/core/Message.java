package universitysystem.models.core;

import universitysystem.models.users.User;
import java.io.*;
import java.util.*;

/**
 * 
 */
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private User from;

    /**
     * 
     */
    private User to;

    /**
     * 
     */
    private String text;

    /**
     * 
     */
    public DateTime sentAt;

    /**
     * 
     */
    private boolean isRead;
    
    /**
     * Default constructor
     */
    public Message() {
    }

    public Message(User from, User to, String text, DateTime sentAt, boolean isRead) {
        this.from = from;
        this.to = to;
        this.text = text;
        this.sentAt = sentAt;
        this.isRead = isRead;
    }
    

    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    public User getTo() {
        return to;
    }

    public void setTo(User to) {
        this.to = to;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public DateTime getSentAt() {
        return sentAt;
    }

    public void setSentAt(DateTime sentAt) {
        this.sentAt = sentAt;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    /**
     * 
     */
    public boolean markAsRead() {
        this.isRead = true;
        return true;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from=" + from +
                ", to=" + to +
                ", text='" + text + '\'' +
                ", isRead=" + isRead +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return Objects.equals(from, message.from) &&
                Objects.equals(to, message.to) &&
                Objects.equals(text, message.text) &&
                Objects.equals(sentAt, message.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, text, sentAt);
    }

}
