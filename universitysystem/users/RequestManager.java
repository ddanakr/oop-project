package universitySystem.users;

import java.io.*;
import java.util.*;

/**
 * 
 */
public interface RequestManager {

    /**
     * 
     */
    public void approveRequest(request: Request): void();

    /**
     * 
     */
    public void viewRequests() : List<Request>();

    /**
     * 
     */
    public void rejectRequest(request: Request): void();

}