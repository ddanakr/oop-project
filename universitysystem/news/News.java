package universitySystem.news;

import universitySystem.users.User;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class News {

    /**
     * Default constructor
     */
    public News() {
    }

    /**
     * 
     */
    private int id;

    /**
     * 
     */
    private String title ;

    /**
     * 
     */
    private String topic;

    /**
     * 
     */
    private String body;

    /**
     * 
     */
    private User author ;

    /**
     * 
     */
    private DateTime createdAt ;

    /**
     * 
     */
    private boolean isPinned  =  false;

    /**
     * 
     */
    private  List<Comment> comments ;


    /**
     * 
     */
    public void addComment(comment : Comment) : void() {
        // TODO implement here
    }

}