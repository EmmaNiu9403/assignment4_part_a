/* CRITTERS Critter.java
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

import java.util.*;

/* see the PDF for descriptions of the methods and fields in this class
 * you may add fields, methods or inner classes to Critter ONLY if you make your additions private
 * no new public, protected or default-package code or data can be added to Critter
 */
public abstract class Critter {

	static ArrayList<Critter> critterCollection = new ArrayList<Critter>();

	private static java.util.Random rand = new java.util.Random();
	private static List<Critter> population = new java.util.ArrayList<Critter>();
	private static List<Critter> babies = new java.util.ArrayList<Critter>();
	private int energy = 0;
	private int x_coord;
	private int y_coord;
	private boolean moved = false;


	private class position {
		int x = x_coord;
		int y = y_coord;

		public boolean equals(position other) {
			if (x == other.x && y == other.y) {
				return true;
			} else
				return false;
		}
	}

	Critter.position position = new position();
	Map<position, Critter> map = new HashMap<position, Critter>();

	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidCritterException {
int i = 0;
	while(i<500){
		makeCritter("project4.Craig");
		i++;
		}
	displayWorld();
	while(critterCollection.size()!=0){
		worldTimeStep();
		displayWorld();
	}
	
	

	}

	public static int getRandomInt(int max) {
		return rand.nextInt(max);
	}

	public static void setSeed(long new_seed) {
		rand = new java.util.Random(new_seed);
	}

	protected int getEnergy() {
		return energy;
	}

	protected final void walk(int direction) {

		move(1, direction);
		energy = energy - Params.walk_energy_cost;
		moved = true;

	}

	protected final void run(int direction) {

		move(2, direction);
		energy = energy - Params.run_energy_cost;
		moved = true;

	}

	protected final void reproduce(Critter offspring, int direction) {
		babies.add(offspring);
	}

	public abstract void doTimeStep();

	public abstract boolean fight(String oponent);

