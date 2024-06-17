package fr.uga.iut2.genevent.modele.personnel;

import fr.uga.iut2.genevent.modele.membre.Membre;

import java.io.Serializable;
import java.util.regex.Matcher;

/**
 * Représente un membre du personnel de l'association/du salon.
 * Un membre du personnel possède un identifiant (unique), ainsi qu'un nom, un prénom et un numéro de téléphone.
 * <br>
 * Cette classe est abstraite et sert de base pour tous les autres rôles du personnel.
 */
public abstract class Personnel implements Serializable {

    // Attributs

    private final String id;
    private final String prenom;
    private final String nom;
    private String telephone;

    // Constructeur

    /**
     * Crée un nouveau membre du personnel à partir de son nom et prénom.
     * @param id L'identifiant du membre du personnel.
     * @param prenom Le prénom du membre du personnel.
     * @param nom Le nom de famille du membre du personnel.
     * @throws PersonnelException Exception levée lorsque le numéro de téléphone est invalide.
     */
    public Personnel(String id, String prenom, String nom) throws PersonnelException {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
        this.telephone = null;
    }

    /**
     * Récupère l'identifiant du membre du personnel.
     * @return L'identifiant du membre du personnel.
     */
    public String getId() {
        return id;
    }

    /**
     * Récupère le prénom du membre du personnel.
     * @return Le prénom du membre du personnel.
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Récupère le nom de famille du membre du personnel.
     * @return Le nom de famille du membre du personnel.
     */
    public String getNom() {
        return nom;
    }

    /**
     * Récupère le numéro de téléphone du membre du personnel.
     * @return Le numéro de téléphone du membre du personnel.
     */
    public String getNumTel() {
        return telephone;
    }

    /**
     * Remplace le numéro de téléphone du membre du personnel par le numéro de téléphone passé en paramètre.
     * Le numéro de téléphone doit correspondre à l'expression régulière {@link Membre#PATERNE_TELEPHONE}.
     * @param telephone Le nouveau numéro de téléphone à attribuer au membre du personnel.
     * @throws PersonnelException L'exception à lever lorsque le numéro de téléphone est invalide.
     */
    public void setNumTel(String telephone) throws PersonnelException {
        Matcher matcher = Membre.PATERNE_TELEPHONE.matcher(telephone);
        if (matcher.find()) {
            this.telephone = telephone;
        } else {
            throw new PersonnelException("Le numéro de téléphone est invalide");
        }
    }
}
