package universitysystem.controllers;

import universitysystem.enums.RequestStatus;
import universitysystem.enums.RequestType;
import universitysystem.enums.Urgency;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.Signature;
import universitysystem.models.users.User;
import universitysystem.services.RequestService;

import java.util.Collections;
import java.util.List;

public class RequestController {
    private final RequestService requestService;

    public RequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    public Request create(User sender, RequestType type, String description, User target, Urgency urgency) {
        return requestService == null ? null : requestService.createRequest(sender, type, description, target, urgency);
    }

    public List<Request> getAll() {
        return requestService == null ? Collections.emptyList() : requestService.getAllRequests();
    }

    public List<Request> getByStatus(RequestStatus status) {
        return requestService == null ? Collections.emptyList() : requestService.getRequestsByStatus(status);
    }

    public Request findById(int requestId) {
        return requestService == null ? null : requestService.findById(requestId);
    }

    public boolean updateStatus(int requestId, RequestStatus status) {
        return requestService != null && requestService.updateStatus(requestId, status);
    }

    public boolean sign(int requestId, User signer, Signature.SignerRole role) {
        return requestService != null && requestService.signRequest(requestId, signer, role);
    }

    public boolean isFullyApproved(int requestId) {
        return requestService != null && requestService.isFullyApproved(requestId);
    }
}
