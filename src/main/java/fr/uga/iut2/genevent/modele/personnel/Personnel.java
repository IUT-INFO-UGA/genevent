package fr.uga.iut2.genevent.modele.personnel;

public abstract class Personnel {
    private String id;
    private String prenom, nom, telephone;

    public Personnel(String prenom, String nom) {
        this.prenom = prenom;
        this.nom = nom;
    }

    public String getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getNom() {
        return nom;
    }

    public String getNumTel() {
        return telephone;
    }

    public void setNumTel(String telephone) {
        this.telephone = telephone;
    }
}
