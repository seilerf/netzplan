/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzview;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import edu.fh.netzcontroller.NewNetzController;

/**
 *
 * @author Florian Seiler
 */
public class NetzplanView extends AbstractView{
    
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
    
    private JScrollPane scrollpane;
    private JButton btnVorgangOeffnen;

    public NetzplanView(){
        this.scrollpane = new JScrollPane();
        this.add(scrollpane);
        this.btnVorgangOeffnen = new JButton("Vorgang öffnen");
        
        this.erzeugeMenu();
        this.erstelleListener();
        this.setzeMenuEin();
        
        this.setVisible(true);
        this.setSize(600, 400);
        this.setTitle("Netzplan");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        bmgAuslast = new JMenuItem("BMG-Auslastung");
        
    }
    
    public void setMenuOeffnenListener(ActionListener l){
        this.oeffnen.addActionListener(l);
    }
    
    public void setVorgangNeuListener(ActionListener l){
        this.vorgangNeu.addActionListener(l);
    }
    
    public void setVorgangOeffnenListener(ActionListener l){
        this.btnVorgangOeffnen.addActionListener(l);
    }
    
    public void setGanttOeffnenListener(ActionListener l){
        this.ganttView.addActionListener(l);
    }
    
    public void setBmgListener(ActionListener l){
        this.bmgAuslast.addActionListener(l);
    }
    
    private void erstelleListener(){
        
        neu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                NewNetzController netzcont = new NewNetzController();
            }
        });
        
        beenden.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
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
        ansichtMenu.add(bmgAuslast);

        menuBar.add(netzplanMenu);
        menuBar.add(ansichtMenu);
        this.setJMenuBar(menuBar);
    }
    
    /**
     * Mit dieser Methode wird die GUI gezeichnet.
     * @author Florian Seiler
     */
    private void setLayout(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(scrollpane);
    }
    
    public void setScrollPane(JScrollPane scrollpane) {
        this.remove(this.scrollpane);
        this.scrollpane = scrollpane;
        this.add(scrollpane, BorderLayout.CENTER);
        this.add(btnVorgangOeffnen, BorderLayout.SOUTH);
        this.revalidate();
    }
}
