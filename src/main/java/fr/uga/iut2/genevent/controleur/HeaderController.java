package fr.uga.iut2.genevent.controleur;

import fr.uga.iut2.genevent.modele.Role;
import fr.uga.iut2.genevent.vue.JavaFXGUI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class HeaderController extends ControllerBase {

    @FXML
    private ImageView logo;

    @FXML
    private ChoiceBox<String> rolesList;

    public void initialiserRoles() {
        rolesList.getItems().clear();

        for (Role role : Role.values()) {
            rolesList.getItems().add(role.getName());
        }

        rolesList.setValue(getControleur().getRole().getName());
    }

    @FXML
    protected void onHeaderButtonClick(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView button = (ImageView) event.getSource();
            if (button.getId().equals("logo")) {
                loadFXML("main-view.fxml", (Stage) button.getScene().getWindow());
            }
        }
    }

    @FXML
    protected void onRoleButtonClick(MouseEvent event) throws IOException {
        String value = rolesList.getValue();

        System.out.println(value);

        if (value == null) {
            return;
        }

        Role role = Role.getByName(value);
        System.out.println(role);

        if (getControleur().getRole() == role) {
            return;
        }

        System.out.println("Mise à jour rôle");
        getControleur().setRole(role);
        JavaFXGUI.start(getStage(), getControleur());
    }

    protected void loadFXML(String fileName, Stage stage) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader(JavaFXGUI.class.getResource(fileName));
        Scene mainScene = new Scene(mainViewLoader.load());
        HeaderController controller = mainViewLoader.getController();

        controller.setControleur(getControleur());
        controller.setStage(stage);
        controller.initialiserRoles();
        controller.refresh();

        stage.setScene(mainScene);
        stage.show();
    }
}
