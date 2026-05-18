package universitysystem.models.research;

import universitysystem.models.users.User;

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
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public ResearchDecorator(User user, List<ResearchPaper> papers, List<ResearchProject> projects) {
        this.user = user;
        this.papers = papers != null ? papers : new ArrayList<>();
        this.projects = projects != null ? projects : new ArrayList<>();
    }

    /**
     * 
     */
    private User user;

    /**
     * 
     */
    private List<ResearchPaper> papers;

    /**
     * 
     */
    private List<ResearchProject> projects;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers != null ? papers : new ArrayList<>();
    }

    public List<ResearchProject> getProjects() {
        return projects;
    }

    public void setProjects(List<ResearchProject> projects) {
        this.projects = projects != null ? projects : new ArrayList<>();
    }






    /**
     * 
     */
    public void publishPaper(ResearchPaper paper) {
        if (this.papers == null) {
            this.papers = new ArrayList<>();
        }
        if (paper != null) {
            this.papers.add(paper);
        }
    }

    /**
     * 
     */
    public int getHIndex() {
        if (this.papers == null) {
            return 0;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        sorted.sort((a, b) -> Integer.compare(b.getCitations(), a.getCitations()));
        int h = 0;
        for (int i = 0; i < sorted.size(); i++) {
            if (sorted.get(i).getCitations() >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }

    /**
     * 
     */
    public void printPapers(Comparator<ResearchPaper> comp) {
        if (this.papers == null) {
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.papers);
        if (comp != null) {
            sorted.sort(comp);
        }
        for (ResearchPaper paper : sorted) {
            System.out.println(paper);
        }
    }

    /**
     * 
     */
    public void joinProject(ResearchProject project) {
        if (project != null) {
            project.addParticipant(this);
        }
    }

}
