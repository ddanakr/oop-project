package universitysystem.services;

import universitysystem.enums.RequestStatus;
import universitysystem.enums.RequestType;
import universitysystem.enums.Urgency;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.Signature;
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
