/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.fh.netzplanModell;

import edu.fh.application.Netzplanung;
import edu.fh.datenbank.SQLConnect;
import edu.fh.netzview.BmgView;
import static java.lang.String.valueOf;
import java.sql.SQLException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;

/**
 *
 * @author Anton
 */
public class ChartModel {
    
    private LinkedList<Vorgang> vorgangList = new LinkedList<Vorgang>();
    private LinkedList<Betriebsmittelgruppe> betriebsmittelgruppe = new LinkedList<Betriebsmittelgruppe>();
    private SQLConnect con = new SQLConnect();
    private Netzplanung netzPl;

    

    public ChartModel(Netzplanung netzPl){
        
        this.netzPl=netzPl;
        vorgangList=netzPl.getSortierteVorgaenge();

    }
    /**
     * Erstellt das Dataset mit den Vorgängen für das Ganttchart
     * benutzt die sortierten Vorgänge von der Netzplanung
     * @return  dataset
     */
    public  IntervalCategoryDataset createDatasetGantt() {
        
        final TaskSeries s1 = new TaskSeries("Vorgänge");
        final TaskSeries s2 = new TaskSeries("Puffer");
        
        Iterator<Vorgang> itr = vorgangList.iterator();
        Vorgang suchvorgang = null;
        
        //geht die sortierten vorgangsliste durch und erstellt jeweils ein Task auf dem Chart + Puffer
            while (itr.hasNext()) {
                 suchvorgang= itr.next();

                 // Task aus der Vorgangs liste
                 Task task = new Task(suchvorgang.getName(), new Date((long) suchvorgang.getFaz()), new Date((long) (suchvorgang.getFez())));
                s1.add(task);
                 String s = "s";

                 // Puffer des Task darüber
                 Task subtask = new Task(suchvorgang.getName()  ,new Date((long) suchvorgang.getFez()), new Date((long) (suchvorgang.getSez())));
                 s2.add(subtask);

             }
        
        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        collection.add(s2);
        

        return collection;
    }
    
    
    
    
    
    /**
     * Erstellt das Dataset mit den BMG und dazugehörigen Vorgängen für das BmgChart
     * benutzt die sortierten Vorgänge von der Netzplanung
     * zu einem Vorgang werden die Betriebsmittel geladen, welche dieser benutzt
     * @return  dataset
     */
     public CategoryDataset createDatasetBMG() {
       DefaultCategoryDataset result = new DefaultCategoryDataset() ;

       Iterator<Vorgang> itr = vorgangList.iterator();
       Vorgang suchvorgang = null;
       
        //geht die sortierte vorgangsliste durch 
            while (itr.hasNext()) {
                suchvorgang=itr.next();

                betriebsmittelgruppe.clear(); //geladenenBetriebsmittel werden immer zurückgesetzt, weil sie jeweils nur für einen Vorgang geladen werden
                   // es werden die Betriebsmittel zu dem einen Vorgang geladen
                try {
                       betriebsmittelgruppe=con.ladeAlleBetrZuVorg(suchvorgang.getVorgangId(),netzPl.getIdNetzplan());

                               Iterator<Betriebsmittelgruppe> itrbet = betriebsmittelgruppe.iterator();
                               Betriebsmittelgruppe betmittel = null;
                               
                        //geht die Betriebsmittel zu einem VOrgang durch 
                        while (itrbet.hasNext())
                         {   
                             betmittel=itrbet.next();
                             String n = valueOf(betmittel.getBetrMittelGrId());

                             //fügt einen Datensatz mit jeweils einem Vorgang und einem Betriebsmittel dem Datenset hinzu
                             result.addValue(suchvorgang.getDauer(),suchvorgang.getName(),betmittel.getNameBetrMittelGr());

                         }
                       
                     } 
                   catch (SQLException ex) {
                               Logger.getLogger(BmgView.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                }

        return result;
    }
    
    /**
     *  Bekommt den Namen des angeklickten Vorgangs auf dem Chart und sucht das entsprechende Vorgangsobjekt
     * @param angeklicktervorgang
     * @return gesuchter Vorgang - suchvorgang
     */
    public Vorgang searchVorgang(String angeklicktervorgang){
        Iterator<Vorgang> itr = vorgangList.iterator();
        Vorgang suchvorgang = null;
        
    while (itr.hasNext()) {
        suchvorgang=itr.next();
      if (suchvorgang.getName().equals(angeklicktervorgang))  
      {
                return suchvorgang;
                //System.out.println(itr.next());

      }
      
    }
        
        return null;
    }
    
}
