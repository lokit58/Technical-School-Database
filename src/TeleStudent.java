import java.time.LocalDate;
import java.util.List;

public class TeleStudent extends Student {
    public TeleStudent(int id, String name, String surename, LocalDate birthDate, List<Integer> grades, Specialisation specialisation) {
        super(id, name, surename, birthDate, grades, specialisation);
    }

    @Override
    public void getCodedIdentity() {
        return;
        //return Morse.translate(name + surename);
    }
}
