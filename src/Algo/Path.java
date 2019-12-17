package Algo;

import Structure_Donnees.City;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Path implements Comparable{
    public LinkedList<City> path;
    private int distance;

    public Path(List<City> cityList){
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

    public Path[] split(){
        LinkedList<City> path1 = new LinkedList<>(path.subList(0,path.size()/2));
        LinkedList<City> path2 = new LinkedList<>(path.subList(path.size()/2, path.size()));
        return new Path[]{new Path(path1), new Path(path2)};
    }



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(City c : path){
            sb.append(c.getName()).append(" ; ");
        }
        return sb.append(" fitness : ").append(this.getDistance()).append("]\n").toString();
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
