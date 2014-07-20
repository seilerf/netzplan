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
    public GanttController(Netzplanung netzPl) {
        
        this.netzPll=netzPl;
        chartmodel = new  ChartModel(netzPll);
        gv = new GanttView("Gantt", this, chartmodel);
        gv.pack();
        RefineryUtilities.centerFrameOnScreen(gv);
        gv.setVisible(true);

        
    }
    
    public void actionPerformed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void keyPressed(KeyEvent e) throws SQLException {
        int key = e.getKeyCode();
        System.out.println(key);
        char keystr =e.getKeyChar();
        System.out.println(keystr);
        if(keystr == 'N'){
         
        
         
         gv.dispose();
         netzPll2 = new Netzplanung(netzPll.getIdNetzplan());
         netzPll2.netzPlanBerechnung();
         GanttController gc = new GanttController(netzPll2);
    }
        
       
    }
    
    
    
    
    public void mousecklicked(ChartMouseEvent e){
        
        System.out.println(e.getEntity());
        
        
        String string = e.getEntity().toString();
        String[] parts = string.split(","); //erstes Komma entfernen
        String part1 = parts[0]; // 004
        String part2 = parts[1]; 
        
        String[] parts2 = part2.split(","); //zweites Komma entfernen
        String part11 = parts2[0];
        System.out.println(part11);
        
        
        String[] parts3 = part11.split("="); //Endgültigen Namen bestimmen
        String endstring = parts3[1];
        System.out.println(endstring);
        Vorgang vorgangs;
        vorgangs=chartmodel.searchVorgang(endstring);
        
        
        VorganganzeigenController vorgang = new VorganganzeigenController(vorgangs);
    }
    
   
}
