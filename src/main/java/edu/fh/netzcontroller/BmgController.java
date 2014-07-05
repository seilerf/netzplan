/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import edu.fh.netzview.BmgView;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author Anton
 */
public class BmgController {
    
     public BmgController() {
     final BmgView bmg = new BmgView("Stacked Bar Chart Demo 4");
        bmg.pack();
        RefineryUtilities.centerFrameOnScreen(bmg);
        bmg.setVisible(true);
    }
}

