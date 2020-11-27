package sim_objects;

import java.awt.Rectangle;

/**
 * The primary purpose of this Class is to detect rectangular collisions
 * 
 */
public class RectangleCollider {
	protected int x, y, width, height;

	/**
	 * Default constructor
	 */
	public RectangleCollider() {
		// default constructor initializes empty Fields
	}
	
	/**
	 * Primary constructor establishes values of potential collider
	 * @param x - x coordinate
	 * @param y - y coordinate
	 * @param width - width of
	 * @param height - height of
	 */
    public RectangleCollider(int x, int y, int width, int height) {
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;	
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
    
	/**
	 * Collision detection between two potential colliders
	 * @param e2 - a collider
	 */
	public boolean collisionDetector(RectangleCollider e2) {
		//Represent the Person objects as Rectangles for simple collision detection
		Rectangle rect1 = new Rectangle(x, y, width, height);
		Rectangle rect2 = new Rectangle(e2.x, e2.y, e2.width, e2.height);

		return rect1.intersects(rect2);
	}
    
}
