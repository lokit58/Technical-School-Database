//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.import java.time.LocalDateTime;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
    	
    	Database database = new Database();
    	LocalDate localDate = LocalDate.of(2002, 11, 23);
    	//database.addStudent("idk", "lol", localDate, Student.Specialisation.CYBERSECURITY);
    	//database.giveGrade(0, 3);
    	
    	//System.out.println(database.getInfo(0));
    	//database.writeStudentsIntoFile(0,0,0);
    	
    	
    	database.readStudentsFromFile();
    	//System.out.println(database.data.get(0).getBirthDate().toString());
    	//System.out.println(database.data.get(0).getSpecialisation().toString());
    	/*
    	System.out.println(database.getInfo(0));
    	System.out.println(database.getInfo(1));
    	System.out.println(database.getInfo(2));
    	System.out.println(database.getInfo(14));
    	*/
    	
    	
    	SQLDatabase DB = new SQLDatabase();
    	
    	System.out.println(DB.connectToDB());
    	System.out.println(DB.createTABLES());
    	System.out.println(DB.insert(database));
    	System.out.println(DB.get());
    	
    	DB.disconnectDB();
    	
    	/*
    	sqltest cdf = new sqltest();
    	cdf.connect();
    	cdf.createTable();
    	cdf.insertRecord("ahoj", 244424, "hovno", 2424);
    	cdf.selectAll();
    	cdf.disconnect();
    	*/
    }
}