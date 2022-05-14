package fr.uga.iut2.genevent.vue.swing_gui;

import javax.swing.JLabel;
import javax.swing.JPanel;


public class VueEtat extends JPanel {
    private final SwingGUI gui;
    private final JLabel msgEtat;

    public VueEtat(SwingGUI gui) {
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
