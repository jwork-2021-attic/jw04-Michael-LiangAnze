package com.anish.calabashbros;


public class World {

    public static final int WIDTH = 50;
    public static final int HEIGHT = 50;
    public static final int mazeSize = 40;

    private Tile<Thing>[][] tiles;

    public World() {

        if (tiles == null) {
            tiles = new Tile[WIDTH][HEIGHT];
        }

        int[][]maze;
        MazeGenerator gennerator = new MazeGenerator(mazeSize);
        gennerator.generateMaze();
        maze = gennerator.getMyMaze();
        

        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {   
                tiles[i][j] = new Tile<>(i, j);
                tiles[i][j].setThing(new Floor(this));
            }
        }
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                if(maze[i][j] == 0){
                    tiles[i][j].setThing(new Wall(this));
                }  
            }
            tiles[i][mazeSize].setThing(new Wall(this));
            tiles[mazeSize][i].setThing(new Wall(this));
        }
        tiles[mazeSize][mazeSize].setThing(new Wall(this));
        tiles[mazeSize - 1][mazeSize].setThing(new Floor(this));
    }

    public Thing get(int x, int y) {
        return this.tiles[x][y].getThing();
    }

    public void put(Thing t, int x, int y) {
        this.tiles[x][y].setThing(t);
    }

}
