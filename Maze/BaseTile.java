package Maze;

import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;
import javax.swing.ImageIcon;

public class BaseTile implements IRenderToCanvas {

	public static final int SIZE = 40;
    private static HashMap<String,Image> images = new HashMap<String,Image>();
    public String image;
    public int posX;
    public int posY;
    public boolean isWalkable;
	public int door = 0;
    public static int cheese = 0;
    public boolean trap = false;

    public BaseTile(String image, int posX, int posY, boolean isWalkable) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.isWalkable = isWalkable;
    }

    /*public BaseTile(String image, int posX, int posY, boolean isWalkable, boolean cheese) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.isWalkable = isWalkable;
        this.cheese += 1;
    }*/

    public BaseTile(String image, int posX, int posY, boolean isWalkable, int door) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.isWalkable = isWalkable;
        this.door = door;
    }

    public BaseTile(String image, int posX, int posY, boolean isWalkable, int door, boolean trap) {
        this.image = image;
        this.posX = posX;
        this.posY = posY;
        this.isWalkable = isWalkable;
        this.door = door;
        this.trap = trap;
    }

    public static  BaseTile getTileById(int tileId) {
        if(tileId == 1) {
            return new BaseTile( "/data/black.png", 0, 0, true);
        }
        if(tileId == 2) {
            return new BaseTile( "/data/white.png", 0, 0, false);
        }
        if(tileId == 3) {
            return new BaseTile( "/data/red.png", 0, 0, true, 2);
        }
        if(tileId == 4) {
            return new BaseTile( "/data/red.png", 0, 0, true, 3);
        }
        if(tileId == 5) {
            return new BaseTile( "/data/red.png", 0, 0, true, 4);
        }
        if(tileId == 7) {
            return new BaseTile( "/data/red.png", 0, 0, true, 5);
        }
        /*if(tileId == 5) {
            return new BaseTile( "/data/cheese.png", 0, 0, true, true);
		}*/
        if(tileId == 6) {
            return new BaseTile( "/data/trap.png", 0, 0, true, 1, true);
        }
        return null;     
    }

    @Override
    public void render(Graphics g) {   
        g.drawImage(getImage(image), posX, posY, SIZE, SIZE, null); 
    }

	public static Image getImage(String name) { 
        if(images.get(name) == null) {
            images.put(name, new ImageIcon(BaseTile.class.getResource(name)).getImage());
        } 
        return images.get(name);
    }
}
