package universitysystem.models.requests;

import src.universitysystem.models.users.User;

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
    }

    /**
     * 
     */
    private int requestId ;

    /**
     * 
     */
    private User sender ;

    /**
     * 
     */
    private RequestType requestType ;

    /**
     * 
     */
    private String description ;

    /**
     * 
     */
    private User target ;

    /**
     * 
     */
    private RequestStatus status ;

    /**
     * 
     */
    private Urgency urgency;

    /**
     * 
     */
    private List<Signature> signatures ;

    /**
     * 
     */
    private DateTime createdAt ;




    /**
     * 
     */
    public void addSignature(signature : Signature) : void() {
        // TODO implement here
    }

    /**
     * 
     */
    public void checkApprovalRequirement() : boolean() {
        // TODO implement here
    }

}