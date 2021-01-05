package fr.uga.iut2.genconf.vue;

import fr.uga.iut2.genconf.controleur.Commande;
import fr.uga.iut2.genconf.controleur.Controleur;
import java.util.Optional;
import java.util.Set;


public class GUI extends IHM {
    private final Controleur controleur;
    private final VuePrincipale vuePrincipale;
    private final VueCreationConference vueCreationConf;
    private final VueCreationUtilisateur vueCreationUser;
    private final VueEtat vueEtat;

    // identifiants uniques pour les vues composant la vue principale
    private static final String VUE_ETAT = "etat";
    private static final String VUE_CREATION_CONF = "creation_conf";
    private static final String VUE_CREATION_USER = "creation_user";

    public GUI(Controleur controleur) {
        this.controleur = controleur;

        // création de l'interface
        this.vueEtat = new VueEtat(this);
        this.vueCreationConf = new VueCreationConference(this);
        this.vueCreationUser = new VueCreationUtilisateur(this);

        this.vuePrincipale = new VuePrincipale(this);
        this.vuePrincipale.ajouterVue(this.vueEtat, GUI.VUE_ETAT);
        this.vuePrincipale.ajouterVue(this.vueCreationConf, GUI.VUE_CREATION_CONF);
        this.vuePrincipale.ajouterVue(this.vueCreationUser, GUI.VUE_CREATION_USER);
        this.vuePrincipale.afficherVue(GUI.VUE_ETAT);
    }

//-----  Éléments du dialogue  -------------------------------------------------

    protected void actionCreerConference() {
        this.controleur.gererDialogue(Commande.CREER_CONFERENCE);
    }

    protected void actionCreerUtilisateur() {
        this.controleur.gererDialogue(Commande.CREER_UTILISATEUR);
    }

    protected void actionTerminer() {
        this.controleur.gererDialogue(Commande.QUITTER);
    }

    protected void creerUtilisateur(Optional<InfosUtilisateur> nouvelUtilisateur) {
        this.vuePrincipale.afficherVue(GUI.VUE_ETAT);
        nouvelUtilisateur.ifPresentOrElse(
                infos -> this.controleur.creerUtilisateur(infos),
                () -> this.vueEtat.setEtat("")
        );
    }

    protected void creerConference(Optional<InfosNouvelleConference> nlleConf) {
        this.vuePrincipale.afficherVue(GUI.VUE_ETAT);
        nlleConf.ifPresentOrElse(
                infos -> this.controleur.creerConference(infos),
                () -> this.vueEtat.setEtat("")
        );
    }

//-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void afficherInterface() {
        this.vuePrincipale.afficher();
    }

    @Override
    public void fermerInterface() {
        this.vuePrincipale.fermer();
    }

    @Override
    public void informerUtilisateur(final String msg, final boolean succes) {
        this.vueEtat.setEtat(msg + (succes ? " [Création ok]" : " [ÉCHEC]"));
    }

    @Override
    public void saisirUtilisateur() {
        this.vuePrincipale.afficherVue(GUI.VUE_CREATION_USER);
    }

    @Override
    public void saisirNouvelleConference(final Set<String> nomsExistants) {
        this.vueCreationConf.setNomsExistants(nomsExistants);
        this.vuePrincipale.afficherVue(GUI.VUE_CREATION_CONF);
    }
}
