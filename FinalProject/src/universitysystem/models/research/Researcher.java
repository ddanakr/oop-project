package universitysystem.models.research;

import java.util.Comparator;

public interface Researcher {
    void publishPaper(ResearchPaper paper);

    int getHIndex();

    void printPapers(Comparator<ResearchPaper> comp);

    void joinProject(ResearchProject project);
}
