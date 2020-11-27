package sim_objects;

import java.util.ArrayList;

import simulation.Control;
import simulation.SettingsUI;

// A person contains properties of Health
public class Person extends Resident {
	
	//health states
	public enum virus {candidate, infected, recovered, died}
	
	//recoveryTime
	protected virus state = virus.candidate;
	private int sickTime = 0; 			//sickTime timer
	
	//control variables
	double toRoam, toBeInfected, toDie;
	int sickTimeLow, sickTimeMax;
	
	private static int InfectionCounter = 0;
	//simulation statistical values
	private int ID;						//ID assigned by control
	private int infectionID = 0;		//infection ID, is infection number for this person (0 never infected, >0 infection counter
	private int timesInCollision = 0;	//how many times the person object is collides with another person object 
	private int infectedBy = -1;		//person who transmitted virus (-100 random, -1 never, >= 0 is person ID
	private int infectedTime = 0;		//time person received the virus (-1 games start, 0 never, >0 time stamp)
	private int infectedDuration = 0;	//time stamp when person recovers or dies
	private int timeOfDeath = 0;		//time stamp when person dies
	private int atInstantInfected = -1;	//how many people infected at instant when person was infected

	/*
	 * Default Person constructor from Static values
	 */
	public Person() {
		toRoam = SettingsUI.sToRoam;
		toBeInfected = SettingsUI.sToBeInfected;
		toDie = SettingsUI.sToDie;
		sickTimeLow = SettingsUI.sSickTimeLow;
		sickTimeMax = SettingsUI.sSickTimeMax;
				
		this.init();
	}

	//Person constructed according to Control Panel settings
	public Person(Control control) {
		super(control);
		
		toRoam = control.toRoam;			    
		toDie = control.toDie;		
		toBeInfected = control.toBeInfected;
		sickTimeLow = control.sickTimeLow;
		sickTimeMax = control.sickTimeMax;

		this.init();
	}
		
	private void init() {
		
		//code to make percentage of the Person objects infected 
		if(Math.random() < toBeInfected) {
			this.setInfectedBy(-100); //randomly infected
			this.setInfected();
		}
		
		//randomize how long it takes for the Person objects to recover!
		//for instance between 5-7 (between Min-Max) seconds (numbers are in milliseconds)
		this.sickTime = ((int)(Math.random()*(sickTimeMax-sickTimeLow+1)+sickTimeLow));
	}
	
	//a series of getters to simplify code reading
	public boolean isCandidate() {
		return getState() == virus.candidate;
	}
	
	public boolean isInfected() {
		return getState() == virus.infected;
	}
	
	public boolean isRecovered() {
		return getState() == virus.recovered;
	}
	
	public boolean isDead() {
		return getState() == virus.died;
	}
	
	// infected setter and update to infected counter
	public void setInfected() {
        int timeStamp = (int) System.currentTimeMillis(); //infection time

		state = virus.infected;
		this.setInfectionID(++InfectionCounter);
		this.setInfectedTime(timeStamp);
		if (control != null) { control.numInfected++; this.setAtInstantInfected(control.numInfected); }
	}
	
	//calculates health of person over time
	public void healthManager() {
		
		//If person is infected, they eventually recover or die so that they don't 
		//infect people forever. 
		if(this.getState() == virus.infected) {
			//recoveryTime update
			this.sickTime -= control.timerValue;
			
			//once the person has been given enough time, they will be considered recovered
			if(this.sickTime <= 0) {
		        int timeStamp = (int) System.currentTimeMillis(); //end time
				this.setInfectedDuration(timeStamp);

				if(Math.random() < toDie) {
					state = virus.died;
					this.setTimeOfDeath(timeStamp);
					if (control != null) control.numDied++;
				} else {
					state = virus.recovered;
				}
				if (control != null) control.numInfected--;
			}
		}			
	}
	
	
	/**
	 * Collision between two person objects has been detected
	 * If two Person objects collide they have a possibility of infecting!
	 * @param p2 collider
	 */
	public int collisionAction(Person p2) {
		//infection only happens if one person is infected and the other has never
		//been infected before
		int action = 0;

		if (this.isInfected() && p2.isCandidate()) {
			p2.setInfectedBy(this.getID());
			p2.setInfected();
			action = 2;
		}else if(this.isCandidate() && p2.isInfected()) {
			this.setInfectedBy(p2.getID());
			this.setInfected();
			action = 1;
		}
		this.setTimesInCollision(this.getTimesInCollision() + 1);
		return action;
	}
	
	public void collisionActionWithVerticalObstacle() {
		vx *= -1;
	}
	
	public void collisionActionWithHorizontalObstacle() {
		vy *= -1;
	}
	
	/*
	 * Perform velocity manager updates on person
	*/
	@Override
	public void velocityManager() {
		if (isRoaming()) {
			if (this.isRecovered() || this.isDead())
				super.velocityStop();
			else
				super.velocityManager();
		}
			
	}
	
	/*
	 * Key properties of person
	 */
	public String toString() {
		
		return ( "" + getState() + "\t" + this.sickTime + "\tx:" + x + "\t" + vx + "\ty:" + y + "\t" + vy ); 
	}
	
	/*
	 * Main Tester method
	 */
	public static void main (String[] args) {
		int numOfPeople = 25;
		
		ArrayList<Person> pl = new ArrayList<>();
		for (int i = 0; i < numOfPeople; i++) {
			Person p = new Person();
			pl.add(p);
		}
		for (Person p : pl) {
			System.out.println( p );
		}
	}

	 public virus getState() {
		return state;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getTimesInCollision() {
		return timesInCollision;
	}

	public void setTimesInCollision(int timesInCollision) {
		this.timesInCollision = timesInCollision;
	}

	public int getInfectedBy() {
		return infectedBy;
	}

	public void setInfectedBy(int infectedBy) {
		this.infectedBy = infectedBy;
	}

	public int getInfectedTime() {
		return infectedTime;
	}

	public void setInfectedTime(int infectedTime) {
		this.infectedTime = infectedTime;
	}

	public int getInfectedDuration() {
		return infectedDuration;
	}

	public void setInfectedDuration(int timeStamp) {
		this.infectedDuration = timeStamp;
	}

	public int getTimeOfDeath() {
		return timeOfDeath;
	}

	public void setTimeOfDeath(int timeOfDeath) {
		this.timeOfDeath = timeOfDeath;
	}

	public int getInfectionID() {
		return infectionID;
	}

	public void setInfectionID(int infectionID) {
		this.infectionID = infectionID;
	}

	public int getAtInstantInfected() {
		return atInstantInfected;
	}

	public void setAtInstantInfected(int atInstantInfected) {
		this.atInstantInfected = atInstantInfected;
	}
	
	
}