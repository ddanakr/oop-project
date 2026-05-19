package universitysystem.models.research;

import universitysystem.exceptions.NotAResearcherException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ResearchProject implements Serializable {
    private int id;
    private String name;
    private String topic;
    private List<Researcher> participants;
    private List<ResearchPaper> papers;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public void addParticipant(Researcher participant) {
        if (participant == null) {
            throw new NotAResearcherException("Participant must be a researcher.");
        }
        if (!participants.contains(participant)) {
            participants.add(participant);
        }
    }

    public void removeParticipant(Researcher participant) {
        participants.remove(participant);
    }

    public void addPaper(ResearchPaper paper) {
        if (paper != null && !papers.contains(paper)) {
            papers.add(paper);
        }
    }

    @Override
    public String toString() {
        return "ResearchProject{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topic='" + topic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchProject)) return false;
        ResearchProject that = (ResearchProject) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
