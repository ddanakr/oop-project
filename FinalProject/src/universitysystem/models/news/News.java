package universitysystem.models.news;

import universitysystem.models.core.DateTime;
import universitysystem.models.users.User;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class News implements Serializable {
    private int id;
    private String title;
    private String topic;
    private String body;
    private User author;
    private DateTime createdAt;
    private boolean pinned;
    private List<Comment> comments;

    public News() {
        this.comments = new ArrayList<>();
    }

    public News(int id, String title, String topic, String body, User author, DateTime createdAt, boolean pinned, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.topic = topic;
        this.body = body;
        this.author = author;
        this.createdAt = createdAt;
        this.pinned = pinned;
        this.comments = comments != null ? comments : new ArrayList<>();
    }

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
        return pinned;
    }

    public void setPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments != null ? comments : new ArrayList<>();
    }

    public void addComment(Comment comment) {
        if (comment != null) {
            comments.add(comment);
        }
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", topic='" + topic + '\'' +
                ", pinned=" + pinned +
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
