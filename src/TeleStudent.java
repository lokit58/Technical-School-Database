import java.time.LocalDate;

public class TeleStudent extends Student {
    public TeleStudent(int id, String name, String surename, LocalDate birthDate, Specialisation specialisation) {
        super(id, name, surename, birthDate, specialisation);
    }

    public String getNameInMorseCode () {
        return SpecialSkills.codeToMorse(name+surename);
    }
}
