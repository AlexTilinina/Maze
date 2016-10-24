package Maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Canvas extends JPanel {

    protected Image bufer = null;
    protected Color backGround = Color.black;
    protected ArrayList<IRenderToCanvas> renders = new ArrayList<IRenderToCanvas>();

    public void addRender(IRenderToCanvas render) {
        renders.add(render);
    }

    public void removeRenders() {
        renders.clear();
    }

    public void paintWorld(Graphics g) {
        g.clearRect(0, 0, getWidth(), getHeight());
        g.setColor(backGround);
        g.fillRect(0, 0, getWidth(), getHeight());
        for(IRenderToCanvas render : renders) {
            render.render(g);
        }
    }

    @Override
    public void paint(Graphics g) { 
        super.paint(g);
		if(bufer == null) {
           bufer = createImage(getWidth(), getHeight());
        }  
        paintWorld(bufer.getGraphics());
        g.drawImage(bufer, 0, 0, null); 
    }
}
