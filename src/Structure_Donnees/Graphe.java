package Structure_Donnees;
import org.json.* ;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Graphe {
    private HashSet<Ville> villes;
    private Ville ville_depart;


    public HashSet<Ville> getVilles() {
        return villes;
    }

    public void setVilles(HashSet<Ville> villes) {
        this.villes = villes;
    }

    public Ville getVille_depart() {
        return ville_depart;
    }

    public void setVille_depart(Ville ville_depart) {
        this.ville_depart = ville_depart;
    }

    Graphe(HashSet<Ville> villes){
        this.villes=villes;
        List<Ville> listVilles = new ArrayList<>(villes);
        Collections.shuffle(listVilles);
        ville_depart = listVilles.get(0);
        this.villes.remove(ville_depart);

    }

    public static Graphe creer_graphe(String fichier){
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader(fichier))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);
            org.json.simple.JSONObject jsonObj = (org.json.simple.JSONObject)obj;
            org.json.simple.JSONArray villes = (org.json.simple.JSONArray) jsonObj.get("villes");
            System.out.println(villes);

            //Iterate over employee array
            Ville[] tabVille = new Ville[villes.size()];
            for (Object ville : villes) {

                Ville v = new Ville((String)((JSONObject)ville).get("name"));
                long id = (long)((JSONObject)ville).get("id");
                tabVille[Math.toIntExact(id)-1]=v;

            }
            for (Object ville : villes) {
                long id = (long)((JSONObject)ville).get("id");
                JSONArray distance = (JSONArray) ((JSONObject)ville).get("dist");
                distance.forEach(emp ->{
                    tabVille[Math.toIntExact(id)-1]
                            .ajouter_voisin(tabVille[Math.toIntExact((long)((JSONObject)emp).get("id"))-1],
                                    Math.toIntExact((long)((JSONObject)emp).get("dist")));

                });
            }
            HashSet<Ville> ville = new HashSet<>(Set.of(tabVille));
            return new Graphe(ville);


        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;

    }
}
