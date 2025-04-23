import java.time.LocalDate;

public class TEST {

    public static void main(String[] args) {
        InputManager input = new InputManager();
        Database database = new Database();
        SQLDatabase sqlDB = new SQLDatabase();
        sqlDB.connectToDB();
        sqlDB.createTABLES();

        boolean run = true;

        while (run) {
            System.out.println("----- Vyberte pozadovanou cinnost:");
            System.out.println("1  .. vlozeni noveho studenta");
            System.out.println("2  .. zadat studentovi znamku");
            System.out.println("3  .. odstranit studenta");
            System.out.println("4  .. najit studenta podle ID ");
            System.out.println("5  .. spustit dovednost studenta ");
            System.out.println("6  .. abecedne vypsat studenty podle oboru");
            System.out.println("7  .. Vypis prumeru znamek v oborech:");
            System.out.println("8  .. vypis poctu studentu v oborech");
            System.out.println("9  .. ulozit studenta do souboru");
            System.out.println("10 .. nacist studenta ze souboru");
            System.out.println("0  .. ulozit do SQL databaze a ukoncit aplikaci ");
            int choice = input.getInt("Zadej číslo volby: ");

            switch (choice) {
                case 1 -> {
                    String name = input.getString("Zadejte jméno: ");
                    String surname = input.getString("Zadejte příjmení: ");
                    int year = input.getInt("Zadejte rok narození: ");
                    int month = input.getInt("Zadejte měsíc narození: ");
                    int day = input.getInt("Zadejte den narození: ");
                    String specStr = input.getString("Zadejte specializaci (CYBERSECURITY/TELECOMMUNICATIONS): ");
                    Student.Specialisation spec = Student.Specialisation.valueOf(specStr.toUpperCase());

                    database.addStudent(name, surname, LocalDate.of(year, month, day), spec);
                }
                case 2 -> {
                    int id = input.getInt("Zadejte ID studenta: ");
                    int grade = input.getInt("Zadejte známku: ");
                    database.giveGrade(id, grade);
                }
                case 3 -> {
                    int id = input.getInt("Zadejte ID studenta k odstranění: ");
                    if (database.removeStudent(id)) {
                        System.out.println("Student odstraněn.");
                    } else {
                        System.out.println("Student nebyl nalezen.");
                    }
                }
                case 4 -> {
                    int id = input.getInt("Zadejte ID studenta: ");
                    System.out.println(database.getInfo(id));
                }
                case 5 -> {
                    int id = input.getInt("Zadejte ID studenta: ");
                    System.out.println("Dovednost: " + database.useSpeciality(id));
                }
                case 6 -> {
                    for (Student s : database.getSortedListBySurnames()) {
                        System.out.println(s);
                        System.out.println("---------------------");
                    }
                }
                case 7 -> {
                    double[] avg = database.getAvreageGradeOfStudentGroup();
                    System.out.printf("TELECOMMUNICATIONS: %.2f\n", avg[0]);
                    System.out.printf("CYBERSECURITY: %.2f\n", avg[1]);
                }
                case 8 -> {
                    int[] count = database.getQuantityOfStudentsInEachGroup();
                    System.out.printf("TELECOMMUNICATIONS: %d\n", count[0]);
                    System.out.printf("CYBERSECURITY: %d\n", count[1]);
                }
                case 9 -> {
                    int count = input.getInt("Kolik studentů chcete uložit?: ");
                    int[] ids = new int[count];
                    for (int i = 0; i < count; i++) {
                        ids[i] = input.getInt("Zadejte ID: ");
                    }
                    if (database.writeStudentsIntoFile(ids)) {
                        System.out.println("Uloženo do souboru.");
                    } else {
                        System.out.println("Chyba při ukládání.");
                    }
                }
                case 10 -> {
                    if (database.readStudentsFromFile()) {
                        System.out.println("Data byla načtena.");
                    } else {
                        System.out.println("Chyba při načítání.");
                    }
                }
                case 0 -> {
                    sqlDB.removeDataFromSQL();
                    sqlDB.insertDatabase(database);
                    sqlDB.disconnectDB();
                    run = false;
                }
                default -> System.out.println("Neplatná volba.");
            }
        }
    }
}
