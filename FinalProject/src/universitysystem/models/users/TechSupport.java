package universitysystem.models.users;

import java.io.*;
import java.util.*;
import universitysystem.enums.RequestStatus;
import universitysystem.models.requests.Request;

/**
 * 
 */
public class TechSupport extends Employee {

    /**
     * Default constructor
     */
    public TechSupport() {
        this.requests = new ArrayList<>();
        this.activeRequests = new ArrayList<>();
    }

    /**
     * 
     */
    private List<Request> requests;

    /**
     * 
     */
    private List<Request> activeRequests;

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests != null ? requests : new ArrayList<>();
    }

    public List<Request> getActiveRequests() {
        return activeRequests;
    }

    public void setActiveRequests(List<Request> activeRequests) {
        this.activeRequests = activeRequests != null ? activeRequests : new ArrayList<>();
    }

    /**
     * 
     */
    public List<Request> viewRequests() {
        return requests != null ? requests : new ArrayList<>();
    }

    /**
     * 
     */
    public void acceptRequest(Request request) {
        if (request == null) {
            return;
        }
        request.setStatus(RequestStatus.ACCEPTED);
        if (this.activeRequests == null) {
            this.activeRequests = new ArrayList<>();
        }
        if (!this.activeRequests.contains(request)) {
            this.activeRequests.add(request);
        }
    }

    /**
     * 
     */
    public void rejectRequest(Request request) {
        if (request == null) {
            return;
        }
        request.setStatus(RequestStatus.REJECTED);
    }

    /**
     * 
     */
    public void markAsDone(Request request) {
        if (request == null) {
            return;
        }
        request.setStatus(RequestStatus.DONE);
        if (this.activeRequests != null) {
            this.activeRequests.remove(request);
        }
    }

    @Override
    public String toString() {
        return "TechSupport{" +
                "requests=" + requests +
                ", activeRequests=" + activeRequests +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TechSupport)) return false;
        return Objects.equals(getLogin(), ((TechSupport) o).getLogin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLogin());
    }

}
