package universitysystem.models.research;

import universitysystem.enums.CitationFormat;
import universitysystem.models.research.citations.CitationStrategyFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ResearchPaper implements Serializable {
    private int id;
    private String title;
    private List<Researcher> authors;
    private int citations;
    private int pages;
    private String journal;
    private Date date;
    private List<ResearchPaper> references;

    public ResearchPaper() {
        this.authors = new ArrayList<>();
        this.references = new ArrayList<>();
    }

    public ResearchPaper(String title, List<Researcher> authors, int citations, int pages, String journal, Date date, List<ResearchPaper> references) {
        this.title = title;
        this.authors = authors != null ? authors : new ArrayList<>();
        this.citations = citations;
        this.pages = pages;
        this.journal = journal;
        this.date = date;
        this.references = references != null ? references : new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Researcher> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Researcher> authors) {
        this.authors = authors != null ? authors : new ArrayList<>();
    }

    public int getCitations() {
        return citations;
    }

    public void setCitations(int citations) {
        this.citations = citations;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ResearchPaper> getReferences() {
        return references;
    }

    public void setReferences(List<ResearchPaper> references) {
        this.references = references != null ? references : new ArrayList<>();
    }

    public String getCitation(CitationFormat format) {
        return CitationStrategyFactory.getStrategy(format).format(this);
    }

    public void addAuthor(Researcher author) {
        if (author != null && !authors.contains(author)) {
            authors.add(author);
        }
    }

    public void addReference(ResearchPaper paper) {
        if (paper != null && !references.contains(paper)) {
            references.add(paper);
        }
    }

    @Override
    public String toString() {
        return "ResearchPaper{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", journal='" + journal + '\'' +
                ", citations=" + citations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResearchPaper)) return false;
        ResearchPaper that = (ResearchPaper) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
