/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import netzplan.Netzplan;
import netzview.NetzplanView;

/**
 *
 * @author fseiler
 */
public class NetzplanController implements ActionListener{

    private Netzplan netzplan;
    private NetzplanView netzplanView;

    public NetzplanController() {
        this.netzplan = new Netzplan();
        this.netzplanView = new NetzplanView(this);
    }
    
    
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
