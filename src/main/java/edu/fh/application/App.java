package edu.fh.application;

import edu.fh.datenbank.SQLConnect;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.fh.netzcontroller.NetzplanController;
import edu.fh.netzplanModell.Netzplan;
import edu.fh.netzview.StartView;


public class App {
    public static void main(String[] args) {
        
        SQLConnect sql = new SQLConnect();
        Netzplan netz = new Netzplan();
        Netzplanung netzPl; 
        
        
        
       try {
           netz = sql.ladeNetzplan(1);
           netzPl = new Netzplanung(netz.getId());
           netzPl.netzPlanBerechnung();
            
       } catch (SQLException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
}
