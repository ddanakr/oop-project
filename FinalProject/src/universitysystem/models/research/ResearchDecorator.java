package universitysystem.models.research;

import src.universitysystem.models.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public abstract class ResearchDecorator implements Researcher {

    /**
     * Default constructor
     */
    public ResearchDecorator() {
    }

    /**
     * 
     */
    private User user;

    /**
     * 
     */
    private  List<ResearchPaper> papers;

    /**
     * 
     */
    private List<ResearchProject> projects ;






    /**
     * 
     */
    public void publishPaper(paper : ResearchPaper) : void() {
        // TODO implement Researcher.publishPaper(paper : ResearchPaper) : void() here
    }

    /**
     * 
     */
    public void getHIndex() : int() {
        // TODO implement Researcher.getHIndex() : int() here
    }

    /**
     * 
     */
    public void printPapers(comp : Comparator<ResearchPaper>) : void() {
        // TODO implement Researcher.printPapers(comp : Comparator<ResearchPaper>) : void() here
    }

    /**
     * 
     */
    public void joinProject(project : ResearchProject) : void() {
        // TODO implement Researcher.joinProject(project : ResearchProject) : void() here
    }

}