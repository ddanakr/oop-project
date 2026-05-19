package universitysystem.views;

import universitysystem.models.news.Comment;
import universitysystem.models.news.News;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewsView {
    public void showMenu() {
        showUserMenu();
    }

    public void showUserMenu() {
        ConsoleUtils.printHeader("News Menu");
        System.out.println("1. Show all news");
        System.out.println("2. Show pinned news");
        System.out.println("3. Show news by topic");
        System.out.println("4. Show news details");
        System.out.println("5. Add comment");
        System.out.println("0. Back");
    }

    public void showManagerMenu() {
        ConsoleUtils.printHeader("Manager News Menu");
        System.out.println("1. Show all news");
        System.out.println("2. Show pinned news");
        System.out.println("3. Show news by topic");
        System.out.println("4. Show news details");
        System.out.println("5. Create news");
        System.out.println("6. Update news");
        System.out.println("7. Delete news");
        System.out.println("8. Pin news");
        System.out.println("9. Unpin news");
        System.out.println("10. Add comment");
        System.out.println("11. Generate top cited researcher news");
        System.out.println("0. Back");
    }

    public int readMenuChoice() {
        return ConsoleUtils.readInt("Choose option: ");
    }

    public NewsInput readNewsInput() {
        String title = ConsoleUtils.readLine("Title: ");
        String topic = ConsoleUtils.readLine("Topic: ");
        String body = ConsoleUtils.readLine("Body: ");
        return new NewsInput(title, topic, body);
    }

    public NewsInput readNewsUpdateInput() {
        System.out.println("Leave a field empty if you do not want to change it.");
        String title = ConsoleUtils.readLine("New title: ");
        String topic = ConsoleUtils.readLine("New topic: ");
        String body = ConsoleUtils.readLine("New body: ");
        return new NewsInput(title, topic, body);
    }

    public int readNewsId() {
        return ConsoleUtils.readInt("News id: ");
    }

    public String readTopic() {
        return ConsoleUtils.readLine("Topic: ");
    }

    public String readCommentText() {
        return ConsoleUtils.readLine("Comment: ");
    }

    public void showNewsList(List<News> newsList) {
        List<List<String>> rows = new ArrayList<>();

        for (News news : newsList) {
            rows.add(Arrays.asList(
                    String.valueOf(news.getId()),
                    news.isPinned() ? "yes" : "no",
                    valueOrDash(news.getTopic()),
                    valueOrDash(news.getTitle()),
                    news.getCreatedAt() == null ? "-" : news.getCreatedAt().toString()
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Pinned", "Topic", "Title", "Created At"),
                rows
        );
    }

    public void showNewsDetails(News news) {
        if (news == null) {
            showError("News was not found.");
            return;
        }

        ConsoleUtils.printHeader("News Details");
        System.out.println("ID: " + news.getId());
        System.out.println("Pinned: " + (news.isPinned() ? "yes" : "no"));
        System.out.println("Topic: " + valueOrDash(news.getTopic()));
        System.out.println("Title: " + valueOrDash(news.getTitle()));
        System.out.println("Author: " + getAuthorName(news));
        System.out.println("Created at: " + (news.getCreatedAt() == null ? "-" : news.getCreatedAt()));
        System.out.println();
        System.out.println(valueOrDash(news.getBody()));
        System.out.println();
        showComments(news.getComments());
    }

    public void showComments(List<Comment> comments) {
        List<List<String>> rows = new ArrayList<>();

        if (comments != null) {
            for (Comment comment : comments) {
                rows.add(Arrays.asList(
                        getCommentAuthor(comment),
                        comment.getSentAt() == null ? "-" : comment.getSentAt().toString(),
                        valueOrDash(comment.getText())
                ));
            }
        }

        ConsoleUtils.printTable(
                Arrays.asList("Author", "Sent At", "Text"),
                rows
        );
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    private String getAuthorName(News news) {
        if (news.getAuthor() == null) {
            return "-";
        }
        return valueOrDash(news.getAuthor().getLogin());
    }

    private String getCommentAuthor(Comment comment) {
        if (comment == null || comment.getUser() == null) {
            return "-";
        }
        return valueOrDash(comment.getUser().getLogin());
    }

    private String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    public static class NewsInput {
        private final String title;
        private final String topic;
        private final String body;

        public NewsInput(String title, String topic, String body) {
            this.title = title;
            this.topic = topic;
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public String getTopic() {
            return topic;
        }

        public String getBody() {
            return body;
        }
    }
}
