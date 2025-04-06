import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Database {
	public Map<Integer, Student> data;
	private List<Integer> priorityIDs;
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
	private void addStudentToDatabase(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation, ArrayList<Integer> grades) {
		nextID = getNextId();
		for (int i = 0; i < priorityIDs.size(); i++) {
			if(priorityIDs.get(i)< nextID) {
				nextID = priorityIDs.get(i);
				break;
			}
		}
		
		if (specialisation == Student.Specialisation.CYBERSECURITY) {
			data.put(nextID, new CyberStudent(nextID, surname, name, birtDate, grades, specialisation));
		}
		else if (specialisation == Student.Specialisation.TELECOMMUNICATIONS) {
			data.put(nextID, new TeleStudent(nextID, surname, name, birtDate, grades, specialisation));
		}
	}
	
	public void addStudent(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation, ArrayList<Integer> grades) {
		addStudentToDatabase(name, surname, birtDate, specialisation, grades);
	}
	
	public void addStudent(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation) {
		ArrayList<Integer> dummy = null;
		addStudentToDatabase(name, surname, birtDate, specialisation, dummy);
	}
	
	public String useSpeciality(int ID) {
		if (data.get(ID) instanceof CyberStudent) {
			//return ((CyberStudent)data.get(ID)).
		}
		else if (data.get(ID) instanceof TeleStudent) {
			//return ((TelerStudent)data.get(ID)).
		}
		return null;
	}
	
	//TODO
	public boolean giveGrade(int ID, int grade) {
		(data.get(ID)).addGrade(grade);
		return true;
	}
	
	public boolean removeStudent(int ID) {
		if (data.containsKey(ID)) {
			priorityIDs.add(ID);
			data.remove(ID);
			return true;
		}
		return false;
	}
	
	public String getInfo(int ID) {
		if (data.containsKey(ID)) {
			return "";
		}
		return (data.get(ID)).toString();
	}
	
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
}
