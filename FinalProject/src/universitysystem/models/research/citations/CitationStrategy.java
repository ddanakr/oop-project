package universitysystem.models.research.citations;

import universitysystem.models.research.ResearchPaper;

public interface CitationStrategy {
    String format(ResearchPaper paper);
}
