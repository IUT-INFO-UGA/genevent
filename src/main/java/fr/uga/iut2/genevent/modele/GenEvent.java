package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.seance.Seance;

import java.io.Serializable;
import java.util.*;


public class GenEvent implements Serializable {

    private static final long serialVersionUID = 1L;  // nécessaire pour la sérialisation
    //private final Map<String, Utilisateur> utilisateurs;  // association qualifiée par l'email
    //private final Map<String, Evenement> evenements;  // association qualifiée par le nom

    private Map<String, Personnel> personnels; // association qualifiée par l'id
    private Map<String, JeuDeSociete> jeuxDeSociete;
    private Map<Integer, Commande> commandes;
    private Map<Integer, Membre> membres;
    private Map<Long, Salle> salles;
    private List<Seance> seances;

    public GenEvent() {
        //this.utilisateurs = new HashMap<>();
        //this.evenements = new HashMap<>();
        this.personnels = new HashMap<>();
        this.jeuxDeSociete = new HashMap<>();
        this.commandes = new HashMap<>();
        this.membres = new HashMap<>();
        this.salles = new HashMap<>();
        this.seances = new ArrayList<>();
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

    public void supprimerPersonnel(Personnel personnel) {
        this.personnels.remove(personnel.getId());
    }

    public Collection<Personnel> getPersonnels() {
        return personnels.values();
    }

    // JEUX DE SOCIETE

    public void addJeuDeSociete(JeuDeSociete jeu) {
        this.jeuxDeSociete.put(jeu.getNom(), jeu);
    }

    public void supprimerJeu(JeuDeSociete jeuDeSociete) {
        this.jeuxDeSociete.remove(jeuDeSociete.getNom());
    }

    public Collection<JeuDeSociete> getJeuxDeSociete() {
        return jeuxDeSociete.values();
    }

    // COMMANDES

    public Commande getCommande(int numero) {
        return this.commandes.get(numero);
    }

    public void addCommande(Commande commande) {
        this.commandes.put(commande.getNumero(), commande);
    }

    public Collection<Commande> getCommandes() {
        return commandes.values();
    }

    // MEMBRES

    public void addMembre(Membre membre) {
        this.membres.put(membre.getId(), membre);
    }

    public Collection<Membre> getMembres() {
        return membres.values();
    }

    public void supprimerMembre(Membre membre) {
        this.membres.remove(membre.getId());
    }

    // SEANCES

    public void addSeance(Seance seance) {
        this.seances.add(seance);
    }

    public void supprimerSeance(Seance seance) {
        this.seances.remove(seance);
    }

    public List<Seance> getSeances() {
        return seances;
    }

    // SALLES

    public void addSalle(Salle salle) {
        this.salles.put(salle.getNumero(), salle);
    }

    public void supprimerSalle(Salle salle) {
        this.salles.remove(salle.getNumero());
    }

    public Map<Long, Salle> getSalles() {
        return salles;
    }
}
