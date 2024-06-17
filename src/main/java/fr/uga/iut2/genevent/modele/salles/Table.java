package fr.uga.iut2.genevent.modele.salles;

public class Table {

    // Attributs

    private final long id;
    private final String type;
    private final int nbPlaces;
    private final int taille;
    private final Salle salle;

    // Constructeur

    public Table(long id, Salle salle, String type, int nbPlaces, int taille) {
        this.id = id;
        this.salle = salle;
        this.type = type;
        this.nbPlaces = nbPlaces;
        this.taille = taille;
    }

    // Méthodes

    public long getId() {
        return id;
    }

    public Salle getSalle() {
        return salle;
    }

    public String getType() {
        return type;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public int getTaille() {
        return taille;
    }
}
