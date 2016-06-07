package pcedev.bencomu;

import java.util.ArrayList;

/**
 * Created by perecullera on 6/6/16.
 */
public class Provincia {
    String nom;
    ArrayList<Candidat> candidats;

    public Provincia(String provincia, ArrayList<Candidat> candidats) {
        this.nom = provincia;
        this.candidats = candidats;
    }

    public Candidat get(int childPosititon) {
        return candidats.get(childPosititon);
    }

    public int size() {
        return candidats.size();
    }
}
