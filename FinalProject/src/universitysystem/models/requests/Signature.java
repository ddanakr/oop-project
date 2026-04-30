package universitysystem.models.requests;

import src.universitysystem.models.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class Signature {

    /**
     * Default constructor
     */
    public Signature() {
    }

    /**
     * 
     */
    private User signer;

    /**
     * 
     */
    private SignerRole signerRole ;

    /**
     * 
     */
    private DateTime signedAt ;



    /**
     * 
     */
    public enum SignerRole {
        DEAN,
        RECTOR
    }

}