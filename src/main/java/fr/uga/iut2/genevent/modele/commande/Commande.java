package fr.uga.iut2.genevent.modele.commande;

/**
 * Classe représentant une commande de jeux de société.
 * Une commande possède un numéro, permettant de l'identifier,
 * ainsi que le nom du jeu à commander, la quantité de jeux,
 * et le pris du jeu à l'unité à commander.
 */
public class Commande {

    // Attributs

    private int numero;
    private String nomDuJeu;
    private int quantite;
    private float prix;

    // Constructeur

    /**
     * Construit un nouvel objet {@link Commande}, à l'aide d'un numéro de commande,
     * du no du jeu à commander, de la quantité à commander et du prix du jeu à l'unité.
     * @param numero Le numéro de commande (doit être unique).
     * @param nomDuJeu Le nom du jeu à commander.
     * @param quantite La quantité de jeu à commander.
     * @param prix Le prix du jeu à l'unité.
     */
    public Commande(int numero, String nomDuJeu, int quantite, float prix) {
        this.numero = numero;
        this.nomDuJeu = nomDuJeu;
        this.quantite = quantite;
        this.prix = prix;
    }

    // Méthodes

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

    /**
     * Calcule et renvoie le prix total de la commande à être passée.
     * Le prix total est calculé de la manière suivante : {@link Commande#prix} * {@link Commande#quantite}.
     * @return Le prix total de la commande après calcul.
     */
    public float getPrixTotal() {
        return getPrix() * getQuantite();
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }
}
