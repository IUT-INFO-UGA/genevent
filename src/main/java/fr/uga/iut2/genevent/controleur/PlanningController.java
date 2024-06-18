package fr.uga.iut2.genevent.controleur;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import fr.uga.iut2.genevent.Main;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.personnel.Animateur;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.modele.seance.Seance;
import fr.uga.iut2.genevent.modele.seance.SeanceException;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;

public class PlanningController extends HeaderController {

    @FXML
    private CalendarView planningView;

    @FXML
    private DatePicker planningDatePicker;

    @FXML
    private TextField planningNameField, planningStartHourField, planningEndHourField;

    @FXML
    private VBox eventBox;

    @FXML
    private CheckBox eventCheckBox;

    @FXML
    private ChoiceBox<Table> planningTableList;

    @FXML
    private CheckComboBox<String> jeuxList, animateursList;

    @FXML
    private TableView<Table> tablesList;

    @FXML
    private void onEventCheckClick(ActionEvent event) {
        eventBox.setVisible(eventCheckBox.isSelected());
    }

    @FXML
    private void onCreateEventButtonAction() {
        boolean valide = ControllerUtilitaire.validateNonEmptyTextInputControl(planningNameField)
                & ControllerUtilitaire.validateNonEmptyDatePicker(planningDatePicker)
                & ControllerUtilitaire.validateNonEmptyTextInputControl(planningStartHourField, ControllerUtilitaire.isNumeric(planningStartHourField.getText()))
                & ControllerUtilitaire.validateNonEmptyTextInputControl(planningEndHourField, ControllerUtilitaire.isNumeric(planningEndHourField.getText()));

        if (valide) {
            String type = planningNameField.getText();
            int start = Integer.parseInt(planningStartHourField.getText());
            int end = Integer.parseInt(planningEndHourField.getText());
            LocalDate date = planningDatePicker.getValue();

            ArrayList<JeuDeSociete> jeux = new ArrayList<>();

            for (String checkedItem : jeuxList.getCheckModel().getCheckedItems()) {
                jeux.add(getControleur().getJeu(checkedItem));
            }

            Table selectedItem = tablesList.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                ControllerUtilitaire.markControlErrorStatus(tablesList, true);
                // TODO : Message d'erreur
                return;
            }

            if (eventCheckBox.isSelected()) {
                ObservableList<String> items = animateursList.getCheckModel().getCheckedItems();
                ArrayList<Animateur> personnels = new ArrayList<>();

                if (items.isEmpty()) {
                    ControllerUtilitaire.markControlErrorStatus(animateursList, true);
                    // TODO : Message d'erreur
                    return;
                }

                for (String item : items) {
                    personnels.add(((Animateur) getControleur().getPersonnel(item)));
                }

                try {
                    getControleur().creerEvenement(new IHM.InfosEvenement(
                            type,
                            Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                            selectedItem,
                            start,
                            end,
                            selectedItem.getNbPlaces(), // TODO
                            personnels
                    ));
                } catch (SeanceException e) {
                    // TODO
                }
            } else {
                getControleur().creerSeance(new IHM.InfosSeance(
                        type,
                        Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()),
                        selectedItem,
                        start,
                        end
                ));
            }
            refreshPlanningView();
        }
    }

    @Override
    public void refresh() {
        refreshPlanningView();
    }

    private void refreshPlanningView() {
        if (planningView != null) {
            planningTableList.setConverter(new StringConverter<>() {
                @Override
                public String toString(Table table) {
                    return table.getSalle().getNumero() + "-" + table.getId();
                }

                @Override
                public Table fromString(String s) {
                    String[] splittedName = s.split("-");

                    return getControleur().getSalle(Integer.parseInt(splittedName[0])).getTable(Long.parseLong(splittedName[1]));
                }
            });

            planningTableList.getItems().clear();
            for (Salle salle : getControleur().getSalles()) {
                salle.getTables().forEach((id, table) -> {
                    planningTableList.getItems().add(table);
                });
            }

            planningView.getCalendarSources().clear();
            for (Seance seance : getControleur().getSeances()) {
                ZonedDateTime d = ZonedDateTime.ofInstant(seance.getDate().toInstant(),
                        ZoneId.systemDefault());

                Entry<?> entry = planningView.createEntryAt(d);
                entry.changeStartTime(LocalTime.of(seance.getHeureDebut(), 0));
                entry.changeEndTime(LocalTime.of(seance.getHeureFin(), 0));
                entry.setTitle(seance.getType());
                entry.setLocation("Table " + seance.getTable().getSalle().getNumero() + "-" + seance.getTable().getId());
            }
        }
    }
}
