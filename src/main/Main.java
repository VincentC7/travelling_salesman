package main;

import Algo.TravellingSalesman;
import Structure_Donnees.Graphe;

public class Main {

    public static void main(String[] args) {
        //Graphe.create_cityJson(25);
        TravellingSalesman travellingSaleman = new TravellingSalesman();
        travellingSaleman.runAlgo();
    }
}
