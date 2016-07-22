/* CRITTERS Main.java
 * EE422C Project 4 submission by
 * Replace <...> with your actual data.
 * Kassandra Perez
 * Kap2589
 * Haoran Niu
 * hn4582
 * Slip days used: <0>
 * Summer 2016
 */
package project4;
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
