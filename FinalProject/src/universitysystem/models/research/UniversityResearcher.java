package universitysystem.models.research;

import universitysystem.models.users.User;

import java.util.List;

public class UniversityResearcher extends ResearchDecorator {
    public UniversityResearcher() {
        super();
    }

    public UniversityResearcher(User user) {
        super();
        setUser(user);
    }

    public UniversityResearcher(User user, List<ResearchPaper> papers, List<ResearchProject> projects) {
        super(user, papers, projects);
    }

    public UniversityResearcher(Researcher researcher) {
        super(researcher);
    }
}
