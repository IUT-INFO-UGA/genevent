package fr.uga.iut2.genevent.modele;

import java.util.Date;

public class Membre {

    private final int id;
    private String nom;
    private Date dateNaissance;
    private String telephone;

    public Membre(int id, String nom, Date dateNaissance, String telephone) {
        this.id = id;
        this.nom = nom;
        this.dateNaissance = dateNaissance;
        this.telephone = telephone;
    }

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
