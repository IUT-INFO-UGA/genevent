package fr.uga.iut2.genevent.modele.jeu;

import java.util.Date;

public class JeuDeSociete {

    // Attributs

    private String nom;
    private String regles;
    private int nbJoueurs;
    private Date dateAchat;
    private String type;
    private TailleTable tailleTable;
    private int dureePartie;
    private float prix;

    // Constructeur

    public JeuDeSociete(String nom, String regles, int nbJoueurs, Date dateAchat, String type, TailleTable tailleTable, int dureePartie, float prix) {
        this.nom = nom;
        this.regles = regles;
        this.nbJoueurs = nbJoueurs;
        this.dateAchat = dateAchat;
        this.tailleTable = tailleTable;
        this.type = type;
        this.dureePartie = dureePartie;
        this.prix = prix;
    }

    // Méthodes

    public String getNom() {
        return nom;
    }

    public String getRegles() {
        return regles;
    }

    public int getNbJoueurs() {
        return nbJoueurs;
    }

    public Date getDateAchat() {
        return dateAchat;
    }

    public String getType() {
        return type;
    }

    public TailleTable getTailleTable() {
        return tailleTable;
    }

    public int getDureePartie() {
        return dureePartie;
    }

    public float getPrix() {
        return prix;
    }

    public void setNbJoueurs(int nbJoueurs) {
        this.nbJoueurs = nbJoueurs;
    }
}
