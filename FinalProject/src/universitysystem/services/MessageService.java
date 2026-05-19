package universitysystem.services;

import universitysystem.models.core.Message;
import universitysystem.models.users.User;

import java.util.List;

public interface MessageService {
    Message send(User from, User to, String text);

    List<Message> inbox(User user);

    List<Message> sent(User user);

    boolean markAsRead(Message message);
}
