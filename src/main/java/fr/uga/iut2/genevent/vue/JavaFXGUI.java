package fr.uga.iut2.genevent.vue;

import com.calendarfx.model.Entry;
import com.calendarfx.view.CalendarView;
import fr.uga.iut2.genevent.controleur.Controleur;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.uga.iut2.genevent.modele.Role;
import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSocieteException;
import fr.uga.iut2.genevent.modele.jeu.TailleTable;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.modele.personnel.Animateur;
import fr.uga.iut2.genevent.modele.personnel.Gestionnaire;
import fr.uga.iut2.genevent.modele.personnel.Personnel;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import fr.uga.iut2.genevent.modele.seance.Seance;
import fr.uga.iut2.genevent.modele.seance.SeanceException;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.StringConverter;
import org.apache.commons.validator.routines.EmailValidator;
import org.controlsfx.control.CheckComboBox;


/**
 * La classe JavaFXGUI est responsable des interactions avec
 * l'utilisa·teur/trice en mode graphique.
 * <p>
 * Attention, pour pouvoir faire le lien avec le
 * {@link fr.uga.iut2.genevent.controleur.Controleur}, JavaFXGUI n'est pas une
 * sous-classe de {@link javafx.application.Application} !
 * <p>
 * Le démarrage de l'application diffère des exemples classiques trouvés dans
 * la documentation de JavaFX : l'interface est démarrée à l'initiative du
 * {@link fr.uga.iut2.genevent.controleur.Controleur} via l'appel de la méthode
 * {@link #demarrerInteraction()}.
 */
public class JavaFXGUI extends IHM {

    private final Controleur controleur;
    private final CountDownLatch eolBarrier;  // /!\ ne pas supprimer /!\ : suivi de la durée de vie de l'interface

    private Stage newJeuWindow;
    
    @FXML
    public TextField tfNomDuJeu;
    @FXML
    public TextArea taRegles;
    @FXML
    public Spinner<Integer> spNbJoueurs;
    @FXML
    public DatePicker dpDateAchat;
    @FXML
    public TextField tfType;
    @FXML
    public ComboBox<String> cbTailleTable;
    @FXML
    public Spinner<Integer> spDureePartie;
    @FXML
    public Spinner<Double> spPrix;
    @FXML
    public Button btnEnregistrer;
    @FXML
    public Button btnCancel;

    @FXML
    private Button stocks, members, salles, planning;

    public JavaFXGUI(Controleur controleur) {
        this.controleur = controleur;

        this.eolBarrier = new CountDownLatch(1);  // /!\ ne pas supprimer /!\
    }

