package Algo;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class TournamentSelection implements Selection{

    @Override
    public ArrayList<Path> getSelectedPoppulation(ArrayList<Path> currentPop, boolean replacementTotal) {
        ArrayList<Path> local_currentPop = new ArrayList<>(currentPop);
        ArrayList<Path> res = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#");
        df.setRoundingMode(RoundingMode.UP);
        int number_of_tournament = (replacementTotal) ?
                Integer.parseInt(df.format((1 + Math.sqrt(1 + 4 * (TravellingSalesman.POPULATION_SIZE))) / 2))
                : Integer.parseInt(df.format((1 + Math.sqrt(1 + 4 * (TravellingSalesman.POPULATION_SIZE * TravellingSalesman.PERSENTAGE_REMPLACEMENT))) / 2));

        ArrayList<Tournament> tournaments = new ArrayList<>();
        for (int i=0; i<number_of_tournament;i++){
            Tournament tournament = new Tournament();
            tournaments.add(tournament);
            for (int k=0;k<local_currentPop.size()/number_of_tournament;k++){
                int index = (int) (Math.random()*local_currentPop.size());
                tournament.addPath(local_currentPop.get(index));
                local_currentPop.remove(index);
            }
        }

        for (Tournament tournament : tournaments) {
            tournament.start();
        }
        for (Tournament tournament : tournaments) {
            try {
                tournament.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            res.add(tournament.getWinner());
        }
        assert res.size()==number_of_tournament;

        return res;
    }

}
