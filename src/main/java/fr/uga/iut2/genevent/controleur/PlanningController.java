package fr.uga.iut2.genevent.controleur;

import com.calendarfx.model.*;
import com.calendarfx.view.CalendarView;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.personnel.Animateur;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.modele.seance.Evenement;
import fr.uga.iut2.genevent.modele.seance.Seance;
import fr.uga.iut2.genevent.modele.seance.SeanceException;
import fr.uga.iut2.genevent.util.ControllerUtilitaire;
import fr.uga.iut2.genevent.vue.IHM;
import fr.uga.iut2.genevent.vue.controls.HeureDebutSpinner;
import fr.uga.iut2.genevent.vue.controls.HeureFinSpinner;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import org.controlsfx.control.CheckComboBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class PlanningController extends HeaderController {

    @FXML
    private CalendarView planningView;

    @FXML
    private DatePicker planningDatePicker;

    @FXML
    private TextField planningNameField;

    @FXML
    private HeureDebutSpinner planningStartHourField;

    @FXML
    private HeureFinSpinner planningEndHourField;

    @FXML
    private VBox eventBox;

    @FXML
    private CheckBox eventCheckBox;

    @FXML
    private ChoiceBox<Table> planningTableList;

    @FXML
    private CheckComboBox<String> jeuxList, animateursList;

    @FXML
    private void initialize() {
        planningNameField.clear();
        planningDatePicker.setValue(LocalDate.now());
        planningStartHourField.getEditor().setText(Integer.toString(HeureDebutSpinner.VALUE_FACTORY.getMin()));
        planningEndHourField.getEditor().setText(Integer.toString(HeureFinSpinner.VALUE_FACTORY.getMin()));
        jeuxList.getCheckModel().clearChecks();
        planningTableList.getSelectionModel().clearSelection();
        animateursList.getCheckModel().clearChecks();
        eventCheckBox.setSelected(false);
    }

    @FXML
    private void onEventCheckClick(ActionEvent event) {
        refreshAnimateurs();
    }

    @FXML
    private void onCreateEventButtonAction() {
        if (ControllerUtilitaire.validateNonEmptyTextInputControl(planningNameField)
                & ControllerUtilitaire.validateNonEmptyDatePicker(planningDatePicker)
                & 0 <= planningStartHourField.getValue() & planningStartHourField.getValue() < 24
                & 0 < planningEndHourField.getValue() & planningEndHourField.getValue() <= 24
                & planningStartHourField.getValue() < planningEndHourField.getValue()
        ) {
            String type = planningNameField.getText();
            int start = planningStartHourField.getValue();
            int end = planningEndHourField.getValue();
            LocalDate date = planningDatePicker.getValue();

            ArrayList<JeuDeSociete> jeux = new ArrayList<>();

            for (String checkedItem : jeuxList.getCheckModel().getCheckedItems()) {
                jeux.add(getControleur().getJeu(checkedItem));
            }

            Table selectedItem = planningTableList.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                ControllerUtilitaire.markControlErrorStatus(planningTableList, true);
                ControllerUtilitaire.openAlert("Veuillez choisir une table !", false);
                return;
            }

            if (eventCheckBox.isSelected()) {
                ObservableList<String> items = animateursList.getCheckModel().getCheckedItems();
                ArrayList<Animateur> personnels = new ArrayList<>();

                if (items.isEmpty()) {
                    ControllerUtilitaire.markControlErrorStatus(animateursList, true);
                    ControllerUtilitaire.openAlert("Un événement a besoin d'au moins un animateur.", false);
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
                    initialize();
                } catch (SeanceException e) {
                    ControllerUtilitaire.openAlert(e.getMessage(), false);
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
            refresh();
        }
    }

    @Override
    public void refresh() {
        refreshPlanningView();
        refreshAnimateurs();

        jeuxList.getItems().clear();
        for (JeuDeSociete jeux : getControleur().getJeux()) {
            jeuxList.getItems().add(jeux.getNom());
        }

        planningTableList.getItems().clear();
        for (Salle salle : getControleur().getSalles()) {
            salle.getTables().forEach((id, table) -> planningTableList.getItems().add(table));
        }

        planningTableList.setConverter(new StringConverter<>() {
            @Override
            public String toString(Table table) {
                if (table == null) {
                    return null;
                }

                return table.getSalle().getNumero() + "-" + table.getId();
            }

            @Override
            public Table fromString(String s) {
                String[] splittedName = s.split("-");

                return getControleur().getSalle(Integer.parseInt(splittedName[0])).getTable(Long.parseLong(splittedName[1]));
            }
        });

        animateursList.getItems().clear();

        for (Personnel personnel : getControleur().getPersonnel()) {
            if (personnel instanceof Animateur) {
                animateursList.getItems().add(personnel.getId());
            }
        }
    }

    @FXML
    private void onPlanningClick(MouseEvent event) {
        System.out.println(planningView.getDraggedEntry());
    }

    private void refreshPlanningView() {
        if (planningView != null) {
            // planningView.setEditAvailability(false);

            Calendar<String> calendar = new Calendar<>();
            calendar.addEventHandler(calendarEvent -> {
                Entry<Seance> entry = (Entry<Seance>) calendarEvent.getEntry();
                Seance seance = entry.getUserObject();

                if (calendarEvent.isEntryRemoved()) {
                    getControleur().supprimerSeance(seance);
                }
            });

            planningView.setEntryFactory(createEntryParameter -> null);
            planningView.setEditAvailability(false);

            CalendarSource source = new CalendarSource();
            source.getCalendars().add(calendar);
            // calendar.setReadOnly(false);
            planningView.setShowAddCalendarButton(false);
            planningView.setShowSourceTray(false);

            for (Seance seance : getControleur().getSeances()) {
                ZonedDateTime d = ZonedDateTime.ofInstant(seance.getDate().toInstant(),
                        ZoneId.systemDefault());

                Entry<Seance> entry = new Entry<>();
                entry.changeStartDate(d.toLocalDate());
                entry.changeEndDate(d.toLocalDate());
                entry.changeStartTime(LocalTime.of(seance.getHeureDebut(), 0));
                entry.changeEndTime(LocalTime.of(seance.getHeureFin() - 1, 59));
                entry.setTitle(seance.getType());
                entry.setUserObject(seance);

                if (seance instanceof Evenement) {
                    System.out.println(seance);
                    entry.setTitle("Év. " + entry.getTitle() + " avec " + ((Evenement) seance).getAnimateurs().stream().map(Animateur::getId).collect(Collectors.joining(", ")));
                }

                entry.setLocation("Table " + seance.getTable().getSalle().getNumero() + "-" + seance.getTable().getId());
                calendar.addEntry(entry);
            }
            planningView.getCalendarSources().add(source);
        }
    }

    private void refreshAnimateurs() {
        eventBox.setVisible(eventCheckBox.isSelected());
    }
}
