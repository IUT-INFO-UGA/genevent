package fr.uga.iut2.genevent.vue.controls;

import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public class PositiveDoubleSpinner extends CustomSpinner<Double> {

    public PositiveDoubleSpinner() {
        super();
        setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(0.01, Double.MAX_VALUE, 1, 0.01));
        getEditor().setTextFormatter(new TextFormatter<>(ControllerUtilitaire.doubleFilter));
    }
}
