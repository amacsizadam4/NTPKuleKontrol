//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.*;
import java.util.Scanner;


public class Main {
    //global variables
    public static int globalIsAdmin;
    public static String globalUser;

    public static void main(String[] args) {
        // ÖNEMLİ!
        loginScreen();

    }

    static class User {
        String username;
        String password;
        int isAdmin;
    }

    private static void loginScreen() {
        globalIsAdmin = 3;
        System.out.println("═════════════════════════════════════════════");
        System.out.println("1 - Giriş Yap");
        System.out.println("2 - Kayıt Ol");
        System.out.println("3 - Ayarlar");
        System.out.println("4 - Programı Kapat");
        System.out.println("═════════════════════════════════════════════");
        System.out.println("Seçiminizi yapınız: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 4:
                System.exit(0);
            default:
                System.out.println("Yanlış seçim yaptınız, tekrar deneyin.");
                loginScreen();
        }
    }

    private static void register() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Kullanıcı adınızı giriniz:");
        String username = scanner.nextLine();

        System.out.println("Şifrenizi giriniz:");
        String password = scanner.nextLine();

        System.out.printf("1 - Yönetici olarak kaydet\n2 - Kullanıcı olarak kaydet\nSeçiminizi yapınız : ");
        int choice = scanner.nextInt();
        int isAdmin = 0;
        if (choice == 1) {
            isAdmin = 1;
        } else if (choice == 2) {
            isAdmin = 0;
        } else {
            System.out.println("Geçersiz seçim, tekrar deneyiniz.");
            register();

        }

        // Write user data to file
        try {
            FileWriter writer = new FileWriter("data/users.txt", true);
            writer.write(username + "," + password + "," + isAdmin + "\n");
            writer.close();
            System.out.println("Kullanıcı başarıyla kaydedildi.");
        } catch (IOException e) {
            System.out.println("Kullanıcıyı kaydederken sorun oluştu.");
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("═════════════════════════════════════════════");

        System.out.print("Kullanıcı adını giriniz: ");
        String username = scanner.nextLine();

        System.out.print("Şifrenizi giriniz: ");
        String password = scanner.nextLine();

        int authResult = authenticateUser(username, password, 0);
        if (authResult == 0) {
            authResult = authenticateUser(username, password, 1);
        }

        if (authResult == 1 || authResult == 2) {
            System.out.println("Giriş Başarılı!");
            // Assign globalUser to username
            globalUser = username;
            // GİRİŞ BAŞARILI MI TEST
            //System.out.printf("DEBUG -- %s, %d%n", globalUser, globalIsAdmin);
            mainMenu();
        } else {
            System.out.println("Giriş başarısız, geçersiz bilgiler.");
            loginScreen();
        }
    }


    private static void mainMenu() {
        System.out.println("Hoşgeldiniz: "+globalUser);
        if (globalIsAdmin == 1) {
            admin();
        } else customer();

    }

