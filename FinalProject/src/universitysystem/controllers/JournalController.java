package universitysystem.controllers;

import universitysystem.models.users.User;
import universitysystem.services.JournalService;
import universitysystem.utils.ConsoleUtils;
import universitysystem.views.JournalView;

public class JournalController {
    private final JournalService journalService;
    private final JournalView journalView;
    private User currentUser;

    public JournalController(User currentUser) {
        this(new JournalService(), new JournalView(), currentUser);
    }

    public JournalController(JournalService journalService, JournalView journalView, User currentUser) {
        this.journalService = journalService;
        this.journalView = journalView;
        this.currentUser = currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void run() {
        boolean running = true;

        while (running) {
            journalView.showMenu();
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
                subscribe();
                return true;
            case 3:
                unsubscribe();
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

    private void subscribe() {
        String name = journalView.readJournalName();

        if (journalService.subscribe(name, currentUser)) {
            journalView.showMessage("Subscribed to journal.");
        } else {
            journalView.showError("Cannot subscribe to journal.");
        }
    }

    private void unsubscribe() {
        String name = journalView.readJournalName();

        if (journalService.unsubscribe(name, currentUser)) {
            journalView.showMessage("Unsubscribed from journal.");
        } else {
            journalView.showError("Journal was not found.");
        }
    }
}
