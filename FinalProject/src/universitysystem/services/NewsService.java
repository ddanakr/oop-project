package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.core.DateTime;
import universitysystem.models.news.Comment;
import universitysystem.models.news.News;
import universitysystem.models.users.User;
import universitysystem.utils.NewsComparators;

import java.util.ArrayList;
import java.util.List;

public class NewsService {
    private final Database database;

    public NewsService() {
        this.database = Database.getInstance();
    }

    public News createNews(String title, String topic, String body, User author) {
        validateRequired(title, "Title");
        validateRequired(topic, "Topic");
        validateRequired(body, "Body");

        News news = new News();
        news.setId(generateNextId());
        news.setTitle(title);
        news.setTopic(topic);
        news.setBody(body);
        news.setAuthor(author);
        news.setCreatedAt(DateTime.now());
        news.setPinned(isResearchTopic(topic));

        database.getNews().add(news);
        database.save();
        return news;
    }

    public List<News> getAllNews() {
        List<News> result = new ArrayList<>(database.getNews());
        result.sort(NewsComparators.PINNED_FIRST_THEN_NEWEST);
        return result;
    }

    public List<News> getPinnedNews() {
        List<News> result = new ArrayList<>();
        for (News news : database.getNews()) {
            if (news.isPinned()) {
                result.add(news);
            }
        }
        return result;
    }

    public List<News> getNewsByTopic(String topic) {
        validateRequired(topic, "Topic");

        List<News> result = new ArrayList<>();
        for (News news : database.getNews()) {
            if (topic.equalsIgnoreCase(news.getTopic())) {
                result.add(news);
            }
        }
        return result;
    }

    public News getNewsById(int id) {
        for (News news : database.getNews()) {
            if (news.getId() == id) {
                return news;
            }
        }
        return null;
    }

    public boolean updateNews(int id, String title, String topic, String body) {
        News news = getNewsById(id);
        if (news == null) {
            return false;
        }

        if (hasText(title)) {
            news.setTitle(title);
        }
        if (hasText(topic)) {
            news.setTopic(topic);
        }
        if (hasText(body)) {
            news.setBody(body);
        }
        database.save();
        return true;
    }

    public boolean deleteNews(int id) {
        boolean deleted = database.getNews().removeIf(news -> news.getId() == id);
        if (deleted) {
            database.save();
        }
        return deleted;
    }

    public boolean pinNews(int id) {
        return setPinned(id, true);
    }

    public boolean unpinNews(int id) {
        return setPinned(id, false);
    }

    public Comment addComment(int newsId, User user, String text) {
        validateRequired(text, "Comment text");

        News news = getNewsById(newsId);
        if (news == null) {
            return null;
        }

        Comment comment = new Comment(user, text, DateTime.now());
        news.addComment(comment);
        database.save();
        return comment;
    }

    public List<Comment> getComments(int newsId) {
        News news = getNewsById(newsId);
        if (news == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(news.getComments());
    }

    private boolean setPinned(int id, boolean pinned) {
        News news = getNewsById(id);
        if (news == null) {
            return false;
        }

        news.setPinned(pinned);
        database.save();
        return true;
    }

    private int generateNextId() {
        int maxId = 0;
        for (News news : database.getNews()) {
            if (news.getId() > maxId) {
                maxId = news.getId();
            }
        }
        return maxId + 1;
    }

    private void validateRequired(String value, String fieldName) {
        if (!hasText(value)) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private boolean isResearchTopic(String topic) {
        return topic != null && topic.equalsIgnoreCase("Research");
    }
}
