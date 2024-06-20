package fr.uga.iut2.genevent.vue.controls;

import javafx.scene.control.SpinnerValueFactory;

public class HeureDebutSpinner extends HeureSpinner {

    public final static SpinnerValueFactory.IntegerSpinnerValueFactory VALUE_FACTORY = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0, 1);

    public HeureDebutSpinner() {
        super(VALUE_FACTORY);
    }
}
