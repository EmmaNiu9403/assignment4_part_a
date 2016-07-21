package project4;

import project4.Critter.TestCritter;

public class Algae extends TestCritter 
{	
	public boolean fight(String not_used) { return false; }
	
	public void doTimeStep() {
		setEnergy(getEnergy() + Params.photosynthesis_energy_amount);
	}
	
	public String toString() { return "@"; }
}
