/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.application.Netzplanung;
import edu.fh.netzplanModell.ChartModel;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.BmgView;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Anton
 */
public class BmgController {
        Netzplanung netzPll;
        Netzplanung netzPll2;
        BmgView bmg;
        ChartModel chartmodel;

    /**
     * Aktualisierung des Netzplans auf die Taste "N" und anschließende aktualisierung des Charts
     * @param netzPl
     */
    public BmgController( Netzplanung netzPl) {
        this.netzPll=netzPl;
        chartmodel = new  ChartModel(netzPll);
        bmg = new BmgView("BMG Auslastung", this,   chartmodel);
        bmg.pack();
        RefineryUtilities.centerFrameOnScreen(bmg);
        bmg.setVisible(true);

     }

    /**
     * Aktualisierung des Netzplans auf die Taste "N" und anschließende aktualisierung des Charts
     * @param e
     * @throws SQLException
     */
    public void keyPressed(KeyEvent e) throws SQLException {
        int key = e.getKeyCode();
        System.out.println(key);
        char keystr =e.getKeyChar();
        System.out.println(keystr);
        if(keystr == 'N'){
         
         bmg.dispose();
         netzPll2 = new Netzplanung(netzPll.getIdNetzplan());
         netzPll2.netzPlanBerechnung();
         BmgController gc = new BmgController(netzPll2);
    }
     
}   
    
    /**
     * Kürzt den Chart string (vom klicken) auf den Vorgangsnamen
     * führt die Suche des Vorgangs zum Namen aus
     * öffnet das Vorgang anzeigen Fenster zum bearbeiten des angeklickten Vorgangs
     * @param e
     */
     public void mousecklicked(ChartMouseEvent e){
         
        System.out.println(e.getEntity());
        
        
        String string = e.getEntity().toString();
        String[] parts = string.split(","); //erstes Komma entfernen
        String part1 = parts[0]; // 004
        String part2 = parts[1]; 
        
        
        String[] parts3 = part1.split("="); //Endgültigen Namen bestimmen
        String endstring = parts3[1];
        System.out.println(endstring);
        Vorgang vorgangs;
        vorgangs=chartmodel.searchVorgang(endstring);
        
        
        VorganganzeigenController vorgang = new VorganganzeigenController(vorgangs);
     }


}