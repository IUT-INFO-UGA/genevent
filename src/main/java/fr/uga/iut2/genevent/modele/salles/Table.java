package fr.uga.iut2.genevent.modele.salles;

import fr.uga.iut2.genevent.modele.jeu.TailleTable;

import java.io.Serializable;

/**
 * Représente une table d'une salle sur laquelle des membres peuvent se rassembler pour former une partie
 * et jouer à un jeu de société.
 *
 */
public class Table implements Serializable {

    // Attributs

    private final long id;
    private final String type;
    private final int nbPlaces;
    private final TailleTable taille;
    private final Salle salle;

    // Constructeur

    /**
     * Crée une nouvelle table, à partir de ses caractéristiques.
     * @param id L'identifiant de la table, il doit être unique.
     * @param salle La salle dans laquelle se situe la table.
     * @param type Le type de table.
     * @param nbPlaces Le nombre de places disponibles à la table.
     * @param taille La taille de la table.
     */
    public Table(long id, Salle salle, String type, int nbPlaces, TailleTable taille) {
        this.id = id;
        this.salle = salle;
        this.type = type;
        this.nbPlaces = nbPlaces;
        this.taille = taille;
    }

    // Méthodes

    /**
     * Récupère l'identifiant de la table.
     * @return L'identifiant de la table.
     */
    public long getId() {
        return id;
    }

    /**
     * Récupère la salle dans laquelle se situe la table.
     * @return La salle dans laquelle se situe la table.
     */
    public Salle getSalle() {
        return salle;
    }

    /**
     * Récupère le type de la table.
     * @return Le type de la table.
     */
    public String getType() {
        return type;
    }

    /**
     * Récupère le nombre de places disponibles à la table.
     * @return Le nombre de places disponibles à la table.
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Récupère la taille de la table.
     * @return La taille de la table.
     */
    public TailleTable getTaille() {
        return taille;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(id=" + getId() + ", type=\"" + getType()
                + "\", taille=\"" + getTaille().toString() + "\", nbPlaces=" + getNbPlaces()
                + ", salle=" + getSalle().getNumero() + ")";
    }
}