	/*
	 * create and initialize a Critter subclass critter_class_name must be the
	 * name of a concrete subclass of Critter, if not an InvalidCritterException
	 * must be thrown
	 */
	public static void makeCritter(String critter_class_name)
			throws InvalidCritterException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> cName = Class.forName(critter_class_name);
		Critter critter = (Critter) cName.newInstance();
		critter.energy = Params.start_energy;
		critter.x_coord = getRandomInt(Params.world_width);
		critter.y_coord = getRandomInt(Params.world_height);
		critterCollection.add(critter);
	}

	public static List<Critter> getInstances(String critter_class_name) throws InvalidCritterException {
		List<Critter> result = new java.util.ArrayList<Critter>();

		return result;
	}

	/**
	 * @param critters
	 */
	public static void runStats(List<Critter> critters) {
		System.out.print("" + critters.size() + " critters as follows -- ");
		java.util.Map<String, Integer> critter_count = new java.util.HashMap<String, Integer>();

		for (Critter crit : critters) {
			String crit_string = crit.toString();
			Integer old_count = critter_count.get(crit_string);
			if (old_count == null) {
				critter_count.put(crit_string, 1);
			} else {
				critter_count.put(crit_string, old_count.intValue() + 1);
			}
		}
		String prefix = "";
		for (String s : critter_count.keySet()) {
			System.out.print(prefix + s + ":" + critter_count.get(s));
			prefix = ", ";
		}
		System.out.println();
	}

	public static void worldTimeStep() throws InstantiationException, IllegalAccessException, ClassNotFoundException, InvalidCritterException {
		for (int i = 0; i < critterCollection.size(); i++) {
			critterCollection.get(i).doTimeStep();
		}
		Iterator<Critter> iterator = critterCollection.iterator();
		while (iterator.hasNext()) {
			Critter C1 = iterator.next();
			if (critterCollection.indexOf(C1) < critterCollection.size() - 1) {
				Critter C2 = critterCollection.get(critterCollection.indexOf(C1) + 1);
				if (C1.x_coord == C2.x_coord && C1.y_coord == C2.y_coord) {
					doEncounter(C1, C2);
				}
			}
		}
		
		for(int i=0;i<Params.refresh_algae_count;i++){
			makeCritter("project4.Algae");
		}
		
		Iterator <Critter> iterator1 = critterCollection.iterator();
		while(iterator1.hasNext()){
			Critter c = iterator1.next();
			if(c.energy<=0) iterator1.remove();
		}
		
		critterCollection.addAll(babies);

	}

	private static void doEncounter(Critter A, Critter B) {
		boolean Awin;
		int rollsA = 0, rollsB = 0;
		boolean killB = A.fight(B.toString());
		boolean killA = B.fight(A.toString());
		if (killA == true && killB == true && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = Critter.getRandomInt(A.energy);
			rollsB = Critter.getRandomInt(B.energy);
		}
		if (killA == false && killB == true && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = 0;
			rollsB = Critter.getRandomInt(B.energy);
		}
		if (killA == true && killB == false && A.energy > 0 && B.energy > 0 && A.position.equals(B.position)) {
			rollsA = Critter.getRandomInt(A.energy);
			rollsB = 0;
		} else {
			rollsA = 0;
			rollsB = 0;
		}
		if (rollsA > rollsB)
		   Awin = true;
		else if (rollsB > rollsA)
			Awin = false;
		else
			Awin= true;
		
		if(Awin) {A.energy = (int) (A.energy+0.5*B.energy);B.energy = (int) (0.5*B.energy);}
		else {
			B.energy = (int) (B.energy+0.5*A.energy);
			A.energy=(int) (0.5*A.energy);
		}
	}

	public static void displayWorld() {
		int rows = Params.world_height + 2;
		int cols = Params.world_width + 2;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if ((i == 0 && j == 0) || (i == 0 && j == cols - 1) || (i == rows - 1 && j == 0)
						|| (i == rows - 1 && j == cols - 1))
					System.out.print("+");
				else if (i == 0 || i == rows - 1)
					System.out.print("-");
				else if (j == 0 || j == cols - 1)
					System.out.print("|");
				else {
					boolean critterFlag = false;
					for (Critter c : critterCollection) {
						if ((c.y_coord == (i - 2)) && (c.x_coord == (j - 2))) {
							System.out.print(c);
							critterFlag = true;
						}
					}
					if (critterFlag == false)
						System.out.print(" ");
				}
			}
			System.out.print("\n");
		}
	}

	/*
	 * a one-character long string that visually depicts your critter in the
	 * ASCII interface
	 */
	public String toString() {
		return "";
	}

	/*
	 * the TestCritter class allows some critters to "cheat". If you want to
	 * create tests of your Critter model, you can create subclasses of this
	 * class and then use the setter functions contained here.
	 * 
	 * NOTE: you must make sure that the setter functions work with your
	 * implementation of Critter. That means, if you're recording the positions
	 * of your critters using some sort of external grid or some other data
	 * structure in addition to the x_coord and y_coord functions, then you MUST
	 * update these setter functions so that they correctly update your
	 * grid/data structure.
	 */
	static abstract class TestCritter extends Critter {
		protected void setEnergy(int new_energy_value) {
			super.energy = new_energy_value;
		}

		protected void setXCoord(int new_x_coord) {
			super.x_coord = new_x_coord;
		}

		protected void setYCoord(int new_y_coord) {
			super.y_coord = new_y_coord;
		}
	}

	public void move(int step, int direction) {
		switch (direction) {
		case 0:
			x_coord += step;
			break;
		case 1:
			x_coord += step;
			y_coord -= step;
			break;
		case 2:
			y_coord -= step;
			break;
		case 3:
			x_coord -= step;
			y_coord -= step;
			break;
		case 4:
			x_coord -= step;
			break;
		case 5:
			x_coord -= step;
			y_coord += step;
			break;
		case 6:
			y_coord += step;
			break;
		case 7:
			x_coord += step;
			y_coord += step;
			break;
		}

	}

}
