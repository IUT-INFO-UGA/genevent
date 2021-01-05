/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.uga.iut2.genconf.vue;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author laurillau
 */
public class VueEtat extends JPanel {
    private JLabel msgEtat;
    private IHMGenConf ihm;

    public VueEtat(IHMGenConf ihm) {
        this.ihm = ihm;

        msgEtat = new JLabel("");
        msgEtat.setHorizontalAlignment(JLabel.CENTER);

        setLayout(new java.awt.BorderLayout());
        this.add(msgEtat, java.awt.BorderLayout.CENTER);
    }

    public void setEtat(String message) {
        msgEtat.setText(message);
    }
}
