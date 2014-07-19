/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzview;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import netzcontroller.BmgController;
import netzcontroller.GanttController;
import netzcontroller.NewNetzController;
import netzcontroller.VorgangController;
import org.jfree.ui.RefineryUtilities;

/**
 *
 * @author fseiler
 */
public class StartView extends JFrame{
    
    private JMenuBar menuBar;

    private JMenu netzplanMenu;
    private JMenu ansichtMenu;

    private JMenuItem neu;
    private JMenuItem oeffnen;
    private JMenuItem speichern;
    private JMenuItem vorgangNeu;
    private JMenuItem beenden;
    private JMenuItem ganttView;
    private JMenuItem vorgangsZeit;
    private JMenuItem bmgAuslast;

    public StartView(){
        this.erzeugeMenu();
        this.erstelleListener();
        this.setzeMenuEin();
        
        this.setVisible(true);
        this.setSize(400, 400);
        this.setTitle("Netzplan");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    
    private void erzeugeMenu(){
        
        menuBar = new JMenuBar();

        netzplanMenu = new JMenu("Netzplan");
        ansichtMenu = new JMenu("Ansicht");

        neu = new JMenuItem("Neu");
        oeffnen = new JMenuItem("Öffnen...");
        speichern = new JMenuItem("Speichern...");
        vorgangNeu = new JMenuItem("Neuer Vorgang...");
        beenden = new JMenuItem("Beenden");
        ganttView = new JMenuItem("Gantt-Diagramm");
        vorgangsZeit = new JMenuItem("Vorgangs-Zeit-Diagramm");
        bmgAuslast = new JMenuItem("BMG-Auslastung");
    }
    
    private void erstelleListener(){
        
        neu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewNetzController netzcont = new NewNetzController();
            }
        });
        
        /*oeffnen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NetzOeffnenController netzoeffnen = new NetzOeffnenController();
            }
        }); */
        
        vorgangNeu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                VorgangController neuvorgang = new VorgangController();
            }
        }); 
        
        beenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        ganttView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GanttController gantt = new GanttController();
            }
        });
        
        bmgAuslast.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                 BmgController bmg = new BmgController();
            }
        });
    }
    
    private void setzeMenuEin(){
        
        netzplanMenu.add(neu);
        netzplanMenu.add(oeffnen);
        netzplanMenu.add(speichern);
        netzplanMenu.add(vorgangNeu);
        netzplanMenu.insertSeparator(4);
        netzplanMenu.add(beenden);

        ansichtMenu.add(ganttView);
        ansichtMenu.add(vorgangsZeit);
        ansichtMenu.add(bmgAuslast);

        menuBar.add(netzplanMenu);
        menuBar.add(ansichtMenu);
        this.setJMenuBar(menuBar);
    
    }
}
