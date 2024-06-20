package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.controleur.HeaderController;
import fr.uga.iut2.genevent.modele.GenEvent;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Level;


/**
 * La classe JavaFXGUI est responsable des interactions avec
 * l'utilisa·teur/trice en mode graphique.
 * <p>
 * Attention, pour pouvoir faire le lien avec le
 * {@link fr.uga.iut2.genevent.controleur.Controleur}, JavaFXGUI n'est pas une
 * sous-classe de {@link javafx.application.Application} !
 * <p>
 * Le démarrage de l'application diffère des exemples classiques trouvés dans
 * la documentation de JavaFX : l'interface est démarrée à l'initiative du
 * {@link fr.uga.iut2.genevent.controleur.Controleur} via l'appel de la méthode
 * {@link #demarrerInteraction()}.
 */
public class JavaFXGUI extends IHM {

    private final Controleur controleur;
    private final CountDownLatch eolBarrier;  // /!\ ne pas supprimer /!\ : suivi de la durée de vie de l'interface

    public JavaFXGUI(Controleur controleur) {
        this.controleur = controleur;

        this.eolBarrier = new CountDownLatch(1);  // /!\ ne pas supprimer /!\
    }

//-----  Éléments du dialogue  -------------------------------------------------

    private void exitAction() {
        // fermeture de l'interface JavaFX : on notifie sa fin de vie
        this.eolBarrier.countDown();
    }

    // menu principal  -----

    @FXML
    private void exitMenuItemAction() {
        Platform.exit();
        this.exitAction();
    }

//-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void demarrerInteraction() {
        // démarrage de l'interface JavaFX
        Platform.startup(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
            try {
                start(primaryStage, controleur);
            }
            catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        });

        // attente de la fin de vie de l'interface JavaFX
        try {
            this.eolBarrier.await();
        }
        catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }

    /**
     * Point d'entrée principal pour le code de l'interface JavaFX.
     *
     * @param primaryStage stage principale de l'interface JavaFX, sur laquelle
     *     définir des scenes.
     *
     * @throws IOException si le chargement de la vue FXML échoue.
     *
     * @see javafx.application.Application#start(Stage)
     */
    public static void start(Stage primaryStage, Controleur controleur) throws IOException {
        loadFXML(primaryStage, controleur, "main-view.fxml");
    }

    public static void loadFXML(Stage stage, Controleur controleur, String fileName) throws IOException {
        FXMLLoader loader = new FXMLLoader(JavaFXGUI.class.getResource(fileName));
        Scene scene = new Scene(loader.load());

        HeaderController controller = loader.getController();
        controller.setControleur(controleur);
        controller.setStage(stage);
        controller.initialiserRoles();
        controller.refresh();

        URL stylesheet = JavaFXGUI.class.getResource("style.css");
        if (stylesheet == null) {
            GenEvent.logger.log(Level.CONFIG, "La feuille de style n'a pas été trouvée dans le dossier de ressources.");
        } else {
            scene.getStylesheets().add(stylesheet.toExternalForm());
        }

        stage.setResizable(false);
        stage.setTitle("GenEvent");
        stage.setScene(scene);
        stage.show();
    }

    // IMPORTANT : Pour les formulaires de création de salles, ...
//    @Override
//    public void saisirUtilisateur() {
//        try {
//            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("new-user-view.fxml"));
//            newUserViewLoader.setController(this);
//            Scene newUserScene = new Scene(newUserViewLoader.load());
//
//            Stage newUserWindow = new Stage();
//            newUserWindow.setTitle("Créer un·e utilisa·teur/trice");
//            newUserWindow.initModality(Modality.APPLICATION_MODAL);
//            newUserWindow.setScene(newUserScene);
//            newUserWindow.showAndWait();
//        } catch (IOException exc) {
//            throw new RuntimeException(exc);
//        }
//    }
}
