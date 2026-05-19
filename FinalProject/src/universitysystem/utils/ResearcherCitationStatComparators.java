package universitysystem.utils;

import universitysystem.services.ResearchService;

import java.util.Comparator;

public final class ResearcherCitationStatComparators {
    public static final Comparator<ResearchService.ResearcherCitationStat> BY_CITATIONS =
            (first, second) -> Integer.compare(second.getCitations(), first.getCitations());

    private ResearcherCitationStatComparators() {
    }
}
