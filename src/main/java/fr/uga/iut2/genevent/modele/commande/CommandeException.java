package fr.uga.iut2.genevent.modele.commande;

public class CommandeException extends Exception {

    private final String message;

    public CommandeException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
