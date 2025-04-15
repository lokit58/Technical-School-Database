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
		
		try {
			Statement stm = connection.createStatement();
			stm.execute(table_1);
			return true;
			
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	//----------------------------------------------------------
	public boolean insert() {
		String ins = "INSERT INTO students(id,name,surname,dateOfBirth,specialisation) VALUES(?,?,?,?,?)"; 
		
		try {
			PreparedStatement stm = connection.prepareStatement(ins);
			stm.setInt(1, 0);
			stm.setString(2, "Vit");
			stm.setString(3, "Nemecek");
			stm.setString(4, "2002-11-23");
			stm.setString(5, "CYBERSECURITY");
			stm.execute();
			return true;
			
		} catch (SQLException ex) {
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
			
			
			System.out.println(rs.getInt("id") + "\t" + rs.getString("name"));
				
		} 
		catch (SQLException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
		
		return true;
	}
}
