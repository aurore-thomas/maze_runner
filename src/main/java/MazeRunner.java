import maze.Maze;

import java.util.ArrayList;
import java.util.List;

public class MazeRunner {
    public static int width, height;
    public static String type, status;

    private static boolean getSize(String[] args) {
        try {
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("Erreur : Veuillez fournir une largeur et une hauteur valides supérieurs à 5");
            return false;
        }

        if (width<5 || height<5) {
            System.out.println("Erreur : Veuillez fournir une largeur et une hauteur valides supérieurs à 5");
            return false;
        }
        return true;
    }

    private static boolean getInput(String[] args) {
        status = args[2];
        type = args[3];

        List<String> authorizedStatus = new ArrayList<>(List.of("perfect", "imperfect"));
        List<String> authorizedTypes = new ArrayList<>(List.of("simple", "graph", "optimized"));
        return authorizedTypes.contains(type) && authorizedStatus.contains(status);
    }

    public static void printManual() {
        System.out.println("Utilisation : java -jar MazeRunner.jar [largeur] [hauteur] [perfect/imperfect] [simple/graph/optimized]");
    }

    public static void main(String[] args) {
        if (!getSize(args)) {
            printManual();
        } else if (!getInput(args)) {
            System.out.println("Erreur : Veuillez fournir un type de labyrinthe et une méthode de génération valides.");
            printManual();
        } else {
            Maze newMaze = new Maze(width, height, status, type);
            newMaze.choiceType();
        }
    }
}