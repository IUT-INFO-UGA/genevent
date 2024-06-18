package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.modele.commande.CommandeException;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class CreationCommandeController extends PopupController {

    @FXML
    private TextField tfNomDuJeu;

    @FXML
    private Spinner<Integer> spQuantite;

    @FXML
    private Spinner<Double> spPrix;

    @FXML
    protected void onBtnEnregistrerAction(ActionEvent event) {
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(tfNomDuJeu)
                & ControllerUtilitaire.validateSpinnerValue(spQuantite, 1, false)
                & ControllerUtilitaire.validateSpinnerValue(spPrix, 0, true)
        ) {
            try {
                getControleur().creerCommande(new IHM.InfosCommande(getControleur().getCommandes().size(), tfNomDuJeu.getText(), spQuantite.getValue(), spPrix.getValue()));
                getStage().close();
            } catch (CommandeException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(e.getMessage());
                alert.showAndWait();
            }
        }
    }
}
