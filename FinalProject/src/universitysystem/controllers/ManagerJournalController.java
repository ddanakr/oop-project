package universitysystem.controllers;

import universitysystem.models.news.Journal;
import universitysystem.services.JournalService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.JournalView;

public class ManagerJournalController {
    private final JournalService journalService;
    private final JournalView journalView;

    public ManagerJournalController() {
        this(new JournalService(), new JournalView());
    }

    public ManagerJournalController(JournalService journalService, JournalView journalView) {
        this.journalService = journalService;
        this.journalView = journalView;
    }

    public void run() {
        boolean running = true;

        while (running) {
            journalView.showManagerMenu();
            int choice = journalView.readMenuChoice();

            try {
                running = handleChoice(choice);
            } catch (IllegalArgumentException e) {
                journalView.showError(e.getMessage());
            }

            if (running) {
                ConsoleUtils.pressEnterToContinue();
            }
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                showJournals();
                return true;
            case 2:
                createJournal();
                return true;
            case 0:
                journalView.showMessage("Back to previous menu.");
                return false;
            default:
                journalView.showError("Unknown option.");
                return true;
        }
    }

    private void showJournals() {
        journalView.showJournals(journalService.getAllJournals());
    }

    private void createJournal() {
        String name = journalView.readJournalName();
        Journal journal = journalService.createJournal(name);
        journalView.showMessage("Journal ready: " + journal.getName());
    }
}
