package universitysystem.controllers;

import universitysystem.models.news.News;
import universitysystem.models.users.User;
import universitysystem.services.NewsService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.NewsView;

public class NewsController {
    private final NewsService newsService;
    private final NewsView newsView;
    private User currentUser;

    public NewsController(User currentUser) {
        this(new NewsService(), new NewsView(), currentUser);
    }

    public NewsController(NewsService newsService, NewsView newsView, User currentUser) {
        this.newsService = newsService;
        this.newsView = newsView;
        this.currentUser = currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run() {
        boolean running = true;

        while (running) {
            newsView.showMenu();
            int choice = newsView.readMenuChoice();

            try {
                running = handleChoice(choice);
            } catch (IllegalArgumentException e) {
                newsView.showError(e.getMessage());
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                showAllNews();
                return true;
            case 2:
                showPinnedNews();
                return true;
            case 3:
                showNewsByTopic();
                return true;
            case 4:
                showNewsDetails();
                return true;
            case 5:
                addComment();
                return true;
            case 0:
                newsView.showMessage("Back to previous menu.");
                return false;
            default:
                newsView.showError("Unknown option.");
                return true;
        }
    }

    private void showAllNews() {
        newsView.showNewsList(newsService.getAllNews());
    }

    private void showPinnedNews() {
        newsView.showNewsList(newsService.getPinnedNews());
    }

    private void showNewsByTopic() {
        String topic = newsView.readTopic();
        newsView.showNewsList(newsService.getNewsByTopic(topic));
    }

    private void showNewsDetails() {
        int id = newsView.readNewsId();
        News news = newsService.getNewsById(id);
        newsView.showNewsDetails(news);
    }

    private void addComment() {
        int id = newsView.readNewsId();
        String text = newsView.readCommentText();

        if (newsService.addComment(id, currentUser, text) == null) {
            newsView.showError("News was not found.");
        } else {
            newsView.showMessage("Comment added.");
        }
    }
}
