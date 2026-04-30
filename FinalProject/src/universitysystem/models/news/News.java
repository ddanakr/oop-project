package universitysystem.models.news;

import universitysystem.models.DateTime;
import universitysystem.models.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class News implements Serializable {

    /**
     * Default constructor
     */
    public News() {
        this.comments = new ArrayList<>();
    }

    public News(int id, String title, String topic, String body, User author, DateTime createdAt, boolean isPinned, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.isPinned = isPinned;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String topic;

    /**
     * 
     */
    private String body;

    /**
     * 
     */
    private User author;

    /**
     * 
     */
    private DateTime createdAt;

    /**
     * 
     */
    private boolean isPinned = false;

    /**
     * 
     */
    private List<Comment> comments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isPinned() {
        return isPinned;
    }

    public void setPinned(boolean pinned) {
        isPinned = pinned;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    /**
     * 
     */
    public void addComment(Comment comment) {
        if (this.comments == null) {
            this.comments = new ArrayList<>();
        }
        if (comment != null) {
            this.comments.add(comment);
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof News)) return false;
        News news = (News) o;
        return id == news.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}