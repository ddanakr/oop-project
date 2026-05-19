package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.news.Journal;
import universitysystem.enums.CitationFormat;
import universitysystem.models.research.ResearchDecorator;
import universitysystem.models.research.ResearchPaper;
import universitysystem.enums.ResearchPaperSortType;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.research.Researcher;
import universitysystem.models.research.UniversityResearcher;
import universitysystem.models.research.citations.CitationStrategy;
import universitysystem.models.research.citations.CitationStrategyFactory;
import universitysystem.models.users.GraduateStudent;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ResearchService {
    private final Database database;
    private final NewsService newsService;

    public ResearchService() {
        this.database = Database.getInstance();
        this.newsService = new NewsService();
    }

    public ResearchPaper createPaper(String title, String journal, int pages, int citations, Researcher author) {
        validateRequired(title, "Title");
        validateRequired(journal, "Journal");
        validateResearcher(author);
        validatePositive(pages, "Pages");
        validateNotNegative(citations, "Citations");

        List<Researcher> authors = new ArrayList<>();
        authors.add(author);

        ResearchPaper paper = new ResearchPaper(
                title,
                authors,
                citations,
                pages,
                journal,
                new Date(),
                new ArrayList<>()
        );
        paper.setId(generateNextPaperId());

        database.getResearchPapers().add(paper);
        author.publishPaper(paper);
        createResearchPaperAnnouncement(paper, author);
        publishPaperInJournal(paper);
        database.save();
        return paper;
    }

    public ResearchProject createProject(String name, String topic, Researcher creator) {
        validateRequired(name, "Project name");
        validateRequired(topic, "Project topic");
        validateResearcher(creator);

        ResearchProject project = new ResearchProject();
        project.setId(generateNextProjectId());
        project.setName(name);
        project.setTopic(topic);
        project.addParticipant(creator);

        database.getResearchProjects().add(project);
        database.save();
        return project;
    }

    public boolean joinProject(int projectId, Researcher researcher) {
        validateResearcher(researcher);

        ResearchProject project = getProjectById(projectId);
        if (project == null) {
            return false;
        }

        if (!project.getParticipants().contains(researcher)) {
            project.addParticipant(researcher);
            database.save();
        }
        return true;
    }

    public boolean addPaperToProject(int projectId, int paperId) {
        ResearchProject project = getProjectById(projectId);
        ResearchPaper paper = getPaperById(paperId);

        if (project == null || paper == null) {
            return false;
        }

        if (!project.getPapers().contains(paper)) {
            project.addPaper(paper);
            database.save();
        }
        return true;
    }

    public List<ResearchPaper> getAllPapers() {
        return getAllPapers(ResearchPaperSortType.DATE);
    }

    public List<ResearchPaper> getAllPapers(ResearchPaperSortType sortType) {
        List<ResearchPaper> papers = new ArrayList<>(database.getResearchPapers());
        papers.sort(getPaperComparator(sortType));
        return papers;
    }

    public List<ResearchProject> getAllProjects() {
        return new ArrayList<>(database.getResearchProjects());
    }

    public List<Researcher> getAllResearchers() {
        List<Researcher> researchers = new ArrayList<>();
        for (User user : database.getUsers()) {
            if (user instanceof Researcher) {
                researchers.add((Researcher) user);
            }
        }
        if (database.getResearchers() != null) {
            for (Researcher researcher : database.getResearchers()) {
                if (researcher != null && !researchers.contains(researcher)) {
                    researchers.add(researcher);
                }
            }
        }
        return researchers;
    }

    public Researcher getResearcherForUser(User user) {
        if (user == null) {
            return null;
        }
        if (user instanceof Researcher) {
            return (Researcher) user;
        }
        if (database.getResearchers() == null) {
            return null;
        }
        for (Researcher researcher : database.getResearchers()) {
            if (researcher instanceof ResearchDecorator) {
                User decoratedUser = ((ResearchDecorator) researcher).getUser();
                if (user.equals(decoratedUser)) {
                    return researcher;
                }
            }
        }
        return null;
    }

    public boolean makeResearcher(User user) {
        if (user == null || getResearcherForUser(user) != null) {
            return false;
        }
        database.getResearchers().add(new UniversityResearcher(user));
        database.save();
        return true;
    }

    public List<Researcher> getTopResearchers() {
        List<Researcher> researchers = getAllResearchers();
        researchers.sort((first, second) -> Integer.compare(second.getHIndex(), first.getHIndex()));
        return researchers;
    }

    public List<ResearchPaper> getPapersByResearcher(Researcher researcher) {
        return getPapersByResearcher(researcher, ResearchPaperSortType.DATE);
    }

    public List<ResearchPaper> getPapersByResearcher(Researcher researcher, ResearchPaperSortType sortType) {
        validateResearcher(researcher);

        List<ResearchPaper> papers;
        if (researcher instanceof Teacher) {
            papers = new ArrayList<>(((Teacher) researcher).getPapers());
        } else if (researcher instanceof GraduateStudent) {
            papers = new ArrayList<>(((GraduateStudent) researcher).getPublications());
        } else if (researcher instanceof ResearchDecorator) {
            ResearchDecorator decorator = (ResearchDecorator) researcher;
            if (decorator.getWrappedResearcher() != null) {
                return getPapersByResearcher(decorator.getWrappedResearcher(), sortType);
            }
            papers = new ArrayList<>(decorator.getPapers());
        } else {
            papers = new ArrayList<>();
        }
        papers.sort(getPaperComparator(sortType));
        return papers;
    }

    public List<ResearchPaper> getAllResearchersPapers(ResearchPaperSortType sortType) {
        List<ResearchPaper> papers = new ArrayList<>();
        for (Researcher researcher : getAllResearchers()) {
            papers.addAll(getPapersByResearcher(researcher, sortType));
        }
        return papers;
    }

    public List<ResearcherCitationStat> getTopCitedResearchers() {
        List<ResearcherCitationStat> stats = getResearcherCitationStats(0);
        stats.sort((first, second) -> Integer.compare(second.getCitations(), first.getCitations()));
        return stats;
    }

    public List<ResearcherCitationStat> getTopCitedResearchersByYear(int year) {
        List<ResearcherCitationStat> stats = getResearcherCitationStats(year);
        stats.sort((first, second) -> Integer.compare(second.getCitations(), first.getCitations()));
        return stats;
    }

    public boolean generateTopCitedResearcherNews() {
        List<ResearcherCitationStat> stats = getTopCitedResearchers();
        if (stats.isEmpty()) {
            return false;
        }

        ResearcherCitationStat top = stats.get(0);
        newsService.createNews(
                "Top cited researcher: " + top.getResearcher(),
                "Research",
                "Top cited researcher has " + top.getCitations() + " citations.",
                getUserFromResearcher(top.getResearcher())
        );
        return true;
    }

    public int getHIndex(Researcher researcher) {
        validateResearcher(researcher);
        return researcher.getHIndex();
    }

    public String getCitation(int paperId, CitationFormat format) {
        ResearchPaper paper = getPaperById(paperId);
        if (paper == null) {
            return null;
        }

        CitationStrategy strategy = CitationStrategyFactory.getStrategy(format);
        return strategy.format(paper);
    }

    public ResearchPaper getPaperById(int paperId) {
        for (ResearchPaper paper : database.getResearchPapers()) {
            if (paper.getId() == paperId) {
                return paper;
            }
        }
        return null;
    }

    public ResearchProject getProjectById(int projectId) {
        for (ResearchProject project : database.getResearchProjects()) {
            if (project.getId() == projectId) {
                return project;
            }
        }
        return null;
    }

    private int generateNextPaperId() {
        int maxId = 0;
        for (ResearchPaper paper : database.getResearchPapers()) {
            if (paper.getId() > maxId) {
                maxId = paper.getId();
            }
        }
        return maxId + 1;
    }

    private int generateNextProjectId() {
        int maxId = 0;
        for (ResearchProject project : database.getResearchProjects()) {
            if (project.getId() > maxId) {
                maxId = project.getId();
            }
        }
        return maxId + 1;
    }

    private List<ResearcherCitationStat> getResearcherCitationStats(int year) {
        List<ResearcherCitationStat> stats = new ArrayList<>();

        for (ResearchPaper paper : database.getResearchPapers()) {
            if (!matchesYear(paper, year) || paper.getAuthors() == null) {
                continue;
            }
            for (Researcher researcher : paper.getAuthors()) {
                ResearcherCitationStat stat = findCitationStat(stats, researcher);
                if (stat == null) {
                    stats.add(new ResearcherCitationStat(researcher, paper.getCitations()));
                } else {
                    stat.addCitations(paper.getCitations());
                }
            }
        }
        return stats;
    }

    private ResearcherCitationStat findCitationStat(List<ResearcherCitationStat> stats, Researcher researcher) {
        for (ResearcherCitationStat stat : stats) {
            if (stat.getResearcher().equals(researcher)) {
                return stat;
            }
        }
        return null;
    }

    private boolean matchesYear(ResearchPaper paper, int year) {
        return year <= 0 || paper.getDate() != null && paper.getDate().getYear() + 1900 == year;
    }

    private Comparator<ResearchPaper> getPaperComparator(ResearchPaperSortType sortType) {
        if (sortType == ResearchPaperSortType.CITATIONS) {
            return Comparator.comparing(ResearchPaper::getCitations).reversed();
        }
        if (sortType == ResearchPaperSortType.PAGES) {
            return Comparator.comparing(ResearchPaper::getPages).reversed();
        }
        return Comparator.comparing(ResearchPaper::getDate, Comparator.nullsLast(Comparator.reverseOrder()));
    }

    private void createResearchPaperAnnouncement(ResearchPaper paper, Researcher author) {
        newsService.createNews(
                "Research paper published: " + paper.getTitle(),
                "Research",
                author + " published a paper in " + paper.getJournal() + ".",
                getUserFromResearcher(author)
        );
    }

    private void publishPaperInJournal(ResearchPaper paper) {
        Journal journal = findJournalByName(paper.getJournal());
        if (journal == null) {
            journal = new Journal(paper.getJournal());
            database.getJournals().add(journal);
        }
        journal.publishPaper(paper);
    }

    private Journal findJournalByName(String journalName) {
        if (journalName == null) {
            return null;
        }
        for (Journal journal : database.getJournals()) {
            if (journalName.equalsIgnoreCase(journal.getName())) {
                return journal;
            }
        }
        return null;
    }

    private User getUserFromResearcher(Researcher researcher) {
        if (researcher instanceof User) {
            return (User) researcher;
        }
        if (researcher instanceof ResearchDecorator) {
            return ((ResearchDecorator) researcher).getUser();
        }
        return null;
    }

    private void validateResearcher(Researcher researcher) {
        if (researcher == null) {
            throw new IllegalArgumentException("Current user is not a researcher.");
        }
    }

    private void validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }

    private void validatePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be positive.");
        }
    }

    private void validateNotNegative(int value, String fieldName) {
        if (value < 0) {
            throw new IllegalArgumentException(fieldName + " cannot be negative.");
        }
    }

    public static class ResearcherCitationStat {
        private final Researcher researcher;
        private int citations;

        public ResearcherCitationStat(Researcher researcher, int citations) {
            this.researcher = researcher;
            this.citations = citations;
        }

        public Researcher getResearcher() {
            return researcher;
        }

        public int getCitations() {
            return citations;
        }

        public void addCitations(int citations) {
            this.citations += citations;
        }
    }
}
