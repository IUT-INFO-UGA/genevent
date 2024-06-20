package fr.uga.iut2.genevent.vue.controls;

import javafx.scene.control.SpinnerValueFactory;

public class HeureFinSpinner extends HeureSpinner {

    public final static SpinnerValueFactory.IntegerSpinnerValueFactory VALUE_FACTORY = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, 1, 1);

    public HeureFinSpinner() {
        super(VALUE_FACTORY);
    }
}
