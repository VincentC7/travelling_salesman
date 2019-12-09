package Structure_Donnees;

import java.util.Iterator;
import java.util.LinkedList;

public class Path {
    LinkedList<City> path;
    private int distance;

    public int getDistance() {
        if (distance==0){
            distance = calculate_distance();
        }
        return distance;
    }

    private int calculate_distance(){
        int i = path.size();
        distance=0;
        for(int j = 0;j<i-1;j++){
            distance+= path.get(j).get_distance(path.get(j+1));
        }
        return distance;
    }

}
