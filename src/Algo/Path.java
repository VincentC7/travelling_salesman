package Algo;

import Structure_Donnees.City;

import java.util.*;


public class Path implements Comparable{
    public LinkedList<City> cities;
    private int distance;

    public Path(List<City> cityList){
        this.cities = new LinkedList<>(cityList);
        calculate_distance();
    }


    public int getDistance() {
        return distance;
    }

    private int calculate_distance(){
        distance=0;
        for(int j = 0;j<cities.size()-1;j++){
            distance+= cities.get(j).get_distance(cities.get(j+1));
        }
        return distance;
    }

    public void add_first_last(City c){
        cities.addFirst(c);
        cities.addLast(c);
    }


    public Path createSon(Path other_parent){
        int index = (int) (TravellingSalesman.CROSSING_POINT * cities.size()-1);
        ArrayList<City> cross_part = new ArrayList<>(other_parent.cities.subList(index,cities.size()-1));
        ArrayList<City> new_path = new ArrayList<>(cities);
        City start = cities.get(0);
        new_path.remove(0);
        new_path.remove(new_path.size()-1);
        for (City city : cross_part) {
            new_path.remove(city);
        }
        //Collections.shuffle(cross_part);
        new_path.addAll(cross_part);
        new_path.add(0,start);
        new_path.add(new_path.size(),start);
        return new Path(new_path);
    }

    public void mutate() {
        int index = (int) (Math.random() * (cities.size()-2))+1;
        if (index+1 != cities.size()-1){
            City c = cities.get(index);
            cities.set(index, cities.get(index+1));
            cities.set(index+1, c);
        }else {
            City c = cities.get(index);
            cities.set(index, cities.get(index-1));
            cities.set(index-1, c);
        }
        calculate_distance();
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
