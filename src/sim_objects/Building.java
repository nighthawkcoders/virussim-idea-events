package sim_objects;

import panel_events.Panel;

import java.awt.*;

/**
 * Building is logical container for Walls, it has Walls which are Entities in Simulation
 * @author JohnMortensen
 *
 */
public class Building {	
	String name;
	int vx, vy, hx, hy;

	//Front facing walls of Building
	Wall vWall;
	Wall hWall;
	
	/**
	 * Building has a name, vertical and horizontal coordinates
	 * @param name - name of building
	 * @param vx - vertical x
	 * @param vy - vertical y
	 * @param hx - horizontal x
	 * @param hy - horizontal y
	 */
    public Building(String name, int vx, int vy, int hx, int hy) {
    	this.name = name;
    	this.vx = vx;
    	this.vy = vy;
    	this.hx = hx;
    	this.hy = hy;
    	
    	vWall = new Wall(vx, vy, "SocialDistancingImages/wall2.png", true);
    	hWall = new Wall(hx, hy, "SocialDistancingImages/wall1.png", false);   	
    }
    
    public String getName() {
    	return name;
    }
    
    public Wall getVWall() {
    	return vWall;
    }
    
    public Wall getHWall() {
    	return hWall;
    }
     
    public void drawImage(Graphics g, Panel panel) {
    	//xoffset is to orient walls
    	int xoffset = Math.min(vx, hx);
		g.drawString(name, xoffset + 30 , vy + 50);
    	g.drawImage(vWall.getImage(), vWall.getX(), vWall.getY(), panel);
    	g.drawImage(hWall.getImage(), hWall.getX(), hWall.getY(), panel);
    }

}
