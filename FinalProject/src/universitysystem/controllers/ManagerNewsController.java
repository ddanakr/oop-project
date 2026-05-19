package universitysystem.controllers;

import universitysystem.models.news.News;
import universitysystem.models.users.Manager;
import universitysystem.services.ManagerService;
import universitysystem.services.NewsService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.NewsView;

public class ManagerNewsController {
    private final NewsService newsService;
    private final ManagerService managerService;
    private final NewsView newsView;
    private Manager currentManager;

    public ManagerNewsController(Manager currentManager) {
        this(new NewsService(), new ManagerService(), new NewsView(), currentManager);
    }

    public ManagerNewsController(NewsService newsService, ManagerService managerService, NewsView newsView, Manager currentManager) {
        this.newsService = newsService;
        this.managerService = managerService;
        this.newsView = newsView;
        this.currentManager = currentManager;
    }

    public void setCurrentManager(Manager currentManager) {
        this.currentManager = currentManager;
    }

    public void run() {
        boolean running = true;

        while (running) {
            newsView.showManagerMenu();
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
                createNews();
                return true;
            case 6:
                updateNews();
                return true;
            case 7:
                deleteNews();
                return true;
            case 8:
                pinNews();
                return true;
            case 9:
                unpinNews();
                return true;
            case 10:
                addComment();
                return true;
            case 11:
                generateTopCitedResearcherNews();
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
        newsView.showNewsDetails(newsService.getNewsById(id));
    }

    private void createNews() {
        NewsView.NewsInput input = newsView.readNewsInput();
        News news = newsService.createNews(
                input.getTitle(),
                input.getTopic(),
                input.getBody(),
                currentManager
        );
        newsView.showMessage("News created with id " + news.getId() + ".");
    }

    private void updateNews() {
        int id = newsView.readNewsId();
        NewsView.NewsInput input = newsView.readNewsUpdateInput();
        boolean updated = newsService.updateNews(
                id,
                input.getTitle(),
                input.getTopic(),
                input.getBody()
        );

        if (updated) {
            newsView.showMessage("News updated.");
        } else {
            newsView.showError("News was not found.");
        }
    }

    private void deleteNews() {
        int id = newsView.readNewsId();
        if (newsService.deleteNews(id)) {
            newsView.showMessage("News deleted.");
        } else {
            newsView.showError("News was not found.");
        }
    }

    private void pinNews() {
        int id = newsView.readNewsId();
        if (newsService.pinNews(id)) {
            newsView.showMessage("News pinned.");
        } else {
            newsView.showError("News was not found.");
        }
    }

    private void unpinNews() {
        int id = newsView.readNewsId();
        if (newsService.unpinNews(id)) {
            newsView.showMessage("News unpinned.");
        } else {
            newsView.showError("News was not found.");
        }
    }

    private void addComment() {
        int id = newsView.readNewsId();
        String text = newsView.readCommentText();

        if (newsService.addComment(id, currentManager, text) == null) {
            newsView.showError("News was not found.");
        } else {
            newsView.showMessage("Comment added.");
        }
    }

    private void generateTopCitedResearcherNews() {
        if (managerService.generateTopCitedResearcherNews()) {
            newsView.showMessage("Top cited researcher news generated.");
        } else {
            newsView.showError("No research citations found.");
        }
    }
}
