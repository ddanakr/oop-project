package universitysystem.views;

import universitysystem.utils.ConsoleUtils;

public class LoginView {
    public void showMenu() {
        ConsoleUtils.printHeader("Login");
        System.out.println("1. Sign in");
        System.out.println("0. Exit");
    }

    public int readMenuChoice() {
        return ConsoleUtils.getIntInput("Choose option: ");
    }

    public LoginInput readLoginInput() {
        String login = ConsoleUtils.getInput("Login: ").trim();
        String password = ConsoleUtils.getInput("Password: ");
        return new LoginInput(login, password);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showError(String message) {
        System.out.println("Error: " + message);
    }

    public static class LoginInput {
        private final String login;
        private final String password;

        public LoginInput(String login, String password) {
            this.login = login;
            this.password = password;
        }

        public String getLogin() {
            return login;
        }

        public String getPassword() {
            return password;
        }
    }
}
