package fr.uga.iut2.genevent.controleur.popup;

import fr.uga.iut2.genevent.controleur.ControllerBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Un type spécial de {@link ControllerBase}, qui correspond à une fenêtre pop-up.
 * Une fenêtre pop-up a obligatoirement un bouton permettant de le fermer ("Annuler"),
 * ainsi qu'un bouton permettant d'enregistrer des modifications, dont la méthode lorsque l'on
 * clique dessus est à implémenter.
 */
public abstract class PopupController extends ControllerBase {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnEnregistrer;

    /**
     * Action à exécuter lorsqu'on appuie sur le bouton permettant d'enregistrer les informations entrées
     * dans la fenêtre pop-up.
     * @param event L'évènement {@link ActionEvent} reçu.
     */
    @FXML
    protected abstract void onBtnEnregistrerAction(ActionEvent event);

    /**
     * Action exécutée lorsqu'on appuie sur le bouton pour annuler les modifications effectuées dans la fenêtre
     * pop-up. Ferme simplement la fenêtre pop-up.
     * @param event L'évènement {@link ActionEvent} reçu.
     */
    @FXML
    private void onBtnCancelAction(ActionEvent event) {
        getStage().close();
    }
}
