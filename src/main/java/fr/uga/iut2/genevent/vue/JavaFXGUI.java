package fr.uga.iut2.genevent.vue;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.controleur.MainViewController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


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

    // initialisation

    /*
    @FXML
    private void initialize() {

        if (members != null) {
            members.setDisable(!controleur.getRole().isAccesMembres());
            stocks.setDisable(!controleur.getRole().isAccesStocks());
            planning.setDisable(!controleur.getRole().isAccesPlanning());
            salles.setDisable(!controleur.getRole().isAccesSalles());
        }

        if (jeuxList != null) {
            jeuxList.getItems().clear();
            for (JeuDeSociete jeux : controleur.getJeux()) {
                jeuxList.getItems().add(jeux.getNom());
            }
        }

        if (animateursList != null) {
            animateursList.getItems().clear();

            for (Personnel personnel : controleur.getPersonnel()) {
                if (personnel instanceof Animateur) {
                    animateursList.getItems().add(personnel.getId());
                }
            }
        }

        if (personnelRankField != null) {
            refreshPersonnelTable();

            for (Role value : Role.values()) {
                personnelRankField.getItems().add(value.getName());
            }

            TableColumn<Personnel, String> personnelTableColumn = (TableColumn<Personnel, String>) personnelList.getColumns().get(3);
            personnelTableColumn.setCellValueFactory(c -> {
                Personnel personnel = c.getValue();
                String roleName = "";

                if (personnel instanceof Gerant) {
                    roleName = "Gérant";
                } else if (personnel instanceof Gestionnaire) {
                    roleName = "Gestionnaire";
                } else if (personnel instanceof Animateur) {
                    roleName = "Animateur";
                }

                return new SimpleStringProperty(roleName);
            });
        }
    }
    */

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
        FXMLLoader mainViewLoader = new FXMLLoader(JavaFXGUI.class.getResource("main-view.fxml"));
        Scene mainScene = new Scene(mainViewLoader.load());
        MainViewController mainViewController = mainViewLoader.getController();
        mainViewController.setControleur(controleur);
        mainViewController.setStage(primaryStage);
        mainViewController.initialiserRoles();

        primaryStage.setTitle("GenEvent");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    @Override
    public void informerUtilisateur(String msg, boolean succes) {
        final Alert alert = new Alert(
                succes ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("DashBoardGame");
        alert.setContentText(msg);
        alert.showAndWait();
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
