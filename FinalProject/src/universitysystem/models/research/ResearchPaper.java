package universitysystem.models.research;

import universitysystem.enums.CitationFormat;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class ResearchPaper implements Serializable {

    /**
     * Default constructor
     */
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

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private List<Researcher> authors;

    /**
     * 
     */
    private int citations;

    /**
     * 
     */
    private int pages;

    /**
     * 
     */
    private String journal;

    /**
     * 
     */
    private Date date;

    /**
     * 
     */
    private List<ResearchPaper> references;

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

    private List<String> getAuthorNames() {
        List<String> names = new ArrayList<>();
        if (this.authors != null) {
            for (Researcher author : this.authors) {
                names.add(author.toString());
            }
        }
        return names;
    }







    /**
     * 
     */
    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.BIBTEX) {
            return "@article{" + this.title.replaceAll("\\s+", "") + ",\n" +
                    "  title={" + title + "},\n" +
                    "  author={" + String.join(", ", getAuthorNames()) + "},\n" +
                    "  journal={" + journal + "},\n" +
                    "  year={" + (date != null ? String.valueOf(date.getYear() + 1900) : "") + "},\n" +
                    "  pages={" + pages + "}" +
                    "\n}";
        }
        return String.join(", ", getAuthorNames()) + ". " + title + ". " + journal + ". " +
                (date != null ? date.toString() : "") + ". Pages p. DOI: " + title;
    }

    /**
     * 
     */
    public void addAuthor(Researcher author) {
        if (this.authors == null) {
            this.authors = new ArrayList<>();
        }
        if (author != null) {
            this.authors.add(author);
        }
    }

    /**
     * 
     */
    public void addReference(ResearchPaper paper) {
        if (this.references == null) {
            this.references = new ArrayList<>();
        }
        if (paper != null) {
            this.references.add(paper);
        }
    }

}
