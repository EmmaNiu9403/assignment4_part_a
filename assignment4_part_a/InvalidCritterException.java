/* CRITTERS InvalidCritterException.java
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

public class InvalidCritterException extends Exception 
{
	String offending_class;
	
	public InvalidCritterException(String critter_class_name) {
		offending_class = critter_class_name;
	}
	
	public String toString() {
		return "Invalid Critter Class: " + offending_class;
	}

}
