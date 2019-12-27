package Algo;

import Structure_Donnees.City;
import Structure_Donnees.Graphe;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TravellingSaleman {

    private static final int MAX_GENERATION = 50;
    private static final int POPULATION_SIZE = 100;
    private static final double PERSENTAGE_REMPLACEMENT = 0.9;
    private static final double PERSENTAGE_MUTATION = 0.1;

    private int current_generation;
    private Graphe graphe;
    private ArrayList<Path> population;
    private ArrayList<Path> best_path_by_gen;
    
    public TravellingSaleman(){
        current_generation=1;
        graphe = Graphe.create_graphe("cities.json");
        assert graphe != null;
        
        population =new ArrayList<>();
        best_path_by_gen = new ArrayList<>();
    }


    public void runAlgo(){
        //creation des premiers chemins
        initialize(POPULATION_SIZE);

        //creation des générations
        while (current_generation != MAX_GENERATION){
            best_path_by_gen.add(population.get(0));
            System.out.println("====Generation : "+current_generation+"========================================================================================================================");
            ArrayList<Path> selected = selection();
            ArrayList<Path> recomined_population = crossing_over(selected);
            ArrayList<Path> muted_population = mutation(recomined_population);
            remplacement(muted_population);
            System.out.println(population.get(0));
            current_generation++;
        }
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
        ArrayList<Path> list_left_path = new ArrayList<>();
        ArrayList<Path> list_right_path = new ArrayList<>();
        for (Path p : selected){
            Path[] split_path = p.split();
            list_left_path.add(split_path[0]);
            list_right_path.add(split_path[1]);
        }
        ArrayList<String> indexs_used = new ArrayList<>();
        for (int i = 0; i < POPULATION_SIZE*PERSENTAGE_REMPLACEMENT; i++) {
            int left_index,right_index;
            do{
                left_index = (int) (Math.random()*list_left_path.size());
                right_index = (int) (Math.random()*list_right_path.size());
            }while (left_index==right_index || indexs_used.contains(left_index+" "+right_index));
            assert left_index != right_index;
            indexs_used.add(left_index+" "+right_index);
            recomined_population.add(Path.merge(list_left_path.get(left_index),list_right_path.get(right_index), graphe.getCities()));
        }
        Collections.sort(recomined_population);
        return recomined_population;
    }

    private ArrayList<Path> mutation(ArrayList<Path> recombined_population){
        for (Path path : recombined_population) {
            int rand = (int) (Math.random()*10);
            if (rand == PERSENTAGE_MUTATION*10){ // rand == 1 => mutation
                path.mutate();
            }
        }
        return recombined_population;
    }

    //remplacement partiel
    private void remplacement(ArrayList<Path> mutated_population){
        ArrayList<Path> new_population = new ArrayList<>(population.subList(0, (int) (POPULATION_SIZE*(1-PERSENTAGE_REMPLACEMENT)+1)));
        new_population.addAll(mutated_population);
        Collections.sort(new_population);
        setPopulation(new_population);
    }

    private void initialize(int nbGenome){
        List<City> cityList = new ArrayList<>(graphe.getCities());
        for(int i = 0;i<nbGenome;i++){
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
}
