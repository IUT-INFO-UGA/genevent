package fr.uga.iut2.genevent.modele.membre;

import java.util.Date;

/**
 * Représente un membre de l'association ou du café de jeux de société.
 * Un membre n'est cependant pas un utilisateur du logiciel.
 * Un membre possède un identifiant unique, un nom, une date de naissance et un numéro de téléphone.
 */
public class Membre {

    // Attributs

    private final int id;
    private String nom;
    private Date dateNaissance;
    private String telephone;

    // Constructeur

    public Membre(int id, String nom, Date dateNaissance, String telephone) {
        this.id = id;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
    }

    // Méthodes

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
