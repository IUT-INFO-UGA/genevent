package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.controleur.popup.ModifierJeuController;
import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class StockCommandesController extends CreateurPopupController {

    private JeuDeSociete jeuSelectionne;
    private Commande commandeSelectionnee;

    @FXML
    private TableView<JeuDeSociete> stocksList;

    @FXML
    private TableView<Commande> commandesList;

    @FXML
    private Button stocksAddButton, stocksModifyButton, stocksDeleteButton;

    @FXML
    private Button commandeCreateButton, commandeSetStatusButton;

    @FXML
    private void onSelection(MouseEvent event) {
        updateSelection();
    }

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
        JeuDeSociete jeuDeSociete = jeuSelectionne;
        if (jeuDeSociete != null) {
            try {
                genererPopup("modifier-jeu.fxml", "Modification du jeu \"" + jeuDeSociete.getNom() + "\"");
                initialiserPopup();
                ModifierJeuController modifierJeuController = (ModifierJeuController) getPopupController();
                modifierJeuController.setPreremplissage(jeuDeSociete);
                afficherPopup();
                refreshStockTable();
                updateSelection();
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
            updateSelection();
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
        updateSelection();
    }

    @Override
    public void refresh() {
        updateSelection();
        refreshCommandeTable();
        refreshStockTable();
    }

    private void updateSelection() {
        jeuSelectionne = stocksList.getSelectionModel().getSelectedItem();
        stocksModifyButton.setDisable(jeuSelectionne == null);
        stocksDeleteButton.setDisable(jeuSelectionne == null);

        commandeSelectionnee = commandesList.getSelectionModel().getSelectedItem();
        commandeSetStatusButton.setDisable(commandeSelectionnee == null);
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
