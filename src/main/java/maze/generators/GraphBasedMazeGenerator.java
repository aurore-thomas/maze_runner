package maze.generators;

import maze.interfaces.MazeGenerator;
import util.Pair;

import java.util.ArrayList;
import java.util.Objects;

import static util.Pair.containsPair;

public class GraphBasedMazeGenerator implements MazeGenerator {
    int lines, columns;
    boolean[][] mazeBoolean;
    ArrayList<Pair<Integer, Integer>> visitedCells = new ArrayList<>();
    ArrayList<Pair<Integer, Integer>> visitedIntersection = new ArrayList<>();

    public GraphBasedMazeGenerator(int inputColumns, int inputLines, String status) {
        initializeMaze(inputColumns, inputLines);
        generateCenterMaze(1, 1);
        if (Objects.equals(status, "imperfect")) {
            destroyWall();
        }
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
                mazeBoolean[i][j] = false;
            }
        }

        // We set the entry and the exit of the maze, which are always the same
        mazeBoolean[0][1] = true;
        mazeBoolean[1][1] = true;
        mazeBoolean[columns-1][lines-2] = true;
    }


    @Override
    public boolean[][] generateCenterMaze(int x, int y) {
        int nextX = 0;
        int nextY = 0;
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
                    mazeBoolean[x][y-1] = true;
                    mazeBoolean[x][y-2] = true;
                    mazeBoolean[x][y-3] = true;
                }  else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "East")) {
                    nextX = x + 3;
                    nextY = y;
                    mazeBoolean[x+1][y] = true;
                    mazeBoolean[x+2][y] = true;
                    mazeBoolean[x+3][y] = true;
                }  else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "South")) {
                    nextX = x;
                    nextY = y + 3;
                    mazeBoolean[x][y+1] = true;
                    mazeBoolean[x][y+2] = true;
                    mazeBoolean[x][y+3] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "West")) {
                    nextX = x - 3;
                    nextY = y;
                    mazeBoolean[x-1][y] = true;
                    mazeBoolean[x-2][y] = true;
                    mazeBoolean[x-3][y] = true;
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

    // In the case of imperfect graph maze :
    public void destroyWall() {
        // In a maze, there are some interior walls that can't be destroyed. (in the optic to not create a path with 2 cells width)
        // So we put these unbreakable walls in an ArrayList
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
        int numberOfWallToDestroy = 10 *interiorWalls / 100;
        // This expression is the number of interior walls in a perfect square maze
        // To create an imperfect maze, we will destroy ten percent of interior walls of the perfect maze.

        while (numberOfWallToDestroy !=0) {
            // Exterior walls can't be destroyed, so we fixed limits
            int potentialX = (int)(1 + (Math.random() * (columns-2 - 1)));
            int potentialY = (int)(1 + (Math.random() * (lines -2 -1)));
            boolean validCell = true;

            for (Pair<Integer, Integer> fixedInteriorWall : fixedInteriorWalls) {
                if (potentialX == fixedInteriorWall.getFirst() && potentialY == fixedInteriorWall.getSecond()) {
                    validCell = false;
                    break;
                }
            }

            if (!mazeBoolean[potentialX][potentialY] && validCell) {
                // This works only if the cell was a wall, otherwise we come back to the beginning of the loop
                mazeBoolean[potentialX][potentialY] = true;
                numberOfWallToDestroy--;
            }
        }
    }
}
