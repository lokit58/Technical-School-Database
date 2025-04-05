import java.time.LocalDate;
import java.util.*;

abstract class Student {
    private static int assignID = 1;
    protected int id;
    protected String name;
    protected String surename;
    protected LocalDate birthDate;
    protected List<Integer> grades;
    protected Specialisation specialisation;
    public Student(int id, String name, String surename, LocalDate birthDate, List<Integer> grades, Specialisation specialisation) {
        this.id = assignID++;
        this.name = name;
        this.surename = surename;
        this.birthDate = birthDate;
        this.grades = new ArrayList<>();
    }
    public enum Specialisation {
        TELECOMMUNICATIONS,
        CYBERSECURITY
    }

    public int getId() {
        return id;
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

    public Specialisation getSpecialisation() {
        return specialisation;
    }
    public void addGrade(int grade) {
        if(grade>=1 && grade<=5) {
            grades.add(grade);
        }
    }

    public double averageGrades () {
        if (grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    @Override
    public String toString() {
        return "ID: " + id + "\\n" +
                "Name: " + name + "\\n" +
                "Surename: " + surename + "\\n" +
                "Date of birth " + birthDate + "\\n" +
                "Specialisation: " + specialisation + "\\n" +
                "Average of grades: " + averageGrades();

    }


    public abstract void getCodedIdentity();
}
