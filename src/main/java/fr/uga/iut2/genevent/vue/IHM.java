package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.modele.jeu.TailleTable;
import fr.uga.iut2.genevent.modele.personnel.Animateur;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import javafx.scene.control.Tab;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public abstract class IHM {
    /**
     * Classe conteneur pour les informations saisies à propos d'un
     * {@link fr.uga.iut2.genevent.modele.Utilisateur}.
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
     * Classe conteneur pour les informations saisies pour un nouvel
     * {@link fr.uga.iut2.genevent.modele.Evenement}.
     *
     * <ul>
     * <li>Tous les attributs sont `public` par commodité d'accès.</li>
     * <li>Tous les attributs sont `final` pour ne pas être modifiables.</li>
     * </ul>
     */
    public static class InfosNouvelEvenement {
        public final String nom;
        public final LocalDate dateDebut;
        public final LocalDate dateFin;
        public final InfosUtilisateur admin;

        public InfosNouvelEvenement(final String nom, final LocalDate dateDebut, final LocalDate dateFin, final InfosUtilisateur admin) {
            assert !dateDebut.isAfter(dateFin);
            this.nom = nom;
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;
            this.admin = admin;
        }
    }

    /**
     * Rend actif l'interface Humain-machine.
     *
     * L'appel est bloquant : le contrôle est rendu à l'appelant une fois que
     * l'IHM est fermée.
     *
     */
    public abstract void demarrerInteraction();

    /**
     * Affiche un message d'information à l'attention de l'utilisa·teur/trice.
     *
     * @param msg Le message à afficher.
     *
     * @param succes true si le message informe d'une opération réussie, false
     *     sinon.
     */
    public abstract void informerUtilisateur(final String msg, final boolean succes);


    public static class InfosMembre {
        public String nom;
        public Date dateNaissance;
        public String telephone;

        public InfosMembre(String nom, Date dateNaissance, String telephone) {
            this.nom = nom;
            this.dateNaissance = dateNaissance;
            this.telephone = telephone;
        }
    }


    public static class InfosJeu {
        public String nom, regles;
        public Date dateAchat;
        public String type;
        public TailleTable taille;
        public int dureePartie;
        public double prix;
        public int nbJoueurs;

        public InfosJeu(String nom, String regles, Date dateAchat, String type, TailleTable taille, int dureePartie, double prix, int nbJoueurs) {
            this.nom = nom;
            this.regles = regles;
            this.dateAchat = dateAchat;
            this.type = type;
            this.taille = taille;
            this.dureePartie = dureePartie;
            this.prix = prix;
            this.nbJoueurs = nbJoueurs;
        }
    }

    public static class InfosCommande {
        public int numero;
        public String nomDuJeu;
        public int quantite;
        public double prix;

        public InfosCommande(int numero, String nomDuJeu, int quantite, double prix) {
            this.numero = numero;
            this.nomDuJeu = nomDuJeu;
            this.quantite = quantite;
            this.prix = prix;
        }
    }

    public static class InfosSalle {
        public long numero;
        public String type;

        public InfosSalle(long numero, String type) {
            this.numero = numero;
            this.type = type;
        }
    }

    public static class InfosTable {
        public long id;
        public Salle salle;
        public String type;
        public int nbPlaces;
        public TailleTable taille;

        public InfosTable(long id, Salle salle, String type, int nbPlaces, TailleTable taille) {
            this.id = id;
            this.salle = salle;
            this.type = type;
            this.nbPlaces = nbPlaces;
            this.taille = taille;
        }
    }

    public static class InfosSeance {
        public String type;
        public Date date;
        public Table table;
        public int heureDebut, heureFin;

        public InfosSeance(String type, Date date, Table table, int heureDebut, int heureFin) {
            this.type = type;
            this.date = date;
            this.table = table;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
        }
    }

    public static class InfosEvenement {
        public String type;
        public Date date;
        public Table table;
        public int heureDebut, heureFin, nbPlaces;
        public ArrayList<Animateur> animateurs;

        public InfosEvenement(String type, Date date, Table table, int heureDebut, int heureFin, int nbPlaces, ArrayList<Animateur> animateurs) {
            this.type = type;
            this.date = date;
            this.table = table;
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            this.nbPlaces = nbPlaces;
            this.animateurs = animateurs;
        }
    }
}
