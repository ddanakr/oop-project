package universitysystem.services;

import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;

import java.util.List;

public interface TechSupportService {
    List<Request> getAllRequests();

    List<Request> getRequestsByStatus(RequestStatus status);

    boolean acceptRequest(int requestId);

    boolean rejectRequest(int requestId);

    boolean markAsDone(int requestId);
}
