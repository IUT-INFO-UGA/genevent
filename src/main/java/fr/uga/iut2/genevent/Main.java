package fr.uga.iut2.genevent;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.modele.GenEvent;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.util.Persisteur;

import java.io.*;
import java.util.logging.Level;


public class Main {

    public static final int EXIT_ERR_LOAD = 2;
    public static final int EXIT_ERR_SAVE = 3;

    public static void main(String[] args) {
        GenEvent genevent = null;

        try {
            genevent = Persisteur.lireEtat();
        } catch (ClassNotFoundException | IOException ignored) {
            GenEvent.logger.log(Level.SEVERE, "Erreur irrécupérable pendant le chargement de l'état : fin d'exécution !");
            System.exit(EXIT_ERR_LOAD);
        }

        File fichierIdentifiants = new File("persistence/id.txt");
        if (fichierIdentifiants.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fichierIdentifiants)));
                Salle.chargerIdentifiant(reader);
                reader.close();
            } catch (IOException e) {
                GenEvent.logger.log(Level.SEVERE, "Fichier de persistence des identifiants est existant mais impossible à lire.");
                System.exit(EXIT_ERR_LOAD);
            }
        }

        Controleur controleur = new Controleur(genevent);
        // `Controleur.demarrer` garde le contrôle de l'exécution tant que
        // l'utilisa·teur/trice n'a pas saisi la commande QUITTER.
        controleur.demarrer();

        try {
            Persisteur.sauverEtat(genevent);
        }
        catch (IOException ignored) {
            System.err.println("Erreur irrécupérable pendant la sauvegarde de l'état : fin d'exécution !");
            System.err.flush();
            System.exit(EXIT_ERR_SAVE);
        }

        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fichierIdentifiants)));
            Salle.enregistrerIdentifiant(writer);
            writer.close();
        } catch (IOException e) {
            GenEvent.logger.log(Level.SEVERE, "Impossible d'enregistrer les identifiants dans le fichier de persistence des identifiants.");
            System.exit(EXIT_ERR_SAVE);
        }
    }
}
