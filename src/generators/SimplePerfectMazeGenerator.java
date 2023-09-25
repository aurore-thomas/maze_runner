package generators;

import interfaces.MazeGenerator;

import javax.xml.stream.FactoryConfigurationError;
import java.util.ArrayList;
import java.util.Objects;

public class SimplePerfectMazeGenerator implements MazeGenerator {
    int lines, columns;
    boolean[][] mazeBoolean;
    ArrayList<Integer> visitedCells = new ArrayList<>();

    // Set the size m*n of the future maze
    @Override
    public void initializeMaze(int lin, int col) {
        lines = lin;
        columns = col;
        mazeBoolean = new boolean[lines][columns];

        // create a False maze
        for (int i=0; i<lines; i++) {
            for (int j=0; j<columns; j++) {
                mazeBoolean[i][j] = false;
            }
        }

        // First line, with the entry of the labyrinth
//        int entry = (int) (Math.random() * (columns -1));
        mazeBoolean[0][1] = true;
    }

    // Fill the maze
    @Override
    public boolean[][] generate(int x, int y) {

        while (true) {
            ArrayList<String> unvisitedCellsNeighbour = new ArrayList<String>();
            if (y > 1 && !visitedCells.contains((x), (y-2)) {
                unvisitedCellsNeighbour.add("North");
            } else if (y<(columns-2) && !mazeBoolean[x][y+2]) {
                unvisitedCellsNeighbour.add("South");
            } else if (x>1 && !mazeBoolean[x-2][y]) {
                unvisitedCellsNeighbour.add("West");
            } else if (x<(lines-2) && !mazeBoolean[x+2][y]) {
                unvisitedCellsNeighbour.add("East");
            }

            if (unvisitedCellsNeighbour.size() == 0) {
                // Come back
            } else {
                int nextDirection = (int)(Math.random() * unvisitedCellsNeighbour.size());
                
                if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "North")) {
                    int nextX = x;
                    int nextY = (y-2);
                    mazeBoolean[x][y-1] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "South")) {
                    int nextX = x;
                    int nextY = y + 2;
                    mazeBoolean[x][y+1] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "West")) {
                    int nextX = x-2;
                    int nextY = y;
                    mazeBoolean[x-1][y] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "East")) {
                    int nextX = x + 2;
                    int nextY = y;
                    mazeBoolean[x+1][y] = true;
                }
            }
        }

        return mazeBoolean;
    }

    @Override
    public void print(boolean[][] maze, int lin, int col)  {
        for (int i=0; i<lin; i++) {
            System.out.println();
            for (int j=0; j<col; j++) {
                if (maze[i][j]) {
                    System.out.print(".");
                } else {
                    System.out.print("#");
                }
            }
        }
    }
}
