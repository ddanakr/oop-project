package universitysystem.controllers;

import universitysystem.database.Database;
import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;
import universitysystem.models.users.User;
import universitysystem.services.TechSupportService;
import universitysystem.views.AdminView;
import universitysystem.views.TechSupportView;

import java.util.Collections;
import java.util.List;

public class TechSupportController {
    private final TechSupportService techSupportService;
    private final TechSupportView techSupportView;
    private final AuthController authController;
    private final MessageController messageController;
    private final NewsController newsController;
    private final JournalController journalController;
    private final User currentUser;

    public TechSupportController(TechSupportService techSupportService) {
        this(techSupportService, new TechSupportView(), null, null, null);
    }

    public TechSupportController(
            TechSupportService techSupportService,
            TechSupportView techSupportView,
            AuthController authController,
            MessageController messageController,
            User currentUser
    ) {
        this.techSupportService = techSupportService;
        this.techSupportView = techSupportView;
        this.authController = authController;
        this.messageController = messageController;
        this.newsController = new NewsController(currentUser);
        this.journalController = new JournalController(currentUser);
        this.currentUser = currentUser;
    }

    public void run() {
        boolean running = true;
        while (running) {
            techSupportView.showMenu();
            int choice = techSupportView.readMenuChoice();
            running = handleChoice(choice);
        }
    }

    private boolean handleChoice(int choice) {
        switch (choice) {
            case 1:
                techSupportView.showRequests(getAllRequests());
                return true;
            case 2:
                showRequestsByStatus();
                return true;
            case 3:
                updateStatus("accept");
                return true;
            case 4:
                updateStatus("reject");
                return true;
            case 5:
                updateStatus("done");
                return true;
            case 6:
                changeMyPassword();
                return true;
            case 7:
                openMessages();
                return true;
            case 8:
                newsController.run();
                return true;
            case 9:
                journalController.run();
                return true;
            case 0:
                techSupportView.showMessage("Logout.");
                return false;
            default:
                techSupportView.showError("Invalid choice.");
                return true;
        }
    }

    private void showRequestsByStatus() {
        RequestStatus status = techSupportView.readStatus();
        if (status == null) {
            return;
        }
        techSupportView.showRequests(getRequestsByStatus(status));
    }

    private void updateStatus(String action) {
        int requestId = techSupportView.readRequestId();
        boolean updated;
        switch (action) {
            case "accept":
                updated = accept(requestId);
                break;
            case "reject":
                updated = reject(requestId);
                break;
            case "done":
                updated = done(requestId);
                break;
            default:
                updated = false;
        }
        techSupportView.showMessage(updated ? "Updated." : "Request not found.");
    }

    private void changeMyPassword() {
        if (authController == null || currentUser == null) {
            techSupportView.showError("Auth is not configured.");
            return;
        }
        TechSupportView.PasswordInput input = techSupportView.readPasswordInput();
        boolean changed = authController.changePassword(currentUser, input.getOldPassword(), input.getNewPassword());
        techSupportView.showMessage(changed ? "Password changed." : "Failed to change password.");
    }

    private void openMessages() {
        if (messageController == null || currentUser == null) {
            techSupportView.showError("Messages are not configured.");
            return;
        }
        Database db = Database.getInstance();
        List<User> users = db == null || db.getUsers() == null ? Collections.emptyList() : db.getUsers();
        messageController.run(currentUser, users);
    }

    public List<Request> getAllRequests() {
        return techSupportService == null ? Collections.emptyList() : techSupportService.getAllRequests(currentUser);
    }

    public List<Request> getRequestsByStatus(RequestStatus status) {
        return techSupportService == null ? Collections.emptyList() : techSupportService.getRequestsByStatus(currentUser, status);
    }

    public boolean accept(int requestId) {
        return techSupportService != null && techSupportService.acceptRequest(currentUser, requestId);
    }

    public boolean reject(int requestId) {
        return techSupportService != null && techSupportService.rejectRequest(currentUser, requestId);
    }

    public boolean done(int requestId) {
        return techSupportService != null && techSupportService.markAsDone(currentUser, requestId);
    }
}
