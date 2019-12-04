package main;

import Structure_Donnees.Graphe;
import Structure_Donnees.Ville;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

public class Main {

    public static void main(String[] args) {
        /**
        try {
            InputStream is = new FileInputStream(new File("villes.json"));
            JSONTokener jtoken = new JSONTokener(is);
            JSONObject json = new JSONObject(jtoken);
            JSONArray jsonArray = json.getJSONArray("villes");
            for (int i =0;i < jsonArray.length();i++){

                JSONObject json2 = jsonArray.getJSONObject(i);
                System.out.println(json2.get("name"));
                System.out.println(json2.get("id"));
                JSONArray array = json2.getJSONArray("dist");
                System.out.println(array);
                for (int j =0;j < array.length();j++){
                    JSONObject json3 = array.getJSONObject(j);
                    JSONArray array2 = json3.getJSONArray("dist");
                    for (int k=0;k < array2.length();k++ ){
                        JSONObject json4 = array2.getJSONObject(k);
                        System.out.println( json4.get("dist"));
                        System.out.println( json4.get("id"));

                    }
                }

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("villes.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            JSONObject jsonObj = (JSONObject)obj;
            JSONArray villes = (JSONArray) jsonObj.get("villes");
            System.out.println(villes);

            //Iterate over employee array
            villes.forEach( emp -> parseEmployeeObject((JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }**/
        Graphe g = Graphe.creer_graphe("villes.json");
        System.out.println(g);
    }

    private static void parseEmployeeObject(JSONObject villes)
    {
        System.out.println(villes);
        //Get employee object within list
        System.out.println(villes.get("name"));
        System.out.println(villes.get("id"));
        JSONArray distance = (JSONArray) villes.get("dist");
        distance.forEach( emp -> {
            System.out.println(((JSONObject)emp).get("id"));
            System.out.println(((JSONObject)emp).get("dist"));
        });


    }
}
