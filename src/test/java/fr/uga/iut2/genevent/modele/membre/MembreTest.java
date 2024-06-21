package fr.uga.iut2.genevent.modele.membre;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class MembreTest {

    @Test
    void setNom() throws MembreException {
        Membre m = new Membre("marcel le fol", new Date(2024, 06, 17), "00 00 00 00 00");
        assertEquals("Marcel Le fol", m.getNom());

        assertThrows(MembreException.class, () -> new Membre("htrrthrhtthr", new Date(2024, 06, 17), "00 00 00 00 00"));
        assertDoesNotThrow(() -> new Membre("MARCEL-Le fol", new Date(2024, 06, 17), "00 00 00 00 00"));
        assertDoesNotThrow(() -> new Membre("Marcel le Fol", new Date(2024, 06, 17), "00 00 00 00 00"));
    }

    @Test
    void setTelephone() throws MembreException {
        Membre m = new Membre("marcel le fol", new Date(2024, 06, 17), "00 00 00 00 00");
        assertEquals("00 00 00 00 00", m.getTelephone());

        assertThrows(MembreException.class, () -> m.setTelephone("00.00.00 00.00"));
        assertThrows(MembreException.class, () -> m.setTelephone("00.00000000"));

        m.setTelephone("0000000000");
        assertEquals("00 00 00 00 00", m.getTelephone());

        m.setTelephone("00.00.00.00.00");
        assertEquals("00 00 00 00 00", m.getTelephone());
    }
}