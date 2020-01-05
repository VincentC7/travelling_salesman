package Algo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class KBestSelection implements Selection{

    @Override
    public ArrayList<Path> getSelectedPoppulation(ArrayList<Path> currentPop, boolean replacementTotal) {
        ArrayList<Path> best_of_population = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.UP);
        int k = (replacementTotal) ?
                Integer.parseInt(df.format((1 + Math.sqrt(1 + 4 * (TravellingSalesman.POPULATION_SIZE))) / 2))
                : Integer.parseInt(df.format((1 + Math.sqrt(1 + 4 * (TravellingSalesman.POPULATION_SIZE * TravellingSalesman.PERSENTAGE_REMPLACEMENT))) / 2));
        for (int i = 0; i < k; i++) best_of_population.add(currentPop.get(i));
        return best_of_population;
    }
}
