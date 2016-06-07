package pcedev.bencomu;

import java.io.Serializable;

/**
 * Created by perecullera on 6/6/16.
 */
public class Candidat implements Serializable{
    String nom;
    String descripcio;
    String foto;

    public Candidat(String nom, String descripcio,String foto ){
        this.nom=nom;
        this.descripcio = descripcio;
        this.foto = foto;
    }

    public String get_nom(){
        return this.nom;
    }
    public void set_nom(String nom){
        this.nom= nom;
    }
}
