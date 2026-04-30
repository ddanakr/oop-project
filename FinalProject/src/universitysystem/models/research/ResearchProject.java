package universitysystem.models.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchProject {

    /**
     * Default constructor
     */
    public ResearchProject() {
        this.participants = new ArrayList<>();
        this.papers = new ArrayList<>();
    }

    public ResearchProject(String name, String topic, List<Researcher> participants, List<ResearchPaper> papers) {
        this.name = name;
        this.topic = topic;
        this.participants = participants != null ? participants : new ArrayList<>();
        this.papers = papers != null ? papers : new ArrayList<>();
    }

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String topic;

    /**
     * 
     */
    private List<Researcher> participants;

    /**
     * 
     */
    private List<ResearchPaper> papers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public List<Researcher> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Researcher> participants) {
        this.participants = participants != null ? participants : new ArrayList<>();
    }

    public List<ResearchPaper> getPapers() {
        return papers;
    }

    public void setPapers(List<ResearchPaper> papers) {
        this.papers = papers != null ? papers : new ArrayList<>();
    }





    /**
     * 
     */
    public void addParticipant(Researcher participant) {
        if (participant == null) {
            throw new universitysystem.exceptions.NotAResearcherException("Participant must be a researcher.");
        }
        if (this.participants == null) {
            this.participants = new ArrayList<>();
        }
        this.participants.add(participant);
    }

    /**
     * 
     */
    public void removeParticipant(Researcher participant) {
        if (this.participants != null) {
            this.participants.remove(participant);
        }
    }

    /**
     * 
     */
    public void addPaper(ResearchPaper paper) {
        if (this.papers == null) {
            this.papers = new ArrayList<>();
        }
        if (paper != null) {
            this.papers.add(paper);
        }
    }

}