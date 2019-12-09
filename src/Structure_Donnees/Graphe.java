package Structure_Donnees;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graphe {
    private HashSet<City> cities;
    private City city_start;


    public HashSet<City> getCities() {
        return cities;
    }

    public void setCities(HashSet<City> cities) {
        this.cities = cities;
    }

    public City getCity_start() {
        return city_start;
    }

    public void setCity_start(City city_start) {
        this.city_start = city_start;
    }

    Graphe(HashSet<City> cities){
        this.cities = cities;
        List<City> listCities = new ArrayList<>(cities);
        Collections.shuffle(listCities);
        city_start = listCities.get(0);
        this.cities.remove(city_start);

    }

    public static Graphe create_graphe(String fichier){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fichier))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject)obj;
            org.json.simple.JSONArray villes = (org.json.simple.JSONArray) jsonObj.get("cities");
            System.out.println(villes);

            //Iterate over employee array
            City[] tabCity = new City[villes.size()];
            for (Object ville : villes) {

                City v = new City((String)((JSONObject)ville).get("name"));
                long id = (long)((JSONObject)ville).get("id");
                tabCity[Math.toIntExact(id)-1]=v;

            }
            for (Object ville : villes) {
                long id = (long)((JSONObject)ville).get("id");
                JSONArray distance = (JSONArray) ((JSONObject)ville).get("dist");
                distance.forEach(emp ->{
                    tabCity[Math.toIntExact(id)-1]
                            .add_neighbour(tabCity[Math.toIntExact((long)((JSONObject)emp).get("id"))-1],
                                    Math.toIntExact((long)((JSONObject)emp).get("dist")));

                });
            }
            HashSet<City> city = new HashSet<>(Set.of(tabCity));
            return new Graphe(city);


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public String toString() {
        String s = city_start.toString();
        for(City v : cities){
            s += v.toString();
        }
        return s;
    }
}
