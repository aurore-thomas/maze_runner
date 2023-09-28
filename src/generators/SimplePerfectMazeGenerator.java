package generators;

import interfaces.MazeGenerator;
import util.Pair;
import static util.Pair.containsPair;

import java.util.ArrayList;
import java.util.Objects;


public class SimplePerfectMazeGenerator implements MazeGenerator {
    int lines, columns;
    boolean[][] mazeBoolean;
    ArrayList<Pair<Integer, Integer>> visitedCells = new ArrayList<>();
    ArrayList<Pair<Integer, Integer>> visitedIntersection = new ArrayList<>();


    @Override
    public void initializeMaze(int lin, int col) {
        lines = lin * 3;
        columns = col * 3;
        mazeBoolean = new boolean[lines][columns];

        // create a False maze
        for (int i=0; i<lines; i++) {
            for (int j=0; j<columns; j++) {
                mazeBoolean[i][j] = false;
            }
        }

        // First line, with the entry of the labyrinth
        mazeBoolean[0][1] = true;
        mazeBoolean[1][1] = true;
    }


    @Override
    public boolean[][] generateCenterMaze(int x, int y) {
        int nextX = 0;
        int nextY = 0;
        int totalCells = (lines/3) * (columns/3);
        int nextDirection;

        ArrayList<String> unvisitedCellsNeighbour = new ArrayList<>();
        Pair<Integer, Integer> coordinates = new Pair<>(x, y);

        if (visitedCells.size() == totalCells) {
            // If all the center of the cells are visited, the maze are entirely generated
            return mazeBoolean;
        } else {
            // Generate the maze
            if (y >=4 && !containsPair(visitedCells, new Pair<>(x, y-3))) {
                unvisitedCellsNeighbour.add("North");
            }
            if (y<=(lines-1-4) && !containsPair(visitedCells, new Pair<>(x, y+3))) {
                unvisitedCellsNeighbour.add("South");
            }
            if (x>=4 && !containsPair(visitedCells, new Pair<>(x-3, y))) {
                unvisitedCellsNeighbour.add("West");
            }
            if (x<=(columns-1-4) && !containsPair(visitedCells, new Pair<>(x+3, y))) {
                unvisitedCellsNeighbour.add("East");
            }

            if (unvisitedCellsNeighbour.size() == 0) {
                visitedIntersection.remove((visitedIntersection.size())-1);
                Pair<Integer, Integer> nextCoordinates = visitedIntersection.get(visitedIntersection.size()-1);
                nextX = nextCoordinates.getFirst();
                nextY = nextCoordinates.getSecond();
                if (!containsPair(visitedCells, new Pair<>(x, y))) {
                    visitedCells.add(coordinates);
                    visitedIntersection.add(coordinates);
                }
                generateCenterMaze(nextX, nextY);
            } else {
                nextDirection = (int)(Math.random() * unvisitedCellsNeighbour.size());

                if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "North")) {
                    nextX = x;
                    nextY = y - 3;
                    mazeBoolean[x][y-1] = true;
                    mazeBoolean[x][y-2] = true;
                    mazeBoolean[x][y-3] = true;
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "South")) {
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
                } else if (Objects.equals(unvisitedCellsNeighbour.get(nextDirection), "East")) {
                    nextX = x + 3;
                    nextY = y;
                    mazeBoolean[x+1][y] = true;
                    mazeBoolean[x+2][y] = true;
                    mazeBoolean[x+3][y] = true;
                }
                if (!containsPair(visitedCells, new Pair<>(x, y))) {
                    visitedCells.add(coordinates);
                    visitedIntersection.add(coordinates);
                }
                generateCenterMaze(nextX, nextY);
            }
        }
        return mazeBoolean;
    }

    @Override
    public void lastLineMaze(boolean[][] maze) {
        int exit;
        ArrayList<Integer> possibleExit = new ArrayList<>();

        if (maze[columns-2][lines-2]) {
            exit = columns-2;
        } else {
            for (int i=1; i<columns; i+=3) {
                System.out.println(maze[i][lines-2]);
                if (maze[i][lines-2]) {
                    System.out.println(i);
                    possibleExit.add(i);
                }
            }
            int indexExit = (int) (Math.random()*possibleExit.size());
            exit = possibleExit.get(indexExit);
        }

        maze[lines-1][exit] = true;
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
