package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSocieteException;
import fr.uga.iut2.genevent.modele.jeu.TailleTable;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ModifierJeuController extends PopupController {

    @FXML
    private Label labelNomDuJeu;

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
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(taRegles)
                & ControllerUtilitaire.validateSpinnerValue(spNbJoueurs, 1, false)
                & ControllerUtilitaire.validateNonEmptyDatePicker(dpDateAchat)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(tfType)
                & ControllerUtilitaire.validateSpinnerValue(spDureePartie, 1, false)
                & ControllerUtilitaire.validateSpinnerValue(spPrix, 0, true)
                & ControllerUtilitaire.validateComboBoxValue(cbTailleTable)
        ) {
            try {
                getControleur().modifierJeu(
                        labelNomDuJeu.getText(),
                        new IHM.InfosJeu(
                                labelNomDuJeu.getText(),
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
            getStage().close();
        }
    }

    public void setPreremplissage(JeuDeSociete jeuDeSociete) {
        labelNomDuJeu.setText(jeuDeSociete.getNom());
        taRegles.setText(jeuDeSociete.getRegles());
        spNbJoueurs.getEditor().setText(Integer.toString(jeuDeSociete.getNbJoueurs()));

        dpDateAchat.setValue(LocalDate.ofInstant(jeuDeSociete.getDateAchat().toInstant(), ZoneId.systemDefault()));

        tfType.setText(jeuDeSociete.getType());

        int i = 0;
        cbTailleTable.getSelectionModel().select(i);
        while (!cbTailleTable.getSelectionModel().getSelectedItem().equals(jeuDeSociete.getTailleTable().getName()) && i < cbTailleTable.getItems().size()) {
            i++;
            cbTailleTable.getSelectionModel().select(i);
        }
        if (i >= cbTailleTable.getItems().size()) {
            cbTailleTable.getSelectionModel().clearSelection();
        }

        cbTailleTable.getEditor().setText(jeuDeSociete.getTailleTable().getName());
        spDureePartie.getEditor().setText(Integer.toString(jeuDeSociete.getDureePartie()));
        spPrix.getEditor().setText(Double.toString(jeuDeSociete.getPrix()).replace('.', ','));
    }
}