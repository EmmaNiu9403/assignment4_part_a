/* CRITTERS Algae.java
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

import project4.Critter.TestCritter;

public class Algae extends TestCritter 
{	
	@Override
	public boolean fight(String not_used) { return false; }
	
	@Override
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	@Override
	public String toString() { return "@"; }
}
