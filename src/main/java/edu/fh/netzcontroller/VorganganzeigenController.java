/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import edu.fh.netzplanModell.Vorgang;
import edu.fh.netzview.VorganganzeigenView;

/**
 *
 * @author Anton
 */
public class VorganganzeigenController implements ActionListener{

    private Vorgang vorgang;
    private VorganganzeigenView vorgangView;

    public VorganganzeigenController(Vorgang vorgangs) {
        //Modell
        this.vorgang = vorgangs;
        // View
        this.vorgangView = new VorganganzeigenView(this,vorgang);
        
        this.vorgangView.setVisible(true);
        vorgangView.pack();
        
    }

    /**
     *  Bei Drücken des Speichern Buttons werde die Werte an das Objekt übergeben
     * @param  vorgang
     */
    public void actionPerformed(String vorgang) {
        
        
        //this.vorgang= vorgang;
        this.vorgang.setDauer(Double.parseDouble(vorgang));
         
         
    }
    
    
    /**
     *  Bei Drücken des beenden Buttons schließt sich das Fenster
     * @param e
     * 
     */
    public void actionPerformed(ActionEvent e) {
        vorgangView.dispose();
    }

    
    
   
    
    
}

