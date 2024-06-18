package fr.uga.iut2.genevent.modele.jeu;

import java.util.HashMap;
import java.util.Map;

public enum TailleTable {
    PETITE("Petite"),
    MOYENNE("Moyenne"),
    GRANDE("Grande");

    private static Map<String, TailleTable> BY_NAME = new HashMap<>();

    static {
        for (TailleTable value : values()) {
            BY_NAME.put(value.name, value);
        }
    }

    private String name;

    TailleTable(String name) {
        this.name = name;
    }

    public static TailleTable getByName(String name) {
        return BY_NAME.get(name);
    }

    public String getName() {
        return name;
    }
}
