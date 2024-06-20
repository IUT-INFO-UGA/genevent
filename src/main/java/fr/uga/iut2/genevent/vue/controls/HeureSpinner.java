package fr.uga.iut2.genevent.vue.controls;

import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;

public abstract class HeureSpinner extends EditableSpinner<Integer> {

    public HeureSpinner(SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory) {
        super();
        setValueFactory(valueFactory);
        getEditor().setTextFormatter(new TextFormatter<>(ControllerUtilitaire.integerFilter));
    }
}
