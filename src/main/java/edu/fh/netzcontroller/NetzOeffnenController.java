/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.OeffnenView;

/**
 *
 * @author Anton
 */
public class NetzOeffnenController implements ActionListener{

    private Vorgang vorgang;
    private OeffnenView oeffnenview;

    public NetzOeffnenController() {
        //Modell
       // this.vorgang = new Vorgang();
        //this.vorgang.setName("Testvorgang");
        // View
        this.oeffnenview = new OeffnenView(this);
        
        // Observer für die Modelle
        //this.vorgang.addObserver(this.vorgangView);
        this.oeffnenview.setVisible(true);
        oeffnenview.pack();
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
