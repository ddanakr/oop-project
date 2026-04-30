package universitysystem.models.requests;

import universitysystem.models.DateTime;
import universitysystem.models.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Request {

    /**
     * Default constructor
     */
    public Request() {
        this.status = RequestStatus.NEW;
        this.signatures = new ArrayList<>();
    }

    /**
     * Full constructor
     */
    public Request(int requestId, User sender, RequestType requestType, String description, User target, Urgency urgency, List<Signature> signatures, DateTime createdAt) {
        this.requestId = requestId;
        this.sender = sender;
        this.requestType = requestType;
        this.description = description;
        this.target = target;
        this.status = RequestStatus.NEW;
        this.urgency = urgency;
        this.signatures = signatures != null ? signatures : new ArrayList<>();
        this.createdAt = createdAt;
    }

    /**
     * 
     */
    private int requestId;

    /**
     * 
     */
    private User sender;

    /**
     * 
     */
    private RequestType requestType;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private User target;

    /**
     * 
     */
    private RequestStatus status;

    /**
     * 
     */
    private Urgency urgency;

    /**
     * 
     */
    private List<Signature> signatures;

    /**
     * 
     */
    private DateTime createdAt;

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getTarget() {
        return target;
    }

    public void setTarget(User target) {
        this.target = target;
    }

    public RequestStatus getStatus() {
        return status;
    }

    public void setStatus(RequestStatus status) {
        this.status = status;
    }

    public Urgency getUrgency() {
        return urgency;
    }

    public void setUrgency(Urgency urgency) {
        this.urgency = urgency;
    }

    public List<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(List<Signature> signatures) {
        this.signatures = signatures != null ? signatures : new ArrayList<>();
    }

    public DateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(DateTime createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     */
    public void addSignature(Signature signature) {
        if (this.signatures == null) {
            this.signatures = new ArrayList<>();
        }
        if (signature != null) {
            this.signatures.add(signature);
        }
    }

    /**
     * 
     */
    public boolean checkApprovalRequirement() {
        boolean hasDean = false;
        boolean hasRector = false;
        if (this.signatures != null) {
            for (Signature signature : this.signatures) {
                if (signature.getSignerRole() == Signature.SignerRole.DEAN) {
                    hasDean = true;
                }
                if (signature.getSignerRole() == Signature.SignerRole.RECTOR) {
                    hasRector = true;
                }
            }
        }
        return hasDean && hasRector;
    }

    @Override
    public String toString() {
        return "Request{" +
                "requestId=" + requestId +
                ", sender=" + sender +
                ", requestType=" + requestType +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Request)) return false;
        Request request = (Request) o;
        return requestId == request.requestId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestId);
    }

}