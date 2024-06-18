package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.salles.Salle;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;


public class GenEvent implements Serializable {

    public static final Logger logger = Logger.getLogger(GenEvent.class.getPackageName());
    public static final LogManager logManager = LogManager.getLogManager();

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    //private final Map<String, Utilisateur> utilisateurs;  // association qualifiée par l'email
    //private final Map<String, Evenement> evenements;  // association qualifiée par le nom

    static {
        try {
            logManager.readConfiguration(new FileInputStream("conf/logging.properties"));
            logManager.addLogger(logger);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Impossible de lire le fichier de configuration du logger.", e);
        }
    }

    private Map<String, Personnel> personnels; // association qualifiée par l'id
    private Map<Integer, Commande> commandes;
    private Map<Integer, Membre> membres;
    private Map<Long, Salle> salles;

    public GenEvent() {
        //this.utilisateurs = new HashMap<>();
        //this.evenements = new HashMap<>();
        this.personnels = new HashMap<>();
        this.commandes = new HashMap<>();
        this.membres = new HashMap<>();
        this.salles = new HashMap<>();
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

    public void addMembre(Membre membre) {
        this.membres.put(membre.getId(), membre);
    }

    public Map<Integer, Membre> getMembres() {
        return membres;
    }

    public void removeMembre(Membre membre) {
        this.membres.remove(membre.getId());
    }

    public Map<Long, Salle> getSalles() {
        return salles;
    }
}
