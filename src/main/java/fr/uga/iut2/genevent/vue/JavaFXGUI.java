package fr.uga.iut2.genevent.vue;

import com.calendarfx.view.CalendarView;
import fr.uga.iut2.genevent.controleur.Controleur;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fr.uga.iut2.genevent.modele.commande.Commande;
import fr.uga.iut2.genevent.modele.jeu.JeuDeSociete;
import fr.uga.iut2.genevent.modele.membre.Membre;
import fr.uga.iut2.genevent.modele.membre.MembreException;
import fr.uga.iut2.genevent.modele.salles.Salle;
import fr.uga.iut2.genevent.modele.salles.Table;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.apache.commons.validator.routines.EmailValidator;


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
    
    @FXML
    public TextField tfNomDuJeu;
    @FXML
    public TextArea taRegles;
    @FXML
    public Spinner spNbJoueurs;
    @FXML
    public DatePicker dpDateAchat;
    @FXML
    public ComboBox cbType;
    @FXML
    public Spinner spTailleTable;
    @FXML
    public Spinner spDureePartie;
    @FXML
    public Spinner spPrix;
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

    // initialisation

    @FXML
    private void initialize() {
        if (memberList != null) {
            refreshMemberTable();
        }

        if (sallesList != null) {
            sallesList.getItems().addAll(controleur.getSalles());
            sallesList.refresh();
        }

        if (stocksList != null) {
            // stocksList.getItems().addAll(controleur.getJeux());
            stocksList.refresh();
        }

        if (commandesList != null) {
            // commandesList.getItems().addAll(controleur.getCommandes());
            commandesList.refresh();
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
        boolean isValid = validateNonEmptyTextField(memberNameField)
                & validateNonEmptyTextField(memberPhoneNbField)
                & validateNonEmptyDatePicker(memberBirthDateField);

        if (!matchesPattern(memberNameField.getText(), Membre.PATERNE_NOM)) {
            isValid = false;
            markTextFieldErrorStatus(memberNameField, false);
        }

        if (!matchesPattern(memberPhoneNbField.getText(), Membre.PATERNE_TELEPHONE)) {
            isValid = false;
            markTextFieldErrorStatus(memberPhoneNbField, false);
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
        memberList.getItems().addAll(new ArrayList<>(controleur.getMembres()));
        memberList.refresh();
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

        controleur.supprimerMembre(selectedItem);
        refreshMemberTable();
    }

    // vue salles

    @FXML
    private TableView<Salle> sallesList;

    @FXML
    private TableView<Table> tablesList;

    @FXML
    private void onSalleSelected(MouseEvent event) {
        Salle salle = sallesList.getSelectionModel().getSelectedItem();

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

            Stage newUserWindow = new Stage();
            newUserWindow.setTitle("Ajouter un jeu au stock");
            newUserWindow.initModality(Modality.WINDOW_MODAL);
            newUserWindow.setScene(newUserScene);
            newUserWindow.showAndWait();
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
        // ...
    }

    @FXML
    private void onCommandeCreateButtonAction(ActionEvent event) {
        // ...
    }

    @FXML
    private void onCommandeSetStatusButtonAction(ActionEvent event) {
        // ...
    }

    // vue planning

    @FXML
    private CalendarView planningView;

    @FXML
    private DatePicker planningDatePicker;

    @FXML
    private TextField planningNameField, planningStartHourField, planningEndHourField;

    @FXML
    private void onCreateEventButtonAction() {
        // ...
    }

    private void refreshPlanningView() {
        planningView.getCalendarSources().clear();
        // ...
    }

    // -------------------------------

    private static void markTextFieldErrorStatus(TextField textField, boolean isValid) {
        if (isValid) {
            textField.setStyle(null);
        } else {
            textField.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    private static boolean validateNonEmptyTextField(TextField textField) {
        boolean isValid = !textField.getText().strip().isEmpty();

        markTextFieldErrorStatus(textField, isValid);

        return isValid;
    }

    private static void markDatePickerErrorStatus(DatePicker datePicker, boolean isValid) {
        if (isValid) {
            datePicker.setStyle(null);
        } else {
            datePicker.setStyle("-fx-control-inner-background: f8d7da");
        }
    }

    private static boolean validateNonEmptyDatePicker(DatePicker datePicker) {
        boolean isValid = datePicker.getValue() != null;

        markDatePickerErrorStatus(datePicker, isValid);

        return isValid;
    }

    private static boolean validateEmailTextField(TextField textField) {
        EmailValidator validator = EmailValidator.getInstance(false, false);
        boolean isValid = validator.isValid(textField.getText().strip().toLowerCase());

        markTextFieldErrorStatus(textField, isValid);

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
