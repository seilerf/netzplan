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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import edu.fh.netzcontroller.VorganganzeigenController;
import edu.fh.netzplanModell.Vorgang;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Anton
 */
public class VorganganzeigenView extends JFrame implements Observer{

    private boolean editable;
    private Vorgang vorgang;
    public VorganganzeigenView(final VorganganzeigenController controller, final Vorgang vorgang) {
        this.vorgang=vorgang;
        this.editable = false;
        this.setTitle(vorgang.getName());
        this.setSize(400, 250);
        
        JMenuBar menu = new JMenuBar();
        JMenu menuVorgang = new JMenu("Vorgang");
        JMenuItem menuEnde = new JMenuItem("Schließen");
        menuEnde.addActionListener(controller) ;
        
        menuVorgang.add(menuEnde);
        
        menu.add(menuVorgang);
        this.setJMenuBar(menu);
        
        GridBagLayout gbl = new GridBagLayout();
        
        Container inhalt = this.getContentPane();
        inhalt.setLayout(gbl);
        
        JLabel titel = new JLabel(vorgang.getName());
        titel.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Textfelder erstellen, die die Daten beinhalten
        JTextField txtFAZ = new JTextField( String.valueOf(vorgang.getFaz()),20);
        txtFAZ.setBorder(new TitledBorder("FAZ"));
        txtFAZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtFAZ.setEditable(true);

        JTextField txtFEZ = new JTextField(String.valueOf(vorgang.getFez()),20);
        txtFEZ.setBorder(new TitledBorder("FEZ"));
        
        txtFEZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtFEZ.setEditable(true);
        
        JTextField txtSAZ = new JTextField(String.valueOf(vorgang.getSaz()),20);
        txtSAZ.setBorder(new TitledBorder("SAZ"));
        txtSAZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtSAZ.setEditable(true);
        
        JTextField txtSEZ = new JTextField(String.valueOf(vorgang.getSez()),20);
        txtSEZ.setBorder(new TitledBorder("SEZ"));
        txtSEZ.setHorizontalAlignment(SwingConstants.CENTER);
        txtSEZ.setEditable(true);
        
        final JTextField txtDauer = new JTextField(String.valueOf(vorgang.getDauer()),20);
        txtDauer.setBorder(new TitledBorder("Dauer"));
        txtDauer.setHorizontalAlignment(SwingConstants.CENTER);
        txtDauer.setEditable(true);
        
        JTextField txtPuffer = new JTextField();
        txtPuffer.setBorder(new TitledBorder("Puffer"));
        txtPuffer.setHorizontalAlignment(SwingConstants.CENTER);
        txtPuffer.setEditable(true);
        
        JComboBox combobox = new JComboBox();
        combobox.setBorder(new TitledBorder("Netzplan:"));
        //combobox.setHorizontalAlignment(SwingConstants.CENTER);
       
        
        JButton btnEditescape = new JButton();
        
            btnEditescape.setText("Schließen");
          // btnEditescape.addActionListener(btnEditescape,controller);
        btnEditescape.addActionListener(controller) ;
 
         
        
        JButton btnEditSave = new JButton();
        
        btnEditSave.setText("Speichern");
         
        btnEditSave.addActionListener(new ActionListener() {
 
            public void actionPerformed(ActionEvent e) {
                controller.actionPerformed(txtDauer.getText());
            }
        });  
        
        // Zeichnen der Komponenten in das GUI
        //                                        x  y    b  h    wx wy
        addComponent(inhalt, gbl, titel,          0, 0,   3, 1,   0, 0);
        addComponent(inhalt, gbl, txtFAZ,         0, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtDauer,       1, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtFEZ,         2, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtSAZ,         0, 3,   1, 1,   3, 0);
        
        addComponent(inhalt, gbl, txtSEZ,         2, 3,   1, 1,   3, 0);
        addComponent(inhalt, gbl, btnEditSave,    0, 5,   1, 1,   4.5, 0);
        addComponent(inhalt, gbl, btnEditescape,  1, 5,   1, 1,   4.5, 0);
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
        gbc.gridx = x; gbc.gridy = y;
        gbc.gridwidth = width; gbc.gridheight = height;
        gbc.weightx = weightx; gbc.weighty = weighty;
        gbc.ipadx = 20;
        gbc.ipady = 5;
        gbl.setConstraints(c, gbc);
        cont.add(c);
    }

    
    
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
