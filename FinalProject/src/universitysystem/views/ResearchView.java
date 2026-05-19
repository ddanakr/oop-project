package universitysystem.views;

import universitysystem.enums.CitationFormat;
import universitysystem.enums.ResearchPaperSortType;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.research.Researcher;
import universitysystem.services.ResearchService;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResearchView {
    public void showMenu() {
        ConsoleUtils.printHeader("Research Menu");
        System.out.println("1. Show all papers");
        System.out.println("2. Show all projects");
        System.out.println("3. Show researchers");
        System.out.println("4. Create paper");
        System.out.println("5. Create project");
        System.out.println("6. Join project");
        System.out.println("7. Add paper to project");
        System.out.println("8. Show my papers");
        System.out.println("9. Show my h-index");
        System.out.println("10. Get paper citation");
        System.out.println("11. Show top researchers");
        System.out.println("12. Show all researchers' papers");
        System.out.println("13. Show top cited researchers");
        System.out.println("14. Show top cited researchers by year");
        System.out.println("15. Generate top cited researcher news");
        System.out.println("0. Back");
    }

    public int readMenuChoice() {
        return ConsoleUtils.readInt("Choose option: ");
    }

    public PaperInput readPaperInput() {
        String title = ConsoleUtils.readLine("Title: ");
        String journal = ConsoleUtils.readLine("Journal: ");
        int pages = ConsoleUtils.readInt("Pages: ");
        int citations = ConsoleUtils.readInt("Citations: ");
        return new PaperInput(title, journal, pages, citations);
    }

    public ProjectInput readProjectInput() {
        String name = ConsoleUtils.readLine("Project name: ");
        String topic = ConsoleUtils.readLine("Project topic: ");
        return new ProjectInput(name, topic);
    }

    public int readPaperId() {
        return ConsoleUtils.readInt("Paper id: ");
    }

    public int readProjectId() {
        return ConsoleUtils.readInt("Project id: ");
    }

    public CitationFormat readCitationFormat() {
        System.out.println("1. Plain text");
        System.out.println("2. BibTeX");
        int choice = ConsoleUtils.readInt("Citation format: ");
        return choice == 2 ? CitationFormat.BIBTEX : CitationFormat.PLAINTEXT;
    }

    public ResearchPaperSortType readPaperSortType() {
        System.out.println("1. Date");
        System.out.println("2. Citations");
        System.out.println("3. Pages");
        int choice = ConsoleUtils.readInt("Sort by: ");
        if (choice == 2) {
            return ResearchPaperSortType.CITATIONS;
        }
        if (choice == 3) {
            return ResearchPaperSortType.PAGES;
        }
        return ResearchPaperSortType.DATE;
    }

    public int readYear() {
        return ConsoleUtils.readInt("Year: ");
    }

    public void showPapers(List<ResearchPaper> papers) {
        List<List<String>> rows = new ArrayList<>();
        for (ResearchPaper paper : papers) {
            rows.add(Arrays.asList(
                    String.valueOf(paper.getId()),
                    valueOrDash(paper.getTitle()),
                    valueOrDash(paper.getJournal()),
                    String.valueOf(paper.getPages()),
                    String.valueOf(paper.getCitations()),
                    paper.getDate() == null ? "-" : paper.getDate().toString()
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Title", "Journal", "Pages", "Citations", "Date"),
                rows
        );
    }

    public void showProjects(List<ResearchProject> projects) {
        List<List<String>> rows = new ArrayList<>();
        for (ResearchProject project : projects) {
            rows.add(Arrays.asList(
                    String.valueOf(project.getId()),
                    valueOrDash(project.getName()),
                    valueOrDash(project.getTopic()),
                    String.valueOf(project.getParticipants().size()),
                    String.valueOf(project.getPapers().size())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("ID", "Name", "Topic", "Participants", "Papers"),
                rows
        );
    }

    public void showResearchers(List<Researcher> researchers) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < researchers.size(); i++) {
            Researcher researcher = researchers.get(i);
            rows.add(Arrays.asList(
                    String.valueOf(i + 1),
                    researcher.toString(),
                    String.valueOf(researcher.getHIndex())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("No", "Researcher", "H-index"),
                rows
        );
    }

    public void showCitationStats(List<ResearchService.ResearcherCitationStat> stats) {
        List<List<String>> rows = new ArrayList<>();
        for (int i = 0; i < stats.size(); i++) {
            ResearchService.ResearcherCitationStat stat = stats.get(i);
            rows.add(Arrays.asList(
                    String.valueOf(i + 1),
                    stat.getResearcher().toString(),
                    String.valueOf(stat.getCitations()),
                    String.valueOf(stat.getResearcher().getHIndex())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("No", "Researcher", "Citations", "H-index"),
                rows
        );
    }

    public void showCitation(String citation) {
        if (citation == null) {
            showError("Paper was not found.");
            return;
        }
        ConsoleUtils.printHeader("Citation");
        System.out.println(citation);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    private String valueOrDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value;
    }

    public static class PaperInput {
        private final String title;
        private final String journal;
        private final int pages;
        private final int citations;

        public PaperInput(String title, String journal, int pages, int citations) {
            this.title = title;
            this.journal = journal;
            this.pages = pages;
            this.citations = citations;
        }

        public String getTitle() {
            return title;
        }

        public String getJournal() {
            return journal;
        }

        public int getPages() {
            return pages;
        }

        public int getCitations() {
            return citations;
        }
    }

    public static class ProjectInput {
        private final String name;
        private final String topic;

        public ProjectInput(String name, String topic) {
            this.name = name;
            this.topic = topic;
        }

        public String getName() {
            return name;
        }

        public String getTopic() {
            return topic;
        }
    }
}
