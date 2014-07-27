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
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import edu.fh.netzplanModell.Vorgang;

/**
 *
 * @author fseiler
 */
public class VorgangView extends JFrame{

    private JMenuBar menu;
    private JMenu menuVorgang;
    private JMenuItem menuEnde;
    private JButton btnEditSave;
    private JComboBox cbNetzplanAuswahl;
    
    private JTextField txtDauer;
    private JTextField vorgangName;
    private int netzplanId;
    
    
    /**
     * Konstruktor für die Erstellung eines neuen Vorgang
     * @param netzplanAuswahl
     */
    public VorgangView(JComboBox netzplanAuswahl) {
        this.erstelleMenu();
        this.erstelleButtons();
        this.cbNetzplanAuswahl = netzplanAuswahl;
        this.showView();
    }
    
    /**
     * Konstruktor, um einen ausgewählten Vorgang zu bearbeiten
     * @param netzplanAuswahl
     * @param vorgang 
     */    
    public VorgangView(JComboBox netzplanAuswahl, Vorgang vorgang){
        this.erstelleMenu();
        this.erstelleButtons();
        this.cbNetzplanAuswahl = netzplanAuswahl;
        this.showView();
    }
    
    private void erstelleMenu(){
        menu = new JMenuBar();
        menuVorgang = new JMenu("Vorgang");
        menuEnde = new JMenuItem("Schließen");
        
        menuVorgang.add(menuEnde);
        menu.add(menuVorgang);
        this.setJMenuBar(menu);
    }
    
    private void erstelleButtons(){
         btnEditSave = new JButton();
         btnEditSave.setText("Speichern");
    }
    
    public void setBtnSaveListener(ActionListener l){
        this.btnEditSave.addActionListener(l);
    }
    
    public void setNetzplanAuswahl(JComboBox cbNetzplanAuswahl){
        this.cbNetzplanAuswahl = cbNetzplanAuswahl;        
    }
    
    /**
     * Gibt den Namen des Vorgangs aus dem Textfeld "Name" zurück.
     * @return Name des Vorgangs
     */
    public String getVorgangName(){
        try{
            return this.vorgangName.getText();
        } catch (NullPointerException e){
            return null;
        }
    }
    
    public void setVorgangName(String name){
        this.setTitle(name);
        this.vorgangName.setText(name);
    }
    
    /**
     * Gibt den Wert aus dem Textfeld "Dauer" als Double zurück.
     * @return Den Wert, der im Textfeld "Dauer" eingetragen wurde.
     */
    public double getDauer(){
        Double vDauer;
        try{
            vDauer = Double.parseDouble(this.txtDauer.getText());
        }
        catch(NullPointerException e){
            vDauer = 0.0;
        }
        return vDauer;
    }
    
    public void setDauer(double dauer){
        this.txtDauer.setText(Double.toString(dauer));
    }
    
    public void setNetzplanId(int id){
        this.netzplanId = id;
    }
    public int getNetzplanId(){
        return netzplanId;
    }
    
    public int getSelectedNetzplan(){
        return netzplanId;
    }
    
    private void showView(){
        this.setSize(350, 200);
        
        GridBagLayout gbl = new GridBagLayout();
        
        Container inhalt = this.getContentPane();
        inhalt.setLayout(gbl);
        
        this.vorgangName = new JTextField();
        vorgangName.setHorizontalAlignment(SwingConstants.LEFT);
        vorgangName.setBorder(new TitledBorder("Name"));
        //vorgangName.setText("Netzplan-Id: " + this.netzplanId);
        
        txtDauer = new JTextField();
        txtDauer.setBorder(new TitledBorder("Dauer"));
        txtDauer.setHorizontalAlignment(SwingConstants.LEFT);
        txtDauer.setEditable(true);
        
        JComboBox combobox = new JComboBox();
        combobox.setBorder(new TitledBorder("Netzplan:"));

        // Zeichnen der Komponenten in das GUI
        //                                           x  y    b  h    wx wy
        addComponent(inhalt, gbl, vorgangName,       0, 0,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtDauer,          0, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, cbNetzplanAuswahl, 0, 2,   1, 1,   3, 0);
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
