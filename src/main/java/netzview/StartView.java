/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author fseiler
 */
public class StartView extends JFrame{
    
    public StartView(){
        this.setVisible(true);
        this.setSize(400, 400);
        this.setTitle("Netzplan");
        
        JMenuBar menuBar = new JMenuBar();
        JMenu netzplanMenu = new JMenu("Netzplan");
        JMenu ansichtMenu = new JMenu("Ansicht");
        
        JMenuItem neu = new JMenuItem("Neu");
        JMenuItem oeffnen = new JMenuItem("Öffnen...");
        JMenuItem speichern = new JMenuItem("Speichern...");
        JMenuItem vorgangNeu = new JMenuItem("Neuer Vorgang...");
        JMenuItem beenden = new JMenuItem("Beenden");
        
        netzplanMenu.add(neu);
        netzplanMenu.add(oeffnen);
        netzplanMenu.add(speichern);
        netzplanMenu.add(vorgangNeu);
        netzplanMenu.insertSeparator(4);
        netzplanMenu.add(beenden);
        
        JMenuItem ganttView = new JMenuItem("Gantt-Diagramm");
        JMenuItem vorgangsZeit = new JMenuItem("Vorgangs-Zeit-Diagramm");
        JMenuItem bmgAuslast = new JMenuItem("BMG-Auslastung");
        
        ansichtMenu.add(ganttView);
        ansichtMenu.add(vorgangsZeit);
        ansichtMenu.add(bmgAuslast);
        
        menuBar.add(netzplanMenu);
        menuBar.add(ansichtMenu);
        this.setJMenuBar(menuBar);
        
    }
    
}
