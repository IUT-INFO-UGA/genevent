package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.controleur.popup.CreationTableController;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class SallesTablesController extends CreateurPopupController {

    private Salle salleSelectionnee;

    @FXML
    private TableView<Salle> sallesList;

    @FXML
    private TableView<Table> tablesList;

    @FXML
    private Button tableCreateButton, tableDeleteButton;

    @FXML
    public TextField tfType;

    @FXML
    private void onSalleSelected(MouseEvent event) {
        salleSelectionnee = sallesList.getSelectionModel().getSelectedItem();

        tableCreateButton.setDisable(salleSelectionnee == null);
        tableDeleteButton.setDisable(salleSelectionnee == null);

        refreshTablesTable(salleSelectionnee);
    }

    @FXML
    private void onCreerSalleAction(ActionEvent event) {
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(tfType)) {
            getControleur().creerSalle(new IHM.InfosSalle(Salle.genererIdentifiant(), tfType.getText()));
            refreshSallesTable();
        }
    }

    @FXML
    private void onSupprimerSalleAction(ActionEvent event) {
        Salle selectedItem = sallesList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            getControleur().supprimerSalle(selectedItem);
            refreshSallesTable();
        }
    }

    @FXML
    private void onCreerTableAction(ActionEvent event) {
        try {
            Salle salleSelectionneePointeur = salleSelectionnee;
            genererPopup("creer-table.fxml", "Ajouter une table");
            CreationTableController popup = (CreationTableController) getPopupController();
            popup.setSalleSelectionnee(salleSelectionneePointeur);
            initialiserPopup();
            afficherPopup();
            refreshTablesTable(salleSelectionnee);
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onSupprimerTableAction(ActionEvent event) {
        Table selectedItem = tablesList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.getSalle().removeTable(selectedItem);
        }
        refreshTablesTable(salleSelectionnee);
    }

    @Override
    public void refresh() {
        refreshSallesTable();
        refreshTablesTable(salleSelectionnee);
    }

    private void refreshSallesTable() {
        sallesList.getItems().clear();
        sallesList.getItems().addAll(getControleur().getSalles());
        sallesList.refresh();
    }

    private void refreshTablesTable(Salle salle) {
        tablesList.getItems().clear();
        if (salle != null) {
            tablesList.getItems().addAll(salle.getTables().values());
        }
        tablesList.refresh();
    }
}
