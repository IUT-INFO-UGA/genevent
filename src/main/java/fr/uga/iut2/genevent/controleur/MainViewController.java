package fr.uga.iut2.genevent.controleur;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class MainViewController extends HeaderController {

    @FXML
    private Button stocks;

    @FXML
    private Button members;

    @FXML
    private Button salles;

    @FXML
    private Button planning;

    @FXML
    private Button personnels;

    @Override
    public void refresh() {
        members.setDisable(!getControleur().getRole().isAccesMembres());
        stocks.setDisable(!getControleur().getRole().isAccesStocks());
        planning.setDisable(!getControleur().getRole().isAccesPlanning());
        salles.setDisable(!getControleur().getRole().isAccesSalles());
        personnels.setDisable(!getControleur().getRole().isAccesMembres());
    }

    @FXML
    private void onMainButtonClicked(ActionEvent event) throws IOException {
        String fileName;

        if (!(event.getSource() instanceof Button)) {
            return;
        }

        Button button = (Button) event.getSource();

        switch (button.getId()) {
            case "stocks":
                fileName = "stock-commandes.fxml";
                break;
            case "members":
                fileName = "membres.fxml";
                break;
            case "salles":
                fileName = "salles-tables.fxml";
                break;
            case "planning":
                fileName = "planning.fxml";
                break;
            case "personnels":
                fileName = "personnels.fxml";
                break;
            default:
                return;
        }

        loadFXML(fileName);
    }
}
