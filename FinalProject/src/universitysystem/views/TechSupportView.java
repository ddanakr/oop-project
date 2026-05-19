package universitysystem.views;

import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TechSupportView {
    public void showMenu() {
        ConsoleUtils.printHeader("Tech Support Menu");
        System.out.println("1. List all requests");
        System.out.println("2. List requests by status");
        System.out.println("3. Accept request");
        System.out.println("4. Reject request");
        System.out.println("5. Mark request as done");
        System.out.println("6. Change my password");
        System.out.println("7. Messages");
        System.out.println("8. News");
        System.out.println("9. Journals");
        System.out.println("0. Logout");
    }

    public int readMenuChoice() {
        return ConsoleUtils.getIntInput("Choose option: ");
    }

    public RequestStatus readStatus() {
        ConsoleUtils.printHeader("Status");
        System.out.println("1. NEW");
        System.out.println("2. VIEWED");
        System.out.println("3. ACCEPTED");
        System.out.println("4. REJECTED");
        System.out.println("5. DONE");
        System.out.println("0. Back");
        int statusChoice = ConsoleUtils.getIntInput("Choose status: ");
        switch (statusChoice) {
            case 1:
                return RequestStatus.NEW;
            case 2:
                return RequestStatus.VIEWED;
            case 3:
                return RequestStatus.ACCEPTED;
            case 4:
                return RequestStatus.REJECTED;
            case 5:
                return RequestStatus.DONE;
            default:
                return null;
        }
    }

    public int readRequestId() {
        return ConsoleUtils.getIntInput("Enter request id: ");
    }

    public PasswordInput readPasswordInput() {
        String oldPassword = ConsoleUtils.getInput("Old password: ");
        String newPassword = ConsoleUtils.getInput("New password: ");
        return new PasswordInput(oldPassword, newPassword);
    }

    public void showRequests(List<Request> requests) {
        List<String> headers = Arrays.asList("ID", "Type", "Status", "Sender", "CreatedAt");
        List<List<String>> rows = new ArrayList<>();
        if (requests != null) {
            for (Request request : requests) {
                if (request == null) {
                    continue;
                }
                rows.add(Arrays.asList(
                        String.valueOf(request.getRequestId()),
                        String.valueOf(request.getRequestType()),
                        String.valueOf(request.getStatus()),
                        request.getSender() == null ? "" : String.valueOf(request.getSender().getLogin()),
                        String.valueOf(request.getCreatedAt())
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

    public static class PasswordInput {
        private final String oldPassword;
        private final String newPassword;

        public PasswordInput(String oldPassword, String newPassword) {
            this.oldPassword = oldPassword;
            this.newPassword = newPassword;
        }

        public String getOldPassword() {
            return oldPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }
    }
}
