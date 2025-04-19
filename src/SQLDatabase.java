import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLDatabase {
	private Connection connection;
	
	//----------------------------------------------------------
	public boolean connectToDB() {
		connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:StudentDatabase.db");
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		return true;
		
	}
	
	public void disconnectDB() {
		if (connection != null) {
		       try 
		       {     
		    	   connection.close(); 
		       } 
	           catch (SQLException ex) 
		       { 
	        	   System.out.println(ex.getMessage());
        	   }
		}
	}
	
	//----------------------------------------------------------
	public boolean createTABLES() {
		if (connection == null) {
			return false;
		}
		
		String table_1 = "CREATE TABLE IF NOT EXISTS students (" 	//name of table 
						+ "id integer PRIMARY KEY," 				//ID
						+ "name varchar(255) NOT NULL,"				//name
						+ "surname varchar(255) NOT NULL,"			//surname
						+ "dateOfBirth varchar(12) NOT NULL,"		//date of birth 
						+ "specialisation varchar(20)"  
						+ ");";
		
		String table_2 = "CREATE TABLE IF NOT EXISTS grades ("		//name of table
						+ "studentId integer,"						//ID of student
						+ "grade integer"							//students grade
						+ ");";
		
		String table_3 = "CREATE TABLE IF NOT EXISTS priorityIds (" //name of table
						+ "priorIds integer"						//priority ID
						+ ");";
		
		try {
			Statement stm = connection.createStatement();
			stm.execute(table_1);
			stm.execute(table_2);
			stm.execute(table_3);
			return true;
			
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	//----------------------------------------------------------
	public boolean insert(Database db) {
		String massStudent = "INSERT INTO students(id,name,surname,dateOfBirth,specialisation) VALUES(?,?,?,?,?)";
		String massGrades = "INSERT INTO grades(studentId,grade) VALUES(?,?)";
		String primaryId = "INSERT INTO priorityIds (priorIds) VALUES(?)";
		
		
		try {
			PreparedStatement psStudent = connection.prepareStatement(massStudent);
			PreparedStatement psGrades = connection.prepareStatement(massGrades);
			PreparedStatement psIds = connection.prepareStatement(primaryId);
			
			for (var student : db.data.values()) {
				System.out.println(	student.getId());
				
				if(student.grades != null) {
					for (int grade : student.grades) {
						psGrades.setInt(1, student.getId());
						psGrades.setInt(2, grade);
						
						psGrades.addBatch();
						psGrades.clearParameters();
					}
					
				}
				
				psStudent.setInt(1, student.getId());
				psStudent.setString(2, student.getName());
				psStudent.setString(3, student.getSurename());
				psStudent.setString(4, student.getBirthDate().toString());
				psStudent.setString(5, student.getSpecialisation().toString());
				
				psStudent.addBatch();
				psStudent.clearParameters();
			}
			
			psStudent.executeBatch();
			psGrades.executeBatch();
			
			if(!db.getPriorityIDs().isEmpty()) {
				for (int pG :db.getPriorityIDs()) {
					psIds.setInt(1, pG);
					psIds.addBatch();
					psIds.clearParameters();
				}
				psGrades.execute();
			}
			
			return true;
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	//----------------------------------------------------------
	
	
	
	//----------------------------------------------------------
	public boolean get() {
		String get = "SELECT id, name FROM students";
		
		try {
			Statement stm = connection.createStatement();
			ResultSet rs = stm.executeQuery(get);
			
			while (rs.next()) {
				System.out.println(rs.getInt("id") + "\t" + rs.getString("name"));;
			}
			
				
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		return true;
	}
}
