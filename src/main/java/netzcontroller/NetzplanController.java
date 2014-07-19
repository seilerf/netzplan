/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import datenbank.SQLConnect;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Vector;
import static javax.swing.GroupLayout.PREFERRED_SIZE;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import netzplan.Netzplan;
import netzplan.Vorgang;
import netzview.NetzplanView;
import netzview.OeffnenView;

/**
 * Ein Controller, der die NetzplanView und die OeffnenView verwaltet.
 * @author Florian Seiler
 */
public class NetzplanController{

    // Model
    private Netzplan netzplanModel;
    // Views
    private NetzplanView netzplanView;
    private OeffnenView oeffnenView;
    // Tabelle zu Auflistung der Netzpläne in der OeffnenView
    private JTable netzplantabelle;
    // Tabelle zu Auflistung der Vorgänge eines Netzplans in der NetzplanView
    private JTable vorgangstabelle;

    /**
     * Konstruktor des Netzplancontrollers.
     * @author Florian Seiler
     */
    public NetzplanController() {
        this.netzplanModel = new Netzplan();
        this.netzplanView = new NetzplanView();
    }
    
    /**
     * Methode zum Anzeigen der NetzplanView.
     * @author Florian Seiler
     */
    public void showView(){
        this.netzplanView.setVisible(true);
    }
    
    /**
     * Methode zum Hinzufügen der Listener zur NetzplanView
     * @author Florian Seiler
     */
    public void addNetzListener(){
        this.netzplanView.setMenuOeffnenListener(new MenuOeffnenListener());
    }
    
    /**
     * Methode zum Hinzufügen der Listener zur OeffnenView
     * @author Florian Seiler
     */
    public void addOeffnenListener(){
        oeffnenView.setBtnOeffnenListener(new btnOeffnenListener());
    }
    
    /**
     * Listener für den Menüeintrag "Öffnen" in der NetzplanView.
     * @author Florian Seiler
     */
    class MenuOeffnenListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            erstelleNetzplanTabelle();
            oeffnenView = new OeffnenView(netzplantabelle);
            addOeffnenListener();
            oeffnenView.setVisible(true);
        }
    }
    
    /**
     * Ändert im zugehörigen Modell den Netzplan.
     * @param netzplan
     * @author Florian Seiler
     */
    private void setNetzplan(Netzplan netzplan){
        this.netzplanModel = netzplan;
    }
    
    /**
     * Methode zum Laden eines Netzplans in die NetzplanView.
     * @author Florian Seiler
     */
    private void ladeNetzplanInView(){
        JScrollPane scrollpane = new JScrollPane(vorgangstabelle);
        netzplanView.setScrollPane(scrollpane);
        //netzplanView.revalidate();
    }
    
    /**
     * ActionListener für den Button "Öffnen", der in der ÖffnenView den ausgewähhlten Netzplan öffnet.
     * Vorher muss die Methode "createTable" aufgerufen, da die Tabelle sonst keine Daten beinhaltet.
     * @author Florian Seiler
     */
    class btnOeffnenListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                int id = (Integer)netzplantabelle.getValueAt(netzplantabelle.getSelectedRow(), 0);
                Netzplan netzplan = new SQLConnect().ladeNetzplan(id);
                System.out.println("Netzplan " + netzplan.getName() + " mit der ID " + id + " geladen!");
                setNetzplan(netzplan);
                oeffnenView.dispose();
                erstelleVorgangsTabelle();
                ladeNetzplanInView();

            } catch (ArrayIndexOutOfBoundsException npe) {
                System.out.println("Fehler: " + npe.getMessage());
            }
        }
    }
        
    /**
     * Erstellt eine Tabelle, die in der ÖffnenView alle Netzpläne zur Auswahl auflistet
     * @author Florian Seiler
     */
    private void erstelleNetzplanTabelle(){
        LinkedList<Netzplan> netzplanListe = new SQLConnect().ladeAlleNetzplaene();
        String[] columnNames = {"Netzplan ID", "Netzplanname"};
        Object[][] data = null;
        data = new Object[netzplanListe.size()][2];
        
        int i = 0;
        for (Netzplan netzplan : netzplanListe) {
            data[i][0] = netzplan.getId();
            data[i][1] = netzplan.getName();
            i++;
        }
        
        netzplantabelle = new JTable(data, columnNames);
        netzplantabelle.setPreferredScrollableViewportSize(new Dimension(500, 70));
        netzplantabelle.setFillsViewportHeight(true);
    }
    
    /**
     * Erstellt eine Tabelle, die alle Vorgänge eines Netzplans auflistet.
     * @author Florian Seiler
     */
    private JTable erstelleVorgangsTabelle(){
        LinkedList<Vorgang> vorgangsliste = this.netzplanModel.getVorgaenge();
        String[] spalten = {"Vorgangsname", "Dauer"};
        Object[][] data = null;
        data = new Object[vorgangsliste.size()][2];
        
        ListIterator<Vorgang> iterator = vorgangsliste.listIterator();
        int i = 0;
        for (Vorgang vorgang : vorgangsliste){
            data[i][0] = vorgang.getName();
            data[i][1] = vorgang.getDauer();
            i++;
        }
        
        System.out.println("Anzahl Vorgänge: " + vorgangsliste.size());
        
        vorgangstabelle = new JTable(data, spalten);
        vorgangstabelle.setPreferredScrollableViewportSize(new Dimension(500, 70));
        vorgangstabelle.setFillsViewportHeight(true);
        
        return vorgangstabelle;
    }
}
