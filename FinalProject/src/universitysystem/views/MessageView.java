package universitysystem.views;

import universitysystem.models.core.Message;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageView {
    public void showMenu() {
        ConsoleUtils.printHeader("Messages");
        System.out.println("1. Inbox");
        System.out.println("2. Sent");
        System.out.println("3. Send message");
        System.out.println("0. Back");
    }

    public int readMenuChoice() {
        return ConsoleUtils.getIntInput("Choose option: ");
    }

    public int readRecipientId() {
        return ConsoleUtils.getIntInput("Recipient user id: ");
    }

    public String readText() {
        return ConsoleUtils.getInput("Text: ");
    }

    public void showMessages(List<Message> messages, boolean showTo) {
        List<String> headers = showTo
                ? Arrays.asList("To", "Time", "Text", "Read")
                : Arrays.asList("From", "Time", "Text", "Read");
        List<List<String>> rows = new ArrayList<>();
        if (messages != null) {
            for (Message message : messages) {
                if (message == null) {
                    continue;
                }
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

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }
}
