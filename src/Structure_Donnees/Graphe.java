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
    private ArrayList<Path> listPath;
    public static int MAX_LENGTH = 500;
    public static int MIN_LENGTH = 100;


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
        this.listPath = new ArrayList<>();
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
            org.json.simple.JSONArray villes = (org.json.simple.JSONArray) jsonObj.get("villes");
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




    public void initialize(int nbGenome){

        List<City> listCities = new ArrayList<>(cities);
        Path p;
        for(int i = 0;i<nbGenome;i++){
            Collections.shuffle(listCities);
            p = new Path(listCities);
            p.add_first_last(city_start);

            this.listPath.add(p);
            Collections.sort(listPath);
        }


    }

    public static List<String> list_villes(int nbVilles){
        ArrayList<String> villes = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("WorldCities.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONArray arrayVilles = (org.json.simple.JSONArray) obj;


            for(int i = 0; i<nbVilles;i++){
                JSONObject ville = (JSONObject) arrayVilles.get(i);
                villes.add((String)ville.get("name"));
            }





        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return villes;
    }



    public void show_genomes(){
        System.out.println(listPath);
    }


    public static int factorielle(int i){
        if (i==1) return(1);
        else return(i*factorielle(i-1));
    }

}
