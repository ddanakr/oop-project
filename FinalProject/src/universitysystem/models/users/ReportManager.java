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
    public void createPerformanceReport() : void();

    /**
     * 
     */
    public void manageNews(news : News) : void();

    /**
     * 
     */
    public void generateTopResearcherNews() : List<News>();

}