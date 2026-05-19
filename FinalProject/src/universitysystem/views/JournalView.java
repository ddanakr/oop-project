package universitysystem.views;

import universitysystem.models.news.Journal;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JournalView {
    public void showMenu() {
        showUserMenu();
    }

    public void showUserMenu() {
        ConsoleUtils.printHeader("Journals");
        System.out.println("1. Show journals");
        System.out.println("2. Subscribe to journal");
        System.out.println("3. Unsubscribe from journal");
        System.out.println("0. Back");
    }

    public void showManagerMenu() {
        ConsoleUtils.printHeader("Manage Journals");
        System.out.println("1. Show journals");
        System.out.println("2. Create journal");
        System.out.println("0. Back");
    }

    public int readMenuChoice() {
        return ConsoleUtils.readInt("Choose option: ");
    }

    public String readJournalName() {
        return ConsoleUtils.readLine("Journal name: ");
    }

    public void showJournals(List<Journal> journals) {
        List<List<String>> rows = new ArrayList<>();

        for (Journal journal : journals) {
            rows.add(Arrays.asList(
                    valueOrDash(journal.getName()),
                    String.valueOf(journal.getSubscribers().size()),
                    String.valueOf(journal.getPublishedPapers().size())
            ));
        }

        ConsoleUtils.printTable(
                Arrays.asList("Name", "Subscribers", "Papers"),
                rows
        );
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
}
