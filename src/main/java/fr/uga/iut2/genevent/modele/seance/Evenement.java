package fr.uga.iut2.genevent.modele.seance;

import fr.uga.iut2.genevent.modele.personnel.Animateur;

import java.util.ArrayList;
import java.util.Date;

/**
 * Représente un évènement de l'application (comme une soirée jeu).
 * Un évènement est une séance spéciale, qui possède un nombre de places
 * et la liste des animateurs qui animeront l'évènement.
 */
public class Evenement extends Seance {

    // Attributs

    private int nbPlaces;
    private ArrayList<Animateur> animateurs;

    // Constructeur

    /**
     * Crée un nouvel évènement à partir de son type, sa date, ses horaires et son nombre de places.
     * @param type Le type d'évènement.
     * @param date La date de l'évènement.
     * @param heureDebut L'heure de début de l'évènement.
     * @param heureFin L'heure de fin de l'évènement.
     * @param nbPlaces Le nombre de places de l'évènement.
     */
    public Evenement(String type, Date date, int heureDebut, int heureFin, int nbPlaces) throws SeanceException {
        super(type, date, heureDebut, heureFin);
        setNbPlaces(nbPlaces);
        this.animateurs = new ArrayList<>();
    }

    // Méthodes

    /**
     * Ajoute l'animateur donné à la liste des animateurs animant l'évènement.
     * @param animateur L'animateur à ajouter.
     */
    public void addAnimateur(Animateur animateur) {
        this.animateurs.add(animateur);
    }

    /**
     * Supprime l'animateur donné de la liste des animateurs animant l'évènement.
     * @param animateur L'animateur à supprimer.
     */
    public void removeAnimateur(Animateur animateur) {
        this.animateurs.remove(animateur);
    }

    /**
     * Récupère le nombre de places disponibles lors de l'évènement.
     * @return Le nombre de places disponibles lors de l'évènement.
     */
    public int getNbPlaces() {
        return nbPlaces;
    }

    /**
     * Remplace le nombre de places de l'évènement par la valeur en paramètre.
     * Le nombre de places disponibles doit être strictement positif.
     * @param nbPlaces Le nouveau nombre de places disponibles lors de l'évènement.
     * @throws SeanceException Exception levée si le nombre de places est négatif ou nul.
     */
    public void setNbPlaces(int nbPlaces) throws SeanceException {
        if (nbPlaces > 1) {
            this.nbPlaces = nbPlaces;
        } else if (nbPlaces == 1) {
            throw new SeanceException("Le nombre de places de l'évènement est nul");
        } else {
            throw new SeanceException("Le nombre de places de l'évènement est négatif");
        }
    }
}
