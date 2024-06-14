package fr.uga.iut2.genevent.modele.personnel;

public class Gerant extends Gestionnaire {
    private String motDePasse;

    public Gerant(String prenom, String nom, String motDePasse) {
        super(prenom, nom);
        this.motDePasse = motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public boolean verifierMotDePasse(String motDePasse) {
        return this.motDePasse.equals(motDePasse);
    }
}
