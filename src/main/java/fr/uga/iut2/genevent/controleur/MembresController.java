package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.Main;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class MembresController extends HeaderController {

    private Membre membreSelectionne;

    @FXML
    private TableView<Membre> memberList;

    @FXML
    private TextField memberNameField, memberPhoneNbField;

    @FXML
    private DatePicker memberBirthDateField;

    @FXML
    private Button memberModifyButton, memberDeleteButton, memberCreateButton;

    @FXML
    private void onMemberListValueClick(MouseEvent event) {
        updateSelection();
    }

    @FXML
    private void onMemberModifyButtonAction(ActionEvent event) {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();

        memberNameField.setText(selectedItem.getNom());
        memberBirthDateField.setValue(selectedItem.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        memberPhoneNbField.setText(selectedItem.getTelephone());

        memberCreateButton.setText("Modifier");
        memberCreateButton.setOnAction(this::onMemberSaveModificationsAction);
    }

    @FXML
    private void onMemberSaveModificationsAction(ActionEvent event) {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();
        if (selectedItem != null && validerEntrees()) {
            try {
                LocalDate value = memberBirthDateField.getValue();
                Date birthDate = Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());

                getControleur().creerMembre(new IHM.InfosMembre(
                        memberNameField.getText().strip(),
                        birthDate,
                        memberPhoneNbField.getText().strip()
                ));

                getControleur().modifierMembre(selectedItem, new IHM.InfosMembre(
                        memberNameField.getText().strip(),
                        birthDate,
                        memberPhoneNbField.getText().strip()
                ));

                memberNameField.clear();
                memberBirthDateField.getEditor().clear();
                memberPhoneNbField.clear();

                memberCreateButton.setText("Créer");
                memberCreateButton.setOnAction(this::addMemberButtonAction);
            } catch (MembreException e) {
                e.printStackTrace();
            }
            refreshMemberTable();
            updateSelection();
        }
    }

    @FXML
    private void onMemberDeleteButtonAction() {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            getControleur().supprimerMembre(selectedItem);
            refreshMemberTable();
            updateSelection();
        }
    }

    @FXML
    private void addMemberButtonAction(ActionEvent event) {
        boolean isValid = validerEntrees();

        if (!isValid) {
            return;
        }

        try {
            LocalDate value = memberBirthDateField.getValue();
            Date birthDate = Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());

            getControleur().creerMembre(new IHM.InfosMembre(
                    memberNameField.getText().strip(),
                    birthDate,
                    memberPhoneNbField.getText().strip()
            ));

            memberNameField.clear();
            memberPhoneNbField.clear();
            memberBirthDateField.setValue(null);
        } catch (MembreException e) {
            e.printStackTrace();
        }

        refreshMemberTable();
        updateSelection();
    }

    @Override
    public void refresh() {
        refreshMemberTable();
        updateSelection();
    }

    public void updateSelection() {
        membreSelectionne = memberList.getSelectionModel().getSelectedItem();
        memberModifyButton.setDisable(membreSelectionne == null);
        memberDeleteButton.setDisable(membreSelectionne == null);
    }

    private void refreshMemberTable() {
        memberList.getItems().clear();
        memberList.getItems().addAll(getControleur().getMembres());
        memberList.refresh();
    }

    private boolean validerEntrees() {
        boolean isValid = ControllerUtilitaire.validateNonEmptyTextInputControl(memberNameField)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(memberPhoneNbField)
                & ControllerUtilitaire.validateNonEmptyDatePicker(memberBirthDateField);

        if (!ControllerUtilitaire.matchesPattern(memberNameField.getText(), Membre.PATERNE_NOM)) {
            isValid = false;
            ControllerUtilitaire.markControlErrorStatus(memberNameField, false);
        }

        if (!ControllerUtilitaire.matchesPattern(memberPhoneNbField.getText(), Membre.PATERNE_TELEPHONE)) {
            isValid = false;
            ControllerUtilitaire.markControlErrorStatus(memberPhoneNbField, false);
        }

        return isValid;
    }
}
