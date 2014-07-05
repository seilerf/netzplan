/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.NewNetzView;

/**
 *
 * @author Anton
 */
public class NewNetzController implements ActionListener{

    
    private NewNetzView netzview;

    public NewNetzController() {
       
        this.netzview = new NewNetzView(this);
       
        this.netzview.setVisible(true);
        netzview.pack();
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
