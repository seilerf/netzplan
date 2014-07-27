/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import datenbank.SQLConnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import netzplan.Netzplan;
import netzplan.Vorgang;
import netzview.VorgangView;

/**
 *
 * @author fseiler
 */
public class VorgangController{

    private final Vorgang vorgang;
    private final VorgangView vorgangView;
    private final JComboBox netzplanAuswahl;
    private final String[] netzplanListe;

    public VorgangController(String[] netzplanListe) {
        this.netzplanListe = netzplanListe;
        this.netzplanAuswahl = new JComboBox(netzplanListe);
        //Modell
        this.vorgang = new Vorgang();
        this.vorgang.setVorgangId(0);
        // View erstellen
        this.vorgangView = new VorgangView(netzplanAuswahl);
        this.vorgangView.setTitle("Neuer Vorgang");
        // Listener hinzufügen
        this.addListener();
        this.vorgangView.setVisible(true);
    }
    
    public VorgangController(String[] netzplanListe, Vorgang vorgang){
        this.netzplanListe = netzplanListe;
        this.netzplanAuswahl = new JComboBox(netzplanListe);
        // Modell
        this.vorgang = vorgang;
        // View erstellen
        this.vorgangView = new VorgangView(netzplanAuswahl);
        this.vorgangView.setVorgangName(vorgang.getName());
        this.vorgangView.setDauer(vorgang.getDauer());
        this.vorgangView.setNetzplanId(vorgang.getNetzRefId());
        // Listener hinzufügen
        this.addListener();
        this.vorgangView.setVisible(true);
    }
    
    /**
     * Gibt den Namen des Vorgangs, den der Controller erzeugt hat aus
     * @author Florian Seiler
     * @return 
     */
    public String getVorgangName(){
        return this.vorgang.getName();
    }
    
    /**
     * Fügt einen Listener in der zugehörigen View an, der das Speichern nach
     * einem Klickevent auslöst.
     * @author Florian Seiler
     */
    private void addListener(){
        vorgangView.setBtnSaveListener(new SaveListener());
    }
    
    /**
     * Speichert den Vorgang in der Datenbank. Die Methode prüft zunächst, ob es sich
     * bei dem Vorgang um einen neuen Vorgang handelt oder, ob ein bereits vorhandener
     * Vorgang geändert werden soll.
     * @param netzRefId die ID des Netzplans, zu dem der Vorgang gehört
     */
    private void speichereVorgang(int netzRefId){
        System.out.println("speichere Vorgang: " + vorgang.getName());
        SQLConnect sqlConnect = new SQLConnect();
        if(this.vorgang.getVorgangId() == 0){
            try {
                sqlConnect.insertVorgang(vorgang.getName(), netzRefId, netzRefId);
            } catch (SQLException ex) {
                Logger.getLogger(VorgangController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else{
            sqlConnect.updateVorgang(vorgang);
        }
    }
    
    /**
     * Hilfsklasse, mit der ein Actionlistener in der VorgangView auf den Speichern-
     * Button gelegt werden kann.
     */
    class SaveListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            System.out.println("Speichern in der VorgangView geklickt");
            String netzplanName = (String)netzplanAuswahl.getSelectedItem();
            LinkedList<Netzplan> netzplanListe = new SQLConnect().ladeAlleNetzplaene();
            int netzRefId = 0;
            // Suchen des ausgewählten Netzplans in der Datenbank
            for (Netzplan netzplan : netzplanListe){
                if (netzplan.getName().equals(netzplanName))
                    netzRefId = netzplan.getId();
            }
            vorgang.setName(vorgangView.getVorgangName());
            vorgang.setDauer(vorgangView.getDauer());
            speichereVorgang(netzRefId);
            vorgangView.dispose();
        }
        
    }
}
