package fr.uga.iut2.genevent.modele.membre;

public class MembreException extends Exception {

    private String message;

    public MembreException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + ": " + super.getMessage();
    }
}
