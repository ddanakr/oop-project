package universitysystem.models.users;

import universitysystem.models.news.News;

import java.util.List;

public interface ReportManager {
    void createPerformanceReport();

    void manageNews(News news);

    List<News> generateTopResearcherNews();
}
