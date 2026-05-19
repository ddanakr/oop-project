package universitysystem.models.research.citations;

import universitysystem.models.research.CitationFormat;

public class CitationStrategyFactory {
    private CitationStrategyFactory() {
    }

    public static CitationStrategy getStrategy(CitationFormat format) {
        if (format == CitationFormat.BIBTEX) {
            return new BibTexCitationStrategy();
        }
        return new PlainTextCitationStrategy();
    }
}
