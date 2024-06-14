package fr.uga.iut2.genevent.modele.commande;

public class Commande {
    private int numero;
    private String nomDuJeu;
    private int quantite;
    private float prix;

    public Commande(int numero, String nomDuJeu, int quantite, float prix) {
        this.numero = numero;
        this.nomDuJeu = nomDuJeu;
        this.quantite = quantite;
        this.prix = prix;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomDuJeu() {
        return nomDuJeu;
    }

    public void setNomDuJeu(String nomDuJeu) {
        this.nomDuJeu = nomDuJeu;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
