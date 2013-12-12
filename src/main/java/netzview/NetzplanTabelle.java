/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

import datenbank.SQLConnect;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import netzplan.Netzplan;

/**
 *
 * @author fseiler
 */
public class NetzplanTabelle extends JPanel{
    
     public NetzplanTabelle() {
        super(new GridLayout(1,0));
 
        String[] columnNames = {"Netzplan ID",
                                "Netzplanname"};
        
        Object[][] data = null;
        
        try {
            LinkedList<Netzplan> netzplanListe = new SQLConnect().ladeAlleNetzplaene();
            data = new Object[netzplanListe.size()][2];
            
            int i = 0;
            for (Netzplan netzplan : netzplanListe) {
                data[i][0] = netzplan.getId();
                data[i][1] = netzplan.getName();
                i++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(SimpleTableDemo.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
 
        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);
 
        //Add the scroll pane to this panel.
        add(scrollPane);
    }
    
}
