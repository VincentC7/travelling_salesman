package Structure_Donnees;

import java.util.HashMap;

public class City {

    private String name;
    private HashMap<City,Integer> neighbours;

    public City(String name){
        this.name=name;
        neighbours = new HashMap<>();
    }

    public void add_neighbour(City c, Integer dist){
        neighbours.put(c,dist);
    }

    public boolean delete_neighbour(City c){
        if (neighbours.containsKey(c)){
            neighbours.remove(c);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        return name.equals(((City)obj).name);
    }

    //============================================= Getters Setters =========================================
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        String s = name + "\n";
        for (City c : neighbours.keySet()){
             s += c.getName() + " : " + neighbours.get(c) + "\n";
        }
        return s+"\n";

    }
}
