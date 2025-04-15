import java.util.Scanner;


public class TEST {


    public static void main(String[] args) {
        // TODO Auto-generated method stub

        Scanner sc=new Scanner(System.in);
        InputManager input = new InputManager();
        boolean run=true;

        {
            System.out.println("----- Vyberte pozadovanou cinnost:");
            System.out.println("1  .. vlozeni noveho studenta");
            System.out.println("2  .. zadat studentovi znamku");
            System.out.println("3  .. odstranit studenta");
            System.out.println("4  .. najit studenta podle ID ");
            System.out.println("5  .. spustit dovednost studenta ");
            System.out.println("6  .. abecedne vypsat studenty podle oboru");
            System.out.println("7  .. Vypis prumeru znamek v oborech:");
            System.out.println("8  .. vypis poctu studentu v oborech");
            System.out.println("9  .. ulozit studenta do souboru");
            System.out.println("10 .. nacist studenta ze souboru");
            System.out.println("0  .. ulozit do SQL databaze a ukoncit aplikaci ");
            int choice = input.getInt("Zadej číslo volby: ");
            switch(choice)
            {
                case 1:
                    System.out.println("Zadejte jmeno studenta, prijmeni, rok narozeni");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    break;
                case 0:
                    run=false;
                    break;
                    
            }

        }
    }

}
