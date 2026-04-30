package universitysystem.models.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface Researcher {




    /**
     * 
     */
    public void publishPaper(paper : ResearchPaper) : void();

    /**
     * 
     */
    public void getHIndex() : int();

    /**
     * 
     */
    public void printPapers(comp : Comparator<ResearchPaper>) : void();

    /**
     * 
     */
    public void joinProject(project : ResearchProject) : void();

}