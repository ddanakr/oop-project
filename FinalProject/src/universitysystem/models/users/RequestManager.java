package universitysystem.models.users;

import universitysystem.models.requests.Request;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RequestManager {

    /**
     * 
     */
    void approveRequest(Request request);

    /**
     * 
     */
    List<Request> viewRequests();

    /**
     * 
     */
    void rejectRequest(Request request);

}
