package fr.uga.iut2.genconf.controleur;

import fr.uga.iut2.genconf.vue.CLI;
import fr.uga.iut2.genconf.modele.GenConf;
import fr.uga.iut2.genconf.vue.IHM;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Controleur {

    private final GenConf genconf;
    private final IHM ihm;

    public Controleur(GenConf genconf) {
        this.genconf = genconf;
        this.ihm = new CLI();
        this.run();
    }

    private void run() {
        Commande cmd;
        do {
            cmd = this.ihm.lireCommande();
//            System.out.println(cmd);
//            System.out.flush();
            switch (cmd) {
                case QUITTER:
                    // rien à faire
                    break;
                case CREER_UTILISATEUR:
                    this.creerUtilisateur();
                    break;
                case CREER_CONFERENCE:
                    this.creerConference();
                    break;
                default:
                    assert false: "Commande inconnue.";
            }
        } while (cmd != Commande.QUITTER);
    }

    private void creerUtilisateur() {
        IHM.InfosUtilisateur infos = this.ihm.saisirUtilisateur();
        boolean nouvelUtilisateur = this.genconf.ajouteUtilisateur(
                infos.email,
                infos.nom,
                infos.prenom
        );
        if (nouvelUtilisateur) {
            this.ihm.informerUtilisateur(
                    "Nouvel·le utilisa·teur/trice : " + infos.prenom + " " + infos.nom + " <" + infos.email + ">",
                    true
            );
        } else {
            this.ihm.informerUtilisateur(
                    "L'utilisa·teur/trice " + infos.email + " est déjà connu·e de GenConf.",
                    false
            );
        }
    }

    private void creerConference() {
        IHM.InfosNouvelleConference infos = this.ihm.saisirNouvelleConference(this.genconf.getConferences().keySet());

        // création d'un Utilisateur si nécessaire
        boolean nouvelUtilisateur = this.genconf.ajouteUtilisateur(
                infos.admin.email,
                infos.admin.nom,
                infos.admin.prenom
        );
        if (nouvelUtilisateur) {
            this.ihm.informerUtilisateur("Nouvel·le utilisa·teur/trice : " + infos.admin.prenom + " " + infos.admin.nom + " <" + infos.admin.email + ">",
                    true
            );
        }

        this.genconf.nouvelleConference(
                infos.nom,
                infos.dateDebut,
                infos.dateFin,
                infos.admin.email
        );
        this.ihm.informerUtilisateur(
                "Nouvelle conférence : " + infos.nom + ", administrée par " + infos.admin.email,
                true
        );
    }

}
