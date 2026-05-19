package universitysystem.services;

import universitysystem.database.Database;
import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;
import universitysystem.models.users.TechSupport;
import universitysystem.models.users.User;

import java.util.ArrayList;
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
    public List<Request> getAllRequests(User techSupport) {
        return markNewAsViewed(filterAssignedRequests(techSupport));
    }

    @Override
    public List<Request> getRequestsByStatus(RequestStatus status) {
        return requestService == null ? Collections.emptyList() : requestService.getRequestsByStatus(status);
    }

    @Override
    public List<Request> getRequestsByStatus(User techSupport, RequestStatus status) {
        if (status == null) {
            return Collections.emptyList();
        }
        List<Request> result = new ArrayList<>();
        for (Request request : markNewAsViewed(filterAssignedRequests(techSupport))) {
            if (request != null && status.equals(request.getStatus())) {
                result.add(request);
            }
        }
        return result;
    }

    @Override
    public boolean acceptRequest(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.ACCEPTED);
    }

    @Override
    public boolean acceptRequest(User techSupport, int requestId) {
        return updateAssignedStatus(techSupport, requestId, RequestStatus.ACCEPTED);
    }

    @Override
    public boolean rejectRequest(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.REJECTED);
    }

    @Override
    public boolean rejectRequest(User techSupport, int requestId) {
        return updateAssignedStatus(techSupport, requestId, RequestStatus.REJECTED);
    }

    @Override
    public boolean markAsDone(int requestId) {
        return requestService != null && requestService.updateStatus(requestId, RequestStatus.DONE);
    }

    @Override
    public boolean markAsDone(User techSupport, int requestId) {
        return updateAssignedStatus(techSupport, requestId, RequestStatus.DONE);
    }

    private boolean updateAssignedStatus(User techSupport, int requestId, RequestStatus status) {
        Request request = findAssignedRequest(techSupport, requestId);
        if (request == null) {
            return false;
        }
        request.setStatus(status);
        Database.getInstance().save();
        return true;
    }

    private Request findAssignedRequest(User techSupport, int requestId) {
        for (Request request : filterAssignedRequests(techSupport)) {
            if (request != null && request.getRequestId() == requestId) {
                return request;
            }
        }
        return null;
    }

    private List<Request> filterAssignedRequests(User techSupport) {
        if (techSupport == null || requestService == null) {
            return Collections.emptyList();
        }
        List<Request> result = new ArrayList<>();
        for (Request request : requestService.getAllRequests()) {
            if (isAssignedTo(techSupport, request)) {
                result.add(request);
            }
        }
        return result;
    }

    private List<Request> markNewAsViewed(List<Request> requests) {
        boolean changed = false;
        for (Request request : requests) {
            if (request != null && request.getStatus() == RequestStatus.NEW) {
                request.setStatus(RequestStatus.VIEWED);
                changed = true;
            }
        }
        if (changed) {
            Database.getInstance().save();
        }
        return requests;
    }

    private boolean isAssignedTo(User techSupport, Request request) {
        if (request == null || techSupport == null) {
            return false;
        }
        if (techSupport.equals(request.getTarget())) {
            return true;
        }
        if (techSupport instanceof TechSupport) {
            List<Request> assigned = ((TechSupport) techSupport).getRequests();
            return assigned != null && assigned.contains(request);
        }
        return false;
    }
}
