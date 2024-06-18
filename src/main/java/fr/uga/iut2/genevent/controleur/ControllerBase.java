package fr.uga.iut2.genevent.controleur;

import javafx.stage.Stage;

public class ControllerBase {

    private Controleur controleur;
    private Stage stage;

    public Controleur getControleur() {
        return controleur;
    }

    public Stage getStage() {
        return stage;
    }

    public void refresh() {}

    public void setControleur(Controleur controleur) {
        if (controleur != null && this.controleur == null) {
            this.controleur = controleur;
        }
    }

    public void setStage(Stage stage) {
        if (stage != null && this.stage == null) {
            this.stage = stage;
        }
    }
}
