//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.import java.time.LocalDateTime;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
    	Database database = new Database();
    	LocalDate localDate = LocalDate.of(2002, 11, 23);
    	database.addStudent("idk", "lol", localDate, Student.Specialisation.CYBERSECURITY);
    	database.giveGrade(0, 3);
    	System.out.println(database.getInfo(0));
    	database.writeStudentsIntoFile(0,0,0);
    	
    }
}