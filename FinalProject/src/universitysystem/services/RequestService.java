package universitysystem.services;

import universitysystem.models.requests.Request;
import universitysystem.models.requests.RequestStatus;
import universitysystem.models.requests.RequestType;
import universitysystem.models.requests.Signature;
import universitysystem.models.requests.Urgency;
import universitysystem.models.users.User;

import java.util.List;

public interface RequestService {
    Request createRequest(User sender, RequestType type, String description, User target, Urgency urgency);

    List<Request> getAllRequests();

    List<Request> getRequestsByStatus(RequestStatus status);

    Request findById(int requestId);

    boolean updateStatus(int requestId, RequestStatus status);

    boolean signRequest(int requestId, User signer, Signature.SignerRole role);

    boolean isFullyApproved(int requestId);
}
