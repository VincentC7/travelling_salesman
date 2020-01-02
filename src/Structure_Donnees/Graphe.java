package Structure_Donnees;
import Algo.Path;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Graphe {
    private HashSet<City> cities;
    private City city_start;
    static int MAX_LENGTH = 5000;
    static int MIN_LENGTH = 10;

    Graphe(HashSet<City> p_cities, City p_city_start){
        cities = p_cities;
        List<City> listCities = new ArrayList<>(p_cities);
        city_start = p_city_start;
        Collections.shuffle(listCities);
        cities.remove(city_start);
    }

    public static Graphe create_graphe(String fichier){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fichier))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject)obj;
            org.json.simple.JSONArray villes = (org.json.simple.JSONArray) jsonObj.get("villes");

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
            return new Graphe(city, tabCity[0]);


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

    public Path generatePath(List<City> listCities){
        Collections.shuffle(listCities);
        Path p = new Path(listCities);
        p.add_first_last(city_start);
        return p;
    }

    private static List<String> list_villes(int nbCities){
        ArrayList<String> villes = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("WorldCities.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONArray arrayVilles = (org.json.simple.JSONArray) obj;
            for(int i = 0; i<nbCities;i++){
                JSONObject ville = (JSONObject) arrayVilles.get(i);
                villes.add((String)ville.get("name"));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return villes;
    }
    @SuppressWarnings("unchecked")
    public static void create_cityJson(int nbCities){
        JSONArray villes = new JSONArray();
        ArrayList<String> cities = new ArrayList<>(list_villes(nbCities));
        Matrix m = Matrix.generate_random_matrix(nbCities);
        for (int i = 0;i<nbCities;i++){
            JSONObject city = new JSONObject();
            city.put("id",i+1);
            city.put("name",cities.get(i));
            JSONArray distance = new JSONArray();
            for (int j = 0;j<nbCities;j++){
                if(!(j == i)) {
                    JSONObject cityDist = new JSONObject();
                    cityDist.put("id", j + 1);
                    cityDist.put("dist",m.getNumber(i,j));
                    distance.add(cityDist);
                }
            }
            city.put("dist",distance);
            villes.add(city);
        }
        JSONObject json = new JSONObject();
        json.put("villes",villes);

        //Write JSON file
        try (FileWriter file = new FileWriter("cities.json")) {

            file.write(json.toJSONString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    //========================================================== Getters/Setters ======================================================
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
        cities.remove(city_start);
        cities.add(this.city_start);
        this.city_start = city_start;
    }

}
