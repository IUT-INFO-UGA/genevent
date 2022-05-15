package fr.uga.iut2.genevent.vue.javafx_gui;

import fr.uga.iut2.genevent.vue.IHM;
import java.io.IOException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.apache.commons.validator.routines.EmailValidator;

public class NewUserView extends TitledPane {

    @FXML private TextField forenameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField emailTextField;
    @FXML private Button okButton;

    private final JavaFXGUI gui;

    public NewUserView(JavaFXGUI gui) throws IOException {
        // load definition from FXML
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("new-user-view.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.load();

        this.gui = gui;
    }

    private void reset() {
        this.forenameTextField.setStyle(null);
        this.surnameTextField.setStyle(null);
        this.emailTextField.setStyle(null);

        this.forenameTextField.setText("");
        this.surnameTextField.setText("");
        this.emailTextField.setText("");

        this.okButton.setDisable(true);
    }

    @FXML void validateTextFields() {
        boolean isValid = true;

        isValid &= validateNonEmptyTextField(this.forenameTextField);
        isValid &= validateNonEmptyTextField(this.surnameTextField);
        isValid &= validateEmailTextField(this.emailTextField);

        this.okButton.setDisable(!isValid);
    }

    private static void markTextFieldErrorStatus(TextField textField, boolean isValid) {
        if (isValid) {
            textField.setStyle(null);
        } else {
            textField.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    private static boolean validateNonEmptyTextField(TextField textField) {
        boolean isValid = textField.getText().strip().length() > 0;

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }

    private static boolean validateEmailTextField(TextField textField) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textField.getText().strip().toLowerCase());

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }

    @FXML
    private void okButtonAction() {
       IHM.InfosUtilisateur data = new IHM.InfosUtilisateur(
               this.emailTextField.getText().strip().toLowerCase(),
               this.surnameTextField.getText().strip(),
               this.forenameTextField.getText().strip()
       );
       this.gui.createNewUser(Optional.of(data));
       this.reset();
    }

    @FXML
    private void cancelButtonAction() {
        this.gui.createNewUser(Optional.empty());
        this.reset();
    }
}