    /**
     * Point d'entrée principal pour le code de l'interface JavaFX.
     *
     * @param primaryStage stage principale de l'interface JavaFX, sur laquelle
     *     définir des scenes.
     *
     * @throws IOException si le chargement de la vue FXML échoue.
     *
     * @see javafx.application.Application#start(Stage)
     */
    private void start(Stage primaryStage) throws IOException {
        FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
        mainViewLoader.setController(this);
        Scene mainScene = new Scene(mainViewLoader.load());

        primaryStage.setTitle("GenEvent");
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

//-----  Éléments du dialogue  -------------------------------------------------

    private void exitAction() {
        // fermeture de l'interface JavaFX : on notifie sa fin de vie
        this.eolBarrier.countDown();
    }

    // menu principal  -----

    @FXML
    private void exitMenuItemAction() {
        Platform.exit();
        this.exitAction();
    }

    // en-tête

    @FXML
    private void onHeaderButtonClick(MouseEvent event) throws IOException {
        if (event.getSource() instanceof ImageView) {
            ImageView button = (ImageView) event.getSource();
            if (button.getId().equals("logo")) {
                FXMLLoader mainViewLoader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                mainViewLoader.setController(this);
                Scene mainScene = new Scene(mainViewLoader.load());

                Stage stage = (Stage) button.getScene().getWindow();
                stage.setScene(mainScene);
                stage.show();
            }
        }
    }

    @FXML
    private ChoiceBox<String> rolesList;

    @FXML
    private void onRoleButtonClick(MouseEvent event) throws IOException {
        String value = rolesList.getValue();

        System.out.println(value);

        if (value == null) {
            return;
        }

        Role role = Role.getByName(value);
        System.out.println(role);

        if (controleur.getRole() == role) {
            return;
        }

        System.out.println("Mise à jour rôle");
        controleur.setRole(role);
        start(((Stage) rolesList.getScene().getWindow()));
    }

    // initialisation

    @FXML
    private void initialize() {
        rolesList.getItems().clear();

        for (Role role : Role.values()) {
            rolesList.getItems().add(role.getName());
        }

        rolesList.setValue(controleur.getRole().getName());

        if (members != null) {
            members.setDisable(!controleur.getRole().isAccesMembres());
            stocks.setDisable(!controleur.getRole().isAccesStocks());
            planning.setDisable(!controleur.getRole().isAccesPlanning());
            salles.setDisable(!controleur.getRole().isAccesSalles());
        }

        if (memberList != null) {
            refreshMemberTable();
        }

        if (sallesList != null) {
            sallesList.getItems().addAll(controleur.getSalles());
            sallesList.refresh();
        }

        refreshPlanningView();

        refreshTableStock();

        if (commandesList != null) {
            commandesList.getItems().addAll(controleur.getCommandes());
            commandesList.refresh();
        }

        if (jeuxList != null) {
            jeuxList.getItems().clear();
            for (JeuDeSociete jeux : controleur.getJeux()) {
                jeuxList.getItems().add(jeux.getNom());
            }
        }

        if (animateursList != null) {
            animateursList.getItems().clear();

            for (Personnel personnel : controleur.getPersonnel()) {
                if (personnel instanceof Animateur) {
                    animateursList.getItems().add(personnel.getId());
                }
            }
        }
    }

    // vue accueil

    @FXML
    private void onMainButtonClicked(ActionEvent event) throws IOException {
        FXMLLoader loader;

        if (!(event.getSource() instanceof Button)) {
            return;
        }

        Button button = (Button) event.getSource();

        switch (button.getId()) {
            case "stocks":
                loader = new FXMLLoader(getClass().getResource("stock-commandes.fxml"));
                break;
            case "members":
                loader = new FXMLLoader(getClass().getResource("membres.fxml"));
                break;
            case "salles":
                loader = new FXMLLoader(getClass().getResource("salles-tables.fxml"));
                break;
            case "planning":
                loader = new FXMLLoader(getClass().getResource("planning.fxml"));
                break;
            default:
                return;
        }

        loader.setController(this);
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) button.getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }


    // Vue membres

    @FXML
    private TableView<Membre> memberList;

    @FXML
    private TextField memberNameField, memberPhoneNbField;

    @FXML
    private DatePicker memberBirthDateField;

    @FXML
    private void addMemberButtonAction() {
        boolean isValid = validateNonEmptyTextInputControl(memberNameField)
                & validateNonEmptyTextInputControl(memberPhoneNbField)
                & validateNonEmptyDatePicker(memberBirthDateField);

        if (!matchesPattern(memberNameField.getText(), Membre.PATERNE_NOM)) {
            isValid = false;
            markControlErrorStatus(memberNameField, false);
        }

        if (!matchesPattern(memberPhoneNbField.getText(), Membre.PATERNE_TELEPHONE)) {
            isValid = false;
            markControlErrorStatus(memberPhoneNbField, false);
        }

        if (!isValid) {
            return;
        }

        try {
            LocalDate value = memberBirthDateField.getValue();
            Date birthDate = Date.from(value.atStartOfDay(ZoneId.systemDefault()).toInstant());

            controleur.creerMembre(new IHM.InfosMembre(
                    memberNameField.getText().strip(),
                    birthDate,
                    memberPhoneNbField.getText().strip()
            ));

            memberNameField.clear();
            memberPhoneNbField.clear();
            memberBirthDateField.setValue(null);
        } catch (MembreException e) {
            isValid = false;
            e.printStackTrace();
        }

        refreshMemberTable();
    }

    private void refreshMemberTable() {
        memberList.getItems().clear();
        memberList.getItems().addAll(controleur.getMembres());
        memberList.refresh();
    }

    private void refreshTableStock() {
        if (stocksList != null) {
            stocksList.getItems().clear();
            stocksList.getItems().addAll(controleur.getJeux());
            stocksList.refresh();
        }
    }

    @FXML
    private Button memberModifyButton, memberDeleteButton;

    @FXML
    private void onMemberListValueClick(MouseEvent event) {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();

        memberModifyButton.setDisable(selectedItem == null);
        memberDeleteButton.setDisable(selectedItem == null);
    }

    @FXML
    private void onMemberModifyButtonAction() {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();

        memberNameField.setText(selectedItem.getNom());
        memberPhoneNbField.setText(selectedItem.getTelephone());
        memberBirthDateField.setValue(selectedItem.getDateNaissance().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());

        // TODO: implémenter la modification
    }

