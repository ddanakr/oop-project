package universitysystem.models.research;

import universitysystem.models.users.User;

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
        super();
    }

    /**
     * 
     */
    public UniversityResearcher(User user) {
        super();
        setUser(user);
    }

    public UniversityResearcher(User user, List<ResearchPaper> papers, List<ResearchProject> projects) {
        super(user, papers, projects);
    }

}
