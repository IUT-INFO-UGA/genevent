package fr.uga.iut2.genevent.modele.personnel;

public class PersonnelException extends Exception {

    private String message;

    public PersonnelException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message + ": " + super.getMessage();
    }
}
