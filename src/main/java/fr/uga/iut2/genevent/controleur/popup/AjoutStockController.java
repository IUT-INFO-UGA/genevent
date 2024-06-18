package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.modele.jeu.JeuDeSocieteException;
import fr.uga.iut2.genevent.modele.jeu.TailleTable;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.ZoneId;
import java.util.Date;

public class AjoutStockController extends PopupController {

    @FXML
    private TextField tfNomDuJeu;

    @FXML
    private TextArea taRegles;

    @FXML
    private Spinner<Integer> spNbJoueurs;

    @FXML
    private DatePicker dpDateAchat;

    @FXML
    private TextField tfType;

    @FXML
    private ComboBox<String> cbTailleTable;

    @FXML
    private Spinner<Integer> spDureePartie;

    @FXML
    private Spinner<Double> spPrix;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnCancel;

    @Override
    protected void onBtnEnregistrerAction(ActionEvent event) {
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(tfNomDuJeu)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(taRegles)
                & ControllerUtilitaire.validateSpinnerValue(spNbJoueurs, 1, false)
                & ControllerUtilitaire.validateNonEmptyDatePicker(dpDateAchat)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(tfType)
                & ControllerUtilitaire.validateSpinnerValue(spDureePartie, 1, false)
                & ControllerUtilitaire.validateSpinnerValue(spPrix, 0, true)
                & ControllerUtilitaire.validateComboBoxValue(cbTailleTable)
        ) {
            try {
                getControleur().creerJeu(
                        new IHM.InfosJeu(
                                tfNomDuJeu.getText(),
                                taRegles.getText(),
                                Date.from(dpDateAchat.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant()),
                                tfType.getText(),
                                TailleTable.getByName(cbTailleTable.getValue()),
                                spDureePartie.getValue(),
                                spPrix.getValue(),
                                spNbJoueurs.getValue()
                        )
                );
            } catch (JeuDeSocieteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(e.getMessage());
                alert.showAndWait();
            }
            ((Stage) tfNomDuJeu.getScene().getWindow()).close();
        }
    }
}
