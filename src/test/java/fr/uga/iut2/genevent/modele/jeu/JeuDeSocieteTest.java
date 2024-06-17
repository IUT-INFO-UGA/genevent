package fr.uga.iut2.genevent.modele.jeu;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class JeuDeSocieteTest {

    @Test
    void setNbJoueurs() throws JeuDeSocieteException {
        JeuDeSociete jds = new JeuDeSociete("Echecs", "trop long", 2, new Date(2024, 06, 17), "Réflexion", TailleTable.PETITE, 30,10);
        assertThrows(JeuDeSocieteException.class, () -> jds.setNbJoueurs(0));
        assertThrows(JeuDeSocieteException.class, () -> jds.setNbJoueurs(-3));
        assertDoesNotThrow(() -> jds.setNbJoueurs(5));
    }
}