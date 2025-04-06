import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
	public Map<Integer, Student> data;
	private List<Integer> priorityIDs;
	
	public Database() {
		data = new HashMap<Integer, Student>();
		priorityIDs = new ArrayList<Integer>();
		
	}
	
	public void addStudent(String name, String surname, LocalDate birtDate, Student.Specialisation specialisation) {

		if (specialisation == Student.Specialisation.CYBERSECURITY ) {
			
		}
		else {
			
		}
	}
	
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
				grades[0] += student.averageGrades();
			}
			else if (student instanceof CyberStudent) {
				grades[1] += student.averageGrades();
			}
		}
		
		for (int i = 0; i < grades.length; i++) {
			grades[i] /= (double)quantity[i];
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
