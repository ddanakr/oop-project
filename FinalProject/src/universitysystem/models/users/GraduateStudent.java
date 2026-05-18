package universitysystem.models.users;

import universitysystem.enums.Degree;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.research.Researcher;

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
        this.diplomaProject = new ArrayList<>();
        this.publicationList = new ArrayList<>();
    }

    /**
     * Full constructor
     */
    public GraduateStudent(int year, Degree degree, String speciality, double gpa, int credits,
                           Researcher supervisor, List<ResearchPaper> diplomaProject,
                           String researchTopic, List<ResearchPaper> publicationList) {
        super(year, degree, speciality, gpa, credits);
        this.supervisor = supervisor;
        this.diplomaProject = diplomaProject != null ? diplomaProject : new ArrayList<>();
        this.researchTopic = researchTopic;
        this.publicationList = publicationList != null ? publicationList : new ArrayList<>();
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

    public Researcher getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(Researcher supervisor) throws universitysystem.exceptions.LowHIndexException {
        if (supervisor != null && supervisor.getHIndex() < 3) {
            throw new universitysystem.exceptions.LowHIndexException("Supervisor does not have enough H-index.");
        }
        this.supervisor = supervisor;
    }

    public List<ResearchPaper> getDiplomaProject() {
        return diplomaProject;
    }

    public void setDiplomaProject(List<ResearchPaper> diplomaProject) {
        this.diplomaProject = diplomaProject != null ? diplomaProject : new ArrayList<>();
    }

    public String getResearchTopic() {
        return researchTopic;
    }

    public void setResearchTopic(String researchTopic) {
        this.researchTopic = researchTopic;
    }

    public List<ResearchPaper> getPublicationList() {
        return publicationList;
    }

    public void setPublicationList(List<ResearchPaper> publicationList) {
        this.publicationList = publicationList != null ? publicationList : new ArrayList<>();
    }

    /**
     * 
     */
    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            if (this.publicationList == null) {
                this.publicationList = new ArrayList<>();
            }
            this.publicationList.add(paper);
        }
    }

    /**
     * 
     */
    public List<ResearchPaper> getPublications() {
        return publicationList != null ? publicationList : new ArrayList<>();
    }

    /**
     * 
     */
    public int getHIndex() {
        if (this.publicationList == null) {
            return 0;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.publicationList);
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
        if (this.publicationList == null) {
            return;
        }
        List<ResearchPaper> sorted = new ArrayList<>(this.publicationList);
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

    @Override
    public String toString() {
        return "GraduateStudent{" +
                "researchTopic='" + researchTopic + '\'' +
                ", supervisor=" + supervisor +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GraduateStudent)) return false;
        GraduateStudent that = (GraduateStudent) o;
        return Objects.equals(getLogin(), that.getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}
