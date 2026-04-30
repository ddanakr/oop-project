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
    void publishPaper(ResearchPaper paper);

    /**
     * 
     */
    int getHIndex();

    /**
     * 
     */
    void printPapers(Comparator<ResearchPaper> comp);

    /**
     * 
     */
    void joinProject(ResearchProject project);

}