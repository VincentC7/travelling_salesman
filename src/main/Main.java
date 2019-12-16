package main;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;
import Structure_Donnees.Matrix;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Graphe.create_cityJson(15);
        Graphe g = Graphe.create_graphe("cities.json");
        System.out.println(g);
        /**
        g.initialize(20);
        g.show_genomes();
        Matrix m = Matrix.generate_random_matrix(20);
        System.out.println(m);
        System.out.println(m.getNumber(0,0));
        System.out.println(m.getNumber(1,0));
        System.out.println(m.getNumber(0,1));
        System.out.println(m.getNumber(1,1));
        System.out.println(m.getNumber(20,20));**/

    }

}
