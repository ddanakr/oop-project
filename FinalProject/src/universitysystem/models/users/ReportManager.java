package universitysystem.models.users;

import universitysystem.models.news.News;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface ReportManager {

    /**
     * 
     */
    void createPerformanceReport();

    /**
     * 
     */
    void manageNews(News news);

    /**
     * 
     */
    List<News> generateTopResearcherNews();

}
