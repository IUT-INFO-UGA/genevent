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

    public void addTable(Table table) {
        tables.put(table.getId(), table);
    }

    public long getNumero() {
        return numero;
    }

    public String getType() {
        return type;
    }

    public Table getTable(long id) {
        return tables.get(id);
    }

    public void removeTable(long id) {
        tables.remove(id);
    }

    public void removeTable(Table table) {
        tables.remove(table.getId());
    }
}
