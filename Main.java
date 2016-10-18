// import Maze.game.Game;

public class Main {

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
        
        javax.swing.JFrame f =  new javax.swing.JFrame(); 
		
        f.setLayout(null);
        f.setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);  
		
        //установка размера холста
        game.getCanvas().setBounds(0, 0, 640, 480);
  
        f.add(game.getCanvas()); 
        f.setTitle("Maze");
        f.setBounds(300,300,640,480);    
        f.setVisible(true);
    }
}
