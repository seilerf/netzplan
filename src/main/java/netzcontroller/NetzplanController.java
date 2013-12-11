/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import datenbank.SQLConnect;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            this.netzplan = this.erstelleNetzplan();
        } catch (SQLException ex) {
            Logger.getLogger(NetzplanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.netzplanView = new NetzplanView(this);
    }
    
    private Netzplan erstelleNetzplan() throws SQLException{
        ResultSet rsNetzplan = new SQLConnect().ladeNetzplan();
        this.netzplan = null;
        
        while(rsNetzplan.next()) {
            this.netzplan = new Netzplan((Integer)rsNetzplan.getObject("idNetzplan"), (String)rsNetzplan.getObject("nameNetzplan"));
        }
        
        return this.netzplan;
    }
    
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
