package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class StockCommandesController extends CreateurPopupController {

    @FXML
    private TableView<JeuDeSociete> stocksList;

    @FXML
    private TableView<Commande> commandesList;

    @FXML
    private Button stocksAddButton, stocksModifyButton, stocksDeleteButton;

    @FXML
    private Button commandeCreateButton, commandeSetStatusButton;

    @FXML
    private void onStocksAddButtonAction(ActionEvent event) {
        try {
            creerPopup("add-to-stock.fxml", "Ajouter un jeu au stock");
            refreshStockTable();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onStocksModifyButtonAction(ActionEvent event) {
        JeuDeSociete jds = stocksList.getSelectionModel().getSelectedItem();

        if (jds != null) {
            try {
                creerPopup("add-to-stock.fxml", "Modification du jeu \"" + jds.getNom() + "\"");
                refreshStockTable();
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }

    @FXML
    private void onStocksDeleteButtonAction(ActionEvent event) {
        JeuDeSociete selectedItem = stocksList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            getControleur().supprimerJeu(selectedItem);
            refreshStockTable();
        }
    }

    @FXML
    private void onCommandeCreateButtonAction(ActionEvent event) {
        try {
            creerPopup("add-commande.fxml", "Ajouter une commande");
            refreshCommandeTable();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onCommandeSetStatusButtonAction(ActionEvent event) {
        Commande selectedItem = commandesList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            selectedItem.marquerCommeRecue();
        }
        refreshCommandeTable();
    }

    @Override
    public void refresh() {
        refreshCommandeTable();
        refreshStockTable();
    }

    private void refreshStockTable() {
        if (stocksList != null) {
            stocksList.getItems().clear();
            stocksList.getItems().addAll(getControleur().getJeux());
            stocksList.refresh();
        }
    }

    private void refreshCommandeTable() {
        Commande commande;
        Collection<Commande> commandes = getControleur().getCommandes();
        Iterator<Commande> it = commandes.iterator();
        if (commandesList != null) {
            commandesList.getItems().clear();
            while (it.hasNext()) {
                commande = it.next();
                if (!commande.estRecue()) {
                    commandesList.getItems().add(commande);
                }
            }
            commandesList.refresh();
        }
    }
}
