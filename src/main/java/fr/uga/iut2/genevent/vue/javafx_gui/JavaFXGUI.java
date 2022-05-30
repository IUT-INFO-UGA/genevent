package fr.uga.iut2.genevent.vue.javafx_gui;

import fr.uga.iut2.genevent.controleur.Controleur;
import fr.uga.iut2.genevent.vue.IHM;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


// JavaFXGUI n'est pas une sous-classe de javafx.application.Application pour
// pouvoir injecter le contrôleur.
// En conséquent le démarrage de l'application diffère des exemples classiques
// donnés par JavaFX.
//
// Le code de l'IHM démarre le thread JavaFX dans son constructeur et lance
// l'interface graphique dans la méthode demarrerInteraction.

public class JavaFXGUI extends IHM {

    private final Controleur controleur;
    private final CountDownLatch eolBarrier;

    private final MainView mainView;
    private final NewUserView newUserView;

    public JavaFXGUI(Controleur controleur) {
        this.controleur = controleur;

        // initialize GUI end-of-life barrier
        this.eolBarrier = new CountDownLatch(1);

        // start JavaFX thread now: we need it to instantiate FXML views
        try {
            final CountDownLatch startupBarrier = new CountDownLatch(1);
            Platform.startup(startupBarrier::countDown);
            startupBarrier.await();
        }
        catch (InterruptedException ex) {
            throw new RuntimeException("Unexpected exception: ", ex);
        }

        // load views from FXML definitions
        try {
            this.mainView = new MainView(this);
            this.newUserView = new NewUserView(this);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void start(Stage primaryStage) {
        Scene scene = new Scene(this.mainView);

        primaryStage.setTitle("GenEvent");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void enableSecondaryView(Node view) {
        assert view != this.mainView;
        this.mainView.enableSecondaryView(view);
    }

    private void disableSecondaryView(Node view) {
        assert view != this.mainView;
        this.mainView.disableSecondaryView(view);
    }

//-----  Éléments du dialogue  -------------------------------------------------

    protected void createUserAction() {
        this.controleur.saisirUtilisateur();
    }

    protected void exitAction() {
        // GUI reached end-of-life: release latch
        this.eolBarrier.countDown();
    }

    protected void createNewUser(Optional<InfosUtilisateur> newUser) {
        newUser.ifPresentOrElse(
                data -> this.controleur.creerUtilisateur(data),
                () -> {}
        );
        this.disableSecondaryView(this.newUserView);
    }

//-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void demarrerInteraction() {
        Platform.runLater(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
            this.start(primaryStage);
        });

        // block until the GUI reached end-of-life
        try {
            this.eolBarrier.await();
        }
        catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }

    @Override
    public void informerUtilisateur(String msg, boolean succes) {
        final Alert alert = new Alert(
                succes ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("GenEvent");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    @Override
    public void saisirUtilisateur() {
        this.enableSecondaryView(this.newUserView);
    }

    @Override
    public void saisirNouvelEvenement(Set<String> nomsExistants) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
