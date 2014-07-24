/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package netzcontroller;

import datenbank.SQLConnect;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
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
    private final NetzplanView netzplanView;
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
        this.showView();
        this.addListener();
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
    public final void addListener(){
        this.netzplanView.setMenuOeffnenListener(new MenuOeffnenListener());
        this.netzplanView.setVorgangNeuListener(new VorgangNeuListener());
        this.netzplanView.setVorgangOeffnenListener(new VorgangOeffnenListener());
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
     * Listener für den Menüeintrag "Öffnen" in der NetzplanView.
     * @author Florian Seiler
     */
    class MenuOeffnenListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            erstelleNetzplanTabelle();
            oeffnenView = new OeffnenView(netzplantabelle);
            oeffnenView.setBtnOeffnenListener(new btnOeffnenListener());
            oeffnenView.setVisible(true);
        }
    }
    
    /**
     * Listener für das Öffnen des Dialogs zum Anlegen eines neuen Vorgangs
     * @author Florian Seiler
     */
    class VorgangNeuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            System.out.println("Netzplan-ID: " + netzplanModel.getId());
            VorgangController vorgangContr = new VorgangController(erstelleNetzplanListe());
        }
    }
    
    /**
     * Listener, um per Doppelklick auf einen Eintrag in der Vorgangstabelle eine Detailansicht zu öffnen.
     * @author Florian Seiler
     */
    class VorgangOeffnenListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            int id;
            VorgangController vorgangContr;
            try {
                id = (Integer)vorgangstabelle.getValueAt(vorgangstabelle.getSelectedRow(), 0);
                Vorgang vorgang = new SQLConnect().ladeVorgang(id);
                vorgangContr = new VorgangController(erstelleNetzplanListe(), vorgang);
            } catch (NullPointerException npe){
                System.out.println("Nullpointer");
                id = 0;
                vorgangContr = new VorgangController(erstelleNetzplanListe());
                System.out.println("NetzplanView wieder aufgerufen.");
                netzplanView.repaint();
            } catch (SQLException ex) {
                System.out.println("SQL-Exc.");
                id = 0;
                vorgangContr = new VorgangController(erstelleNetzplanListe());
                System.out.println("NetzplanView wieder aufgerufen.");
                netzplanView.repaint();
            }
        }
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
                JScrollPane scrollpane = new JScrollPane(erstelleVorgangsTabelle());
                netzplanView.setScrollPane(scrollpane);
            } catch (ArrayIndexOutOfBoundsException npe) {
                System.out.println("Fehler: " + npe.getMessage());
            }
        }
    }
    
    private String[] erstelleNetzplanListe(){
        LinkedList<Netzplan> netzplanListe = new SQLConnect().ladeAlleNetzplaene();
        String[] netzplaene = new String[netzplanListe.size()];
        int i=0;
        for (Netzplan netzplan : netzplanListe){
            netzplaene[i] = netzplan.getName();
            i++;
        }
        System.out.println("Netzplan: " + netzplaene[0]);
        return netzplaene;
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
        String[] spalten = {"ID", "Vorgangsname", "Dauer"};
        Object[][] data = null;
        data = new Object[vorgangsliste.size()][3];
        
        try{
            ListIterator<Vorgang> iterator = vorgangsliste.listIterator();
            int i = 0;
            Vorgang vorgang;
            while (iterator.hasNext()){
                vorgang = iterator.next();
                data[i][0] = vorgang.getVorgangId();
                data[i][1] = vorgang.getName();
                data[i][2] = vorgang.getDauer();
                i++;
            }
        }
        catch(NullPointerException e){
            data[0][0] = null;
        }

        vorgangstabelle = new JTable(new DefaultTableModel(data, spalten));
        vorgangstabelle.getColumnModel().getColumn(0).setPreferredWidth(50);
        //vorgangstabelle.setPreferredScrollableViewportSize(new Dimension(500, 70));
        //vorgangstabelle.setFillsViewportHeight(true);
        
        return vorgangstabelle;
    }
}
