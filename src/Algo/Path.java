package Algo;

import Structure_Donnees.City;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class Path implements Comparable{
    public LinkedList<City> cities;
    private int distance;

    public Path(List<City> cityList){
        this.cities = new LinkedList<>(cityList);
    }


    public int getDistance() {
        if (distance==0){
            distance = calculate_distance();
        }
        return distance;
    }

    private int calculate_distance(){
        int i = cities.size();
        distance=0;
        for(int j = 0;j<i-1;j++){
            distance+= cities.get(j).get_distance(cities.get(j+1));
        }
        return distance;
    }

    public void add_first_last(City c){
        cities.addFirst(c);
        cities.addLast(c);
    }

    public Path[] split(){
        LinkedList<City> path1 = new LinkedList<>(cities.subList(0, cities.size()/2));
        LinkedList<City> path2 = new LinkedList<>(cities.subList(cities.size()/2, cities.size()));
        return new Path[]{new Path(path1), new Path(path2)};
    }

    public static Path merge(Path path_left, Path path_right, HashSet<City> p_cities){
        ArrayList<City> cities = new ArrayList<>(p_cities);
        ArrayList<City> newPath = new ArrayList<>(path_left.cities);
        cities.removeAll(newPath);
        for (int i=0;i<path_right.cities.size()-1;i++) {
            City city = path_right.cities.get(i);
            if (!newPath.contains(city)) {
                newPath.add(city);
                cities.remove(city);
            }
        }
        newPath.addAll(cities);
        newPath.add(newPath.get(0));
        return new Path(newPath);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for(City c : cities){
            sb.append(c.getName()).append(" ; ");
        }
        return sb.append(" fitness : ").append(this.getDistance()).append("]\n").toString();
    }

    @Override
    public boolean equals(Object obj) {
        Path p = (Path)obj;
        for (int i = 0; i < this.cities.size(); i++){
            if(!(p.cities.get(i).equals(this.cities.get(i)))){
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