    @FXML
    private void onMemberDeleteButtonAction() {
        Membre selectedItem = memberList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            controleur.supprimerMembre(selectedItem);
            refreshMemberTable();
        }
    }

    // vue salles

    @FXML
    private TableView<Salle> sallesList;

    @FXML
    private TableView<Table> tablesList;

    @FXML
    private Button tableCreateButton, tableDeleteButton;

    @FXML
    private void onSalleSelected(MouseEvent event) {
        Salle salle = sallesList.getSelectionModel().getSelectedItem();
        tableCreateButton.setDisable(salle == null);
        tableDeleteButton.setDisable(salle == null);

        if (salle != null) {
            tablesList.getItems().clear();
            tablesList.getItems().addAll(new ArrayList<>(salle.getTables().values()));
            tablesList.refresh();
        }
    }

    // vue stocks

    @FXML
    private TableView<JeuDeSociete> stocksList;

    @FXML
    private TableView<Commande> commandesList;

    @FXML
    private Button stocksAddButton, stocksModifyButton, stocksDeleteButton;

    @FXML
    private Button commandeCreateButton, commandeSetStatusButton;

    @FXML
    private void onStocksAddButtonAction(ActionEvent event) {
        try {
            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("add-to-stock.fxml"));
            newUserViewLoader.setController(this);
            Scene newUserScene = new Scene(newUserViewLoader.load());

            newJeuWindow = new Stage();
            newJeuWindow.setTitle("Ajouter un jeu au stock");
            newJeuWindow.initModality(Modality.WINDOW_MODAL);
            newJeuWindow.setScene(newUserScene);
            newJeuWindow.showAndWait();
            refreshTableStock();
        } catch (IOException exc) {
            throw new RuntimeException(exc);
        }
    }

    @FXML
    private void onStocksModifyButtonAction(ActionEvent event) {
        // ...
    }

    @FXML
    private void onStocksDeleteButtonAction(ActionEvent event) {
        JeuDeSociete selectedItem = stocksList.getSelectionModel().getSelectedItem();

        if (selectedItem != null) {
            controleur.supprimerJeu(selectedItem);
            refreshTableStock();
        }
    }

    @FXML
    private void onCommandeCreateButtonAction(ActionEvent event) {
        // ...
    }

    @FXML
    private void onCommandeSetStatusButtonAction(ActionEvent event) {
        // ...
    }

    // vue ajout d'un jeu au stock

    @FXML
    private void onBtnEnregistrerAction(ActionEvent event) {
        boolean valide = true;
        if (!validateNonEmptyTextInputControl(tfNomDuJeu)) {
            valide = false;
        }
        String nomDuJeu = tfNomDuJeu.getText();

        if (!validateNonEmptyTextInputControl(taRegles)) {
            valide = false;
        }
        String regles = taRegles.getText();

        int nbJoueurs = spNbJoueurs.getValue();
        if (nbJoueurs < 1) {
            valide = false;
        }

        if (!validateNonEmptyDatePicker(dpDateAchat)) {
            valide = false;
        }
        Date date = Date.from(dpDateAchat.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        if (!validateNonEmptyTextInputControl(tfType)) {
            valide = false;
        }
        String type = tfType.getText();

        String nomTailleTable = cbTailleTable.getValue();
        TailleTable tailleTable = TailleTable.getByName(nomTailleTable);

        int dureePartie = spDureePartie.getValue();
        if (dureePartie < 1) {
            valide = false;
        }
        double prix = spPrix.getValue();
        if (prix <= 0) {
            valide = false;
        }

        if (valide) {
            try {
                controleur.creerJeu(new InfosJeu(nomDuJeu, regles, date, type, tailleTable, dureePartie, prix, nbJoueurs));
            } catch (JeuDeSocieteException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(e.getMessage());
                alert.showAndWait();
            }
        }
        newJeuWindow.close();
    }

    @FXML
    private void onBtnCancelAction(ActionEvent event) {
        newJeuWindow.close();
    }

    // vue planning

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
    private void onEventCheckClick(ActionEvent event) {
        eventBox.setVisible(eventCheckBox.isSelected());
    }

    @FXML
    private void onCreateEventButtonAction() {
        boolean valide = validateNonEmptyTextInputControl(planningNameField)
                & validateNonEmptyDatePicker(planningDatePicker)
                & validateNonEmptyTextInputControl(planningStartHourField, isNumeric(planningStartHourField.getText()))
                & validateNonEmptyTextInputControl(planningEndHourField, isNumeric(planningEndHourField.getText()));

        if (valide) {
            String type = planningNameField.getText();
            int start = Integer.parseInt(planningStartHourField.getText());
            int end = Integer.parseInt(planningEndHourField.getText());
            LocalDate date = planningDatePicker.getValue();

            ArrayList<JeuDeSociete> jeux = new ArrayList<>();

            for (String checkedItem : jeuxList.getCheckModel().getCheckedItems()) {
                jeux.add(controleur.getJeu(checkedItem));
            }

            Table selectedItem = tablesList.getSelectionModel().getSelectedItem();

            if (selectedItem == null) {
                markControlErrorStatus(tablesList, true);
                // TODO : Message d'erreur
                return;
            }

            if (eventCheckBox.isSelected()) {
                ObservableList<String> items = animateursList.getCheckModel().getCheckedItems();
                ArrayList<Animateur> personnels = new ArrayList<>();

                if (items.isEmpty()) {
                    markControlErrorStatus(animateursList, true);
                    // TODO : Message d'erreur
                    return;
                }

                for (String item : items) {
                    personnels.add(((Animateur) controleur.getPersonnel(item)));
                }

                try {
                    controleur.creerEvenement(new InfosEvenement(
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
                controleur.creerSeance(new InfosSeance(
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

                    return controleur.getSalle(Integer.parseInt(splittedName[0])).getTable(Long.parseLong(splittedName[1]));
                }
            });

            planningTableList.getItems().clear();
            for (Salle salle : controleur.getSalles()) {
                salle.getTables().forEach((id, table) -> {
                    planningTableList.getItems().add(table);
                });
            }

            planningView.getCalendarSources().clear();
            for (Seance seance : controleur.getSeances()) {
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

    // -------------------------------

    private static void markControlErrorStatus(Control control, boolean isValid) {
        if (isValid) {
            control.setStyle(null);
        } else {
            control.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    private static boolean validateNonEmptyTextInputControl(TextInputControl textInputControl) {
        boolean isValid = !textInputControl.getText().strip().isEmpty();

        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

    private static boolean validateNonEmptyTextInputControl(TextInputControl textInputControl, boolean isValid) {
        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

    private static boolean validateNonEmptyDatePicker(DatePicker datePicker) {
        boolean isValid = datePicker.getValue() != null;

        markControlErrorStatus(datePicker, isValid);

        return isValid;
    }

    private static boolean isNumeric(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean validateEmailTextField(TextInputControl textInputControl) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textInputControl.getText().strip().toLowerCase());

        markControlErrorStatus(textInputControl, isValid);

        return isValid;
    }

//-----  Implémentation des méthodes abstraites  -------------------------------

    @Override
    public void demarrerInteraction() {
        // démarrage de l'interface JavaFX
        Platform.startup(() -> {
            Stage primaryStage = new Stage();
            primaryStage.setOnCloseRequest((WindowEvent t) -> this.exitAction());
            try {
                this.start(primaryStage);
            }
            catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        });

        // attente de la fin de vie de l'interface JavaFX
        try {
            this.eolBarrier.await();
        }
        catch (InterruptedException exc) {
            System.err.println("Erreur d'exécution de l'interface.");
            System.err.flush();
        }
    }

    @Override
    public void informerUtilisateur(String msg, boolean succes) {
        final Alert alert = new Alert(
                succes ? Alert.AlertType.INFORMATION : Alert.AlertType.WARNING
        );
        alert.setTitle("DashBoardGame");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private static boolean matchesPattern(String text, Pattern pattern) {
        Matcher matcher = pattern.matcher(text);

        return matcher.find();
    }

    // IMPORTANT : Pour les formulaires de création de salles, ...
//    @Override
//    public void saisirUtilisateur() {
//        try {
//            FXMLLoader newUserViewLoader = new FXMLLoader(getClass().getResource("new-user-view.fxml"));
//            newUserViewLoader.setController(this);
//            Scene newUserScene = new Scene(newUserViewLoader.load());
//
//            Stage newUserWindow = new Stage();
//            newUserWindow.setTitle("Créer un·e utilisa·teur/trice");
//            newUserWindow.initModality(Modality.APPLICATION_MODAL);
//            newUserWindow.setScene(newUserScene);
//            newUserWindow.showAndWait();
//        } catch (IOException exc) {
//            throw new RuntimeException(exc);
//        }
//    }
}
