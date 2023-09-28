package maze.interfaces;

public interface MazeGenerator {
    public void initializeMaze(int inputColumns, int inputLines);
    public boolean[][] generateCenterMaze(int x, int y);
    public void print(boolean[][] maze);
}
