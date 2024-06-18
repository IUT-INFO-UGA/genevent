package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.Main;
import fr.uga.iut2.genevent.modele.Role;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.personnel.PersonnelException;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class PersonnelController extends HeaderController {

    @FXML
    private Button personnelDeleteButton;

    @FXML
    private TextField personnelLoginField, personnelFirstNameField, personnelNameField, personnelPhoneNbField;

    @FXML
    private ChoiceBox<String> personnelRankField;

    @FXML
    private TableView<Personnel> personnelList;

    @FXML
    private void addPersonnelButtonAction(ActionEvent event) {
        boolean valid = ControllerUtilitaire.validateNonEmptyTextInputControl(personnelLoginField)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(personnelFirstNameField)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(personnelNameField)
                & personnelRankField.getValue() != null;

        if (!personnelPhoneNbField.getText().isEmpty()) {
            valid &= ControllerUtilitaire.validateNonEmptyTextInputControl(personnelPhoneNbField, ControllerUtilitaire.matchesPattern(personnelPhoneNbField.getText(), Membre.PATERNE_TELEPHONE));
        }

        if (getControleur().getPersonnel(personnelLoginField.getText()) != null) {
            // TODO : erreur existe déjà
        }

        if (valid) {
            Role role = Role.getByName(personnelRankField.getValue());

            try {
                getControleur().creerPersonnel(new IHM.InfosPersonnel(
                        personnelLoginField.getText(),
                        personnelFirstNameField.getText(),
                        personnelNameField.getText(),
                        role,
                        personnelPhoneNbField.getText()
                ));
            } catch (PersonnelException e) {
                // TODO : Message d'erreur
                e.printStackTrace();
            }
        }

        refreshPersonnelTable();
    }

    private void refreshPersonnelTable() {
        personnelList.getItems().clear();
        for (Personnel personnel : getControleur().getPersonnel()) {
            personnelList.getItems().add(personnel);
        }
        personnelList.refresh();
    }

    @FXML
    private void onPersonnelListValueClick(MouseEvent event) {
        Personnel selectedItem = personnelList.getSelectionModel().getSelectedItem();

        personnelDeleteButton.setDisable(selectedItem == null);
    }

    @FXML
    private void onPersonnelDeleteButtonAction() {
        Personnel selectedItem = personnelList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            getControleur().supprimerPersonnel(selectedItem);
            refreshPersonnelTable();
        }
    }
}
