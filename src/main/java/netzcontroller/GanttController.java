/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import java.awt.event.ActionEvent;
import netzview.GanttView;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author Anton
 */
public class GanttController {
    

    public GanttController() {
        //Modell
       // this.vorgang = new Vorgang();
        //this.vorgang.setName("Testvorgang");
        // View
        
        final GanttView gv = new GanttView("Gantt Chart Demo 1", this);
        gv.pack();
        RefineryUtilities.centerFrameOnScreen(gv);
        gv.setVisible(true);
        
        // Observer für die Modelle
        //this.vorgang.addObserver(this.vorgangView);
       // this.netzview.setVisible(true);
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
