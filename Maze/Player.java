package Maze;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Player implements IRenderToCanvas {

    protected Image imageSrc;
    public int posX;
    public int posY;
    public int speed = 5;
    public int directionX = 0;
    public int directionY = 0;
    public int width = 30;
    public int height = 30;
    public int posRenderX;
    public int posRenderY;
    public Player() {
        String name = "/data/player.png";
        imageSrc = new ImageIcon(getClass().getResource(name)).getImage();
    }
    public void render(Graphics g) {   
        g.drawImage(imageSrc, posRenderX, posRenderY, width, height, null);
    }
}
