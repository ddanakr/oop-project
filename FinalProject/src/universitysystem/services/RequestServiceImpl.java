package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.enums.RequestStatus;
import universitysystem.enums.RequestType;
import universitysystem.enums.Urgency;
import universitysystem.models.core.DateTime;
import universitysystem.models.requests.Request;
import universitysystem.models.requests.Signature;
import universitysystem.models.users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RequestServiceImpl implements RequestService {
    @Override
    public Request createRequest(User sender, RequestType type, String description, User target, Urgency urgency) {
        if (sender == null || type == null || description == null || description.trim().isEmpty()) {
            return null;
        }

        Database db = Database.getInstance();
        if (db == null) return null;

        if (db.getRequests() == null) {
            db.setRequests(new ArrayList<>());
        }

        int nextId = nextRequestId(db.getRequests());
        List<Signature> signatures = new ArrayList<>();
        Request request = new Request(nextId, sender, type, description, target, urgency, signatures, DateTime.now());
        db.getRequests().add(request);
        db.save();
        return request;
    }

    @Override
    public List<Request> getAllRequests() {
        Database db = Database.getInstance();
        if (db == null || db.getRequests() == null) return Collections.emptyList();
        return db.getRequests();
    }

    @Override
    public List<Request> getRequestsByStatus(RequestStatus status) {
        if (status == null) return Collections.emptyList();
        List<Request> result = new ArrayList<>();
        for (Request request : getAllRequests()) {
            if (request != null && status.equals(request.getStatus())) {
                result.add(request);
            }
        }
        return result;
    }

    @Override
    public Request findById(int requestId) {
        if (requestId <= 0) return null;
        for (Request request : getAllRequests()) {
            if (request != null && request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

    @Override
    public boolean updateStatus(int requestId, RequestStatus status) {
        if (status == null) return false;
        Request request = findById(requestId);
        if (request == null) return false;
        request.setStatus(status);
        Database.getInstance().save();
        return true;
    }

    @Override
    public boolean signRequest(int requestId, User signer, Signature.SignerRole role) {
        if (signer == null || role == null) return false;
        Request request = findById(requestId);
        if (request == null) return false;
        Signature signature = new Signature();
        signature.setSigner(signer);
        signature.setSignerRole(role);
        request.addSignature(signature);
        Database.getInstance().save();
        return true;
    }

    @Override
    public boolean isFullyApproved(int requestId) {
        Request request = findById(requestId);
        if (request == null) return false;
        return request.checkApprovalRequirement();
    }

    private int nextRequestId(List<Request> existing) {
        int max = 0;
        if (existing != null) {
            for (Request request : existing) {
                if (request != null && request.getRequestId() > max) {
                    max = request.getRequestId();
                }
            }
        }
        return max + 1;
    }
}
