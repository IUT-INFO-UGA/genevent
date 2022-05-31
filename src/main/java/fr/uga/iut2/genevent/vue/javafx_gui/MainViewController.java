package fr.uga.iut2.genevent.vue.javafx_gui;

import javafx.application.Platform;
import javafx.fxml.FXML;

public class MainViewController {

    private final JavaFXGUI gui;

    public MainViewController(JavaFXGUI gui) {
        this.gui = gui;
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