package fr.uga.iut2.genevent.modele.membre;

import java.io.Serializable;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Représente un membre de l'association ou du café de jeux de société.
 * Un membre n'est cependant pas un utilisateur du logiciel.
 * Un membre possède un identifiant unique, un nom, une date de naissance et un numéro de téléphone.
 */
public class Membre implements Serializable {

    // Constantes

    public static final Pattern PATERNE_NOM = Pattern.compile("^[a-z-A-Z]+(-[a-z-A-Z]+)*( [a-z-A-Z]+(-[a-z-A-Z]+)*)+$");
    public static final Pattern PATERNE_TELEPHONE = Pattern.compile("^[0-9]{2}(([0-9]{2}){4})|(\\.([0-9]{2}){4})|( ([0-9]{2}){4})$");

    // Attributs

    private final int id;
    private String nom;
    private Date dateNaissance;
    private String telephone;

    // Constructeur

    /**
     * Crée un nouveau membre à partir de ses informations personnelles.
     * @param id L'identifiant du membre, il doit être unique.
     * @param nom Le nom du membre.
     * @param dateNaissance La date de naissance du membre.
     * @param telephone Le numéro de téléphone du membre.
     */
    public Membre(int id, String nom, Date dateNaissance, String telephone) throws MembreException {
        this.id = id;
        setNom(nom);
        setDateNaissance(dateNaissance);
        setTelephone(telephone);
    }

    // Méthodes

    /**
     * Récupère l'identifiant du membre.
     * @return L'identifiant du membre.
     */
    public int getId() {
        return id;
    }

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
        Matcher matcher = PATERNE_NOM.matcher(nom);
        if (matcher.find()) {
            int indiceEspace = nom.indexOf(' ');
            String maj = nom.toUpperCase();
            StringBuilder sb = new StringBuilder(nom.toLowerCase());
            sb.setCharAt(0, maj.charAt(0));
            sb.setCharAt(indiceEspace + 1, maj.charAt(indiceEspace + 1));
            this.nom = sb.toString();
        } else {
            throw new MembreException("Nom du membre invalide");
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
        Matcher matcher = PATERNE_TELEPHONE.matcher(telephone);
        if (matcher.find()) {
            this.telephone = telephone;
        } else {
            throw new MembreException("numéro de téléphone du membre invalide");
        }
    }
}
