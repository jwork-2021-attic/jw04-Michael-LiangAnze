package com.anish.algorithm;
import java.util.Stack;

public class DFS{

    int[][]map;     // 0 is wall,8 is white,9 is gray,10 is black
    int dimension;
    String plan;
    private Stack<Tuple<Integer,Integer>> stack = new Stack<Tuple<Integer,Integer>>();

    public DFS(int[][]inputMap, int dimension){

        this.dimension = dimension;
        map = new int[dimension][dimension];
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if(inputMap[i][j] == 1){ //movable, set to white 8
                    map[i][j] = 8;
                }
                else{
                    map[i][j] = inputMap[i][j];
                }
            }
        }
    }


    public void searchPath(){
        // begins from (0,0) and ends with (dimension-1,dimension-1)
        dfsSubroutine(new Tuple(0, 0));
    }

    private boolean dfsSubroutine(Tuple<Integer,Integer>p){
        map[p.first][p.second] = 9; // is searching now
        plan += "" + p.first + "<->" + p.second + "\n"; // add plan
        if(p.first == dimension - 1 && p.second == dimension - 1)
            return true;
        Tuple<Integer,Integer>[]movableNeighbor = searchNeighbor(p.first,p.second);
        for(Tuple<Integer,Integer> t:movableNeighbor){
            if(dfsSubroutine(t)){// yes! this way!
                return true;
            }
            else{// so sad, come back to p now
                plan += "" + t.first + "<->" + t.second + "\n"; // add plan
            }
        }

        map[p.first][p.second] = 10; // finish searching
        return false;
    }

    private Tuple[]searchNeighbor(int x,int y){
        // up left down right
        Tuple<Integer,Integer>[] temp= new Tuple[4];
        int size = 0;
        if(y - 1 >= 0){
            if(map[x][y-1] == 8){
                temp[size] = new Tuple(x,y-1);
                size++;
            }
        }
        if(x - 1 >= 0){
            if(map[x-1][y] == 8){
                temp[size] = new Tuple(x,y-1);
                size++;
            }
        }
        if(y + 1 < dimension){
            if(map[x][y+1] == 8){
                temp[size] = new Tuple(x,y+1);
                size++;
            }
        }
        if(x + 1 >= 0){
            if(map[x+1][y] == 8){
                temp[size] = new Tuple(x+1,y);
                size++;
            }
        }
        Tuple<Integer,Integer>[] res= new Tuple[size];
        for(int i = 0;i < size;i++){
            res[i] = temp[i];
        }
        System.out.println(res.length);
        return res;
    }
    public String getPlan(){
        return this.plan;
    } 
}