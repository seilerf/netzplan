/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import netzplan.Vorgang;
import netzview.VorgangView;

/**
 *
 * @author fseiler
 */
public class VorgangController implements ActionListener{

    private Vorgang vorgang;
    private VorgangView vorgangView;

    public VorgangController() {
        //Modell
        this.vorgang = new Vorgang();
        this.vorgang.setName("Neuer Vorgang");
        // View
        this.vorgangView = new VorgangView(this);
        
        // Observer für die Modelle
        this.vorgang.addObserver(this.vorgangView);
        this.vorgangView.setVisible(true);
        vorgangView.pack();
        
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public String getVorgangName(){
        return this.vorgang.getName();
    }
    
}
