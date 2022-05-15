package fr.uga.iut2.genevent.vue.javafx_gui;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MainView extends VBox {

    @FXML
    private Pane viewPane;

    private final JavaFXGUI gui;

    public MainView(JavaFXGUI gui) throws IOException {
        // load definition from FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.gui = gui;
    }

    protected void enableSecondaryView(Node view) {
        view.setDisable(false);
        this.viewPane.getChildren().setAll(view);
    }

    protected void disableSecondaryView(Node view) {
        this.viewPane.getChildren().remove(view);
        view.setDisable(true);
    }

    @FXML
    private void newUserMenuItemAction() {
        this.gui.createUserAction();
    }

    @FXML
    private void exitMenuItemAction() {
        Platform.exit();
        this.gui.exitAction();
    }
}
