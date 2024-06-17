package fr.uga.iut2.genevent.modele.commande;

/**
 * Classe représentant une commande de jeux de société.
 * Une commande possède un numéro, permettant de l'identifier,
 * ainsi que le nom du jeu à commander, la quantité de jeux,
 * et le pris du jeu à l'unité à commander.
 */
public class Commande {

    // Attributs

    private final int numero;
    private String nomDuJeu;
    private int quantite;
    private double prix;

    // Constructeur

    /**
     * Construit un nouvel objet {@link Commande}, à l'aide d'un numéro de commande,
     * du no du jeu à commander, de la quantité à commander et du prix du jeu à l'unité.
     * @param numero Le numéro de commande (doit être unique).
     * @param nomDuJeu Le nom du jeu à commander.
     * @param quantite La quantité de jeu à commander.
     * @param prix Le prix du jeu à l'unité.
     */
    public Commande(int numero, String nomDuJeu, int quantite, double prix) throws CommandeException {
        this.numero = numero;
        setNomDuJeu(nomDuJeu);
        setQuantite(quantite);
        setPrix(prix);
    }

    // Méthodes

    /**
     * Récupère le numéro de la commande.
     * @return Le numéro de la commande.
     */
    public int getNumero() {
        return numero;
    }

    /**
     * Récupère le nom du jeu de société à commander.
     * @return Le nom du jeu de société à commander.
     */
    public String getNomDuJeu() {
        return nomDuJeu;
    }

    /**
     * Remplace le nom du jeu de société à commande par le nom en paramètre.
     * Le nom du jeu ne peut pas être vide ou null.
     * @param nomDuJeu Le nouveau nom du jeu de société à commander.
     * @throws CommandeException Exception levée lorsque le nom du jeu est null ou vide.
     */
    public void setNomDuJeu(String nomDuJeu) throws CommandeException{
        if (nomDuJeu != null && !nomDuJeu.isBlank()) {
            this.nomDuJeu = nomDuJeu;
        } else if (nomDuJeu == null) {
            throw new CommandeException("Le nom du jeu est null");
        } else {
            throw new CommandeException("Le nom du jeu est vide");
        }
    }

    /**
     * Récupère la quantité de jeux de société à commander simultanément.
     * @return La quantité de jeux de société à commander.
     */
    public int getQuantite() {
        return quantite;
    }

    /**
     * Remplace la quantité de jeux de société à commander par la valeur en paramètre.
     * La quantité ne peut pas être nulle ou négative.
     * @param quantite La nouvelle quantité de jeux de société à commander.
     * @throws CommandeException Exception levée lorsque la quantité est négative ou nulle.
     */
    public void setQuantite(int quantite) throws CommandeException {
        if (prix > 0) {
            this.quantite = quantite;
        } else if (prix == 0) {
            throw new CommandeException("Quantité nulle");
        } else {
            throw new CommandeException("Quantité négative");
        }
    }

    /**
     * Récupère le prix du jeu de société à commander à l'unité.
     * @return Le prix à l'unité du jeu de société à commander.
     */
    public double getPrix() {
        return prix;
    }

    /**
     * Remplace le prix du jeu de société à commander à l'unité par la valeur en paramètre.
     * Le prix ne peut pas être nul ou négatif.
     * @param prix Le nouveau prix du jeu de société à commander à l'unité.
     * @throws CommandeException Exception levée lorsque le prix est négatif ou nul.
     */
    public void setPrix(double prix) throws CommandeException {
        if (prix > 0) {
            this.prix = prix;
        } else if (prix == 0) {
            throw new CommandeException("Prix nul");
        } else {
            throw new CommandeException("Prix négatif");
        }
    }

    /**
     * Calcule et renvoie le prix total de la commande à être passée.
     * Le prix total est calculé de la manière suivante : {@link Commande#prix} * {@link Commande#quantite}.
     * @return Le prix total de la commande après calcul.
     */
    public double getPrixTotal() {
        return getPrix() * getQuantite();
    }
}
