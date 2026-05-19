package universitysystem.models.news;

import universitysystem.models.research.ResearchPaper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Journal {
    private String name;
    private List<Subscriber> subscribers;
    private List<ResearchPaper> publishedPapers;

    public Journal() {
        this.subscribers = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public Journal(String name) {
        this.name = name;
        this.subscribers = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscriber> subscribers) {
        this.subscribers = subscribers != null ? subscribers : new ArrayList<>();
    }

    public List<ResearchPaper> getPublishedPapers() {
        return publishedPapers;
    }

    public void setPublishedPapers(List<ResearchPaper> publishedPapers) {
        this.publishedPapers = publishedPapers != null ? publishedPapers : new ArrayList<>();
    }

    public void subscribe(Subscriber subscriber) {
        if (subscriber != null && !subscribers.contains(subscriber)) {
            subscribers.add(subscriber);
        }
    }

    public void unsubscribe(Subscriber subscriber) {
        subscribers.remove(subscriber);
    }

    public void publishNews(News news) {
        notifySubscribers("New news available in " + name + ": " + (news == null ? "" : news.getTitle()));
    }

    public void publishPaper(ResearchPaper paper) {
        if (paper != null) {
            publishedPapers.add(paper);
            notifySubscribers("New paper published in " + name + ": " + paper.getTitle());
        }
    }

    private void notifySubscribers(String notification) {
        for (Subscriber subscriber : subscribers) {
            subscriber.update(notification);
        }
    }

    @Override
    public String toString() {
        return "Journal{" +
                "name='" + name + '\'' +
                ", subscribers=" + subscribers.size() +
                ", publishedPapers=" + publishedPapers.size() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Journal)) return false;
        Journal journal = (Journal) o;
        return Objects.equals(name, journal.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
