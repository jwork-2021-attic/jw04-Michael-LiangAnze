package com.anish.screen;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Color;
import javax.lang.model.type.NullType;

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
    String steps[];
    Calabash calabash;
    int[][]maze;
    public static final int mazeSize = 10;
    

    public WorldScreen() {
        world = new World();
        generateMyMaze();

        calabash = new Calabash(new Color(240, 240, 0), 1, world); // original place
        world.put(calabash, 0, 0);

        DFS dfsAlgorithm = new DFS(maze,mazeSize);
        dfsAlgorithm.searchPath();
        String plan = dfsAlgorithm.getPlan();
        steps = parsePlan(plan);
        for(String s:steps){
            System.out.println(s);
        }
        System.out.println('\n');
    }

    private void generateMyMaze(){
        MazeGenerator gennerator = new MazeGenerator(mazeSize);
        gennerator.generateMaze();
        maze = gennerator.getMyMaze();
        for (int i = 0; i < mazeSize; i++) {
            for (int j = 0; j < mazeSize; j++) {
                if(maze[j][i] == 0){
                    world.put(new Wall(this.world),i,j);
                }  
            }
            world.put(new Wall(world),i,mazeSize);
            world.put(new Wall(world),mazeSize,i);
        }
        world.put(new Wall(this.world),mazeSize,mazeSize);
        world.put(new Floor(this.world,Color.gray),mazeSize-1,mazeSize);
        // System.out.println("SYMBOLIC MAZE\n" + gennerator.getSymbolicMaze());
    }

    private String[] parsePlan(String plan) {
        return plan.split("\n");
    }

    private void execute(String step,String lastStep) {
        System.out.println("step:"+step+" lastStep:"+lastStep);
        String[] couple1 = step.split("<->");
        String[] couple2 = lastStep.split("<->");
        world.put(calabash,Integer.parseInt(couple1[1]),Integer.parseInt(couple1[0]));
        if(Integer.parseInt(couple1[2]) == 9){ // gray
            world.put(new Floor(this.world,Color.green),Integer.parseInt(couple2[1]),Integer.parseInt(couple2[0]));
        }
        else{
            world.put(new Floor(this.world,Color.red),Integer.parseInt(couple2[1]),Integer.parseInt(couple2[0]));
        }
    }


    @Override
    public void displayOutput(AsciiPanel terminal) {

        for (int x = 0; x < World.WIDTH; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {

                terminal.write(world.get(x, y).getGlyph(), x, y, world.get(x, y).getColor());

            }
        }
    }

    int i = 1;

    @Override
    public Screen respondToUserInput(KeyEvent key) {

        if (i < this.steps.length) {
            this.execute(steps[i],steps[i-1]);
            i++;
        }

        return this;
    }

}
