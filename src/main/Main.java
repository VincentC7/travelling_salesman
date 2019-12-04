package main;

import Structure_Donnees.Graphe;

public class Main {

    public static void main(String[] args) {
        Graphe g = Graphe.creer_graphe("villes.json");
        System.out.println(g);
    }

}
