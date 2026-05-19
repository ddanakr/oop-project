package universitysystem.utils;

import universitysystem.models.research.ResearchPaper;
import universitysystem.enums.ResearchPaperSortType;

import java.util.Comparator;

public final class ResearchPaperComparators {
    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getDate, Comparator.nullsLast(Comparator.reverseOrder()));

    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparing(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_PAGES =
            Comparator.comparing(ResearchPaper::getPages).reversed();

    private ResearchPaperComparators() {
    }

    public static Comparator<ResearchPaper> bySortType(ResearchPaperSortType sortType) {
        if (sortType == ResearchPaperSortType.CITATIONS) {
            return BY_CITATIONS;
        }
        if (sortType == ResearchPaperSortType.PAGES) {
            return BY_PAGES;
        }
        return BY_DATE;
    }
}
