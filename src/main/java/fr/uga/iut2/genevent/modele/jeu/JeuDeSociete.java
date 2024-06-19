package fr.uga.iut2.genevent.modele.jeu;

import fr.uga.iut2.genevent.modele.GenEvent;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Représente un jeu de société dont l'association/le salon est en possession.
 * Un jeu de société est composé d'un nom, de règles, d'un type,
 * possède une taille de table, un nombre de joueurs et la durée d'une partie,
 * et enfin un prix et une date d'achat.
 */
public class JeuDeSociete implements Serializable {

    // Logger

    public static Logger logger = Logger.getLogger(JeuDeSociete.class.getPackageName());

    static {
        GenEvent.logManager.addLogger(logger);
    }

    // Attributs

    private final String nom;
    private final String regles;
    private final Date dateAchat;
    private final String type;
    private final TailleTable tailleTable;
    private final int dureePartie;
    private final double prix;
    private int nbJoueurs;

    // Constructeur

    /**
     * Crée un nouveau jeu de société à partir des valeurs en paramètres.
     * @param nom Le nom du jeu de socéié.
     * @param regles Les règles du jeu de société.
     * @param nbJoueurs Le nombre de joueurs pouvant jouer ensemble dans une partie.
     * @param dateAchat La date de l'achat du jeu de société par l'association/le salon.
     * @param type Le type de jeu de société.
     * @param tailleTable La taille de la table nécessaire pour organiser une partie.
     * @param dureePartie La durée moyenne d'une partie (en minutes).
     * @param prix Le prix du jeu de société lors de son achat.
     */
    public JeuDeSociete(String nom, String regles, int nbJoueurs, Date dateAchat, String type, TailleTable tailleTable, int dureePartie, double prix) throws JeuDeSocieteException {
        this.nom = nom;
        this.regles = regles;
        setNbJoueurs(nbJoueurs);
        this.dateAchat = dateAchat;
        this.tailleTable = tailleTable;
        this.type = type;
        this.dureePartie = dureePartie;
        this.prix = prix;
    }

    // Méthodes

    /**
     * Récupère le nom du jeu de société.
     * @return Le nom du jeu de société.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère les règles du jeu de société.
     * @return Les règles du jeu de société.
     */
    public String getRegles() {
        return regles;
    }

    /**
     * Récupère le nombre de joueurs du jeu de société.
     * @return Le nombre de joueurs du jeu de société.
     */
    public int getNbJoueurs() {
        return nbJoueurs;
    }

    /**
     * Récupère la date d'achat du jeu de société.
     * @return La date d'achat du jeu de société.
     */
    public Date getDateAchat() {
        return dateAchat;
    }

    /**
     * Récupère le type du jeu de société.
     * @return Le type du jeu de société.
     */
    public String getType() {
        return type;
    }

    /**
     * Récupère la taille de la table minimum nécessaire pour faire une partie.
     * @return La taille de la table minimum nécessaire pour faire une partie.
     */
    public TailleTable getTailleTable() {
        return tailleTable;
    }

    /**
     * Récupère la durée moyenne en minutes d'une partie du jeu de société.
     * @return Ladurée moyenne en minutes d'une partie du jeu de société.
     */
    public int getDureePartie() {
        return dureePartie;
    }

    /**
     * Récupère le prix d'achat du jeu de société.
     * @return Le prix d'achat du jeu de société.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Remplace le nombre de joueurs nécessaire pour faire une partie de jeu de société par la valeur en paramètre.
     * Le nombre de joueurs minimum pour un jeu de société est de 1. Un nombre de joueurs négatif ou nul lèvera une exception.
     * @param nbJoueurs Le nouveau nombre de joueurs nécessaires pour faire une partie du jeu de société.
     * @throws JeuDeSocieteException Exception levée lorsque le nombre de joueurs est négatif ou nul.
     */
    public void setNbJoueurs(int nbJoueurs) throws JeuDeSocieteException {
        if (nbJoueurs >= 1) {
            this.nbJoueurs = nbJoueurs;
        } else if (nbJoueurs == 0) {
            throw new JeuDeSocieteException("Nombre de joueurs nul");
        } else {
            throw new JeuDeSocieteException("Nombre de joueurs négatif");
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(nom=\"" + getNom() + "\", type=\"" + getType() + "\""
                + ", nbJoueurs=" + getNbJoueurs()
                + ", dateAchat=\"" + LocalDate.ofInstant(getDateAchat().toInstant(), ZoneId.systemDefault()).format(GenEvent.DATE_FORMATTER)
                + "\", prix=" + getPrix() + ")";
    }
}
