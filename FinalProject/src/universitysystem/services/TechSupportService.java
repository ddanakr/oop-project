package universitysystem.services;

import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;
import universitysystem.models.users.User;

import java.util.List;

public interface TechSupportService {
    List<Request> getAllRequests();

    List<Request> getAllRequests(User techSupport);

    List<Request> getRequestsByStatus(RequestStatus status);

    List<Request> getRequestsByStatus(User techSupport, RequestStatus status);

    boolean acceptRequest(int requestId);

    boolean acceptRequest(User techSupport, int requestId);

    boolean rejectRequest(int requestId);

    boolean rejectRequest(User techSupport, int requestId);

    boolean markAsDone(int requestId);

    boolean markAsDone(User techSupport, int requestId);
}
