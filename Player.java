import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
public class Player implements IRenderToConvas { 
    protected Image imageSrc;
    public int x;
    public int y;//координаты
	public int width = 10;
    public int length = 10;//габариты перса
    public int vx = 0;
    public int vy = 0;//движение
	public int v = 5;
	public int posRenderX;
    public int posRenderY;
    public Player() {
        String name = "/data/player.png";
        imageSrc = new ImageIcon(getClass().getResource(name)).getImage();
    }
    public void render(Graphics g) {   
        g.drawImage(imageSrc, posRenderX, posRenderY, width, length, null); 
    }
}
