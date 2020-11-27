package simulation;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
/*
import com.amazonaws.model.ItemsTable;
*/
import panel_events.Community;
import sim_objects.Building;
import sim_objects.Person;

public class Control {
	public String title = "Virus Pandemic Simulation";
	
	//Model and View
	ArrayList<Person> people; //the community of Person objects
	Community view;
	//ItemsTable model;
	
	//counters for "this" simulation instance
	public int numInfected = 0;
	public int numDied= 0;
	
	//simulation control values
	public int  numPeople;			
	public double toRoam;			    
	public double toBeInfected;		
	public double toDie;				
	public int sickTimeLow;			
	public int sickTimeMax;
	//frame extents
	public int frameX;
	public int frameY;
	//position extents, keep objects away from the edges
	public int xExt;
	public int yExt;
	//oval size, represents person in frame
	public int OvalW;	//Height
	public int OvalH;	//Width
	//refresh timer, also used to calculate time/age of infection
	public int timerValue;
	
	//simulation properties for Buildings
	public Building[] buildings() {
		return new Building[]{
				new Building("Rubber Chicken",550,0,620,160),
				new Building("Sprouts",200,0,-25,160),
				new Building("Wuhan",550,400,620,400),
				new Building("CollegeBoard",200,400,-25,400),
		};
	}

	/*
	 * Default constructor uses Static/Default simulation values
	 */
	public Control() {
		//This sets defaults in case run with default constructor
		// simulation control starting values
		numPeople = SettingsUI.sNumPeople;
		toRoam = SettingsUI.sToRoam;
		toBeInfected = SettingsUI.sToBeInfected;
		toDie = SettingsUI.sToDie;
		sickTimeLow = SettingsUI.sSickTimeLow;
		sickTimeMax = SettingsUI.sSickTimeMax;
		//frame extents
		frameX = SettingsUI.sFrameX;
		frameY = SettingsUI.sFrameY;
		//position extents, keep objects away from the edges
		xExt = SettingsUI.sXExt;
		yExt = SettingsUI.sYExt;
		//oval size, represents person in frame
		OvalW = SettingsUI.sOvalW;	//Height
		OvalH = SettingsUI.sOvalH;	//Width
		//refresh timer, also used to calculate time/age of infection
		timerValue = SettingsUI.sTimerValue;
	}

	/*
	 * This constructor uses user defined simulation Settings
	 */
	public Control(SettingsUI sets) {
		// health settings
		numPeople = sets.numPeople;
		toRoam = sets.toRoam;
		toBeInfected = sets.toBeInfected;
		toDie = sets.toDie;
		sickTimeLow = sets.sickTimeLow;
		sickTimeMax = sets.sickTimeMax;
		// simulator settings
		frameX = sets.frameX;
		frameY = sets.frameY;
		yExt = sets.yExt;
		xExt = sets.xExt;
		OvalW = sets.OvalW;
		OvalH = sets.OvalH;
		timerValue = sets.timerValue;
	}
	
	/*
	 * Tester method to run simulation
	 */
	public static void main (String[] args) {
		Control c = new Control();
		c.simulation();
	}
	
	/* 
	 * This method coordinates MVC for Simulation
	 * - The Simulation is managing People in a Graphics frame to simulate a virus outbreak
	 * - Prerequisite: Control values from constructor are ready
	 */
	public void simulation() {
/* AWS stubs removed
		model = new ItemsTable(this);
*/
			
		//Setup the Community and Refresh Timer
		view = new Community(this);
	
		//Setup the People
		people = new ArrayList<>();
		for(int i = 0; i < numPeople; i++) {
			//instantiate Person object and add it to the ArrayList
			Person p = new Person(this);
			p.setID(i);	//set person object with a simulation ID
			
			//add person to list
			people.add(p);
		}
		
		//start the Simulation
		view.activate();

	}
	
	/*
	 * Keep simulation running
	 * numInfected > 0 means contagion can continue to spread
	 */
	public boolean keepAlive() {
		if (numInfected > 0) return true;
/* AWS Stubs removed
		//record all people and stats to database
		for(Person p : people) {
			new Thread(() -> {
				model.personRecord(p);
			}).start();
		}
		
		//mark simulation as ended
		new Thread(() -> {
			model.endRecord();
		}).start();
 */
		return false;
	}

		
	/*
	 * Draw method for Panel
	 * paints/repaints the person objects in the frame
	 */
	public void paintCB(Graphics g) {
		
		//get each Person in the community!
		for(int i=0; i < people.size(); i++ ) {
		    Person p1 = people.get(i);
		    int p1Index = i;

			//Person colliding with other persons
			for(Person p2: people) {
				//collision returns 1 or 2 if infection occurs to that person
				if (p1.collisionDetector(p2)) {
					switch (p1.collisionAction(p2)) {
					case 1:
/* AWS stubs removed
						new Thread(() -> {
							model.infectionRecord(p1,p2);
						}).start();
 */
						break;
					case 2:
/* AWS stubs removed
						new Thread(() -> {
							model.infectionRecord(p2,p1);
						}).start();
 */
						break;
					default:
						//NoOp
					}
				}
			}

			//person colliding with buildings
			for (Building b: view.getBuildings()) {
				if (p1.collisionDetector(b.getVWall()))
					p1.collisionActionWithVerticalObstacle();

				if (p1.collisionDetector(b.getHWall()))
					p1.collisionActionWithHorizontalObstacle();
			}
			
			//update person health
			p1.healthManager(); //manage health values of the Person
			p1.velocityManager(); //manage social distancing and/or roaming values of the Person
			
			//set the color of the for the person oval based on the health status of person object
			switch (p1.getState()) {
				case candidate -> g.setColor(Color.LIGHT_GRAY);
				case infected -> g.setColor(Color.red);
				case recovered -> g.setColor(Color.green);
				case died -> g.setColor(Color.black);
			}
			
			//paint/repaint the person oval in the simulation frame
			g.fillOval(p1.getX(), p1.getY(), p1.getWidth(), p1.getHeight());
			
			//paint/repaint the person oval in meter/bar indicator
			g.fillOval((frameX-(int)(frameX*.02)), (int)(frameY-((numPeople-p1Index)*OvalH)/1.67), OvalW, OvalH);
			
		}
	}		
		
}