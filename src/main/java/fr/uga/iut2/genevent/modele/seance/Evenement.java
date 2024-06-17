package fr.uga.iut2.genevent.modele.seance;

import fr.uga.iut2.genevent.modele.personnel.Animateur;

import java.util.ArrayList;
import java.util.Date;

public class Evenement extends Seance {

    // Attributs

    private int nbPlaces;
    private ArrayList<Animateur> animateurs;

    // Constructeur

    public Evenement(String type, Date date, int heureDebut, int heureFin, int nbPlaces) {
        super(type, date, heureDebut, heureFin);
        setNbPlaces(nbPlaces);
        this.animateurs = new ArrayList<>();
    }

    // Méthodes

    public void addAnimateur(Animateur animateur) {
        this.animateurs.add(animateur);
    }

    public void removeAnimateur(Animateur animateur) {
        this.animateurs.remove(animateur);
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }
}
