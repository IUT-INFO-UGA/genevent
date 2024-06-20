package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.Role;
import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.commande.CommandeException;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSocieteException;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.modele.personnel.*;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.modele.seance.Evenement;
import fr.uga.iut2.genevent.modele.seance.Seance;
import fr.uga.iut2.genevent.modele.seance.SeanceException;
import fr.uga.iut2.genevent.vue.IHM;
import fr.uga.iut2.genevent.vue.JavaFXGUI;

import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;


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


    public void creerMembre(IHM.InfosMembre infos) throws MembreException {
        Membre membre = new Membre(
                infos.nom,
                infos.dateNaissance,
                infos.telephone
        );

        this.genevent.addMembre(membre);
    }

    public void modifierMembre(Membre membre, IHM.InfosMembre infos) throws MembreException {
        Membre versionPrecedente = new Membre(membre.getNom(), membre.getDateNaissance(), membre.getTelephone());
        membre.setNom(infos.nom);
        membre.setDateNaissance(infos.dateNaissance);
        membre.setTelephone(infos.telephone);
        Membre.logger.log(Level.INFO, "Modification d'un membre : " + versionPrecedente + " -> " + membre + ".");
    }

    public Collection<Membre> getMembres() {
        return Collections.unmodifiableCollection(this.genevent.getMembres());
    }

    public void saisirEvenement() {
       // this.ihm.saisirNouvelEvenement(this.genevent.getEvenements().keySet());
    }

    public void supprimerMembre(Membre membre) {
        this.genevent.supprimerMembre(membre);
    }

    public boolean existeSalle(long numero) {
        return getSalle(numero) != null;
    }

    public void creerSalle(IHM.InfosSalle salle) {
        this.genevent.addSalle(new Salle(
                genevent.getAndIncrementSalleId(),
                salle.type
        ));
    }

    public Collection<Salle> getSalles() {
        return genevent.getSalles().values();
    }

    public void creerTable(IHM.InfosTable table) {
        table.salle.addTable(new Table(
                table.salle.getAndIncrementTableId(),
                table.salle,
                table.type,
                table.nbPlaces,
                table.taille
        ));
    }

    public Salle getSalle(long numero) {
        return this.genevent.getSalles().get(numero);
    }

    public void supprimerSalle(Salle salle) {
        this.genevent.supprimerSalle(salle);
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

    public void modifierJeu(String nomDuJeu, IHM.InfosJeu infos) throws JeuDeSocieteException {
        JeuDeSociete jeu = this.genevent.getJeu(nomDuJeu);
        JeuDeSociete versionPrecedente = new JeuDeSociete(
                jeu.getNom(), jeu.getRegles(), jeu.getNbJoueurs(), jeu.getDateAchat(),
                jeu.getType(), jeu.getTailleTable(), jeu.getDureePartie(), jeu.getPrix()
        );

        jeu.setRegles(infos.regles);
        jeu.setNbJoueurs(infos.nbJoueurs);
        jeu.setDateAchat(infos.dateAchat);
        jeu.setType(infos.type);
        jeu.setTailleTable(infos.taille);
        jeu.setDureePartie(infos.dureePartie);
        jeu.setPrix(infos.prix);

        JeuDeSociete.logger.log(Level.INFO, "Un jeu de société a été modifié dans le stock : " + versionPrecedente + " -> " + jeu + ".");
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

    public void creerPersonnel(IHM.InfosPersonnel personnel) throws PersonnelException {
        Personnel p;

        switch (personnel.role) {
            case GERANT:
                p = new Gerant(personnel.login, personnel.prenom, personnel.nom);
                break;
            case ANIMATEUR:
                p = new Animateur(personnel.login, personnel.prenom, personnel.nom);
                break;
            case GESTIONNAIRE:
                p = new Gestionnaire(personnel.login, personnel.prenom, personnel.nom);
                break;
            default:
                return;
        }

        if (!personnel.telephone.isEmpty()) {
            p.setNumTel(personnel.telephone);
        }

        genevent.addPersonnel(p);
    }

    public void supprimerPersonnel(Personnel personnel) {
        genevent.supprimerPersonnel(personnel);
    }
}
