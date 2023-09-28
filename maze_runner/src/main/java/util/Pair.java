package util;

import java.util.ArrayList;

/*
I recreate the Pair class and the contains method which will be used with the unvisitedCells arraylist
I could also have used the Pair class by injecting a dependency, but I only need one method.
 */
public class Pair<T, U> {
    private T first;
    private U second;

    // Getters & setters
    public Pair(T first, U second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public U getSecond() {
        return second;
    }


    public  static <T, U> boolean containsPair(ArrayList<Pair<T, U>> list, Pair<T, U> targetPair) {
        for (Pair<T, U> pair : list) {
            if (pair.getFirst().equals(targetPair.getFirst()) && pair.getSecond().equals(targetPair.getSecond())) {
                return true;
            }
        }
        return false;
    }
}
