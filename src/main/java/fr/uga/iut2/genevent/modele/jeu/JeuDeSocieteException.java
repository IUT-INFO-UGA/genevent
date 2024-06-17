package fr.uga.iut2.genevent.modele.jeu;

public class JeuDeSocieteException extends Exception {

    private String message;

    public JeuDeSocieteException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + ": " + super.getMessage();
    }
}
