package universitysystem.models.users;

import src.universitysystem.models.research.Researcher;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class GraduateStudent extends Student implements Researcher {

    /**
     * Default constructor
     */
    public GraduateStudent() {
    }

    /**
     * 
     */
    private Researcher supervisor;

    /**
     * 
     */
    private List<ResearchPaper> diplomaProject;

    /**
     * 
     */
    private String researchTopic;

    /**
     * 
     */
    private List<ResearchPaper> publicationList;

    /**
     * 
     */
    public void setSupervisor(supervisor : Researcher) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void publishPaper(paper : ResearchPaper) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void getPublications() : List<ResearchPaper>() {
        // TODO implement here
    }

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