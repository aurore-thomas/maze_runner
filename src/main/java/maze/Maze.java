package maze;


import maze.generators.GraphBasedMazeGenerator;
import maze.generators.OptimizedMazeGenerator;
import maze.generators.SimpleImperfectMazeGenerator;
import maze.generators.SimplePerfectMazeGenerator;

import java.util.Objects;

public class Maze {
    static int width;
    static int height;
    static String status;
    static String type;
    public Maze(int width, int height, String status, String type) {
        Maze.width = width;
        Maze.height = height;
        Maze.status = status;
        Maze.type = type;
    }

    public void choiceType() {
        try {
            switch (type) {
                case "simple":
                    if (Objects.equals(status, "perfect")) {
                        new SimplePerfectMazeGenerator(width, height);
                    } else if (Objects.equals(status, "imperfect")){
                        new SimpleImperfectMazeGenerator(width, height);
                    }
                case "graph":
                    new GraphBasedMazeGenerator(width, height, status);
                case "optimized":
                    new OptimizedMazeGenerator(width, height, status);
            }
        } catch (Exception e) {
            System.out.println("Erreur inattendue lors de la génération du labyrinthe. Veuillez réessayer.");
            System.out.println("Utilisation : java -jar MazeRunner.jar [largeur] [hauteur] [perfect/imperfect] [simple/graph/optimized]");
        }
    }

}
