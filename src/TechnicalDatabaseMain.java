import java.time.LocalDate;
import java.util.zip.ZipEntry;

public class TechnicalDatabaseMain {

    public static void main(String[] args) {

        InputManager input = new InputManager();
        Database database = new Database();
        SQLDatabase sqlDB = new SQLDatabase();
        sqlDB.connectToDB();
        sqlDB.createTABLES();
        sqlDB.getDatabase(database);
        
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
                    boolean added = false;

                    while (!added) {
                        try {
                            String name = input.getString("Zadejte jméno: ");
                            String surname = input.getString("Zadejte příjmení: ");
                            int year = input.getInt("Zadejte rok narození: ");
                            int month = input.getInt("Zadejte měsíc narození: ");
                            int day = input.getInt("Zadejte den narození: ");
                            String specStr = input.getString("Zadejte specializaci (CYBERSECURITY/TELECOMMUNICATIONS): ");

                            LocalDate birthDate = LocalDate.of(year, month, day);
                            Student.Specialisation spec = Student.Specialisation.valueOf(specStr.toUpperCase());

                            database.addStudent(name, surname, birthDate, spec);
                            System.out.println("Student úspěšně přidán.");
                            added = true;

                        } catch (java.time.DateTimeException e) {
                            System.out.println("Neplatné datum narození: " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            System.out.println("Neplatná specializace. Zkuste CYBERSECURITY nebo TELECOMMUNICATIONS.");
                        } catch (Exception e) {
                            System.out.println("Chyba při vkládání studenta: " + e.getMessage());
                        }
                    }
                }
                case 2 -> {
                    int id = input.getInt("Zadejte ID studenta: ");

                    if (!database.data.containsKey(id)) {
                        System.out.println("Student s tímto ID neexistuje.");
                        break;
                    }

                    int grade;
                    while (true) {
                        grade = input.getInt("Zadejte známku (1–5): ");
                        if (grade >= 1 && grade <= 5) {
                            break;
                        } else {
                            System.out.println("Neplatná známka. Zadejte číslo mezi 1 a 5.");
                        }
                    }

                    database.giveGrade(id, grade);
                    System.out.println(".");
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
                    String info = database.getInfo(id);
                    
                    if (info.isEmpty() || info.isBlank()) {
                        System.out.println("Student nebyl nalezen.");
                    } 
                    else {
                        System.out.println(info);
                    }
                    
                }
                case 5 -> {
                    int id = input.getInt("Zadejte ID studenta: ");
                    if (!database.data.containsKey(id)) {
                        System.out.println("Chyba: Student s tímto ID neexistuje.");
                    } else {
                        System.out.println("Dovednost: " + database.useSpeciality(id));
                    }
                }
                case 6 -> {
                	System.out.println("Ze ktereho oboru vypsat studenty?(CYBERSECURITY/TELECOMMUNICATIONS/BOTH)");
                	String type = input.getString();
                	
                	switch (type) {
					case "CYBERSECURITY" -> {
						for (Student s : database.getSortedListBySurnames()) {
							if (s instanceof CyberStudent) {
								System.out.println(s);
		                        System.out.println("---------------------");
							}
	                        
	                    }
						
						}
					
					case "TELECOMMUNICATIONS" -> {
						for (Student s : database.getSortedListBySurnames()) {
							if (s instanceof TeleStudent) {
								System.out.println(s);
		                        System.out.println("---------------------");
							}
	                        
	                    }
						
						}
					
					case "BOTH" -> {
						 for (Student s : database.getSortedListBySurnames()) {
		                        System.out.println(s);
		                        System.out.println("---------------------");
		                    }
						
					}
					default ->{
						System.out.println("Neplatná volba.");
						}
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
