package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.controleur.popup.PopupController;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateurPopupController extends HeaderController {

    private Stage popup;
    private PopupController popupController;

    protected void genererPopup(String fileName, String title) throws IOException {
        FXMLLoader popupLoader = new FXMLLoader(JavaFXGUI.class.getResource(fileName));
        Scene popupScene = new Scene(popupLoader.load());
        popupController = popupLoader.getController();
        popup = new Stage();
        popup.setTitle(title);
        popup.setScene(popupScene);
        popup.setResizable(false);
    }

    protected void initialiserPopup() {
        popupController.setControleur(getControleur());
        popupController.setStage(popup);
        popupController.refresh();
    }

    protected void afficherPopup() {
        popup.initModality(Modality.WINDOW_MODAL);
        popup.showAndWait();
    }

    protected void creerPopup(String fileName, String title) throws IOException {
        genererPopup(fileName, title);
        initialiserPopup();
        afficherPopup();
    }

    public PopupController getPopupController() {
        return popupController;
    }
}
