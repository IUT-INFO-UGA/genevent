module genevent {
    requires commons.validator;
    requires javafx.controls;
    requires javafx.fxml;
    requires com.calendarfx.view;
    requires java.logging;

    opens fr.uga.iut2.genevent.vue to javafx.fxml;
    opens fr.uga.iut2.genevent.modele.commande to javafx.base;
    opens fr.uga.iut2.genevent.modele.membre to javafx.base;
    opens fr.uga.iut2.genevent.modele.personnel to javafx.base;
    opens fr.uga.iut2.genevent.modele.jeu to javafx.base;

    exports fr.uga.iut2.genevent;
}
