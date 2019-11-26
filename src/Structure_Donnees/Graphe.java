package Structure_Donnees;

import java.io.File;
import java.util.HashSet;

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

    static Graphe creer_graphe(File fichier){
        
    }
}
