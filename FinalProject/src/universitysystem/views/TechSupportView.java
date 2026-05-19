package universitysystem.views;

import universitysystem.controllers.AuthController;
import universitysystem.controllers.TechSupportController;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;
import universitysystem.models.users.Database;
import universitysystem.models.users.User;
import universitysystem.utils.ConsoleUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TechSupportView {
    private final TechSupportController techSupportController;
    private final AuthController authController;
    private final User currentUser;
    private final MessageView messageView;

    public TechSupportView(TechSupportController techSupportController) {
        this(techSupportController, null, null, null);
    }

    public TechSupportView(
            TechSupportController techSupportController,
            AuthController authController,
            User currentUser,
            MessageView messageView
    ) {
        this.techSupportController = techSupportController;
        this.authController = authController;
        this.currentUser = currentUser;
        this.messageView = messageView;
    }

    public void show() {
        while (true) {
            ConsoleUtils.printMenu(
                    "Tech Support Menu",
                    "List all requests",
                    "List requests by status",
                    "Accept request",
                    "Reject request",
                    "Mark request as done",
                    "Change my password",
                    "Messages",
                    "Logout"
            );

            int choice = ConsoleUtils.getIntInput("Choose option: ");
            if (choice == 8) {
                return;
            }

            switch (choice) {
                case 1:
                    listRequests(techSupportController.getAllRequests());
                    break;
                case 2:
                    listByStatus();
                    break;
                case 3:
                    updateStatus("accept");
                    break;
                case 4:
                    updateStatus("reject");
                    break;
                case 5:
                    updateStatus("done");
                    break;
                case 6:
                    changeMyPassword();
                    break;
                case 7:
                    openMessages();
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private void listByStatus() {
        ConsoleUtils.printMenu("Status", "NEW", "VIEWED", "ACCEPTED", "REJECTED", "DONE");
        int statusChoice = ConsoleUtils.getIntInput("Choose status: ");
        RequestStatus status;
        switch (statusChoice) {
            case 1:
                status = RequestStatus.NEW;
                break;
            case 2:
                status = RequestStatus.VIEWED;
                break;
            case 3:
                status = RequestStatus.ACCEPTED;
                break;
            case 4:
                status = RequestStatus.REJECTED;
                break;
            case 5:
                status = RequestStatus.DONE;
                break;
            default:
                System.out.println("Invalid status.");
                return;
        }
        listRequests(techSupportController.getRequestsByStatus(status));
    }

    private void updateStatus(String action) {
        int requestId = ConsoleUtils.getIntInput("Enter request id: ");
        boolean ok;
        switch (action) {
            case "accept":
                ok = techSupportController.accept(requestId);
                break;
            case "reject":
                ok = techSupportController.reject(requestId);
                break;
            case "done":
                ok = techSupportController.done(requestId);
                break;
            default:
                ok = false;
        }
        System.out.println(ok ? "Updated." : "Request not found.");
    }

    private void listRequests(List<Request> requests) {
        List<String> headers = Arrays.asList("ID", "Type", "Status", "Sender", "CreatedAt");
        List<List<String>> rows = new ArrayList<>();
        if (requests != null) {
            for (Request request : requests) {
                if (request == null) continue;
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

    private void changeMyPassword() {
        if (authController == null || currentUser == null) {
            System.out.println("Auth is not configured for this view.");
            return;
        }
        String oldPassword = ConsoleUtils.getInput("Old password: ");
        String newPassword = ConsoleUtils.getInput("New password: ");
        boolean ok = authController.changePassword(currentUser, oldPassword, newPassword);
        System.out.println(ok ? "Password changed." : "Failed to change password.");
    }

    private void openMessages() {
        if (messageView == null || currentUser == null) {
            System.out.println("Messages are not configured for this view.");
            return;
        }
        Database db = Database.getInstance();
        List<User> users = (db == null || db.getUsers() == null) ? Collections.emptyList() : db.getUsers();
        messageView.show(currentUser, users);
    }
}
