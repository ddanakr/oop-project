package universitysystem.services;

import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;

import java.util.Collections;
import java.util.List;

public class TechSupportServiceImpl implements TechSupportService {
    private final RequestService requestService;

    public TechSupportServiceImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public List<Request> getAllRequests() {
        return requestService == null ? Collections.emptyList() : requestService.getAllRequests();
    }

    @Override
    public List<Request> getRequestsByStatus(RequestStatus status) {
        return requestService == null ? Collections.emptyList() : requestService.getRequestsByStatus(status);
    }

    @Override
    public boolean acceptRequest(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.ACCEPTED);
    }

    @Override
    public boolean rejectRequest(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.REJECTED);
    }

    @Override
    public boolean markAsDone(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.DONE);
    }
}
