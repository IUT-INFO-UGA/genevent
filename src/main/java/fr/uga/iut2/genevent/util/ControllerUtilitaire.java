package fr.uga.iut2.genevent.util;

import javafx.scene.control.*;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.function.UnaryOperator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerUtilitaire {

    public static final UnaryOperator<TextFormatter.Change> integerFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("^[0-9]*$")) {
            return change;
        }
        return null;
    };

    public static final UnaryOperator<TextFormatter.Change> doubleFilter = change -> {
        String newText = change.getControlNewText();
        if (newText.matches("^([0-9]+,[0-9]+)|([0-9]+)$")) {
            return change;
        }
        return null;
    };

    public static boolean matchesPattern(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    public static void markControlErrorStatus(Control control, boolean isValid) {
        if (isValid) {
            control.setStyle(null);
        } else {
            control.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    public static boolean validateSpinnerValue(Spinner<? extends Number> spinner, int min, boolean strict) {
        double spinnerValue = spinner.getValue().doubleValue();
        if (spinnerValue > min || (!strict && spinnerValue == min)) {
            markControlErrorStatus(spinner, true);
            return true;
        } else {
            markControlErrorStatus(spinner, false);
            return false;
        }
    }

    public static boolean validateComboBoxValue(ComboBox<String> comboBox) {
        String value = comboBox.getValue();
        if (value != null) {
            markControlErrorStatus(comboBox, true);
            return true;
        } else {
            markControlErrorStatus(comboBox, false);
            return false;
        }
    }

    public static boolean validateNonEmptyTextInputControl(TextInputControl textInputControl) {
        boolean isValid = !textInputControl.getText().strip().isEmpty();

        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

    public static boolean validateNonEmptyTextInputControl(TextInputControl textInputControl, boolean isValid) {
        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

    public static boolean validateNonEmptyDatePicker(DatePicker datePicker) {
        boolean isValid = datePicker.getValue() != null;

        markControlErrorStatus(datePicker, isValid);

        return isValid;
    }

    public static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean validateEmailTextField(TextInputControl textInputControl) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textInputControl.getText().strip().toLowerCase());

        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

    public static void openAlert(String message, boolean isSuccess) {
        final Alert alert = new Alert(
                isSuccess ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("DashBoardGame");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
