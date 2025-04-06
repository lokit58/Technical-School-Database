import java.util.Scanner;

public class InputManager {
	private Scanner scanner;
	
	public InputManager() {
		scanner = new Scanner(System.in);
	}
	
	//----------------------------------------------------------
	public int getInt() {
		while (!scanner.hasNextInt()) {
			System.out.println("Input is not formated as whole number. Try again:");
			scanner.next();
		}
		return scanner.nextInt();
	}
	
	public int getInt(String statement) {
		System.out.println(statement);
		return getInt();
	}
	
	//----------------------------------------------------------
	public double getDouble() {
		while (!scanner.hasNextDouble()) {
			System.out.println("Input is not formated as decimal number. Try again:");
			scanner.next();
		}
		return scanner.nextDouble();
	}
	
	public double getDouble(String statement) {
		System.out.println(statement);
		return getDouble();
	}
	
	//----------------------------------------------------------
	public String getString() {
		
		return scanner.next();
	}
	
	public String getString(String statement) {
		System.out.println(statement);
		return getString();
	}
}
