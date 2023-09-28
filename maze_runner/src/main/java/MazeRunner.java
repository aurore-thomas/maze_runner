import maze.generators.SimpleImperfectMazeGenerator;

public class MazeRunner {
    public static void main(String[] args) {
//        SimplePerfectMazeGenerator maze = new SimplePerfectMazeGenerator();
//        maze.initializeMaze(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
//        boolean[][] finalMaze = maze.generateCenterMaze(1, 1);
////        maze.lastLineMaze(finalMaze);
//        maze.print(finalMaze);

        SimpleImperfectMazeGenerator maze = new SimpleImperfectMazeGenerator();
        maze.initializeMaze(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
        boolean[][] finalMaze = maze.generateCenterMaze(1, 1);
        maze.destroyWall(finalMaze);
        maze.print(finalMaze);
    }
}