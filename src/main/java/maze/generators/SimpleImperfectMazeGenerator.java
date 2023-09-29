package maze.generators;

import maze.interfaces.MazeGenerator;
import util.Pair;

import java.util.ArrayList;

public class SimpleImperfectMazeGenerator implements MazeGenerator {
    int lines, columns;
    boolean[][] mazeBoolean;


    public SimpleImperfectMazeGenerator(int inputColumns, int inputLines) {
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
                mazeBoolean[i][j] = true;
            }
        }

        // We set the entry and the exit of the maze, which are always the same
        mazeBoolean[0][1] = true;
        mazeBoolean[columns-1][lines-2] = true;
    }

    @Override
    public boolean[][] generateCenterMaze(int x, int y) {
        ArrayList<Pair<Integer, Integer>> fixedInteriorWalls = new ArrayList<>();
        for (int i=2; i<columns-2; i+=3) {
            for (int j=2; j<lines-2; j+=3) {
                fixedInteriorWalls.add(new Pair<>(i, j));
                fixedInteriorWalls.add(new Pair<>(i+1, j));
                fixedInteriorWalls.add(new Pair<>(i, j+1));
                fixedInteriorWalls.add(new Pair<>(i+1, j+1));
            }
        }

        int interiorWalls = 2*(columns*lines/3 -columns-lines+3);

        while (interiorWalls !=0) {
            // Exterior walls can't be destroyed, so we fixed limits
            int potentialX = (int) (1 + (Math.random() * (columns - 2 - 1)));
            int potentialY = (int) (1 + (Math.random() * (lines - 2 - 1)));
            boolean validCell = true;

            for (Pair<Integer, Integer> fixedInteriorWall : fixedInteriorWalls) {
                if (potentialX == fixedInteriorWall.getFirst() && potentialY == fixedInteriorWall.getSecond()) {
                    validCell = false;
                    break;
                }
            }

            if (mazeBoolean[potentialX][potentialY] && validCell) {
                // This works only if the cell was a wall, otherwise we come back to the beginning of the loop
                mazeBoolean[potentialX][potentialY] = false;
                interiorWalls--;
            }
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
