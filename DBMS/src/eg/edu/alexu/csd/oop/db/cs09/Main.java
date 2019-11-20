package eg.edu.alexu.csd.oop.db.cs09;

import java.util.Scanner;

public class Main {
	private static Object LOCK = new Object();
	public static void main(String[] args) throws InterruptedException {
		UI userInterface = new UI();
		System.out.println("ENTRE QUERY..");
			Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String a = sc.nextLine();
			userInterface.parser(a);
			synchronized (LOCK) {
		        LOCK.wait(500);
		        System.out.println("ENTRE QUERY..");
		    }
			
		}
	}
}
