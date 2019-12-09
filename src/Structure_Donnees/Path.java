package Structure_Donnees;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Path implements Comparable{
    LinkedList<City> path;
    private int distance;

    Path(List<City> cityList){
        this.path= new LinkedList<>(cityList);
    }


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

    public void add_first_last(City c){
        path.addFirst(c);
        path.addLast(c);
    }

    @Override
    public String toString() {
        String s ="";
        for(City c : path){
            s +=  "["+c.getName()+"]";
        }
        return s+this.getDistance();
    }

    @Override
    public boolean equals(Object obj) {
        Path p = (Path)obj;
        for (int i = 0;i < this.path.size();i++){
            if(!(p.path.get(i).equals(this.path.get(i)))){
                return false;
            }
        }
        return true;
    }

    @Override
    public int compareTo(Object o) {
        return this.getDistance() - ((Path)o).getDistance();
    }
}
