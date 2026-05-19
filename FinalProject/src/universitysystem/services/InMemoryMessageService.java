package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.models.core.DateTime;
import universitysystem.models.core.Message;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryMessageService implements MessageService {
    private final Database database;

    public InMemoryMessageService() {
        this(Database.getInstance());
    }

    public InMemoryMessageService(Database database) {
        if (database == null) {
            throw new IllegalArgumentException("Database cannot be null.");
        }
        this.database = database;
    }

    @Override
    public Message send(User from, User to, String text) {
        if (from == null || to == null || text == null || text.trim().isEmpty()) {
            return null;
        }
        Message message = new Message(from, to, text, DateTime.now(), false);
        getMessages().add(message);
        database.save();
        return message;
    }

    @Override
    public List<Message> inbox(User user) {
        if (user == null) return Collections.emptyList();
        List<Message> result = new ArrayList<>();
        for (Message message : getMessages()) {
            if (message != null && user.equals(message.getTo())) {
                result.add(message);
            }
        }
        return result;
    }

    @Override
    public List<Message> sent(User user) {
        if (user == null) return Collections.emptyList();
        List<Message> result = new ArrayList<>();
        for (Message message : getMessages()) {
            if (message != null && user.equals(message.getFrom())) {
                result.add(message);
            }
        }
        return result;
    }

    @Override
    public boolean markAsRead(Message message) {
        if (message == null) return false;
        boolean marked = message.markAsRead();
        if (marked) {
            database.save();
        }
        return marked;
    }

    private List<Message> getMessages() {
        if (database.getMessages() == null) {
            database.setMessages(new ArrayList<>());
        }
        return database.getMessages();
    }
}
