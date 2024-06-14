package fr.uga.iut2.genevent.modele;

import fr.uga.iut2.genevent.modele.jeux.JeuDeSociete;

import java.util.ArrayList;
import java.util.Date;

public class Seance {

    private String type;
    private Date date;
    private int heureDebut;
    private int heureFin;
    private final ArrayList<Membre> membres;
    private final ArrayList<JeuDeSociete> jeuxDeSociete;

    public Seance(String type, Date date, int heureDebut, int heureFin) {
        this.type = type;
        this.date = date;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.membres = new ArrayList<>();
        this.jeuxDeSociete = new ArrayList<>();
    }

    public String getType() {
        return type;
    }

    public Date getDate() {
        return date;
    }

    public int getHeureDebut() {
        return heureDebut;
    }

    public int getHeureFin() {
        return heureFin;
    }

    public ArrayList<Membre> getMembres() {
        return membres;
    }

    public ArrayList<JeuDeSociete> getJeuxDeSociete() {
        return jeuxDeSociete;
    }

    public void addMembre(Membre membre) {
        this.membres.add(membre);
    }

    public void addJeuDeSociete(JeuDeSociete jeuDeSociete) {
        this.jeuxDeSociete.add(jeuDeSociete);
    }

    public void removeMembre(Membre membre) {
        this.membres.remove(membre);
    }

    public void removeJeuDeSociete(JeuDeSociete jeuDeSociete) {
        this.jeuxDeSociete.remove(jeuDeSociete);
    }
}
