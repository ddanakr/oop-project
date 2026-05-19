package universitysystem.models.research.citations;

import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.Researcher;

import java.util.ArrayList;
import java.util.List;

public class PlainTextCitationStrategy implements CitationStrategy {
    @Override
    public String format(ResearchPaper paper) {
        return String.join(", ", getAuthorNames(paper)) + ". "
                + paper.getTitle() + ". "
                + paper.getJournal() + ". "
                + (paper.getDate() == null ? "" : paper.getDate().toString())
                + ". Pages p. DOI: "
                + paper.getTitle();
    }

    private List<String> getAuthorNames(ResearchPaper paper) {
        List<String> names = new ArrayList<>();
        if (paper.getAuthors() != null) {
            for (Researcher author : paper.getAuthors()) {
                names.add(author.toString());
            }
        }
        return names;
    }
}
