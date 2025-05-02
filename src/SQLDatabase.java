import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

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
	public boolean insertDatabase(Database db) {
		String massStudent = "INSERT INTO students(id,name,surname,dateOfBirth,specialisation) VALUES(?,?,?,?,?)";
		String massGrades = "INSERT INTO grades(studentId,grade) VALUES(?,?)";
		String primaryId = "INSERT INTO priorityIds (priorIds) VALUES(?)";
		
		
		try {
			PreparedStatement psStudent = connection.prepareStatement(massStudent);
			PreparedStatement psGrades = connection.prepareStatement(massGrades);
			PreparedStatement psIds = connection.prepareStatement(primaryId);
			
			for (var student : db.data.values()) {
				if (student.grades != null) {
					if(!student.grades.isEmpty()) {
						for (int grade : student.grades) {
							//System.out.println(student.getId() + ":" + grade);
							psGrades.setInt(1, student.getId());
							psGrades.setInt(2, grade);
							
							psGrades.addBatch();
							psGrades.clearParameters();
						}
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
					//System.out.println(pG);
					psIds.setInt(1, pG);
					
					psIds.addBatch();
					psIds.clearParameters();
				}
				psIds.execute();
			}
			
			return true;
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	//----------------------------------------------------------
	
	public boolean removeDataFromSQL() {
		String removeStudent ="DELETE FROM students";
		String removeGrades ="DELETE FROM grades";
		String removePriorityIDs ="DELETE FROM priorityIds";
		
		try {
			Statement stm = connection.createStatement();
			stm.execute(removeStudent);
			stm.execute(removeGrades);
			stm.execute(removePriorityIDs);
			
			return true;
		}
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	
	//----------------------------------------------------------
	public boolean getDatabase(Database db) {
		String getStudnets = "SELECT * FROM students";
		String getGrades = "SELECT * FROM grades";
		String getPriorityID = "SELECT * FROM priorityIds";
		
		try {
			Statement stm = connection.createStatement();
			
			ResultSet rs = stm.executeQuery(getStudnets);
			while (rs.next()) {
				
				db.addStudentToDatabaseWithID(	rs.getInt("id"), 
												rs.getString("name"), 
												rs.getString("surname"),
												LocalDate.parse(rs.getString("dateOfBirth")),
												Student.Specialisation.valueOf(rs.getString("specialisation")), 
												null);
			}
			
			rs = stm.executeQuery(getGrades);
			while (rs.next()) {
				if (rs.getInt("studentId") == 0 && rs.getInt("grade") == 0) {
					continue;
				}
				db.giveGrade(rs.getInt("studentId"),rs.getInt("grade"));
				
			}
			
			rs = stm.executeQuery(getPriorityID);
			while (rs.next()) {
				db.addPriorityID(rs.getInt("priorIds"));;
			}		
			
			return true;
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
}
