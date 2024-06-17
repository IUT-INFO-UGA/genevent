package fr.uga.iut2.genevent.modele.membre;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MembreTest {

    @Test
    void setNom() throws MembreException {
        Membre m = new Membre(0, "marcel le fol", new Date(2024, 06, 17), "00 00 00 00 00");
        assertEquals("Marcel Le fol", m.getNom());

        assertThrows(MembreException.class, () -> m.setNom("kjgqshljgk"));

        m.setNom("MARCEL-Le fol");
        assertEquals("Marcel le Fol", m.getNom());
    }

    @Test
    void setTelephone() throws MembreException {
        Membre m = new Membre(0, "marcel le fol", new Date(2024, 06, 17), "00 00 00 00 00");
        assertEquals("00 00 00 00 00", m.getTelephone());

        assertThrows(MembreException.class, () -> m.setTelephone("00.00.00 00.00"));

        m.setTelephone("0000000000");
        assertEquals("0000000000", m.getTelephone());
    }
}