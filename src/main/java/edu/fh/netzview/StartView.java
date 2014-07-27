/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;

import edu.fh.application.Netzplanung;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import edu.fh.netzcontroller.BmgController;
import edu.fh.netzcontroller.GanttController;
import edu.fh.netzcontroller.NewNetzController;
//import edu.fh.netzcontroller.NetzOeffnenController;

/**
 *
 * @author fseiler
 */
public class StartView extends JFrame{
    Netzplanung netzPll;
    
    public StartView(   Netzplanung netzPl){
        this.netzPll=netzPl;
        this.setVisible(true);
        this.setSize(400, 400);
        this.setTitle("Netzplan");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //this.sortierteVorgänge=sortierteVorgänge;
        
        JMenuBar menuBar = new JMenuBar();
        JMenu netzplanMenu = new JMenu("Netzplan");
        JMenu ansichtMenu = new JMenu("Ansicht");
        
        JMenuItem neu = new JMenuItem("Neu");
        
        
           neu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             NewNetzController netzcont = new NewNetzController();
        }
    }); 
        
       
        JMenuItem oeffnen = new JMenuItem("Öffnen...");
        
         oeffnen.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             //NetzOeffnenController netzoeffnen = new NetzOeffnenController();
        }
    }); 
        
        
        JMenuItem speichern = new JMenuItem("Speichern...");
        JMenuItem vorgangNeu = new JMenuItem("Neuer Vorgang...");
        
        
        vorgangNeu.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             //VorgangController neuvorgang = new VorgangController();
        }
    }); 
        
        
        JMenuItem beenden = new JMenuItem("Beenden");
        
        beenden.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
              System.exit(0);
        }
    }); 
       
        
        netzplanMenu.add(neu);
        netzplanMenu.add(oeffnen);
        netzplanMenu.add(speichern);
        netzplanMenu.add(vorgangNeu);
        netzplanMenu.insertSeparator(4);
        netzplanMenu.add(beenden);
        
        
        
        JMenuItem ganttView = new JMenuItem("Gantt-Diagramm");
        
        ganttView.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             
            
             GanttController gantt = new GanttController( netzPll);
        }
    }); 
        
        
        
        
        
        JMenuItem vorgangsZeit = new JMenuItem("Vorgangs-Zeit-Diagramm");
        JMenuItem bmgAuslast = new JMenuItem("BMG-Auslastung");
        
        
            bmgAuslast.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
             BmgController bmg = new BmgController(netzPll);
        }
    }); 
        
        
        ansichtMenu.add(ganttView);
        ansichtMenu.add(vorgangsZeit);
        ansichtMenu.add(bmgAuslast);
        
        menuBar.add(netzplanMenu);
        menuBar.add(ansichtMenu);
        this.setJMenuBar(menuBar);
        
    }
    
}
