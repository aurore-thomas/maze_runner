package interfaces;

public interface MazeGenerator {
    public void initializeMaze(int lin, int col);
    public boolean[][] generate(int x, int y);
    public void print(boolean[][] maze, int lin, int col);
}
