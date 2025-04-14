import java.time.LocalDate;
import java.util.ArrayList;

public class CyberStudent extends Student {
    public CyberStudent (int id, String name, String surename, LocalDate birthDate, Specialisation specialisation) {
        super(id, name, surename, birthDate, specialisation);
    }
    
    public CyberStudent (int id, String name, String surename, LocalDate birthDate, Specialisation specialisation, ArrayList<Integer> grades) {
        super(id, name, surename, birthDate, specialisation, grades);
    }

    public String getHashName() {
        return SpecialSkills.createHash(name+surename);
    }
}
