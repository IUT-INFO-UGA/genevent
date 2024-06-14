package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.personnel.Personnel;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class GenEvent implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    //private final Map<String, Utilisateur> utilisateurs;  // association qualifiée par l'email
    //private final Map<String, Evenement> evenements;  // association qualifiée par le nom

    private final Map<String, Personnel> personnels; // association qualifiée par l'id
    private final Map<Integer, Commande> commandes;

    public GenEvent() {
        //this.utilisateurs = new HashMap<>();
        //this.evenements = new HashMap<>();
        this.personnels = new HashMap<>();
        this.commandes = new HashMap<>();
    }

    /*public boolean ajouteUtilisateur(String email, String nom, String prenom) {
        if (this.utilisateurs.containsKey(email)) {
            return false;
        } else {
            this.utilisateurs.put(email, new Utilisateur(email, nom, prenom));
            return true;
        }
    }

    public Map<String, Evenement> getEvenements() {
        return this.evenements;
    }

    public void nouvelEvenement(String nom, LocalDate dateDebut, LocalDate dateFin, String adminEmail) {
        assert !this.evenements.containsKey(nom);
        assert this.utilisateurs.containsKey(adminEmail);
        Utilisateur admin = this.utilisateurs.get(adminEmail);
        Evenement evt = Evenement.initialiseEvenement(this, nom, dateDebut, dateFin, admin);
        this.evenements.put(nom, evt);
    }*/

    public void addPersonnel(Personnel personnel) {
        this.personnels.put(personnel.getId(), personnel);
    }

    public Commande getCommande(int numero) {
        return this.commandes.get(numero);
    }

    public void addCommande(Commande commande) {
        this.commandes.put(commande.getNumero(), commande);
    }

    public Collection<Commande> getCommandes() {
        return commandes.values();
    }
}
