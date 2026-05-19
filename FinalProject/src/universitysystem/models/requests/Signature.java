package universitysystem.models.requests;

import universitysystem.models.DateTime;
import universitysystem.models.users.User;

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
    
    

    public Signature(User signer, SignerRole signerRole, DateTime signedAt) {
        this.signer = signer;
        this.signerRole = signerRole;
        this.signedAt = signedAt;
    }

    public User getSigner() {
        return signer;
    }

    public void setSigner(User signer) {
        this.signer = signer;
    }

    public SignerRole getSignerRole() {
        return signerRole;
    }

    public void setSignerRole(SignerRole signerRole) {
        this.signerRole = signerRole;
    }

    public DateTime getSignedAt() {
        return signedAt;
    }

    public void setSignedAt(DateTime signedAt) {
        this.signedAt = signedAt;
    }

    @Override
    public String toString() {
        return "Signature{signer=" + (signer == null ? null : signer.getLogin()) + ", signerRole=" + signerRole
                + ", signedAt=" + signedAt + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Signature signature = (Signature) o;
        return Objects.equals(signer == null ? null : signer.getLogin(),
                signature.signer == null ? null : signature.signer.getLogin())
                && signerRole == signature.signerRole
                && Objects.equals(signedAt, signature.signedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(signer == null ? null : signer.getLogin(), signerRole, signedAt);
    }

    /**
     * 
     */
    public enum SignerRole {
        DEAN,
        RECTOR
    }

}
