package Algo;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;

import java.util.*;

@SuppressWarnings( "deprecation" )
public class TravellingSalesman extends Observable {

    /**
     * Paramètres de l'algo
     */
    private static int MAX_GENERATION = 100;
    public static int POPULATION_SIZE = 100;
    public static int MAX_REPETITION_SAME_FITNESS = 10;
    public static double PERSENTAGE_REMPLACEMENT = 0.7;
    private static double PERSENTAGE_MUTATION = 0.1;
    public static double CROSSING_POINT = 0.5;
    private boolean replacementTotal=false;

    /**
     * Données en interne
     */
    private boolean stopped = false;
    private int current_generation;
    private Graphe graphe;
    private ArrayList<Path> population;
    private ArrayList<Path> fitness;
    private Selection selector;
    
    public TravellingSalesman(){
        current_generation=1;
        graphe = Graphe.create_graphe("cities.json");
        assert graphe != null;

        population =new ArrayList<>();
        fitness = new ArrayList<>();
        selector = new TournamentSelection();
    }

    public TravellingSalesman(String fichier){
        current_generation=1;
        graphe = Graphe.create_graphe(fichier);
        assert graphe != null;

        population =new ArrayList<>();
        fitness = new ArrayList<>();
    }

    public void runAlgo(){
        //creation des premiers chemins
        initialize();

        //creation des générations
        while (current_generation != MAX_GENERATION && !stopped && !is_same_fitness_since_n_gen()){
            fitness.add(population.get(0));
            setChanged();
            notifyObservers();
            System.out.println("====Generation : " + current_generation + "==========================================================================================================================================================================================");
            remplacement(mutation(crossing_over(selection())));
            System.out.println("Meilleur chemin de la génération "+current_generation);
            System.out.println(population.get(0));
            current_generation++;
        }
        fitness.add(population.get(0));
        setChanged();
        notifyObservers();
        System.out.println("##########Meilleur chemin##########");
        System.out.println(fitness.get(fitness.size() - 1));
    }

    private boolean is_same_fitness_since_n_gen(){
        if (MAX_REPETITION_SAME_FITNESS > fitness.size()) return false;
        for (int i = 0; i<MAX_REPETITION_SAME_FITNESS-1; i++){
            Path current = fitness.get(fitness.size()-i-1);
            Path next = fitness.get(fitness.size()-i-2);
            if (!current.equals(next)) return false;
        }
        return true;
    }

    private ArrayList<Path> selection() {
        return selector.getSelectedPoppulation(population,replacementTotal);
    }

    private ArrayList<Path> crossing_over(ArrayList<Path> selected) {
        ArrayList<Path> recomined_population = new ArrayList<>();
        for (Path path1 : selected) {
            for (Path path2 : selected) {
                if (path1 != path2) {
                    recomined_population.add(path1.createSon(path2));
                }
            }
        }
        return recomined_population;
    }

    private ArrayList<Path> mutation(ArrayList<Path> recombined_population) {
        for (Path path : recombined_population) {
            int rand = (int) (Math.random());
            if (rand <= PERSENTAGE_MUTATION) { // rand <= 1 : mutation
                path.mutate();
            }
        }
        return recombined_population;
    }

    private void remplacement(ArrayList<Path> mutated_population) {
        if(!(this.replacementTotal)){
            ArrayList<Path> new_population = new ArrayList<>(population.subList(0, (int) (POPULATION_SIZE * (1 - PERSENTAGE_REMPLACEMENT) + 1)));
            new_population.addAll(mutated_population.subList(0,POPULATION_SIZE-new_population.size()));
            setPopulation(new_population);

        }
        else{
            setPopulation(new ArrayList<>(mutated_population.subList(0,POPULATION_SIZE)));
        }

        Collections.sort(population);
    }

    private void initialize() {
        List<City> cityList = new ArrayList<>(graphe.getCities());
        for (int i = 0; i < POPULATION_SIZE; i++) {
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
        return fitness.get(fitness.size() - 1);
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

    public static void setMaxRepetitionSameFitness(int maxRepetitionSameFitness) {
        MAX_REPETITION_SAME_FITNESS = maxRepetitionSameFitness;
    }

    public ArrayList<Path> getFitness() {
        return fitness;
    }

    public int getCurrent_generation() {
        return current_generation;
    }

    public void stop(){
        this.stopped = true;
    }

    public HashSet<City> getCities(){
        return graphe.getCities();
    }

    public City getCityStart(){
        return graphe.getCity_start();
    }

    public void setCity_start(City c){
        graphe.setCity_start(c);
    }


    public void setReplacementTotal(boolean raplaclementTotal) {
        this.replacementTotal = raplaclementTotal;
    }
}
