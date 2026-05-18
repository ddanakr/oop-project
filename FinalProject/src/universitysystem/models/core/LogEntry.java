package universitysystem.models.core;

import universitysystem.models.users.User;

public class LogEntry extends LogFile {
    public LogEntry() {
        super();
    }

    public LogEntry(int id, User user, String action, DateTime timestamp) {
        super(id, user, action, timestamp);
    }
}
