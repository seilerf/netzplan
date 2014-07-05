/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;

import edu.fh.datenbank.SQLConnect;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.GroupLayout;
import static javax.swing.GroupLayout.Alignment.LEADING;
import static javax.swing.GroupLayout.Alignment.TRAILING;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import static javax.swing.LayoutStyle.ComponentPlacement.RELATED;
import edu.fh.netzcontroller.NetzOeffnenController;
import edu.fh.netzplanModell.Netzplan;

/**
 *
 * @author Anton
 */
public class OeffnenView extends JFrame implements Observer{

    private boolean editable;
    
    public OeffnenView(NetzOeffnenController controller) {
        
        this.setTitle("Netzplan öffnen");
        
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
            Logger.getLogger(OeffnenView.class.getName()).log(Level.SEVERE, null, ex);
        }
 
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollpane = new JScrollPane(table);
        JButton btnOeffnen = new JButton("Öffnen");
        
        btnOeffnen.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("Lade " + table.getValueAt(table.getSelectedRow(), 1) + "...");
                    Netzplan netzplan = new SQLConnect().ladeNetzplan((Integer)table.getValueAt(table.getSelectedRow(), 0));
                    System.out.println("Netzplan geladen!");                            
                } catch(SQLException sqlE){
                    System.out.println("Fehler bei der Datenbankabfrage: " + sqlE.getMessage());
                } catch (ArrayIndexOutOfBoundsException npe) {
                    System.out.println("Fehler: " + npe.getMessage());
                }
            }
        });
        
        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                    .addComponent(scrollpane)
                    .addGroup(TRAILING, layout.createSequentialGroup()
                        .addGap(0, 323, Short.MAX_VALUE)
                        .addComponent(btnOeffnen)))
                .addContainerGap())
        );
        
        layout.setVerticalGroup(
            layout.createParallelGroup(LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(scrollpane, PREFERRED_SIZE, 146, PREFERRED_SIZE)
                .addPreferredGap(RELATED)
                .addPreferredGap(RELATED)
                .addComponent(btnOeffnen)
                .addGap(0, 109, Short.MAX_VALUE))
        );

        pack();
    }
    
    public void update(Observable o, Object arg) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

