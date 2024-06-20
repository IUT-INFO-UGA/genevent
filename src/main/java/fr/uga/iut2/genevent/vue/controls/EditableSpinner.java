package fr.uga.iut2.genevent.vue.controls;

import javafx.scene.control.Spinner;

public class EditableSpinner<T> extends Spinner<T> {

    public EditableSpinner() {
        super();
        setEditable(true);
    }
}
