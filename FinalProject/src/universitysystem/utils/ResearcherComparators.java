package universitysystem.utils;

import universitysystem.models.research.Researcher;

import java.util.Comparator;

public final class ResearcherComparators {
    public static final Comparator<Researcher> BY_H_INDEX =
            (first, second) -> Integer.compare(second.getHIndex(), first.getHIndex());

    private ResearcherComparators() {
    }
}
