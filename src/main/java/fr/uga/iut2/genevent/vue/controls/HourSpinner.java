package fr.uga.iut2.genevent.vue.controls;

import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public class HourSpinner extends EditableSpinner<Integer> {

    public HourSpinner() {
        super();
        setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 24, 0, 1));
        getEditor().setTextFormatter(new TextFormatter<>(ControllerUtilitaire.integerFilter));
    }
}
