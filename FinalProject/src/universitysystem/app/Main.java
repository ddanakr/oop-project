package universitysystem.app;

import universitysystem.controllers.AdminController;
import universitysystem.controllers.AuthController;
import universitysystem.controllers.ManagerController;
import universitysystem.controllers.MessageController;
import universitysystem.controllers.StudentController;
import universitysystem.controllers.TeacherController;
import universitysystem.controllers.TechSupportController;
import universitysystem.controllers.UserRole;
import universitysystem.database.Database;
import universitysystem.models.users.Manager;
import universitysystem.models.users.Student;
import universitysystem.models.users.Teacher;
import universitysystem.models.users.User;
import universitysystem.services.AdminServiceImpl;
import universitysystem.services.AuthServiceImpl;
import universitysystem.services.InMemoryMessageService;
import universitysystem.services.RequestServiceImpl;
import universitysystem.services.TechSupportServiceImpl;
import universitysystem.views.AdminView;
import universitysystem.views.LoginView;
import universitysystem.views.MessageView;
import universitysystem.views.TechSupportView;

public class Main {
    public static void main(String[] args) {
        Database database = Database.load();
        DemoDataSeeder.seedIfEmpty(database);

        AuthController authController = new AuthController(new AuthServiceImpl());
        MessageController messageController = new MessageController(new InMemoryMessageService(database), new MessageView());
        LoginView loginView = new LoginView();

        boolean running = true;
        while (running) {
            User user = authController.runLogin(loginView);
            if (user == null) {
                running = false;
                continue;
            }
            openRoleMenu(user, authController, messageController);
            database.save();
        }

        database.save();
    }

    private static void openRoleMenu(User user, AuthController authController, MessageController messageController) {
        UserRole role = authController.getRole(user);
        switch (role) {
            case STUDENT:
                new StudentController((Student) user).start();
                break;
            case TEACHER:
                new TeacherController((Teacher) user).start();
                break;
            case MANAGER:
                new ManagerController((Manager) user).run();
                break;
            case ADMIN:
                new AdminController(
                        new AdminServiceImpl(),
                        new AdminView(),
                        authController,
                        messageController,
                        user
                ).run();
                break;
            case TECH_SUPPORT:
                new TechSupportController(
                        new TechSupportServiceImpl(new RequestServiceImpl()),
                        new TechSupportView(),
                        authController,
                        messageController,
                        user
                ).run();
                break;
            default:
                System.out.println("Unknown role.");
        }
    }
}
