package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.controleur.ControllerBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public abstract class PopupController extends ControllerBase {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnEnregistrer;

    @FXML
    protected abstract void onBtnEnregistrerAction(ActionEvent event);

    @FXML
    private void onBtnCancelAction(ActionEvent event) {
        getStage().close();
    }
}
