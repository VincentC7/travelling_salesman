package main;

import Structure_Donnees.Graphe;

public class Main {

    public static void main(String[] args) {
        Graphe g = Graphe.create_graphe("villes.json");
        System.out.println(g);
    }

}
