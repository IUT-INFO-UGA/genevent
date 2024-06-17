package fr.uga.iut2.genevent.modele.commande;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommandeTest {

    @Test
    void getPrixTotal() throws CommandeException {
        Commande c = new Commande(1, "Jeu cool", 5, 20);
        assertEquals(100, c.getPrixTotal());
        assertDoesNotThrow(() -> c.setPrix(15));
        assertEquals(60, c.getPrixTotal());
        assertDoesNotThrow(() -> c.setPrix(16.75));
        assertEquals(67.75, c.getPrixTotal());
    }

    @Test
    void setNomDuJeu() throws CommandeException {
        Commande c = new Commande(1, "Jeu encore plus cool", 5, 20);
        assertThrows(CommandeException.class, () -> c.setNomDuJeu(null));
        assertThrows(CommandeException.class, () -> c.setNomDuJeu(""));
        assertThrows(CommandeException.class, () -> c.setNomDuJeu("     "));
        assertDoesNotThrow(() -> c.setNomDuJeu("Jeu"));
    }

    @Test
    void setQuantite() throws CommandeException {
        Commande c = new Commande(1, "Jeu super cool", 5, 20);
        assertThrows(CommandeException.class, () -> c.setQuantite(0));
        assertThrows(CommandeException.class, () -> c.setQuantite(-10));
        assertDoesNotThrow(() -> c.setQuantite(99));
    }

    @Test
    void setPrix() throws CommandeException {
        Commande c = new Commande(1, "Jeu super cool", 5, 20);
        assertThrows(CommandeException.class, () -> c.setPrix(0));
        assertThrows(CommandeException.class, () -> c.setPrix(-10));
        assertDoesNotThrow(() -> c.setPrix(99));
    }
}