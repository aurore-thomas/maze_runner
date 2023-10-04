package maze.generators;

import maze.interfaces.MazeGenerator;
import util.Pair;
import static util.Pair.containsPair;

import java.util.ArrayList;
import java.util.Objects;


public class SimplePerfectMazeGenerator implements MazeGenerator {
    int lines, columns;
    private boolean[][] mazeBoolean;

    public SimplePerfectMazeGenerator(int inputColumns, int inputLines) {
        initializeMaze(inputColumns, inputLines);
        generateCenterMaze(1, 1);
        print(mazeBoolean);
    }


    @Override
    public void initializeMaze(int inputColumns, int inputLines) {
        lines = inputLines * 3; // One cell is composed by 9 mini-cells (3*3)
        columns = inputColumns * 3;
        mazeBoolean = new boolean[columns][lines]; // We create a 2D list boolean to represent the maze

        // We fill the maze with "False" value
        for (int i=0; i<columns; i++) {
            for (int j=0; j<lines; j++) {
                mazeBoolean[j][i] = false;
            }
        }

        // We set the entry and the exit of the maze, which are always the same
        mazeBoolean[0][1] = mazeBoolean[1][1] = mazeBoolean[columns-1][lines-2] = true;
    }


    @Override
    public boolean[][] generateCenterMaze(int x, int y) {
       // First, we create the vertical paths on each line there are cells.
        for (int i=1; i<columns-1; i++) {
            for (int j=1; j<lines-1; j+=3) {
                mazeBoolean[i][j] = true;
            }
        }

        // We connect the paths by destroying one wall between each path
        for (int i=2; i<columns-1; i+=3) {
            int wallToDestroy = (int) (2 + (Math.random() *(lines-2 - 2)));

            mazeBoolean[wallToDestroy][i] = true;
            mazeBoolean[wallToDestroy][i+1] = true;
        }

        return mazeBoolean;
    }
    
    @Override
    public void print(boolean[][] maze)  {
        for (int i=0; i<columns; i++) {
            System.out.println();
            for (int j=0; j<lines; j++) {
                if (maze[i][j]) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
        }
    }
}
