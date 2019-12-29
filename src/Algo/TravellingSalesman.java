package Algo;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravellingSalesman {

    private static int MAX_GENERATION = 1000;
    private static int POPULATION_SIZE = 50;
    private static double PERSENTAGE_REMPLACEMENT = 0.7;
    private static double PERSENTAGE_MUTATION = 0.1;
    public static double CROSSING_POINT = 0.5;

    private int current_generation;
    private Graphe graphe;
    private ArrayList<Path> population;
    private ArrayList<Path> fitness;
    
    public TravellingSalesman(){
        current_generation=1;
        graphe = Graphe.create_graphe("cities.json");
        assert graphe != null;
        
        population =new ArrayList<>();
        fitness = new ArrayList<>();
    }

    public void runAlgo(){
        //creation des premiers chemins
        initialize();

        //creation des générations
        while (current_generation != MAX_GENERATION){
            fitness.add(population.get(0));
            System.out.println("====Generation : "+current_generation+"==========================================================================================================================================================================================");
            System.out.println("Population au départ");
            System.out.println(population);
            System.out.println();

            ArrayList<Path> selected = selection();
            System.out.println("individues sélectionnés");
            System.out.println(selected);
            System.out.println();

            ArrayList<Path> recomined_population = crossing_over(selected);
            System.out.println("fils engendrés");
            System.out.println(recomined_population);
            System.out.println();

            ArrayList<Path> muted_population = mutation(recomined_population);
            System.out.println("fils mutés");
            System.out.println(muted_population);
            System.out.println();

            remplacement(muted_population);

            System.out.println("Nouvelle population");
            System.out.println(population);
            System.out.println();

            System.out.println("Meilleur chemin");
            System.out.println(population.get(0));
            current_generation++;
        }
        System.out.println("##########Meilleur chemin##########");
        System.out.println(fitness.get(fitness.size()-1));
        System.out.println(fitness);
    }

    //selection des k premiers individus
    private ArrayList<Path> selection(){
        ArrayList<Path> best_of_population = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.UP);
        int k = Integer.parseInt(df.format((1+Math.sqrt(1+4*(POPULATION_SIZE*PERSENTAGE_REMPLACEMENT)))/2));
        for (int i=0; i<k;i++) best_of_population.add(population.get(i));
        return best_of_population;
    }

    private ArrayList<Path> crossing_over(ArrayList<Path> selected){
        ArrayList<Path> recomined_population = new ArrayList<>();
        for (Path path1 : selected) {
            for (Path path2 : selected) {
                if (path1!=path2){
                    recomined_population.add(path1.createSon(path2));
                }
            }
        }
        return recomined_population;
    }

    private ArrayList<Path> mutation(ArrayList<Path> recombined_population){
        for (Path path : recombined_population) {
            int rand = (int) (Math.random());
            if (rand <= PERSENTAGE_MUTATION){ // rand <= 1 : mutation
                path.mutate();
            }
        }
        return recombined_population;
    }

    //remplacement partiel
    private void remplacement(ArrayList<Path> mutated_population){
        ArrayList<Path> new_population = new ArrayList<>(population.subList(0, (int) (POPULATION_SIZE*(1-PERSENTAGE_REMPLACEMENT)+1)));
        new_population.addAll(mutated_population);
        setPopulation(new_population);
        Collections.sort(population);
    }

    private void initialize(){
        List<City> cityList = new ArrayList<>(graphe.getCities());
        for(int i = 0; i<POPULATION_SIZE; i++){
           population.add(graphe.generatePath(cityList));
        }
        Collections.sort(population);
    }

    //================================================================== Getter / Setter =====================================================================================

    public ArrayList<Path> getPopulation() {
        return population;
    }

    public void setPopulation(ArrayList<Path> population) {
        this.population = population;
    }

    public Path getBestPath() {
        return fitness.get(fitness.size()-1);
    }

    public static void setMaxGeneration(int maxGeneration) {
        MAX_GENERATION = maxGeneration;
    }

    public static void setPopulationSize(int populationSize) {
        POPULATION_SIZE = populationSize;
    }

    public static void setPersentageRemplacement(double persentageRemplacement) {
        PERSENTAGE_REMPLACEMENT = persentageRemplacement;
    }

    public static void setPersentageMutation(double persentageMutation) {
        PERSENTAGE_MUTATION = persentageMutation;
    }

    public static void setCrossingPoint(double crossingPoint) {
        CROSSING_POINT = crossingPoint;
    }
}
