/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.datenbank.SQLConnect;
import edu.fh.netzplanModell.Betriebsmittelgruppe;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.VorganganzeigenView;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;

/**
 *
 * @author Anton
 */
public class VorganganzeigenController implements ActionListener{

    private Vorgang vorgang;
    private VorganganzeigenView vorgangView;
    private SQLConnect con = new SQLConnect();
    private final JComboBox bmgauswahl;
    private int id;
    
    public VorganganzeigenController(Vorgang vorgangs) {
        //Modell
        this.vorgang = vorgangs;
        // View
        this.bmgauswahl = new JComboBox(erstelleNetzplanListe());

        this.vorgangView = new VorganganzeigenView(this,vorgang,bmgauswahl);
        
        this.vorgangView.setVisible(true);
        vorgangView.pack();
        
    }

    public VorganganzeigenController(Vorgang vorgangs, int id) {
        //Modell
        this.vorgang = vorgangs;
        this.id=id;
        // View
        this.bmgauswahl = new JComboBox(erstelleNetzplanListe());

        this.vorgangView = new VorganganzeigenView(this,vorgang,bmgauswahl);
        
        this.vorgangView.setVisible(true);
        vorgangView.pack();
        
    }
    /**
     *  Bei Drücken des Speichern Buttons werde die Werte an das Objekt übergeben
     * @param  vorgang
     */
    public void actionPerformed(String dauer, String name, String bmg) throws SQLException {
         SQLConnect con = new SQLConnect();
        
        //this.vorgang= vorgang;
        this.vorgang.setDauer(Double.parseDouble(dauer));
        this.vorgang.setName(name);
        con.updateVorgang(this.vorgang.getName(), this.vorgang.getDauer(), this.vorgang.getVorgangId());
         LinkedList<Betriebsmittelgruppe> netzplanListe = new SQLConnect().ladeAlleBetriebsMittelGruppen();
                int netzRefId = 0;
                // Suchen des ausgewählten Netzplans in der Datenbank
                for (Betriebsmittelgruppe netzplan : netzplanListe){
                    if (netzplan.getNameBetrMittelGr().equals(bmg))
                    
                        netzRefId = netzplan.getBetrMittelGrId();
                    
                    
                    
                }
        System.out.println(this.vorgang.getVorgangId());
                    System.out.println(id);
                    System.out.println(netzRefId);
        
        try {
             
            int i=  con.updateVorgangBmg(this.vorgang.getVorgangId(),netzRefId);
                if (i<=0)
                    con.insertVorgangBmg(this.vorgang.getVorgangId(),id,netzRefId);
                     } 
                   catch (SQLException ex) {
                       
                        //System.out.println(ex);
                       Logger.getLogger(VorganganzeigenController.class.getName()).log(Level.SEVERE, null, ex);
                       System.out.println("Porblem bei Insert!");
                              
                                    }
        
        
        
    }
    
    
    /**
     *  Bei Drücken des beenden Buttons schließt sich das Fenster
     * @param e
     * 
     */
    public void actionPerformed(ActionEvent e) {
        vorgangView.dispose();
    }

    
    
   private String[] erstelleNetzplanListe(){
        try {
            LinkedList<Betriebsmittelgruppe> netzplanListe = new SQLConnect().ladeAlleBetriebsMittelGruppen();
            String[] netzplaene = new String[netzplanListe.size()];
            int i=0;
            for (Betriebsmittelgruppe netzplan : netzplanListe){
                netzplaene[i] = netzplan.getNameBetrMittelGr();
                i++;
            }
            System.out.println("Netzplan: " + netzplaene[0]);
            return netzplaene;
        } catch (SQLException ex) {
            return null;
        }
    }
    
    
}

