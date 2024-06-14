package fr.uga.iut2.genevent.modele.salles;

import java.util.HashMap;

public class Salle {
    private final long numero;
    private final String type;
    private final HashMap<Long, Table> tables;

    public Salle(long numero, String type) {
        this.numero = numero;
        this.type = type;
        this.tables = new HashMap<>();
    }

    public long getNumero() {
        return numero;
    }

    public String getType() {
        return type;
    }
}
