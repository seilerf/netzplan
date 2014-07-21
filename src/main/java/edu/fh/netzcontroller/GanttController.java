/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.application.Netzplanung;
import edu.fh.netzplanModell.ChartModel;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.GanttView;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author Anton
 */
public class GanttController {
    Netzplanung netzPll;
    Netzplanung netzPll2;
    GanttView gv;
    ChartModel chartmodel;
    
    /**
     * Konstruktor öffnet die GanttView 
     * @param netzPl
     */
    public GanttController(Netzplanung netzPl) {
        
        this.netzPll=netzPl;
        chartmodel = new  ChartModel(netzPll);
        gv = new GanttView("Gantt", this, chartmodel);
        gv.pack();
        RefineryUtilities.centerFrameOnScreen(gv);
        gv.setVisible(true);

    }

    /**
     * Aktualisierung des Netzplans auf die Taste "N" und anschließende aktualisierung des Charts
     * @param e
     * @throws SQLException
     */
    public void keyTyped(KeyEvent e) throws SQLException {
        int key = e.getKeyCode();
        System.out.println(key);
        char keystr =e.getKeyChar();
        System.out.println(keystr);
        if(keystr == 'N'){  //Getippte Taste = N ?
        
         gv.dispose();  // wenn ja schließen des aktuellen Charts
         netzPll2 = new Netzplanung(netzPll.getIdNetzplan());
         netzPll2.netzPlanBerechnung(); //neue Netzplan berechnung
         GanttController gc = new GanttController(netzPll2); // nach neuer Reihenfolge berechnen öffnen des aktualisierten GanttCharts
    }
        
       
    }
    
    /**
     * Kürzt den Chart string (vom klicken) auf den Vorgangsnamen
     * führt die Suche des Vorgangs zum Namen aus
     * öffnet das Vorgang anzeigen Fenster zum bearbeiten des angeklickten Vorgangs
     * @param e
     */
    public void mousecklicked(ChartMouseEvent e){
        
        //System.out.println(e.getEntity());
        
        //die folgenden Befehle dienen dazu den ganzen String welchen man vom Chart 
        //bekommt auf den Vorgangsnamen zu kürzen, damit mit diesem Namen der dazugehörige Vorgang gesucht werden kann
        
        String string = e.getEntity().toString();
        String[] parts = string.split(","); //beim Komma Splitten...
        
        String part2 = parts[1];  //rechter teil vom Komma
        
        String[] parts2 = part2.split(","); //zweites Komma entfernen
        String part11 = parts2[0];          // linker teil vom zweiten Komma
        //System.out.println(part11);
        
        
        String[] parts3 = part11.split("="); //Endgültigen Namen bestimmen
        String endstring = parts3[1];
        //System.out.println(endstring);
        Vorgang vorgangs;
        vorgangs=chartmodel.searchVorgang(endstring); //Aufruf der Vorgangssuchfunktion --> gibt den Vorgang zurück zum Namen
        
        
        VorganganzeigenController vorgang = new VorganganzeigenController(vorgangs); //öffnet das Vorgang Anzeigen Fenster zum bearbeiten
    }
    
   
}
