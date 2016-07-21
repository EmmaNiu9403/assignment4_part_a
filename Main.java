/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Kassandra Perez
 * kap2589
 * <Student2 Name>
 * <Student2 EID>
 * <Student2 5-digit Unique No.>
 * Slip days used: <0>
 * Summer 2016
 */
package project4;
import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) 
	{	
		while(true)
		{
			System.out.print("critters> ");
			Scanner sc = new Scanner(System.in);
			String in = sc.nextLine().trim();
			ControllerCommands.processCommand(in);
		}
	}
	
	
}
