package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.modele.salles.Salle;
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
                genevent.getMembres().size() + 1, // Trouver un meilleur moyen
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

    public Collection<Salle> getSalles() {
        return genevent.getSalles().values();
    }
}
