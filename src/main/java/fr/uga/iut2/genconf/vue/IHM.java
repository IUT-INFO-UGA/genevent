package fr.uga.iut2.genconf.vue;

import fr.uga.iut2.genconf.controleur.Commande;
import java.time.LocalDate;
import java.util.Set;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public abstract class IHM {

    /**
     * Affiche un message d'information à l'attention de l'utilisa·teur/trice.
     *
     * @param msg Le message à afficher.
     *
     * @param succes true si le message informe d'une opération réussie, false
     *     sinon.
     */
    public abstract void informerUtilisateur(final String msg, final boolean succes);

    /**
     * Lit une {@link Commande}.
     *
     * @return La {@link Commande} saisie par l'utilisa·teur/trice.
     */
    public abstract Commande lireCommande();

    /**
     * Récupère les informations à propos d'un
     * {@link fr.uga.iut2.genconf.modele.Utilisateur}.
     *
     * @return Le conteneur {@link InfosUtilisateur} contenant les informations
     *     lues.
     */
    public abstract InfosUtilisateur saisirUtilisateur();

    /**
     * Classe conteneur pour les informations saisies à propos d'un
     * {@link fr.uga.iut2.genconf.modele.Utilisateur}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosUtilisateur {
        public final String email;
        public final String nom;
        public final String prenom;

        public InfosUtilisateur(final String email, final String nom, final String prenom) {
            this.email = email;
            this.nom = nom;
            this.prenom = prenom;
        }
    }

    /**
     * Récupère les informations nécessaires à la création d'une nouvelle
     * {@link fr.uga.iut2.genconf.modele.Conference}.
     *
     * @param nomsExistants L'ensemble des noms de conférences qui ne sont plus
     *     disponibles.
     *
     * @return Le conteneur {@link InfosNouvelleConference} contenant les
     *     informations lues.
     */
    public abstract InfosNouvelleConference saisirNouvelleConference(final Set<String> nomsExistants);

    /**
     * Classe conteneur pour les informations saisies pour une nouvelle
     * {@link fr.uga.iut2.genconf.modele.Conference}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosNouvelleConference {
        public final String nom;
        public final LocalDate dateDebut;
        public final LocalDate dateFin;
        public final InfosUtilisateur admin;

        public InfosNouvelleConference(final String nom, final LocalDate dateDebut, final LocalDate dateFin, final InfosUtilisateur admin) {
            assert !dateDebut.isAfter(dateFin);
            this.nom = nom;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.admin = admin;
        }
    }
}
