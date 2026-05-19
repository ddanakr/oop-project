package universitysystem.utils;

import universitysystem.models.news.News;

import java.util.Comparator;

public final class NewsComparators {
    public static final Comparator<News> PINNED_FIRST_THEN_NEWEST =
            Comparator.comparing(News::isPinned).reversed()
                    .thenComparing(News::getId).reversed();

    private NewsComparators() {
    }
}
