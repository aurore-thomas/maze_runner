package interfaces;

public interface MazeGenerator {
    public void initializeMaze(int lin, int col);
    public boolean[][] generateCenterMaze(int x, int y);
    public void lastLineMaze(boolean[][] maze);
    public void print(boolean[][] maze);
}
