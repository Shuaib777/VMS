import ui.MainMenu;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Visitor Management System!");
        MainMenu menu = new MainMenu();
        menu.showMainMenu();
        System.out.println("Thank you for using the system. Goodbye!");
    }
}
