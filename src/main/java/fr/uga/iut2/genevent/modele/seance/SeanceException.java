package fr.uga.iut2.genevent.modele.seance;

public class SeanceException extends Exception {

    private final String message;

    public SeanceException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
