package universitysystem.models.research;

import java.io.Serializable;
import java.util.Comparator;

public interface Researcher extends Serializable {
    void publishPaper(ResearchPaper paper);

    int getHIndex();

    void printPapers(Comparator<ResearchPaper> comp);

    void joinProject(ResearchProject project);
}
