package fr.uga.iut2.genevent.controleur;

import javafx.stage.Stage;

/**
 * Classe définissant la base d'un contrôleur de l'application.
 * Un contrôleur de base peut accéder à l'instance de type {@link Controleur} en cours d'utilisation,
 * au {@link Stage} dans lequel il se trouve, et possède une méthode {@link ControllerBase#refresh()}
 * qui doit être appelé lorsque certains contrôles nécessitent un rafraîchissement (comme une {@link javafx.scene.control.TableView}).
 */
public abstract class ControllerBase {

    private Controleur controleur;
    private Stage stage;

    /**
     * Récupère l'instance de {@link Controleur} en cours d'utilisation dans l'application.
     * @return L'instance courante de {@link Controleur}.
     */
    public Controleur getControleur() {
        return controleur;
    }

    /**
     * Récupère l'instance de {@link Stage} dans laquelle le contrôleur s'exécute.
     * @return L'instance courante de {@link Stage}.
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Rafraîchit le contrôleur, notamment en rafraîchissant les composants qui demandent une actualisation
     * (comme les {@link javafx.scene.control.TableView}).
     */
    public void refresh() {}

    /**
     * Remplace l'instance de {@link Controleur} en cours d'utilisation par l'instance en paramètre.
     * La nouvelle instance ne doit pas être nulle, et la valeur précédente est modifiée seulement si le contrôleur
     * n'avait déjà pas d'instance de {@link Controleur}.
     * @param controleur La nouvelle instance de {@link Controleur} à utiliser.
     */
    public void setControleur(Controleur controleur) {
        if (controleur != null && this.controleur == null) {
            this.controleur = controleur;
        }
    }

    /**
     * Remplace l'instance de {@link Stage} dans laquelle le contrôleur s'exécute par la valeur en paramètre.
     * La nouvelle instance ne doit pas être nulle et la valeur précédente doit être nulle pour qu'elle soit modifiée.
     * @param stage La nouvelle instance de {@link Stage} dans laquelle s'exécute le contrôleur.
     */
    public void setStage(Stage stage) {
        if (stage != null && this.stage == null) {
            this.stage = stage;
        }
    }
}
