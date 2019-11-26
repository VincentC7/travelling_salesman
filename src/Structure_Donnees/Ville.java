package Structure_Donnees;

import java.util.HashMap;

public class Ville {

    private String nom;
    private HashMap<Ville,Integer> voisins;

    public Ville(String nom){
        this.nom=nom;
        voisins = new HashMap<>();
    }

    public void ajouter_voisin(Ville v,Integer dist){
        voisins.put(v,dist);
    }

    public boolean supprimer_voisin(Ville v){
        if (voisins.containsKey(v)){
            voisins.remove(v);
            return true;
        }
        return false;
    }

    //============================================= Getters Setters =========================================


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
