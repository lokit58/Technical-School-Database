import java.time.LocalDate;
import java.util.*;

abstract class Student {
    protected int id;
    protected String name;
    protected String surename;
    protected LocalDate birthDate;
    protected ArrayList<Integer> grades;
    protected Specialisation specialisation;


    public Student(int id, String name, String surename, LocalDate birthDate, Specialisation specialisation) {
        this.id = id;
        this.name = name;
        this.surename = surename;
        this.birthDate = birthDate;
        this.specialisation = specialisation;
        this.grades = new ArrayList<Integer>();
    }


    public Student(int id, String name, String surename, LocalDate birthDate, Specialisation specialisation, ArrayList<Integer> grades) {
    	 this.id = id;
         this.name = name;
         this.surename = surename;
         this.birthDate = birthDate;
         this.specialisation = specialisation;
         this.grades = grades;
    }
    public enum Specialisation {
        TELECOMMUNICATIONS,
        CYBERSECURITY
    }

    public String getName() {
        return name;
    }

    public String getSurename(){
        return surename;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public int getId() {
		return id;
	}
    
	public Specialisation getSpecialisation() {
        return specialisation;
    }
    public boolean addGrade(int grade) {
    	if (grades == null) {
			grades = new ArrayList<Integer>();
		}
    	
        if(grade>=1 && grade<=5) {
            grades.add(grade);
            return true;
        }
        return false;
    }

    public double averageGrades() {
    	if (grades == null) return 0.0;
    	
        if (grades.isEmpty()) return 0.0;

        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }

        return (double) sum / grades.size();
    }
    @Override
    public String toString() {
        //String gradeText = grades.isEmpty() ? "No grades yet" : grades.toString();
        //String averageText = grades.isEmpty() ? "-" : String.format("%.2f", averageGrades());
        return "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Surename: " + surename + "\n" +
                "Date of birth " + birthDate + "\n" +
                "Specialisation: " + specialisation + "\n" +
                "Grades: " + (grades == null? "No grades yet": grades) + "\n" +
                "Average of grades: " + averageGrades();

    }

}
