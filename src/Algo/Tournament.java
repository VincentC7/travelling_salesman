package Algo;

import java.util.ArrayList;
import java.util.Collections;

public class Tournament extends Thread {

    private ArrayList<Path> tournament_population;
    private Path winner;


    public Tournament(){
        tournament_population = new ArrayList<>();
    }

    public void addPath(Path p){
        tournament_population.add(p);
    }

    @Override
    public void run(){
        Collections.sort(tournament_population);
        winner = tournament_population.get(0);
    }

    public Path getWinner() {
        return winner;
    }
}
