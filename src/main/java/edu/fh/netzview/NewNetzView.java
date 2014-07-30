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
import java.awt.event.ActionListener;
import javax.swing.JSeparator;

/**
 *
 * @author Anton
 */
public class NewNetzView extends AbstractView {

     private JMenuBar menu;
    private JMenu menuBmg;
    private JMenuItem menuEnde;
    private JButton btnEditSave;
    private JTextField netzName;
    private JTextField txtend;
    private JTextField txtstart;
    
    public NewNetzView() {
        this.erstelleMenu();
        this.erstelleButtons();
        
        this.showView();
    }
    
    private void erstelleMenu(){
        menu = new JMenuBar();
        menuBmg = new JMenu("Netzplan");
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
    public String getNetzName(){
        try{
            return this.netzName.getText();
        } catch (NullPointerException e){
            return null;
        }
    }
        
    /**
     * Gibt den Wert aus dem Textfeld "Kapazität" als Double zurück.
     * @return Den Wert, der im Textfeld "Kapazität" eingetragen wurde.
     */
    public double getstart(){
        Double start;
        try{
            start = Double.parseDouble(this.txtstart.getText());
        }
        catch(NullPointerException e){
            start = 0.0;
        }
        return start;
    }
    
    
     public double getend(){
        Double end;
        try{
            end = Double.parseDouble(this.txtend.getText());
        }
        catch(NullPointerException e){
            end = 0.0;
        }
        return end;
    }
    
    private void showView(){
        this.setSize(350, 200);
        
        GridBagLayout gbl = new GridBagLayout();
        
        Container inhalt = this.getContentPane();
        inhalt.setLayout(gbl);
        
        this.netzName = new JTextField();
        netzName.setHorizontalAlignment(SwingConstants.LEFT);
        netzName.setBorder(new TitledBorder("Name"));
        
        txtend = new JTextField();
        txtend.setBorder(new TitledBorder("Ende:"));
        txtend.setHorizontalAlignment(SwingConstants.LEFT);
        txtend.setEditable(true);
        
        txtstart = new JTextField();
        txtstart.setBorder(new TitledBorder("Start:"));
        txtstart.setHorizontalAlignment(SwingConstants.LEFT);
        txtstart.setEditable(true);
        
        // Zeichnen der Komponenten in das GUI
        //                                           x  y    b  h    wx wy
        addComponent(inhalt, gbl, netzName,           0, 0,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtstart,      0, 1,   1, 1,   3, 0);
        addComponent(inhalt, gbl, txtend,      0, 2,   1, 1,   3, 0);
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