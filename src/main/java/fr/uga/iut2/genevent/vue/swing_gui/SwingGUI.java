package fr.uga.iut2.genevent.vue.swing_gui;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.vue.IHM;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;


public class SwingGUI extends IHM {
    private final Controleur controleur;
    private final CountDownLatch eolBarrier;

    private final VuePrincipale vuePrincipale;
    private final VueCreationEvenement vueCreationEvt;
    private final VueCreationUtilisateur vueCreationUser;
    private final VueEtat vueEtat;

    // identifiants uniques pour les vues composant la vue principale
    private static final String VUE_ETAT = "etat";
    private static final String VUE_CREATION_EVT = "creation_evt";
    private static final String VUE_CREATION_USER = "creation_user";

    public SwingGUI(Controleur controleur) {
        this.controleur = controleur;

        // initialisé à 1 pour attendre l'évènement correspondant à la fin de vie de SwingGUI
        this.eolBarrier = new CountDownLatch(1);

        // création de l'interface
        this.vueEtat = new VueEtat(this);
        this.vueCreationEvt = new VueCreationEvenement(this);
        this.vueCreationUser = new VueCreationUtilisateur(this);

        this.vuePrincipale = new VuePrincipale(this);
        this.vuePrincipale.ajouterVue(this.vueEtat, SwingGUI.VUE_ETAT);
        this.vuePrincipale.ajouterVue(this.vueCreationEvt, SwingGUI.VUE_CREATION_EVT);
        this.vuePrincipale.ajouterVue(this.vueCreationUser, SwingGUI.VUE_CREATION_USER);
        this.vuePrincipale.afficherVue(SwingGUI.VUE_ETAT);
    }

//-----  Éléments du dialogue  -------------------------------------------------

    protected void actionCreerEvenement() {
        this.controleur.saisirEvenement();
    }

    protected void actionCreerUtilisateur() {
        this.controleur.saisirUtilisateur();
    }

    protected void actionTerminer() {
        this.vuePrincipale.fermer();

        // On notifie la fin de vie de SwingGUI pour rendre la main au contrôleur
        this.eolBarrier.countDown();
    }

    protected void creerUtilisateur(Optional<InfosUtilisateur> nouvelUtilisateur) {
        this.vuePrincipale.afficherVue(SwingGUI.VUE_ETAT);
        nouvelUtilisateur.ifPresentOrElse(
                infos -> this.controleur.creerUtilisateur(infos),
                () -> this.vueEtat.setEtat("")
        );
    }

    protected void creerEvenement(Optional<InfosNouvelEvenement> nouvelEvt) {
        this.vuePrincipale.afficherVue(SwingGUI.VUE_ETAT);
        nouvelEvt.ifPresentOrElse(
                infos -> this.controleur.creerEvenement(infos),
                () -> this.vueEtat.setEtat("")
        );
    }

//-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void demarrerInteraction() {
        this.vuePrincipale.afficher();

        // On attend que SwingGUI ait fini avant de rendre la main au contrôleur
        // (c'est à dire au moment de l'appel de `actionTerminer`)
        try {
            this.eolBarrier.await();
        }
        catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }

    @Override
    public void informerUtilisateur(final String msg, final boolean succes) {
        this.vueEtat.setEtat(msg + (succes ? " [Création ok]" : " [ÉCHEC]"));
    }

    @Override
    public void saisirUtilisateur() {
        this.vuePrincipale.afficherVue(SwingGUI.VUE_CREATION_USER);
    }

    @Override
    public void saisirNouvelEvenement(final Set<String> nomsExistants) {
        this.vueCreationEvt.setNomsExistants(nomsExistants);
        this.vuePrincipale.afficherVue(SwingGUI.VUE_CREATION_EVT);
    }
}
