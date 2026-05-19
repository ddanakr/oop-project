package universitysystem.utils;

import universitysystem.models.news.News;

import java.util.Comparator;

public final class NewsComparators {
    public static final Comparator<News> PINNED_FIRST_THEN_NEWEST =
    		Comparator
            .comparing(News::isPinned, Comparator.reverseOrder())
            .thenComparing(Comparator.comparing(News::getId).reversed());

    private NewsComparators() {
    }
}
