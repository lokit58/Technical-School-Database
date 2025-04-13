import java.time.LocalDate;

public class CyberStudent extends Student {
    public CyberStudent (int id, String name, String surename, LocalDate birthDate, Specialisation specialisation) {
        super(id, name, surename, birthDate, specialisation);
    }

    public String getHashName() {
        return SpecialSkills.createHash(name+surename);
    }
}
