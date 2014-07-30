/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.datenbank.SQLConnect;
import edu.fh.netzplanModell.Netzplan;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.NewNetzView;
import java.sql.SQLException;

/**
 *
 * @author Anton
 */
public class NewNetzController implements ActionListener{

    
    
    private final NewNetzView bmgNeuView;
    private Netzplan bmg;

    public NewNetzController(){
        // View erstellen
        this.bmgNeuView = new NewNetzView();
        // Modell
        this.bmg = new Netzplan();
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
    private void speichereBmg(String name, double startzeit, double endzeit){
        try {
            SQLConnect sqlConnect = new SQLConnect();
            sqlConnect.insertNetzplan(name, startzeit, endzeit);
        } catch (SQLException ex) {
            System.out.println("SQL-Fehler...");
        }
    }

    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Hilfsklasse, mit der ein Actionlistener in der VorgangView auf den Speichern-
     * Button gelegt werden kann.
     */
    class SaveListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("Speichern in der BmgView geklickt");
            bmg.setName(bmgNeuView.getNetzName());
            bmg.setStart(bmgNeuView.getstart());
            bmg.setEnde(bmgNeuView.getend());
            speichereBmg(bmg.getName(), bmg.getStart(), bmg.getEnde());
            bmgNeuView.dispose();
        }
        
    }

   
}
