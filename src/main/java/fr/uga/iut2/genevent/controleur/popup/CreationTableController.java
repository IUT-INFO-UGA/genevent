package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.modele.jeu.TailleTable;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class CreationTableController extends PopupController {

    private Salle salleSelectionnee;

    @FXML
    private Spinner<Integer> spNbJoueurs;

    @FXML
    private TextField tfType;

    @FXML
    private ComboBox<String> cbTailleTable;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnCancel;

    @FXML
    protected void onBtnEnregistrerAction(ActionEvent event) {
        if (getSalleSelectionnee() == null) {
            getStage().close();
            return;
        }
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(tfType)
                & ControllerUtilitaire.validateSpinnerValue(spNbJoueurs, 1, false)
                & ControllerUtilitaire.validateComboBoxValue(cbTailleTable)
        ) {
            System.out.println("hello ??");
            getControleur().creerTable(
                    new IHM.InfosTable(
                            getSalleSelectionnee().getTables().size(),
                            getSalleSelectionnee(),
                            tfType.getText(),
                            spNbJoueurs.getValue(),
                            TailleTable.getByName(cbTailleTable.getValue())
                    )
            );
            getStage().close();
        }
    }

    public Salle getSalleSelectionnee() {
        return salleSelectionnee;
    }

    public void setSalleSelectionnee(Salle salleSelectionnee) {
        this.salleSelectionnee = salleSelectionnee;
    }
}
