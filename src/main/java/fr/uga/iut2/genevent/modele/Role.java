package fr.uga.iut2.genevent.modele;

import java.util.HashMap;
import java.util.Map;

public enum Role {
    GERANT("Gérant", true, true, true, true),
    GESTIONNAIRE("Gestionnaire", true, true, false, true),
    ANIMATEUR("Animateur", false, false, true, false);

    private static Map<String, Role> BY_NAME = new HashMap<>();

    static {
        for (Role role : Role.values()) {
            BY_NAME.put(role.getName(), role);
        }
    }

    private String name;
    private boolean accesStocks, accesMembres, accesPlanning, accesSalles;

    Role(String name, boolean accesStocks, boolean accesMembres, boolean accesPlanning, boolean accesSalles) {
        this.name = name;
        this.accesStocks = accesStocks;
        this.accesMembres = accesMembres;
        this.accesPlanning = accesPlanning;
        this.accesSalles = accesSalles;
    }

    public String getName() {
        return name;
    }

    public boolean isAccesStocks() {
        return accesStocks;
    }

    public boolean isAccesMembres() {
        return accesMembres;
    }

    public boolean isAccesPlanning() {
        return accesPlanning;
    }

    public boolean isAccesSalles() {
        return accesSalles;
    }

    public static Role getByName(String name) {
        return BY_NAME.get(name);
    }
}
