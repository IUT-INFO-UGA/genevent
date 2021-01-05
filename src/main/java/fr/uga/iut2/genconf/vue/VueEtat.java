package fr.uga.iut2.genconf.vue;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class VueEtat extends JPanel {
    private final GUI gui;
    private final JLabel msgEtat;

    public VueEtat(GUI gui) {
        this.gui = gui;

        this.msgEtat = new JLabel("");
        this.msgEtat.setHorizontalAlignment(JLabel.CENTER);

        this.setLayout(new java.awt.BorderLayout());
        this.add(this.msgEtat, java.awt.BorderLayout.CENTER);
    }

    public void setEtat(String message) {
        this.msgEtat.setText(message);
    }
}
