/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;

import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import edu.fh.netzcontroller.NewNetzController;

/**
 *
 * @author Anton
 */
public class NewNetzView extends JFrame implements Observer{

    private boolean editable;
    
    public NewNetzView(NewNetzController controller) {
        
        this.editable = false;
        this.setTitle("Neuer Netzplan");
        this.setSize(350, 200);
        
        JMenuBar menu = new JMenuBar();
        JMenu menuVorgang = new JMenu("Datei");
        JMenuItem menuEnde = new JMenuItem("Schließen");
        JMenuItem speichern = new JMenuItem("Speichern");
        menuVorgang.add(menuEnde);
        menuVorgang.add(speichern);
        
        menu.add(menuVorgang);
        this.setJMenuBar(menu);
        
        GridBagLayout gbl = new GridBagLayout();
        
        Container inhalt = this.getContentPane();
        inhalt.setLayout(gbl);
        
        // Textfelder erstellen, die die Daten beinhalten
        JTextField txtFAZ = new JTextField();
        txtFAZ.setBorder(new TitledBorder("Maximale Dauer"));
        txtFAZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtFAZ.setEditable(true);

        JTextField txtFEZ = new JTextField();
        txtFEZ.setBorder(new TitledBorder("Anzahl Vorgänge"));
        txtFEZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtFEZ.setEditable(true);
        
        JTextField txtSAZ = new JTextField();
        txtSAZ.setBorder(new TitledBorder("Name"));
        txtSAZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtSAZ.setEditable(true);
        
        JTextField txtSEZ = new JTextField();
        txtSEZ.setBorder(new TitledBorder("dies das Ananas"));
        txtSEZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtSEZ.setEditable(true);
        
        JTextField txtDauer = new JTextField();
        txtDauer.setBorder(new TitledBorder("blub"));
        txtDauer.setHorizontalAlignment(SwingConstants.CENTER);
        txtDauer.setEditable(true);
        
        JButton btnEditSave = new JButton();
       
        btnEditSave.setText("Speichern");
        btnEditSave.addActionListener(controller);
            
        JButton neuVorgang = new JButton();
       
        neuVorgang.setText("Vorgänge anlegen");
        neuVorgang.addActionListener(controller);
            
        
        // Zeichnen der Komponenten auf Bildschirm x  y    b  h    wx wy
        //---------------------------------------------------------------//
        //addComponent(inhalt, gbl, titel,         0, 0,   3, 1,   0, 0);
        addComponent(inhalt, gbl, txtFAZ,          0, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtDauer,        1, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtFEZ,          2, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtSAZ,          0, 3,   1, 1,   3, 0);
        //addComponent(inhalt, gbl, txtPuffer,     1, 3,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtSEZ,          2, 3,   1, 1,   3, 0);
        addComponent(inhalt, gbl, btnEditSave,     1, 5,   1, 1,   3, 0);
        addComponent(inhalt, gbl, neuVorgang,      0, 5,   1, 1,   3, 0);
    }

    
    /**
     * 
     * @param cont Container, in den das Objekt aufgenommen werden soll
     * @param gbl LayoutManager
     * @param c Objekt, das hinzugefügt werden soll
     * @param x Punkt auf der x-Achse
     * @param y Punkt auf der y-Achse
     * @param width Breite des Objektes
     * @param height Höhe des Objektes
     * @param weightx
     * @param weighty 
     */
     private static void addComponent(Container cont,
                            GridBagLayout gbl,
                            Component c,
                            int x, int y,
                            int width, int height,
                            double weightx, double weighty){
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = x; 
        gbc.gridy = y;
        gbc.gridwidth = width; 
        gbc.gridheight = height;
        gbc.weightx = weightx; 
        gbc.weighty = weighty;
        gbc.ipadx = 20;
        gbc.ipady = 5;
        gbl.setConstraints(c, gbc);
        cont.add(c);
    }
    
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
