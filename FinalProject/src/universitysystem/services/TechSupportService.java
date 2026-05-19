package universitysystem.services;

import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;

import java.util.List;

public interface TechSupportService {
    List<Request> getAllRequests();

    List<Request> getRequestsByStatus(RequestStatus status);

    boolean acceptRequest(int requestId);

    boolean rejectRequest(int requestId);

    boolean markAsDone(int requestId);
}
