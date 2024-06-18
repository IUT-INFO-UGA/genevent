package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Role;
import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.commande.CommandeException;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSocieteException;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.modele.seance.Evenement;
import fr.uga.iut2.genevent.modele.seance.Seance;
import fr.uga.iut2.genevent.modele.seance.SeanceException;
import fr.uga.iut2.genevent.vue.IHM;
import fr.uga.iut2.genevent.vue.JavaFXGUI;

import java.util.Collection;
import java.util.Collections;


public class Controleur {

    private final GenEvent genevent;
    private final IHM ihm;

    public Controleur(GenEvent genevent) {
        this.genevent = genevent;

        // choisir la classe CLI ou JavaFXGUI
//        this.ihm = new CLI(this);
        this.ihm = new JavaFXGUI(this);
    }

    public void demarrer() {
        this.ihm.demarrerInteraction();
    }

    public void saisirUtilisateur() {
        // this.ihm.saisirUtilisateur();
    }

    public void creerUtilisateur(IHM.InfosUtilisateur infos) {
//        boolean nouvelUtilisateur = this.genevent.ajouteUtilisateur(
//                infos.email,
//                infos.nom,
//                infos.prenom
//        );
//        if (nouvelUtilisateur) {
//            this.ihm.informerUtilisateur(
//                    "Nouvel·le utilisa·teur/trice : " + infos.prenom + " " + infos.nom + " <" + infos.email + ">",
//                    true
//            );
//        } else {
//            this.ihm.informerUtilisateur(
//                    "L'utilisa·teur/trice " + infos.email + " est déjà connu·e de GenEvent.",
//                    false
//            );
//        }
    }

    public void creerMembre(IHM.InfosMembre infos) throws MembreException {
        Membre membre = new Membre(
                infos.nom,
                infos.dateNaissance,
                infos.telephone
        );

        this.genevent.addMembre(membre);
    }

    public Collection<Membre> getMembres() {
        return Collections.unmodifiableCollection(this.genevent.getMembres());
    }

    public void saisirEvenement() {
       // this.ihm.saisirNouvelEvenement(this.genevent.getEvenements().keySet());
    }

    public void creerEvenement(IHM.InfosNouvelEvenement infos) {
        // création d'un Utilisateur si nécessaire
//        boolean nouvelUtilisateur = this.genevent.ajouteUtilisateur(
//                infos.admin.email,
//                infos.admin.nom,
//                infos.admin.prenom
//        );
//        if (nouvelUtilisateur) {
//            this.ihm.informerUtilisateur("Nouvel·le utilisa·teur/trice : " + infos.admin.prenom + " " + infos.admin.nom + " <" + infos.admin.email + ">",
//                    true
//            );
//        }
//
//        this.genevent.nouvelEvenement(
//                infos.nom,
//                infos.dateDebut,
//                infos.dateFin,
//                infos.admin.email
//        );
//        this.ihm.informerUtilisateur(
//                "Nouvel évènement : " + infos.nom + ", administré par " + infos.admin.email,
//                true
//        );
    }

    public void supprimerMembre(Membre membre) {
        this.genevent.supprimerMembre(membre);
    }

    public boolean existeSalle(long numero) {
        return getSalle(numero) != null;
    }

    public void creerSalle(IHM.InfosSalle salle) {
        this.genevent.addSalle(new Salle(
                salle.numero,
                salle.type
        ));
    }

    public Collection<Salle> getSalles() {
        return genevent.getSalles().values();
    }

    public void creerTable(IHM.InfosTable table) {
        table.salle.addTable(new Table(
                table.id,
                table.salle,
                table.type,
                table.nbPlaces,
                table.taille
        ));
    }

    public Salle getSalle(long numero) {
        return this.genevent.getSalles().get(numero);
    }

    public Role getRole() {
        return genevent.getRole();
    }

    public void setRole(Role role) {
        genevent.setRole(role);
    }

    public void creerJeu(IHM.InfosJeu jeu) throws JeuDeSocieteException {
        genevent.addJeuDeSociete(new JeuDeSociete(
                jeu.nom,
                jeu.regles,
                jeu.nbJoueurs,
                jeu.dateAchat,
                jeu.type,
                jeu.taille,
                jeu.dureePartie,
                jeu.prix
        ));
    }

    public void supprimerJeu(JeuDeSociete jeuDeSociete) {
        this.genevent.supprimerJeu(jeuDeSociete);
    }

    public JeuDeSociete getJeu(String name) {
        return genevent.getJeu(name);
    }

    public Collection<JeuDeSociete> getJeux() {
        return genevent.getJeuxDeSociete();
    }

    public void creerCommande(IHM.InfosCommande commande) throws CommandeException {
        genevent.addCommande(new Commande(
                commande.numero,
                commande.nomDuJeu,
                commande.quantite,
                commande.prix
        ));
    }

    public Collection<Commande> getCommandes() {
        return genevent.getCommandes();
    }

    public void creerSeance(IHM.InfosSeance seance) {
        Seance se = new Seance(
                seance.type,
                seance.date,
                seance.table,
                seance.heureDebut,
                seance.heureFin
        );

        genevent.addSeance(se);
    }

    public void supprimerSeance(Seance seance) {
        genevent.supprimerSeance(seance);
    }

    public void creerEvenement(IHM.InfosEvenement evenement) throws SeanceException {
        Evenement ev = new Evenement(
                evenement.type,
                evenement.date,
                evenement.table,
                evenement.heureDebut,
                evenement.heureFin,
                evenement.nbPlaces
        );

        evenement.animateurs.forEach(ev::addAnimateur);

        genevent.addSeance(ev);
    }

    public Collection<Seance> getSeances() {
        return genevent.getSeances();
    }

    public Collection<Personnel> getPersonnel() {
        return genevent.getPersonnels();
    }

    public Personnel getPersonnel(String login) {
        return genevent.getPersonnel(login);
    }
}
