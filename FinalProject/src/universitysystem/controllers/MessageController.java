package universitysystem.controllers;

import universitysystem.models.core.Message;
import universitysystem.models.users.User;
import universitysystem.services.MessageService;
import universitysystem.views.MessageView;

import java.util.Collections;
import java.util.List;

public class MessageController {
    private final MessageService messageService;
    private final MessageView messageView;

    public MessageController(MessageService messageService) {
        this(messageService, new MessageView());
    }

    public MessageController(MessageService messageService, MessageView messageView) {
        this.messageService = messageService;
        this.messageView = messageView;
    }

    public void run(User currentUser, List<User> allUsers) {
        if (currentUser == null) {
            messageView.showError("No active user.");
            return;
        }

        boolean running = true;
        while (running) {
            messageView.showMenu();
            int choice = messageView.readMenuChoice();
            running = handleChoice(choice, currentUser, allUsers);
        }
    }

    private boolean handleChoice(int choice, User currentUser, List<User> allUsers) {
        switch (choice) {
            case 1:
                messageView.showMessages(inbox(currentUser), false);
                return true;
            case 2:
                messageView.showMessages(sent(currentUser), true);
                return true;
            case 3:
                sendMessage(currentUser, allUsers);
                return true;
            case 4:
            case 0:
                return false;
            default:
                messageView.showError("Invalid choice.");
                return true;
        }
    }

    private void sendMessage(User from, List<User> allUsers) {
        if (messageService == null) {
            messageView.showError("MessageService is not configured.");
            return;
        }
        if (allUsers == null || allUsers.isEmpty()) {
            messageView.showError("No users available.");
            return;
        }

        int toId = messageView.readRecipientId();
        User to = findUserById(allUsers, toId);
        if (to == null) {
            messageView.showError("User not found.");
            return;
        }

        Message message = messageService.send(from, to, messageView.readText());
        messageView.showMessage(message == null ? "Failed to send." : "Sent.");
    }

    private User findUserById(List<User> users, int userId) {
        for (User user : users) {
            if (user != null && user.getId() == userId) {
                return user;
            }
        }
        return null;
    }

    public List<Message> inbox(User user) {
        return messageService == null ? Collections.emptyList() : messageService.inbox(user);
    }

    public List<Message> sent(User user) {
        return messageService == null ? Collections.emptyList() : messageService.sent(user);
    }
}
