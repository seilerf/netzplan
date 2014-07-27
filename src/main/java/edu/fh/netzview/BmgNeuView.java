/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;

import edu.fh.netzcontroller.BmgNeuController;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

/**
 *
 * @author fseiler
 */
public class BmgNeuView extends AbstractView {

    private JMenuBar menu;
    private JMenu menuBmg;
    private JMenuItem menuEnde;
    private JButton btnEditSave;
    private JTextField bmgName;
    private JTextField txtKapazität;
    
    public BmgNeuView() {
        this.erstelleMenu();
        this.erstelleButtons();
        
        this.showView();
    }
    
    private void erstelleMenu(){
        menu = new JMenuBar();
        menuBmg = new JMenu("Betriebsmittelgruppe");
        menuEnde = new JMenuItem("Schließen");
        
        menuBmg.add(menuEnde);
        menu.add(menuBmg);
        this.setJMenuBar(menu);
    }
    
    private void erstelleButtons(){
         btnEditSave = new JButton();
         btnEditSave.setText("Speichern");
    }
    
    public void setBtnSaveListener(ActionListener l){
        this.btnEditSave.addActionListener(l);
    }
     
    /**
     * Gibt den Namen der Betriebsmittelgruppe aus dem Textfeld "Name" zurück.
     * @return Name der Betriebsmittelgruppe
     */
    public String getBmgName(){
        try{
            return this.bmgName.getText();
        } catch (NullPointerException e){
            return null;
        }
    }
        
    /**
     * Gibt den Wert aus dem Textfeld "Kapazität" als Double zurück.
     * @return Den Wert, der im Textfeld "Kapazität" eingetragen wurde.
     */
    public double getKapa(){
        Double bmgKapa;
        try{
            bmgKapa = Double.parseDouble(this.txtKapazität.getText());
        }
        catch(NullPointerException e){
            bmgKapa = 0.0;
        }
        return bmgKapa;
    }
    
    private void showView(){
        this.setSize(350, 200);
        
        GridBagLayout gbl = new GridBagLayout();
        
        Container inhalt = this.getContentPane();
        inhalt.setLayout(gbl);
        
        this.bmgName = new JTextField();
        bmgName.setHorizontalAlignment(SwingConstants.LEFT);
        bmgName.setBorder(new TitledBorder("Name"));
        
        txtKapazität = new JTextField();
        txtKapazität.setBorder(new TitledBorder("Kapazität"));
        txtKapazität.setHorizontalAlignment(SwingConstants.LEFT);
        txtKapazität.setEditable(true);
        
        // Zeichnen der Komponenten in das GUI
        //                                           x  y    b  h    wx wy
        addComponent(inhalt, gbl, bmgName,           0, 0,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtKapazität,      0, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, new JSeparator(),  0, 3,   1, 1,   3, 0);
        addComponent(inhalt, gbl, btnEditSave,       0, 4,   0, 0,   3, 0);
        
        this.setSize(400, 285);
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
}
