package Maze;

abstract class GameMap {

    protected int[][] map;

    public boolean hasTile(int x, int y) {
       return (x >= 0 && y >= 0 && y <  getHeight() && x <  getWidth()); 
    }

    public int getTileId(int x, int y) {
        return hasTile(x,y) ? map[y][x] : 0;
    }

    public int getHeight() {
        return map.length;
    }

    public int getWidth() {
        return map[0].length;
    }
}
