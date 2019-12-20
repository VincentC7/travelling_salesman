package Algo;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravellingSaleman {

    private static final int MAX_GENERATION = 500;
    private static final int POPULATION_SIZE = 100;
    private static final int K = 50;

    private static final double PERSENTAGE_REMPLACEMENT = 0.8;
    private static final double PERSENTAGE_MUTATION = 0.1;

    private int current_generation;
    private Graphe graphe;
    
    private ArrayList<Path> population;
    
    public TravellingSaleman(){
        current_generation =0;
        
        //Graphe.create_cityJson(10);
        graphe = Graphe.create_graphe("cities.json");
        assert graphe != null;
        
        population =new ArrayList<>();
    }


    public void startAlgo(){
        initialize(POPULATION_SIZE);
        while (current_generation != MAX_GENERATION){
            System.out.println("====Generation : "+current_generation+"========================================================================================================================");
            ArrayList<Path> selected = selection();
            crossing_over(selected);
            mutation();
            remplacement();
            System.out.println(population);
            current_generation++;
        }
    }

    private ArrayList<Path> selection(){
        ArrayList<Path> best_of_population = new ArrayList<>();
        for (int i=0; i<K;i++) best_of_population.add(population.get(i));
        return best_of_population;
    }

    private void crossing_over(ArrayList<Path> selected){

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

    private int binomial_coef(int n){
        return (n*(n-1))/2;
    }

    //================================================================== Getter / Setter =====================================================================================

    public ArrayList<Path> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Path> population) {
        this.population = population;
    }
}
