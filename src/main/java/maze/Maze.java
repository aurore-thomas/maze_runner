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
//        System.out.println(type + " type");
//        System.out.println(status + " stat");
//        System.out.println(width + " x");
//        System.out.println(height + " y");
    }

    public void choiceType() {

        try {
            System.out.println("Entrée try maze");
            switch (type) {
                case "simple":
                    System.out.println("Simple choice");
                    if (Objects.equals(status, "perfect")) {
                        System.out.println("Perfect choice");
                        new SimplePerfectMazeGenerator(width, height);
                    } else if (Objects.equals(status, "imperfect")){
                        System.out.println("Imperfect choice");
                        new SimpleImperfectMazeGenerator(width, height);
                    }
                case "graph":
                    new GraphBasedMazeGenerator(width, height, status);
                case "optimized":
                    new OptimizedMazeGenerator(width, height, status);
            }
        } catch (Exception e) {
            System.out.println("Erreur inattendue lors de la génération du labyrinthe. Veuillez réessayer.");
            System.out.println("Pour lancer un labyrinthe, merci d'entrer : ");
            System.out.println("java -jar MazeRunner.jar [largeur] [hauteur] [perfect/imperfect] [simple/graph/optimized]");
        }
    }

}
