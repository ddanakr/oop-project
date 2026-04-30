package universitysystem.models.users;

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