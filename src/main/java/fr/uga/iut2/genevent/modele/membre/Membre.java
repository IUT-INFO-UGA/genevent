package fr.uga.iut2.genevent.modele.membre;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.personnel.PersonnelException;
import fr.uga.iut2.genevent.util.ModeleUtilitaire;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.Logger;
import java.util.regex.Matcher;

/**
 * Représente un membre de l'association ou du café de jeux de société.
 * Un membre n'est cependant pas un utilisateur du logiciel.
 * Un membre possède un identifiant unique, un nom, une date de naissance et un numéro de téléphone.
 */
public class Membre implements Serializable {

    // Logger

    public static final Logger logger = Logger.getLogger(Membre.class.getPackageName());

    static {
        GenEvent.logManager.addLogger(logger);
    }

    // Attributs

    private String nom;
    private Date dateNaissance;
    private String telephone;

    // Constructeur

    /**
     * Crée un nouveau membre à partir de ses informations personnelles.
     * @param nom Le nom du membre.
     * @param dateNaissance La date de naissance du membre.
     * @param telephone Le numéro de téléphone du membre.
     */
    public Membre(String nom, Date dateNaissance, String telephone) throws MembreException {
        setNom(nom);
        setDateNaissance(dateNaissance);
        setTelephone(telephone);
    }

    // Méthodes

    /**
     * Récupère le nom complet du membre.
     * @return Le nom complet du membre.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère la date de naissance du membre.
     * @return La date de naissance du membre.
     */
    public Date getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Récupère le numéro de téléphone du membre.
     * @return Le numéro de téléphone du membre.
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     *
     * Remplace le nom complet du membre par la valeur donnée en paramètre.
     * Un nom complet doit respecter certaines règles pour pouvoir être accepté :
     * <ul>
     *     <li>Le nom complet doit contenir au moins un espace (séparant prénom et nom).</li>
     *     <li>Un prénom peut contenir des tirets pour représenter un nom composé.</li>
     *     <li>Un nom de famille peut contenir des espaces.</li>
     * </ul>
     * La première lettre du prénom et du nom de famille sont automatiquement capitalisées, le reste est minimisé.
     * Une exception est levée si le nom du membre est invalide (ne correspond pas à l'expression régulière).
     * @param nom Le nouveau nom complet du membre.
     * @throws MembreException Exception levée si le nom ne correspond pas à l'expression régulière.
     */
    public void setNom(String nom) throws MembreException {
        Matcher matcher = ModeleUtilitaire.PATERNE_NOM.matcher(nom);
        if (matcher.find()) {
            int indiceEspace = nom.indexOf(' ');
            String maj = nom.toUpperCase();
            StringBuilder sb = new StringBuilder(nom.toLowerCase());
            sb.setCharAt(0, maj.charAt(0));
            sb.setCharAt(indiceEspace + 1, maj.charAt(indiceEspace + 1));
            this.nom = sb.toString();
        } else {
            throw new MembreException(
                    "Nom du membre invalide : " + nom + "."
                            + "\nLe nom du membre doit être de la forme \"prénom[-composé] nom de famille\""
            );
        }
    }

    /**
     * Remplace la date de naissance du membre avec la nouvelle date de naissance passée en paramètre.
     * @param dateNaissance La nouvelle date de naissance du membre.
     */
    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    /**
     * Remplace le numéro de téléphone du membre par le numéro de téléphone passé en paramètre.
     * @param telephone Le nouveau numéro de téléphone du membre.
     */
    public void setTelephone(String telephone) throws MembreException {
        String telephoneFormate = ModeleUtilitaire.formaterTelephone(telephone);
        if (telephoneFormate != null) {
            this.telephone = ModeleUtilitaire.formaterTelephone(telephone);
        } else {
            throw new MembreException(
                    "Le numéro de téléphone est invalide : " + telephone  + "ne respecte pas le format attendu."
                            + "\nLes formats possibles sont :"
                            + "\n\t- XXXXXXXXXX"
                            + "\n\t- XX.XX.XX.XX.XX"
                            + "\n\t- XX XX XX XX XX"
            );
        }
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(nom=\"" + getNom()
                + "\", dateNaissance=\"" + LocalDate.ofInstant(getDateNaissance().toInstant(), ZoneId.systemDefault()).format(GenEvent.DATE_FORMATTER)
                + "\", tel=\"" + getTelephone() + "\")";
    }
}
