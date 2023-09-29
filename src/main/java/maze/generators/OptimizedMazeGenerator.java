package maze.generators;

import maze.interfaces.MazeGenerator;
import util.Pair;

import java.util.ArrayList;
import java.util.Objects;

import static util.Pair.containsPair;

public class OptimizedMazeGenerator implements MazeGenerator {
    private int lines, columns;
    private boolean[][] mazeBoolean;
    ArrayList<Pair<Integer, Integer>> visitedCells = new ArrayList<>();
    ArrayList<Pair<Integer, Integer>> visitedIntersection = new ArrayList<>();

    // Chronometer :
    private long begin;

    @Override
    public void initializeMaze(int inputColumns, int inputLines) {
        // Begin the chronometer
        begin = System.nanoTime();

        lines = inputLines * 3; // One cell is composed by 9 mini-cells (3*3)
        columns = inputColumns * 3;
        mazeBoolean = new boolean[columns][lines]; // We create a 2D list boolean to represent the maze

        // We fill the maze with "False" value
        for (int i=0; i<columns; i++) {
            for (int j=0; j<lines; j++) {
                mazeBoolean[i][j] = false;
            }
        }

        // We set the entry and the exit of the maze, which are always the same
        mazeBoolean[0][1] = mazeBoolean[1][1] = mazeBoolean[columns-1][lines-2] = true;
    }


    @Override
    public boolean[][] generateCenterMaze(int x, int y) {
        int nextX = 0, nextY = 0;
        int totalCells = (columns/3) * (lines/3); // It's the total of 3*3 cells, so inputColumns*inputLines
        int nextDirection;

        ArrayList<String> unvisitedCellsNeighbour = new ArrayList<>(); // This arraylist will stock the possible next directions
        Pair<Integer, Integer> coordinates = new Pair<>(x, y); // These are the actual coordinates, used to not come back and chose the same neighbour

        if (visitedCells.size() == totalCells) {
            // If all the center of the cells are visited, the maze are entirely generated
            return mazeBoolean;
        } else {
            // Else, we continue to generate the cells of the maze. We also think about the exterior walls with the first condition of each 'if'
            // First, we check which neighbours aren't been visited yet
            if (y >=4 && !containsPair(visitedCells, new Pair<>(x, y-3))) {
                unvisitedCellsNeighbour.add("North");
            }
            if (x<=(columns-1-4) && !containsPair(visitedCells, new Pair<>(x+3, y))) {
                unvisitedCellsNeighbour.add("East");
            }
            if (y<=(lines-1-4) && !containsPair(visitedCells, new Pair<>(x, y+3))) {
                unvisitedCellsNeighbour.add("South");
            }
            if (x>=4 && !containsPair(visitedCells, new Pair<>(x-3, y))) {
                unvisitedCellsNeighbour.add("West");
            }

            // If all the neighbours have been visited, we come back to the precedent intersection (precedent cell visited)
            if (unvisitedCellsNeighbour.size() == 0) {
                visitedIntersection.remove((visitedIntersection.size())-1);
                Pair<Integer, Integer> nextCoordinates = visitedIntersection.get(visitedIntersection.size()-1);
                nextX = nextCoordinates.getFirst();
                nextY = nextCoordinates.getSecond();
            } else {
                // Else, we chose randomly in the possible next direction which cell will be visited during the next move
                nextDirection = (int)(Math.random() * unvisitedCellsNeighbour.size());

                if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "North")) {
                    nextX = x;
                    nextY = y - 3;
                    mazeBoolean[x][y-1] = mazeBoolean[x][y-2] = mazeBoolean[x][y-3] = true;
                }  else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "East")) {
                    nextX = x + 3;
                    nextY = y;
                    mazeBoolean[x+1][y] = mazeBoolean[x+2][y] = mazeBoolean[x+3][y] = true;
                }  else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "South")) {
                    nextX = x;
                    nextY = y + 3;
                    mazeBoolean[x][y+1] = mazeBoolean[x][y+2] = mazeBoolean[x][y+3] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "West")) {
                    nextX = x - 3;
                    nextY = y;
                    mazeBoolean[x-1][y] = mazeBoolean[x-2][y] = mazeBoolean[x-3][y] = true;
                }
            }
            // If we are on a cell which haven't been visited yet, we add it coordinates to the visitedCells arraylist
            if (!containsPair(visitedCells, new Pair<>(x, y))) {
                visitedCells.add(coordinates);
                visitedIntersection.add(coordinates);
            }
            // We use recursion the generate the entire maze
            generateCenterMaze(nextX, nextY);
        }
        return mazeBoolean;
    }

    @Override
    public void print(boolean[][] maze)  {
        // Stop chronometer here, because of the option perfect/imperfect
        long end = System.nanoTime();
        // Print the maze
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
        System.out.println();
        System.out.println();

        // Print the chronometer
        double time = (end - begin) / 1.0e6;
        System.out.print("Maze generated in " + time + " ms.");
    }

    // In the case of imperfect graph maze :
    public void destroyWall(boolean[][] imperfectMaze) {
        // To create an imperfect maze, we only need to create a loop in the maze, so to break two walls
        externLoop:
        for (int i=2; i<columns-1; i+=3) {
            for (int j=1; j<lines-2; j+=3) {
                if (!imperfectMaze[i][j]) {
                    imperfectMaze[i][j] = imperfectMaze[i+1][j] = true;
                    break externLoop;
                }
            }
        }
    }
}
