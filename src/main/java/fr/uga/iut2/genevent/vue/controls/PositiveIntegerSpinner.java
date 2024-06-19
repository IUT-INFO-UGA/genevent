package fr.uga.iut2.genevent.vue.controls;

import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public class PositiveIntegerSpinner extends CustomSpinner<Integer> {

    public PositiveIntegerSpinner() {
        super();
        setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE));
        getEditor().setTextFormatter(new TextFormatter<>(ControllerUtilitaire.integerFilter));
    }
}
