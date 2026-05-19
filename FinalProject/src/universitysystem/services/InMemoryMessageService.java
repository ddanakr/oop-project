package universitysystem.services;

import universitysystem.models.DateTime;
import universitysystem.models.users.Message;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InMemoryMessageService implements MessageService {
    private final List<Message> messages = new ArrayList<>();

    @Override
    public Message send(User from, User to, String text) {
        if (from == null || to == null || text == null || text.trim().isEmpty()) {
            return null;
        }
        Message message = new Message(from, to, text, DateTime.now(), false);
        messages.add(message);
        return message;
    }

    @Override
    public List<Message> inbox(User user) {
        if (user == null) return Collections.emptyList();
        List<Message> result = new ArrayList<>();
        for (Message message : messages) {
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
        for (Message message : messages) {
            if (message != null && user.equals(message.getFrom())) {
                result.add(message);
            }
        }
        return result;
    }

    @Override
    public boolean markAsRead(Message message) {
        if (message == null) return false;
        return message.markAsRead();
    }
}
