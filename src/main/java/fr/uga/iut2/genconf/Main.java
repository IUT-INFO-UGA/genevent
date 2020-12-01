package fr.uga.iut2.genconf;

import fr.uga.iut2.genconf.controleur.Controleur;
import fr.uga.iut2.genconf.modele.GenConf;
import fr.uga.iut2.genconf.util.Persisteur;
import java.io.IOException;

/**
 *
 * @author Raphaël Bleuse <raphael.bleuse@univ-grenoble-alpes.fr>
 */
public class Main {

    public static final int EXIT_ERR_LOAD = 2;
    public static final int EXIT_ERR_SAVE = 3;

    public static void main(String[] args) {
        GenConf genconf = null;

        try {
            genconf = Persisteur.lireEtat();
        }
        catch (ClassNotFoundException | IOException ignored) {
            System.err.println("Erreur irrécupérable pendant le chargement de l'état : fin d'exécution !");
            System.err.flush();
            System.exit(Main.EXIT_ERR_LOAD);
        }

        // Controleur garde le contrôle de l'exécution tant que
        // l'utilisa·teur/trice n'a pas saisi la commande QUITTER.
        new Controleur(genconf);

        try {
            Persisteur.sauverEtat(genconf);
        }
        catch (IOException ignored) {
            System.err.println("Erreur irrécupérable pendant la sauvegarde de l'état : fin d'exécution !");
            System.err.flush();
            System.exit(Main.EXIT_ERR_SAVE);
        }
    }
}
