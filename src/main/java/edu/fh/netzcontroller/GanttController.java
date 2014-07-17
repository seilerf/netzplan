/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.application.Netzplanung;
import edu.fh.netzplanModell.Vorgang;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import edu.fh.netzview.GanttView;
import java.util.LinkedList;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


/**
 *
 * @author Anton
 */
public class GanttController {
    Netzplanung netzPll;

    public GanttController(Netzplanung netzPl) {
        
        this.netzPll=netzPl;
        
        final GanttView gv = new GanttView("Gantt Chart Demo 1", this, netzPll);
        gv.pack();
        RefineryUtilities.centerFrameOnScreen(gv);
        gv.setVisible(true);

        
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
