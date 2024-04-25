//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    //global variables
    public static int isAdmin;

    public static void main(String[] args) {
        // Alt + Enter for suggestions

    }


    public static void loginScreen() {
        System.out.println("1 - Giriş Yap");
        System.out.println("2 - Kayıt Ol");
        System.out.println("3 - Ayarlar");
        System.out.println("4 - Programı Kapat");
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
        System.out.println();
    }

}