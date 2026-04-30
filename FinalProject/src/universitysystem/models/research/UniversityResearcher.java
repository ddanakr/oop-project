package universitysystem.models.research;

import java.io.*;
import java.util.*;

/**
 * 
 */
public class UniversityResearcher extends ResearchDecorator {

    /**
     * Default constructor
     */
    public UniversityResearcher() {
    }

    /**
     * 
     */
    public UniversityResearcher(User user) {
        setUser(user);
    }

}