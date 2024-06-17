package fr.uga.iut2.genevent.modele.salles;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Représente une salle de l'établissement de l'association/le salon de jeux de société.
 * Une salle est composée d'un numéro ainsi qu'un type, et possède des tables.
 */
public class Salle implements Serializable {

    // Attributs

    private final long numero;
    private final String type;
    private final HashMap<Long, Table> tables;

    // Constructeur

    /**
     * Crée une nouvelle salle à partir de son numéro et de son type.
     * @param numero Le numéro de la salle.
     * @param type Le type de la salle.
     */
    public Salle(long numero, String type) {
        this.numero = numero;
        this.type = type;
        this.tables = new HashMap<>();
    }

    // Méthodes

    /**
     * Ajoute une table donnée aux tables contenues dans la salle.
     * @param table La table à ajouter à la salle.
     */
    public void addTable(Table table) {
        tables.put(table.getId(), table);
    }

    /**
     * Récupère le numéro de la salle.
     * @return Le numéro de la salle.
     */
    public long getNumero() {
        return numero;
    }

    /**
     * Récupère le type de la salle.
     * @return Le type de la salle.
     */
    public String getType() {
        return type;
    }

    /**
     * Récupère la table d'indice donné parmi les tables contenues dans la salle.
     * @param id L'identifiant de la table que l'on cherche.
     * @return L'objet {@link Table} si celui-ci est trouvé, sinon <code>null</code>.
     */
    public Table getTable(long id) {
        return tables.get(id);
    }

    /**
     * Supprime la table d'identifiant donné des tables contenues dans la salle.
     * @param id L'identifiant de la table à supprimer de la salle.
     */
    public void removeTable(long id) {
        tables.remove(id);
    }

    /**
     * Supprime la table donnée des tables contenues dans la salle.
     * @param table La table à supprimer de la salle.
     */
    public void removeTable(Table table) {
        tables.remove(table.getId());
    }

    /**
     * Récupère l'ensemble des tables contenues dans la salle.
     * @return Un {@link HashMap} contenant les tables de la salle.
     */
    public HashMap<Long, Table> getTables() {
        return tables;
    }
}
