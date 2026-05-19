package universitysystem.views;

import universitysystem.models.users.Message;
import universitysystem.models.users.User;
import universitysystem.services.MessageService;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MessageView {
    private final MessageService messageService;

    public MessageView(MessageService messageService) {
        this.messageService = messageService;
    }

    public void show(User currentUser, List<User> allUsers) {
        if (currentUser == null) {
            System.out.println("No active user.");
            return;
        }
        while (true) {
            ConsoleUtils.printMenu("Messages", "Inbox", "Sent", "Send message", "Back");
            int choice = ConsoleUtils.getIntInput("Choose option: ");
            if (choice == 4) return;

            switch (choice) {
                case 1:
                    listInbox(currentUser);
                    break;
                case 2:
                    listSent(currentUser);
                    break;
                case 3:
                    sendMessage(currentUser, allUsers);
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void listInbox(User user) {
        List<Message> messages = messageService == null ? Collections.emptyList() : messageService.inbox(user);
        printMessages(messages, false);
    }

    private void listSent(User user) {
        List<Message> messages = messageService == null ? Collections.emptyList() : messageService.sent(user);
        printMessages(messages, true);
    }

    private void sendMessage(User from, List<User> allUsers) {
        if (messageService == null) {
            System.out.println("MessageService is not configured.");
            return;
        }
        if (allUsers == null || allUsers.isEmpty()) {
            System.out.println("No users available.");
            return;
        }

        int toId = ConsoleUtils.getIntInput("Recipient user id: ");
        User to = null;
        for (User user : allUsers) {
            if (user != null && user.getId() == toId) {
                to = user;
                break;
            }
        }
        if (to == null) {
            System.out.println("User not found.");
            return;
        }

        String text = ConsoleUtils.getInput("Text: ");
        Message message = messageService.send(from, to, text);
        System.out.println(message == null ? "Failed to send." : "Sent.");
    }

    private void printMessages(List<Message> messages, boolean showTo) {
        List<String> headers = showTo
                ? Arrays.asList("To", "Time", "Text", "Read")
                : Arrays.asList("From", "Time", "Text", "Read");
        List<List<String>> rows = new ArrayList<>();
        if (messages != null) {
            for (Message message : messages) {
                if (message == null) continue;
                rows.add(Arrays.asList(
                        showTo
                                ? (message.getTo() == null ? "" : String.valueOf(message.getTo().getLogin()))
                                : (message.getFrom() == null ? "" : String.valueOf(message.getFrom().getLogin())),
                        String.valueOf(message.getSentAt()),
                        String.valueOf(message.getText()),
                        String.valueOf(message.isRead())
                ));
            }
        }
        ConsoleUtils.printTable(headers, rows);
    }
}
