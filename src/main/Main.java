package main;

import Structure_Donnees.Ville;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
        try {
            InputStream is = new FileInputStream(new File("villes.json"));
            JSONTokener jtoken = new JSONTokener(is);
            JSONObject json = new JSONObject(jtoken);
            System.out.println(json);

        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
