package universitySystem.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchPaper {

    /**
     * Default constructor
     */
    public ResearchPaper() {
    }

    /**
     * 
     */
    private String title ;

    /**
     * 
     */
    private List<Researcher> authors ;

    /**
     * 
     */
    private int citations ;

    /**
     * 
     */
    private int pages ;

    /**
     * 
     */
    private String journal ;

    /**
     * 
     */
    private Date date ;

    /**
     * 
     */
    private List<ResearchPaper> references ;





    /**
     * 
     */
    public void getCitation(format : CitationFormat) : String() {
        // TODO implement here
    }

    /**
     * 
     */
    public void addAuthor(author: Researcher): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void addReference(paper: ResearchPaper): void() {
        // TODO implement here
    }

    /**
     * 
     */
    public enum CitationFormat {
        PLAINTEXT,
        BIBTEX
    }

}