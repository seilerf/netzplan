/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.datenbank.SQLConnect;
import edu.fh.netzplanModell.Betriebsmittelgruppe;
import edu.fh.netzplanModell.Netzplan;
import edu.fh.netzview.BmgNeuView;
import edu.fh.netzview.BmgView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author fseiler
 */
public class BmgNeuController{

    private final BmgNeuView bmgNeuView;
    private Betriebsmittelgruppe bmg;

    public BmgNeuController(){
        // View erstellen
        this.bmgNeuView = new BmgNeuView();
        // Modell
        this.bmg = new Betriebsmittelgruppe();
        // Listener hinzufügen
        this.addListener();
        this.bmgNeuView.setVisible(true);
    }
        
    /**
     * Fügt einen Listener in der zugehörigen View an, der das Speichern nach
     * einem Klickevent auslöst.
     * @author Florian Seiler
     */
    private void addListener(){
        bmgNeuView.setBtnSaveListener(new SaveListener());
    }
    
    /**
     * Speichert den Vorgang in der Datenbank. Die Methode prüft zunächst, ob es sich
     * bei dem Vorgang um einen neuen Vorgang handelt oder, ob ein bereits vorhandener
     * Vorgang geändert werden soll.
     * @param netzRefId die ID des Netzplans, zu dem der Vorgang gehört
     */
    private void speichereBmg(String name, double kapa){
        try {
            SQLConnect sqlConnect = new SQLConnect();
            sqlConnect.insertBetriebsmittelgruppe(name, kapa);
        } catch (SQLException ex) {
            System.out.println("SQL-Fehler...");
        }
    }
    
    /**
     * Hilfsklasse, mit der ein Actionlistener in der VorgangView auf den Speichern-
     * Button gelegt werden kann.
     */
    class SaveListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("Speichern in der BmgView geklickt");
            bmg.setNameBetrMittelGr(bmgNeuView.getBmgName());
            bmg.setBetrMittelKapa(bmgNeuView.getKapa());
            speichereBmg(bmg.getNameBetrMittelGr(), bmg.getBetrMittelKapa());
            bmgNeuView.dispose();
        }
        
    }
}
