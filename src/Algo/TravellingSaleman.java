package Algo;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TravellingSaleman {

    private static final int MAX_GENERATION = 500;
    private static final int POPULATION_SIZE = 100;
    private static final int K = 50;

    private int current_generation;
    private Graphe graphe;
    
    private ArrayList<Path> population;
    
    public TravellingSaleman(){
        current_generation =0;
        
        Graphe.create_cityJson(10);
        graphe = Graphe.create_graphe("cities.json");
        assert graphe != null;
        
        population =new ArrayList<>();
    }


    public void startAlgo(){
        initialize(POPULATION_SIZE);
        while (current_generation != MAX_GENERATION){
            System.out.println("====Generation : "+current_generation+"========================================================================================================================");
            selection();
            crossing_over();
            mutation();
            remplacement();
            System.out.println(population);
            current_generation++;
        }
    }

    private void selection(){
        ArrayList<Path> new_population = new ArrayList<>();
        for (int i=0; i<K;i++) new_population.add(population.get(i));

        List<City> cityList = new ArrayList<>(graphe.getCities());
        for (int i=K;i<population.size();i++) new_population.add(graphe.generatePath(cityList));

        Collections.sort(new_population);
        population=new_population;
    }

    private void crossing_over(){
        ArrayList<Path> new_population = new ArrayList<>();

        for (Path path : population) {

        }

    }

    private void mutation(){

    }

    private void remplacement(){

    }

    private void initialize(int nbGenome){
        List<City> cityList = new ArrayList<>(graphe.getCities());
        for(int i = 0;i<nbGenome;i++){
           population.add(graphe.generatePath(cityList));
        }
        Collections.sort(population);
    }



}