    private static void customer() {
        System.out.println("═════════════════════════════════════════════");
        System.out.println("1 - Tüm Uçuşları Göster");
        System.out.println("2 - Uçuş Ara");
        System.out.println("3 - Uçuş Yolcu Ol");
        System.out.println("4 - Destek Mesajı Gönder");
        System.out.println("9 - Çıkış Yap");
        System.out.println("═════════════════════════════════════════════");
        System.out.println("Seçiminizi yapınız: ");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                readAndPrintFlights();
                break;
            case 2:
                searchFlight();
                break;
            case 3:
                addPassenger();
                break;
            case 9:
                loginScreen();
                break;
            default:
                System.out.println("Yanlış seçim yaptınız! Tekrar deneyiniz.");
                customer();
        }

    }

    public static void admin() {
        System.out.println("═════════════════════════════════════════════");
        System.out.println("1 - Uçuş Ekle");
        System.out.println("2 - Tüm Uçuşları Göster");
        System.out.println("3 - Uçuş Ara");
        System.out.println("4 - Uçuş Sil");
        System.out.println("5 - Yolcu Ekle");
        System.out.println("6 - Yolcu Sil");
        System.out.println("7 - Yolcu Görüntüle");
        System.out.println("9 - Çıkış Yap");
        System.out.println("═════════════════════════════════════════════");
        System.out.println("Seçiminizi yapınız.");
        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                addFlight();
                break;
            case 2:
                readAndPrintFlights();
                break;
            case 3:
                searchFlight();
                break;
            case 4:
                deleteFlight();
                break;
            case 5:
                addPassenger();
            case 6:
                deletePassenger();
                break;
            case 7:
                showPassenger();
                break;
            case 9:
                loginScreen();
                break;

            default:
                System.out.println("Yanlış seçim yaptınız! Tekrar deneyiniz.");
                admin();
        }
    }

    static int authenticateUser(String username, String password, int isAdmin) {
        File file = new File("data/users.txt");
        Scanner scanner = null;

        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            System.out.println("Dosya açılamadı!");
            System.exit(1);
        }

        while (scanner.hasNextLine()) {
            String[] userDetails = scanner.nextLine().split(",");
            if (userDetails.length == 3) {
                User user = new User();
                user.username = userDetails[0];
                user.password = userDetails[1];
                user.isAdmin = Integer.parseInt(userDetails[2]);

                if (username.equals(user.username) && password.equals(user.password)) {
                    globalIsAdmin = user.isAdmin;
                    scanner.close();
                    return (user.isAdmin == 1) ? 2 : 1; // 2 for admin, 1 for regular user
                }
            }
        }
        if (scanner != null) {
            scanner.close();
        }
        return 0; // authentication failed
    }

    static class Flight {
        String flightNumber;
        String airline;
        String destination;
        String departureAirport;
        String departureTime;
        String arrivalTime;
        String status;
        String gate;
        String terminal;
        String aircraftType;

        public Flight(String flightNumber, String airline, String destination, String departureAirport,
                      String departureTime, String arrivalTime, String status, String gate, String terminal,
                      String aircraftType) {
            this.flightNumber = flightNumber;
            this.airline = airline;
            this.destination = destination;
            this.departureAirport = departureAirport;
            this.departureTime = departureTime;
            this.arrivalTime = arrivalTime;
            this.status = status;
            this.gate = gate;
            this.terminal = terminal;
            this.aircraftType = aircraftType;
        }


        @Override
        public String toString() {
            return "Flight Number: " + flightNumber + ", Airline: " + airline + ", Destination: " + destination +
                    ", Departure Airport: " + departureAirport + ", Departure Time: " + departureTime +
                    ", Arrival Time: " + arrivalTime + ", Status: " + status + ", Gate: " + gate +
                    ", Terminal: " + terminal + ", Aircraft Type: " + aircraftType;
        }

        // Constructor and other methods
    }

    public static class Passenger {
        private String flightID;
        private String name;
        private int age;
        private String contactInformation;

        public Passenger(String flightID, String name, int age, String contactInformation) {
            this.flightID = flightID;
            this.name = name;
            this.age = age;
            this.contactInformation = contactInformation;
        }

    }

    static void addFlight() {
        // Implementation to add a flight
        Scanner scanner = new Scanner(System.in);

        System.out.print("Uçuş Kodu Giriniz: ");
        String flightNumber = scanner.nextLine();

        System.out.print("Havayolu Şirketi: ");
        String airline = scanner.nextLine();

        System.out.print("Varış Noktası: ");
        String destination = scanner.nextLine();

        System.out.print("Kalkış Havalimanı: ");
        String departureAirport = scanner.nextLine();

        System.out.print("Hareket Saati: (YYYY-AA-GG SS:DD): ");
        String departureTime = scanner.nextLine();

        System.out.print("Varış Zamanı: (YYYY-AA-GG SS:DD): ");
        String arrivalTime = scanner.nextLine();

        System.out.print("Durum: ");
        String status = scanner.nextLine();

        System.out.print("Kapı: ");
        String gate = scanner.nextLine();

        System.out.print("Terminal: ");
        String terminal = scanner.nextLine();

        System.out.print("Uçak Tipi ");
        String aircraftType = scanner.nextLine();

        Flight newFlight = new Flight(flightNumber, airline, destination, departureAirport, departureTime,
                arrivalTime, status, gate, terminal, aircraftType);

        //Uçuş kodu ile txt dosyası oluştur
        String filename = "data/" + flightNumber + ".txt";
        try {
            // Create a new file
            File file = new File(filename);
            if (file.createNewFile()) {
                System.out.println("Dosya oluşturuldu: " + filename);
            } else {
                System.out.println("Dosya zaten mevcut: " + filename);
            }
        } catch (IOException e) {
            System.err.println("Dosya oluşturalamadı: " + e.getMessage());
        }
        // Write the flight information to the file
        writeFlightToFile(newFlight);
    }

    static void writeFlightToFile(Flight flight) {

        try (PrintWriter writer = new PrintWriter(new FileWriter("data/flights.txt", true))) {
            String flightData = String.join(",", flight.flightNumber, flight.airline, flight.destination,
                    flight.departureAirport, flight.departureTime, flight.arrivalTime, flight.status,
                    flight.gate, flight.terminal, flight.aircraftType);
            writer.println(flightData);
            System.out.println("Uçuş bilgileri başarıyla kaydedildi!");
        } catch (IOException e) {
            System.out.println("Uçuş bilgileri kaydedilirken bir sorun oluştu: " + e.getMessage());
        }
        procComplete();
    }

    static void readAndPrintFlights() {
        File file = new File("data/flights.txt");
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] flightData = scanner.nextLine().split(",");
                if (flightData.length == 10) { // Ensure all fields are present
                    Flight flight = new Flight(flightData[0], flightData[1], flightData[2], flightData[3],
                            flightData[4], flightData[5], flightData[6], flightData[7], flightData[8], flightData[9]);

                    // Print each flight detail individually
                    System.out.println("Flight Number: " + flight.flightNumber);
                    System.out.println("Airline: " + flight.airline);
                    System.out.println("Destination: " + flight.destination);
                    System.out.println("Departure Airport: " + flight.departureAirport);
                    System.out.println("Departure Time: " + flight.departureTime);
                    System.out.println("Arrival Time: " + flight.arrivalTime);
                    System.out.println("Status: " + flight.status);
                    System.out.println("Gate: " + flight.gate);
                    System.out.println("Terminal: " + flight.terminal);
                    System.out.println("Aircraft Type: " + flight.aircraftType);
                    System.out.println();
                } else {
                    System.out.println("Dosya sonu. " + String.join(",", flightData));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + e.getMessage());
        }

        procComplete();
    }


    static Flight parseFlightInfo(String line) {
        String[] flightData = line.split(",");
        return new Flight(flightData[0], flightData[1], flightData[2], flightData[3],
                flightData[4], flightData[5], flightData[6], flightData[7], flightData[8], flightData[9]);
    }

    static void searchFlight() {
        try (Scanner fileScanner = new Scanner(new File("data/flights.txt"))) {
            Scanner inputScanner = new Scanner(System.in);
            System.out.print("Aradığınız anahtar kelimeyi giriniz: ");
            String searchKeyword = inputScanner.nextLine().trim();

            boolean found = false;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if (line.contains(searchKeyword)) {
                    found = true;
                    Flight flight = parseFlightInfo(line);
                    System.out.println(flight.toString()); // Ensure toString() is called
                }
            }

            if (!found) {
                System.out.println("Bu anahtar kelime ile bir uçuş bulunamadı.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Dosya bulunamadı: " + e.getMessage());
        }

        procComplete();
    }

    static void deleteFlight() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Silmek istediğiniz uçuş kodunu giriniz: ");
            int flightCode = scanner.nextInt();
            // Create a temporary file
            File inputFile = new File("data/flights.txt");
            File tempFile = new File("data/flights_temp.txt");

            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String currentLine;
            boolean found = false;

            // Read each line from the input file
            while ((currentLine = reader.readLine()) != null) {
                // Check if the current line contains the flight code
                if (currentLine.startsWith(flightCode + ",")) {
                    found = true;
                    continue; // Skip writing this line
                }
                // Write the current line to the temporary file
                writer.write(currentLine + System.lineSeparator());
            }

            writer.close();
            reader.close();

            // Replace the original file with the temporary file
            if (found) {
                if (inputFile.delete()) {
                    if (!tempFile.renameTo(inputFile)) {
                        System.out.println("Dosya değiştirirken sorun oluştu.");
                        return;
                    }
                } else {
                    System.out.println("Dosya silinirken sorun oluştu.");
                    return;
                }
                System.out.println(flightCode + " kodlu uçuş başarıyla silindi.");
            } else {
                System.out.println(flightCode + " kodlu uçuş bulunamadı.");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        procComplete();
    }

    static void addPassenger() {
    // UÇUŞ KODU İLE .TXT OLUŞTURUP KULLANICIDAN YOLCU BİLGİLERİ ALINIP DOSYAYA EKLENİLECEK. Örn. 13.txt içinde: flightID,passName,passAge,passContactInfo
        Scanner scanner = new Scanner(System.in);

        System.out.print("Uçuş Kodunu Giriniz: ");
        String flightID = scanner.nextLine();

        System.out.print("Yolcu İsmi: ");
        String name = scanner.nextLine();

        int age = 0;
        boolean validAge = false;
        while (!validAge) {
            try {
                System.out.print("Yolcu Yaşı: ");
                age = Integer.parseInt(scanner.nextLine());
                validAge = true;
            } catch (NumberFormatException e) {
                System.out.println("Lütfen doğru yaş giriniz.");
            }
        }

        System.out.print("Yolcu iletişim bilgileri giriniz: (Telefon No, ÖRN: +905388413431) ");
        String contactInformation = scanner.nextLine();

        Passenger passenger = new Passenger(flightID, name, age, contactInformation);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/"+flightID + ".txt", true))) {
            String passengerInfo = flightID + "," + name + "," + age + "," + contactInformation;
            writer.write(passengerInfo);
            writer.newLine();
            System.out.println("Yolcu bilgileri dosyaya başarıyla eklendi.");
        } catch (IOException e) {
            System.out.println("Yolcu bilgisi kaydedilirken hata oluştu: " + e.getMessage());
        }

        procComplete();
    }

    static void showPassenger(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Yolcularını görmek istediğiniz uçuş kodunu giriniz: ");
        int flightID = scanner.nextInt();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/" + flightID + ".txt"))) {
            System.out.println("Passenger Information for Flight ID: " + flightID);
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                String[] passengerDetails = currentLine.split(",");
                if (passengerDetails.length == 4) {
                    System.out.println("Flight ID: " + passengerDetails[0] +
                            ", Name: " + passengerDetails[1] +
                            ", Age: " + passengerDetails[2] +
                            ", Contact Information: " + passengerDetails[3]);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(flightID + "kodlu uçuşla ilgili bilgi bulunamadı.");
        } catch (IOException e) {
            System.out.println("Yolcu bilgisi gösterirken hata oluştu: " + e.getMessage());
        }
        procComplete();
    }

    static void deletePassenger() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Uçuş Kodunu Giriniz: ");
        String flightID = scanner.nextLine();
        System.out.println("Silmek İstediğiniz Yolcunun İletişim Bilgisini Giriniz: ");
        String contactInfo = scanner.nextLine();

        // uçuş txt içinde belirli yolcuyu silecek
        File inputFile = new File("data/" + flightID + ".txt");
        File tempFile = new File("data/" + flightID + "_temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String currentLine;
            boolean found = false;

            while ((currentLine = reader.readLine()) != null) {
                String[] passengerDetails = currentLine.split(",");
                if (passengerDetails.length == 4 && passengerDetails[3].equals(contactInfo)) {
                    found = true;
                    continue; // Skip this line
                }
                writer.write(currentLine);
                writer.newLine();
            }

            if (!found) {
                System.out.println(contactInfo + " iletişim bilgisiyle " + flightID + "kodlu uçuşta yolcu bulunamadı.");
            } else {
                System.out.println(contactInfo + " iletişim bilgisiyle " + flightID + "kodlu uçuştaki yolcu BAŞARIYLA silindi.");
            }

        } catch (IOException e) {
            System.out.println("Dosya işlenirken hata oluştu: " + e.getMessage());
        }

        // Replace the original file with the updated file
        if (!inputFile.delete()) {
            System.out.println("Orijinal dosya değiştirilemedi.");
        } else if (!tempFile.renameTo(inputFile)) {
            System.out.println("Geçiçi dosya yeniden adlandırılamadı.");
        }
        procComplete();
    }

    static void procComplete() {
        System.out.print("═════════════════════════════════════════════\nİşlem Tamamlandı\nAna menüye dönmek için herhangi bir tuşa basınız.\n═════════════════════════════════════════════\n");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        mainMenu();
    }
}

