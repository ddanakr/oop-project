package universitysystem.controllers;

import universitysystem.enums.CitationFormat;
import universitysystem.enums.ResearchPaperSortType;
import universitysystem.models.research.ResearchPaper;
import universitysystem.models.research.ResearchProject;
import universitysystem.models.research.Researcher;
import universitysystem.services.ResearchService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.ResearchView;

public class ResearchController {
    private final ResearchService researchService;
    private final ResearchView researchView;
    private Researcher currentResearcher;

    public ResearchController(Researcher currentResearcher) {
        this(new ResearchService(), new ResearchView(), currentResearcher);
    }

    public ResearchController(ResearchService researchService, ResearchView researchView, Researcher currentResearcher) {
        this.researchService = researchService;
        this.researchView = researchView;
        this.currentResearcher = currentResearcher;
    }

    public void setCurrentResearcher(Researcher currentResearcher) {
        this.currentResearcher = currentResearcher;
    }

    public void run() {
        boolean running = true;

        while (running) {
            researchView.showMenu();
            int choice = researchView.readMenuChoice();

            try {
                running = handleChoice(choice);
            } catch (IllegalArgumentException e) {
                researchView.showError(e.getMessage());
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                showAllPapers();
                return true;
            case 2:
                showAllProjects();
                return true;
            case 3:
                showResearchers();
                return true;
            case 4:
                createPaper();
                return true;
            case 5:
                createProject();
                return true;
            case 6:
                joinProject();
                return true;
            case 7:
                addPaperToProject();
                return true;
            case 8:
                showMyPapers();
                return true;
            case 9:
                showMyHIndex();
                return true;
            case 10:
                showCitation();
                return true;
            case 11:
                showTopResearchers();
                return true;
            case 12:
                showAllResearchersPapers();
                return true;
            case 13:
                showTopCitedResearchers();
                return true;
            case 14:
                showTopCitedResearchersByYear();
                return true;
            case 15:
                generateTopCitedResearcherNews();
                return true;
            case 0:
                researchView.showMessage("Back to previous menu.");
                return false;
            default:
                researchView.showError("Unknown option.");
                return true;
        }
    }

    private void showAllPapers() {
        ResearchPaperSortType sortType = researchView.readPaperSortType();
        researchView.showPapers(researchService.getAllPapers(sortType));
    }

    private void showAllProjects() {
        researchView.showProjects(researchService.getAllProjects());
    }

    private void showResearchers() {
        researchView.showResearchers(researchService.getAllResearchers());
    }

    private void createPaper() {
        ResearchView.PaperInput input = researchView.readPaperInput();
        ResearchPaper paper = researchService.createPaper(
                input.getTitle(),
                input.getJournal(),
                input.getPages(),
                input.getCitations(),
                currentResearcher
        );
        researchView.showMessage("Paper created with id " + paper.getId() + ": " + paper.getTitle());
    }

    private void createProject() {
        ResearchView.ProjectInput input = researchView.readProjectInput();
        ResearchProject project = researchService.createProject(
                input.getName(),
                input.getTopic(),
                currentResearcher
        );
        researchView.showMessage("Project created with id " + project.getId() + ": " + project.getName());
    }

    private void joinProject() {
        researchView.showProjects(researchService.getAllProjects());
        int projectId = researchView.readProjectId();

        if (researchService.joinProject(projectId, currentResearcher)) {
            researchView.showMessage("Joined project.");
        } else {
            researchView.showError("Project was not found.");
        }
    }

    private void addPaperToProject() {
        researchView.showProjects(researchService.getAllProjects());
        int projectId = researchView.readProjectId();
        researchView.showPapers(researchService.getAllPapers());
        int paperId = researchView.readPaperId();

        if (researchService.addPaperToProject(projectId, paperId)) {
            researchView.showMessage("Paper added to project.");
        } else {
            researchView.showError("Project or paper was not found.");
        }
    }

    private void showMyPapers() {
        ResearchPaperSortType sortType = researchView.readPaperSortType();
        researchView.showPapers(researchService.getPapersByResearcher(currentResearcher, sortType));
    }

    private void showMyHIndex() {
        int hIndex = researchService.getHIndex(currentResearcher);
        researchView.showMessage("Your h-index: " + hIndex);
    }

    private void showCitation() {
        researchView.showPapers(researchService.getAllPapers());
        int paperId = researchView.readPaperId();
        CitationFormat format = researchView.readCitationFormat();
        researchView.showCitation(researchService.getCitation(paperId, format));
    }

    private void showTopResearchers() {
        researchView.showResearchers(researchService.getTopResearchers());
    }

    private void showAllResearchersPapers() {
        ResearchPaperSortType sortType = researchView.readPaperSortType();
        researchView.showPapers(researchService.getAllResearchersPapers(sortType));
    }

    private void showTopCitedResearchers() {
        researchView.showCitationStats(researchService.getTopCitedResearchers());
    }

    private void showTopCitedResearchersByYear() {
        int year = researchView.readYear();
        researchView.showCitationStats(researchService.getTopCitedResearchersByYear(year));
    }

    private void generateTopCitedResearcherNews() {
        if (researchService.generateTopCitedResearcherNews()) {
            researchView.showMessage("Top cited researcher news generated.");
        } else {
            researchView.showError("No research citations found.");
        }
    }
}
