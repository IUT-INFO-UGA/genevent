/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.iut2.genconf.vue;

import fr.uga.iut2.genconf.controleur.Commande;
import fr.uga.iut2.genconf.controleur.ControleurGUI;
import java.util.Set;

/**
 *
 * @author laurillau
 */
public class IHMGenConf extends IHM {
    private static final String VUE_ETAT = "creation_etat";
    private static final String VUE_CREATION_CONF = "creation_conf";
    private static final String VUE_CREATION_USER = "creation_user";

    private final ControleurGUI controleur;
    private VuePrincipale vuePrincipale;
    private VueCreationConference vueCreationConf;
    private VueCreationUtilisateur vueCreationUser;
    private VueEtat vueEtat;

    public IHMGenConf(ControleurGUI controleur) {
        this.controleur = controleur;

        creerInterface();
    }

    private void creerInterface() {
        vueEtat = new VueEtat(this);
        vueCreationConf = new VueCreationConference(this);
        vueCreationUser = new VueCreationUtilisateur(this);

        vuePrincipale = new VuePrincipale(this);
        vuePrincipale.ajouterVue(vueEtat, VUE_ETAT);
        vuePrincipale.ajouterVue(vueCreationConf, VUE_CREATION_CONF);
        vuePrincipale.ajouterVue(vueCreationUser, VUE_CREATION_USER);
        vuePrincipale.afficherVue(VUE_ETAT);
    }

//-----  Elements du dialogue  -------------------------------

    protected void actionCreerConference() {
        controleur.gererDialogue(Commande.CREER_CONFERENCE);
    }

    protected void actionCreerUtilisateur() {
        controleur.gererDialogue(Commande.CREER_UTILISATEUR);
    }

    protected void actionTerminer() {
        controleur.gererDialogue(Commande.QUITTER);
    }

    protected void creerUtilisateur(InfosUtilisateur nxUser) {
        vuePrincipale.afficherVue(VUE_ETAT);

        if (nxUser != VueCreationUtilisateur.VIDE) {
            controleur.creerUtilisateur(nxUser);
        } else {
            vueEtat.setEtat("");
        }
    }

    protected void creerConference(InfosNouvelleConference nlleConf) {
        vuePrincipale.afficherVue(VUE_ETAT);
        if (nlleConf != VueCreationConference.VIDE) {
            controleur.creerConference(nlleConf);
        } else {
            vueEtat.setEtat("");
        }
    }

//-----  Implémentation des méthodes abstraites  -------------------------------
    @Override
    public void afficherInterface() {
         vuePrincipale.afficher();
    }

    @Override
    public void fermerInterface() {
        vuePrincipale.fermer();
    }

    @Override
    public void informerUtilisateur(final String msg, final boolean succes) {
        vueEtat.setEtat(msg + (succes ? " [Création ok]" : " [ECHEC]"));
    }

    @Override
    public void saisirUtilisateur() {
        vuePrincipale.afficherVue(VUE_CREATION_USER);
    }

    @Override
    public void saisirNouvelleConference(final Set<String> nomsExistants) {
        vueCreationConf.setNomsExistants(nomsExistants);
        vuePrincipale.afficherVue(VUE_CREATION_CONF);
    }
}
