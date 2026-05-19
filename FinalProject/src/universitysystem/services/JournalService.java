package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.news.Journal;
import universitysystem.models.news.Subscriber;

import java.util.ArrayList;
import java.util.List;

public class JournalService {
    private final Database database;

    public JournalService() {
        this.database = Database.getInstance();
    }

    public Journal createJournal(String name) {
        validateRequired(name, "Journal name");

        Journal existing = getJournalByName(name);
        if (existing != null) {
            return existing;
        }

        Journal journal = new Journal(name);
        database.getJournals().add(journal);
        return journal;
    }

    public List<Journal> getAllJournals() {
        return new ArrayList<>(database.getJournals());
    }

    public Journal getJournalByName(String name) {
        if (name == null) {
            return null;
        }
        for (Journal journal : database.getJournals()) {
            if (name.equalsIgnoreCase(journal.getName())) {
                return journal;
            }
        }
        return null;
    }

    public boolean subscribe(String journalName, Subscriber subscriber) {
        if (subscriber == null) {
            return false;
        }

        Journal journal = createJournal(journalName);
        journal.subscribe(subscriber);
        return true;
    }

    public boolean unsubscribe(String journalName, Subscriber subscriber) {
        Journal journal = getJournalByName(journalName);
        if (journal == null || subscriber == null) {
            return false;
        }

        journal.unsubscribe(subscriber);
        return true;
    }

    private void validateRequired(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
    }
}
