//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;


public class Main {

    //global variables
    public static int isAdmin=1;

    public static void main(String[] args) {
        // Alt + Enter for suggestions
        loginScreen();

    }


    public static void loginScreen() {
        System.out.println("1 - Giriş Yap");
        System.out.println("2 - Kayıt Ol");
        System.out.println("3 - Ayarlar");
        System.out.println("4 - Programı Kapat");
    }

    private static void register(){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to User Registration System");
        System.out.println("Please enter your username:");
        String username = scanner.nextLine();

        System.out.println("Please enter your password:");
        String password = scanner.nextLine();

        System.out.println("Are you an administrator? (0 for no, 1 for yes):");
        int isAdmin = scanner.nextInt();

        // Write user data to file
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + "," + password + "," + isAdmin + "\n");
            writer.close();
            System.out.println("User registered successfully.");
        } catch (IOException e) {
            System.out.println("An error occurred while registering user.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    public static void mainMenu() {
        if (isAdmin==1) {
            admin();
        }
        else customer();

    }

    public static void customer() {

    }
    public static void admin() {
        System.out.println("Testing purposes.");
    }

}