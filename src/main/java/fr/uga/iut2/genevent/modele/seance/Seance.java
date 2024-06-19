package fr.uga.iut2.genevent.modele.seance;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.salles.Table;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Représente une séance du planning de l'association/du salon.
 * Une séance a un type, une date, une heure de début ainsi qu'une heure de fin.
 * Elle possède aussi la liste des membres qui participent à cette séance
 */
public class Seance implements Serializable {

    // Logger

    public static final Logger logger = Logger.getLogger(Seance.class.getPackageName());

    static {
        GenEvent.logManager.addLogger(logger);
    }

    // Attributs

    private String type;
    private Date date;
    private Table table;
    private int heureDebut;
    private int heureFin;
    private final ArrayList<Membre> membres;
    private final ArrayList<JeuDeSociete> jeuxDeSociete;

    // Constructeur

    /**
     * Crée une nouvelle séance à partir de son type, sa date et ses horaires.
     * @param type Le type de la séance.
     * @param date La date de la séance.
     * @param heureDebut L'heure de début de la séance.
     * @param heureFin L'heure de fin de la séance.
     */
    public Seance(String type, Date date, Table table, int heureDebut, int heureFin) {
        this.type = type;
        this.date = date;
        this.table = table;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.membres = new ArrayList<>();
        this.jeuxDeSociete = new ArrayList<>();
    }

    // Méthodes

    /**
     * Récupère le type de la séance.
     * @return Le type de la séance.
     */
    public String getType() {
        return type;
    }

    /**
     * Récupère la date de la séance.
     * @return La date de la séance.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Récupère la table de la séance
     * @return la table de la séance
     */
    public Table getTable() {
        return table;
    }

    /**
     * Récupère l'heure de début de la séance.
     * @return L'heure de début de la séance.
     */
    public int getHeureDebut() {
        return heureDebut;
    }

    /**
     * Récupère l'heure de fin de la séance.
     * @return L'heure de fin de la séance.
     */
    public int getHeureFin() {
        return heureFin;
    }

    /**
     * Récupère la liste des membres participants à la séance.
     * @return La liste des membres participants à la séance.
     */
    public ArrayList<Membre> getMembres() {
        return membres;
    }

    /**
     * Récupère la liste des jeux de société qui seront présent lors de la séance.
     * @return La liste des jeux de société qui seront présent lors de la séance.
     */
    public ArrayList<JeuDeSociete> getJeuxDeSociete() {
        return jeuxDeSociete;
    }

    /**
     * Ajoute le membre donné à la liste des membres participants à la séance.
     * @param membre Le membre participant à la séance.
     */
    public void addMembre(Membre membre) {
        this.membres.add(membre);
    }

    /**
     * Ajoute le jeu de société donné à la liste des jeux de société qui seront présents lors de la séance.
     * @param jeuDeSociete Le jeu de société à ajouter.
     */
    public void addJeuDeSociete(JeuDeSociete jeuDeSociete) {
        this.jeuxDeSociete.add(jeuDeSociete);
    }

    /**
     * Supprime le membre donné de la liste des membres participants à la séance.
     * @param membre Le membre à supprimer de la séance.
     */
    public void removeMembre(Membre membre) {
        this.membres.remove(membre);
    }

    /**
     * Supprime le jeu de société donné de la liste des jeux de sociétés qui seront présent lors de la séance.
     * @param jeuDeSociete Le jeu de société à supprimer.
     */
    public void removeJeuDeSociete(JeuDeSociete jeuDeSociete) {
        this.jeuxDeSociete.remove(jeuDeSociete);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(type=\"" + getType()
                + "\", date=\"" + LocalDate.ofInstant(getDate().toInstant(), ZoneId.systemDefault()).format(GenEvent.DATE_FORMATTER)
                + "\", heureDebut=" + getHeureDebut() + "h, heureFin=" + getHeureFin()
                + "h, table=" + getTable().getId();
    }
}
