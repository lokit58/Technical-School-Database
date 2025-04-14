import java.time.LocalDate;
import java.util.ArrayList;

public class TeleStudent extends Student {
    public TeleStudent(int id, String name, String surename, LocalDate birthDate, Specialisation specialisation) {
        super(id, name, surename, birthDate, specialisation);
    }
    
    public TeleStudent (int id, String name, String surename, LocalDate birthDate, Specialisation specialisation, ArrayList<Integer> grades) {
        super(id, name, surename, birthDate, specialisation, grades);
    }

    public String getNameInMorseCode () {
        return SpecialSkills.codeToMorse(name+surename);
    }
}
