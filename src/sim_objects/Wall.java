package sim_objects;

import java.awt.Image;
import javax.swing.ImageIcon;

public class Wall extends RectangleCollider {

    protected boolean visible;
    protected Image image;
    protected boolean vertical;

    public Wall(int x, int y, String imageS, boolean vertical) {

        this.x = x;
        this.y = y;
        visible = true;
        this.vertical = vertical;
        loadImage(imageS);
        setWallDimensions();
    }

    protected void loadImage(String imageName) {
        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    protected void setWallDimensions() {
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);       
    }    

    public Image getImage() {
        return image;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
    
}