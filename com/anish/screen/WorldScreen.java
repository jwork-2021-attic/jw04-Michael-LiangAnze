package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;


import com.anish.calabashbros.BubbleSorter;
import com.anish.calabashbros.Calabash;
import com.anish.calabashbros.World;
import com.anish.calabashbros.Wall;
import com.anish.calabashbros.Floor;
import com.anish.calabashbros.MazeGenerator;


import asciiPanel.AsciiPanel;
import com.anish.algorithm.DFS;


public class WorldScreen implements Screen {

    private World world;
    String[] sortSteps;
    int[][]maze;
    public static final int mazeSize = 40;
    String plan;

    public WorldScreen() {
        world = new World();
        generateMyMaze();

        Calabash calabash = new Calabash(new Color(240, 240, 0), 1, world);
        world.put(calabash, 0, 0);

        DFS dfsAlgorithm = new DFS(maze,mazeSize);
        dfsAlgorithm.searchPath();
        plan = dfsAlgorithm.getPlan();
        String steps[] = parsePlan(plan);
        System.out.println(steps.length);
        for(String s:steps){
            System.out.println(s);
        }
    }

    private void generateMyMaze(){
        MazeGenerator gennerator = new MazeGenerator(mazeSize);
        gennerator.generateMaze();
        maze = gennerator.getMyMaze();
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                if(maze[i][j] == 0){
                    world.put(new Wall(this.world),i,j);
                }  
            }
            world.put(new Wall(world),i,mazeSize);
            world.put(new Wall(world),mazeSize,i);
        }
        world.put(new Wall(this.world),mazeSize,mazeSize);
        // world.put(new Floor(this.world),mazeSize-1,mazeSize);
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(Calabash[] bros, String step) {
        String[] couple = step.split("<->");
        getBroByRank(bros, Integer.parseInt(couple[0])).swap(getBroByRank(bros, Integer.parseInt(couple[1])));
    }

    private Calabash getBroByRank(Calabash[] bros, int rank) {
        for (Calabash bro : bros) {
            if (bro.getRank() == rank) {
                return bro;
            }
        }
        return null;
    }

    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 0;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        // if (i < this.sortSteps.length) {
        //     this.execute(bros, sortSteps[i]);
        //     i++;
        // }

        return this;
    }

}
