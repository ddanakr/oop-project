package universitysystem.models.research.citations;

import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.Researcher;

import java.util.ArrayList;
import java.util.List;

public class BibTexCitationStrategy implements CitationStrategy {
    @Override
    public String format(ResearchPaper paper) {
        return "@article{" + buildKey(paper) + ",\n"
                + "  title={" + paper.getTitle() + "},\n"
                + "  author={" + String.join(", ", getAuthorNames(paper)) + "},\n"
                + "  journal={" + paper.getJournal() + "},\n"
                + "  year={" + getYear(paper) + "},\n"
                + "  pages={" + paper.getPages() + "}\n"
                + "}";
    }

    private String buildKey(ResearchPaper paper) {
        if (paper.getTitle() == null) {
            return "untitled";
        }
        return paper.getTitle().replaceAll("\\s+", "");
    }

    private String getYear(ResearchPaper paper) {
        if (paper.getDate() == null) {
            return "";
        }
        return String.valueOf(paper.getDate().getYear() + 1900);
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
