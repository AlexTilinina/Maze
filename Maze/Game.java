package Maze;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Game implements Runnable {

    private boolean running;
    private static long start = 0;
    private static long finish = 0;
    private static long record = 0;
    private static long time;
    //private static int sumBall = 0;

    protected int timeDelay = 100;
    protected Canvas canvas;
    protected GameMap map;
    protected Player player;
    protected KeyAdapter keyListener = new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == 37) {
                    player.directionX = -1;
                } 
                if(e.getKeyCode() == 39) {
                    player.directionX = 1;
                }
                if(e.getKeyCode() == 38) {
                    player.directionY = -1;
                }
                if(e.getKeyCode() == 40) {
                    player.directionY = 1;
                }
            }

            public void keyReleased(KeyEvent e) {

               if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
                    player.directionX = 0;
                }  
                if(e.getKeyCode() == 38 || e.getKeyCode() == 40) {
                    player.directionY = 0;
                }   
            }
    };

    public void start() {
        start = System.currentTimeMillis();
        running = true;
        canvas = new Canvas();
        player = new Player();
        setMap(1);
        setListener();
        new Thread(this).start();
    }

    public void setMap(int mapId) {
        if(mapId == 2) {
            map = new Map2();
        }
        else if(mapId == 3) {
            map = new Map3();
        }
        else if(mapId == 4) {
            map = new Map4();
        }
        else if(mapId == 5) {
            map = new Map4();
            JOptionPane.showMessageDialog(null, "Вы победили. Новые уровни появятся в ближайшее время");
            stop();
        }
        else {
            map = new Map1();
        }

        player.posX = BaseTile.SIZE;
        player.posY = BaseTile.SIZE; 
        player.posRenderX =BaseTile.SIZE;
        player.posRenderY =BaseTile.SIZE;
    }

    public void stop() {
        JOptionPane.showMessageDialog(null, "Вы победили. Новые уровни появятся в ближайшее время");
        unSetListener();
        running = false;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void run() {
       while(running) {
           try {
                TimeUnit.MILLISECONDS.sleep(timeDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }             
            update(); 
       } 
    }

    public void setListener() {        
        canvas.setFocusable(true);//нужно указать для получения событий с клавиатуры
        canvas.addKeyListener(keyListener);
    }

    public void unSetListener() {
        canvas.setFocusable(false);//убираем    фокус
        canvas.removeKeyListener(keyListener);
    }

    protected void update() {
        checkingChangeMap();
        canvas.removeRenders(); 
        int mapW = map.getWidth(),
            mapH = map.getHeight(),
            x,y, tileId,
            widthTile = 18,
            heightTile = 12,
            cWidthTile = widthTile / 2 - 1,
            cHeightTile = heightTile / 2 - 1,
            offsetX = (player.posX - player.posRenderX)  % BaseTile.SIZE,
            offsetY = (player.posY - player.posRenderY) % BaseTile.SIZE,
            startTileX = (int)Math.floor( Math.abs(player.posX - player.posRenderX) / BaseTile.SIZE),
            startTileY = (int)Math.floor( Math.abs(player.posY - player.posRenderY) / BaseTile.SIZE),
            endTileX = widthTile + startTileX > mapW ? mapW : widthTile + startTileX,
            endTileY = heightTile + startTileY > mapH ? mapH : heightTile + startTileY;

        boolean movePlayerX = (player.posX / BaseTile.SIZE < cWidthTile || mapW - player.posX / BaseTile.SIZE < cWidthTile+1),
            movePlayerY = (player.posY / BaseTile.SIZE < cHeightTile || mapH - player.posY / BaseTile.SIZE < cHeightTile+2);
        BaseTile tile;

        for(y = startTileY; y < endTileY; y ++) {
            for(x = startTileX; x < endTileX; x ++) {
                tileId = map.getTileId(x, y); 
                tile = BaseTile.getTileById(tileId);
                tile.posX = (x - startTileX) * BaseTile.SIZE;
                tile.posY = (y - startTileY) * BaseTile.SIZE;
                tile.posX -=offsetX; 
                tile.posY -=offsetY;
                canvas.addRender( tile );
            }
        }
        canvas.addRender(player);
        if((player.directionX != 0 || player.directionY  != 0) && accessMove()) {
            player.posX += player.directionX * player.speed;
            player.posY += player.directionY * player.speed;
            if(movePlayerX) {
               player.posRenderX += player.directionX * player.speed;
            } 
            if(movePlayerY) {
               player.posRenderY += player.directionY * player.speed;
            } 
        } 
        canvas.repaint();
    }

    public void checkingChangeMap() {
        int tileX = player.posX / BaseTile.SIZE,
            tileY = player.posY / BaseTile.SIZE,
            tileId = map.getTileId(tileX, tileY);
        BaseTile tile = BaseTile.getTileById(tileId);

        if(tile.door > 0) {
            setMap(tile.door);
        }
        finish = System.currentTimeMillis();
        time = (finish - start) / 1000;
        if(tile.door > 0 && !tile.trap) {
            if ((time < record) || (record == 0)){
                record = time;
            }
            JOptionPane.showMessageDialog(null, "Уровень пройден. " +
                    "Время прохождения уровня:" + time + " c." + " Ваш рекорд " + record + " c.");
            start = finish;
            finish = 0;
        }

    }

    protected boolean accessMove() {          
        int left ,right, top,down; 
        boolean isWalkable = true;
        left = (int)Math.ceil((player.posX) / BaseTile.SIZE); 
        right = (int)Math.floor((player.posX + player.width - 1) / BaseTile.SIZE);
        top = (int)Math.ceil((player.posY + player.speed * player.directionY) / BaseTile.SIZE); 
        down = (int)Math.floor((player.posY + player.height  + player.speed * player.directionY - 1) / BaseTile.SIZE);

        if(player.directionY == -1 && !(tileIsWalkable(left,top) && tileIsWalkable(right,top))) {
            isWalkable = false;
        } else if(player.directionY == 1 && !(tileIsWalkable(left,down) && tileIsWalkable(right,down))) {
            isWalkable = false;
        }

        left = (int)Math.ceil((player.posX + player.speed * player.directionX) / BaseTile.SIZE); 
        right = (int)Math.floor((player.posX + player.width + player.speed * player.directionX - 1) / BaseTile.SIZE);
        top = (int)Math.ceil((player.posY) / BaseTile.SIZE); 
        down = (int)Math.floor((player.posY + player.height -1) / BaseTile.SIZE);

        if(player.directionX == -1 && !(tileIsWalkable(left,top) && tileIsWalkable(left,down))) {
            isWalkable = false;
        } else if(player.directionX == 1 && !(tileIsWalkable(right,top) && tileIsWalkable(right,down))) {
            isWalkable = false;
        }
        return isWalkable;
    }

    protected boolean tileIsWalkable(int x, int y) {
        BaseTile tile =  BaseTile.getTileById(map.getTileId(x, y) );
        return (tile != null && tile.isWalkable);
    }
}