import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class Database {
	public Map<Integer, Student> data;
	private ArrayList<Integer> priorityIDs;
	private int nextID;
	
	public Database() {
		data = new HashMap<Integer, Student>();
		priorityIDs = new ArrayList<Integer>();
	}
	private int getNextId() {
		if (data.isEmpty()) {
			return 0;
		}
		return Collections.max(data.keySet()) + 1 ;
	}
	
	//----------------------------------------------------------
	public void addStudentToDatabaseWithID(int id, String name, String surname, LocalDate birtDate, Student.Specialisation specialisation, ArrayList<Integer> grades) {
		if (specialisation == Student.Specialisation.CYBERSECURITY) {
			data.put(id, new CyberStudent(nextID, name, surname, birtDate, specialisation, grades));
		}
		else if (specialisation == Student.Specialisation.TELECOMMUNICATIONS) {
			data.put(id, new TeleStudent(nextID, name, surname, birtDate, specialisation, grades));
		}
	}
		
	
	private void addStudentToDatabase(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation, ArrayList<Integer> grades) {
		nextID = getNextId();
		for (int i = 0; i < priorityIDs.size(); i++) {
			if(priorityIDs.get(i)<= nextID) {
				nextID = priorityIDs.get(i);
				priorityIDs.remove(i);
				break;
			}
		}
		
		if (specialisation == Student.Specialisation.CYBERSECURITY) {
			data.put(nextID, new CyberStudent(nextID, name, surname, birtDate, specialisation, grades));
		}
		else if (specialisation == Student.Specialisation.TELECOMMUNICATIONS) {
			data.put(nextID, new TeleStudent(nextID, name, surname, birtDate, specialisation, grades));
		}
	}
	
	public void addStudent(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation, ArrayList<Integer> grades) {
		addStudentToDatabase(name, surname, birtDate, specialisation, grades);
	}
	
	public void addStudent(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation) {
		ArrayList<Integer> dummy = null;
		addStudentToDatabase(name, surname, birtDate, specialisation, dummy);
	}
	
	//----------------------------------------------------------
	public String useSpeciality(int ID) {
		if (data.get(ID) instanceof CyberStudent) {
			return ((CyberStudent)data.get(ID)).getHashName();
		}
		else if (data.get(ID) instanceof TeleStudent) {
			return ((TeleStudent)data.get(ID)).getNameInMorseCode();
		}
		return null;
	}
	
	//----------------------------------------------------------
	public boolean giveGrade(int ID, int grade) {
		return data.get(ID).addGrade(grade);
	}
	
	//----------------------------------------------------------
	public boolean removeStudent(int ID) {
		if (data.containsKey(ID)) {
			priorityIDs.add(ID);
			data.remove(ID);
			return true;
		}
		return false;
	}
	//----------------------------------------------------------
	public ArrayList<Integer> getPriorityIDs(){
		return priorityIDs;
	}
	
	public void addPriorityID(int ID) {
		priorityIDs.add(ID);
	}
	
	//----------------------------------------------------------
	public String getInfo(int ID) {
		if (!data.containsKey(ID)) {
			return "";
		}
		return (data.get(ID)).toString();
	}
	
	//----------------------------------------------------------
	public int[] getQuantityOfStudentsInEachGroup() {
		int [] quantity = new int[] {0,0}; //TELE, CYBER
		
		for (Student student : data.values()) {
			if (student instanceof TeleStudent) {
				quantity[0] ++;
			}
			else if (student instanceof CyberStudent) {
				quantity[1] ++;
			}
		}
		return quantity;
	}
	
	//----------------------------------------------------------
	public double[] getAvreageGradeOfStudentGroup() {
		double grades[] = new double[] {0.0,0.0}; //TELE, CYBER
		
		int [] quantity = getQuantityOfStudentsInEachGroup();
		
		for (Student student : data.values()) {
			if (student instanceof TeleStudent) {
				if(student.averageGrades() == 0.0) {
					quantity[0]--;
				}
				else {
					grades[0] += student.averageGrades();
				}
			}
			else if (student instanceof CyberStudent) {
				if(student.averageGrades() == 0.0) {
					quantity[1]--;
				}
				else {
					grades[1] += student.averageGrades();
				}
			}
		}
		
		for (int i = 0; i < grades.length; i++) {
			if(quantity[i] <=0 ) {
				grades[i] = 0;
			}
			else {
				grades[i] /= (double)quantity[i];
			}
		} 
		return grades;
	}
	
	//----------------------------------------------------------
	public ArrayList<Student> getSortedListBySurnames(){
		ArrayList<Student> sortedData = new ArrayList<Student>(data.values());
		
		sortedData.sort(new Comparator<Student>() {
			@Override
			public int compare(Student s1, Student s2) {
				return (s1.getSurename()).compareTo(s2.getSurename());
			}
		});
		return sortedData;
	}
	
	//----------------------------------------------------------
	public boolean writeStudentsIntoFile(String path, int...IDs) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		
		try {
			fw = new FileWriter(path);
			bw = new BufferedWriter(fw);
			
			for (int ID : IDs) {
				String info = getInfo(ID);
		
				if (info == "") {
					continue;
				}
				
				bw.write(info);
				bw.newLine();
				bw.write("################################");
				bw.newLine();
			}
		} 
		catch (Exception e1) {
			return false;
		} 
		finally {
			
			try {
				bw.close();
				fw.close();
			} 
			catch (Exception e2) {
				return false;
			}
		}
		return true;
	}
	
	public boolean writeStudentsIntoFile(int...IDs) {
		return writeStudentsIntoFile("output.txt",IDs);
	}
	
	//----------------------------------------------------------
	public boolean readStudentsFromFile(String path) {
		File inputFile = new File(path);
		Scanner fileScanner = null;
		
		try {
			fileScanner = new Scanner(inputFile);
			while (fileScanner.hasNextLine()) {
				fileLineToNewStudent(fileScanner.nextLine());
			}
			
			
		} 
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
		finally {
			fileScanner.close();
		}
		
		return true;
	}
	
	public boolean readStudentsFromFile() {
		return readStudentsFromFile("input.txt");
	}
	
	private boolean fileLineToNewStudent(String input) {
		if (input.startsWith("#")) 
			return false;
		
		input = input.replaceAll("\\s","");
		
		String [] studentInfo = input.split(";");
		String [] date = studentInfo[2].split("-");
		
		
		if (studentInfo.length != 5) {
			addStudent( studentInfo[0], 
					studentInfo[1], 
					LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])), 
					Student.Specialisation.valueOf(studentInfo[3]));
			return true;
		}
		
		ArrayList<Integer> grades = new ArrayList<Integer>();
		String [] grade = studentInfo[4].split(",");
		for (int i = 0; i < grade.length; i++) {
			grades.add(Integer.parseInt(grade[i]));
		}
		
		addStudent( studentInfo[0], 
					studentInfo[1], 
					LocalDate.of(Integer.parseInt(date[0]), Integer.parseInt(date[1]), Integer.parseInt(date[2])), 
					Student.Specialisation.valueOf(studentInfo[3]),
					grades);
					
		return true;	
	}
}
