package universitysystem.models.news;

import universitysystem.models.DateTime;
import universitysystem.models.users.User;

import java.util.Objects;

public class Comment {
    private User user;
    private String text;
    private DateTime sentAt;

    public Comment() {
    }

    public Comment(User user, String text, DateTime sentAt) {
        this.user = user;
        this.text = text;
        this.sentAt = sentAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    @Override
    public String toString() {
        return "Comment{" +
                "user=" + (user == null ? null : user.getLogin()) +
                ", sentAt=" + sentAt +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Comment)) return false;
        Comment comment = (Comment) o;
        return Objects.equals(user == null ? null : user.getLogin(), comment.user == null ? null : comment.user.getLogin())
                && Objects.equals(sentAt, comment.sentAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user == null ? null : user.getLogin(), sentAt);
    }
}
