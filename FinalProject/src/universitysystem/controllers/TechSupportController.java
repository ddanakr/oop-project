package universitysystem.controllers;

import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;
import universitysystem.services.TechSupportService;

import java.util.Collections;
import java.util.List;

public class TechSupportController {
    private final TechSupportService techSupportService;

    public TechSupportController(TechSupportService techSupportService) {
        this.techSupportService = techSupportService;
    }

    public List<Request> getAllRequests() {
        return techSupportService == null ? Collections.emptyList() : techSupportService.getAllRequests();
    }

    public List<Request> getRequestsByStatus(RequestStatus status) {
        return techSupportService == null ? Collections.emptyList() : techSupportService.getRequestsByStatus(status);
    }

    public boolean accept(int requestId) {
        return techSupportService != null && techSupportService.acceptRequest(requestId);
    }

    public boolean reject(int requestId) {
        return techSupportService != null && techSupportService.rejectRequest(requestId);
    }

    public boolean done(int requestId) {
        return techSupportService != null && techSupportService.markAsDone(requestId);
    }
}
