package main;

import Structure_Donnees.Graphe;

public class Main {

    public static void main(String[] args) {
        Graphe.create_cityJson(15);
        Graphe g = Graphe.create_graphe("cities.json");
        System.out.println(g);

    }

}
