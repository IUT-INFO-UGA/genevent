module genevent {
    requires commons.validator;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.calendarfx.view;

    opens fr.uga.iut2.genevent.vue to javafx.fxml;
    opens fr.uga.iut2.genevent.modele.membre to javafx.base;

    exports fr.uga.iut2.genevent;
}
