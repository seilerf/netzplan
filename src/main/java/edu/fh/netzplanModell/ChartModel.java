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
import org.jfree.data.time.SimpleTimePeriod;

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
     * Creates a sample dataset for a Gantt chart.
     *
     * @return The dataset.
     */
    public  IntervalCategoryDataset createDatasetGantt() {
        
       
      
        final TaskSeries s1 = new TaskSeries("Vorgänge");
        final TaskSeries s2 = new TaskSeries("Puffer");
        
        Iterator<Vorgang> itr = vorgangList.iterator();
        
        
        
        
        Vorgang suchvorgang = null;
        
   while (itr.hasNext()) {
        suchvorgang= itr.next();
        
        Task task = new Task(suchvorgang.getName(), new Date((long) suchvorgang.getFaz()), new Date((long) (suchvorgang.getFez())));
       s1.add(task);
        String s = "s";
               
              
        
        //subtask.setPercentComplete(0.00);
        //task.addSubtask(subtask);
        
        Task subtask = new Task(suchvorgang.getName()  ,new Date((long) suchvorgang.getFez()), new Date((long) (suchvorgang.getSez())));
        s2.add(subtask);
        
       
    }
        
      

        TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        collection.add(s2);
        

        return collection;
    }
    
    
    
    
    
    
     public CategoryDataset createDatasetBMG() {
       DefaultCategoryDataset result = new DefaultCategoryDataset() ;

        
       
        
        Iterator<Vorgang> itr = vorgangList.iterator();
        
       
        Vorgang suchvorgang = null;
        
        
        
       
        
        
        
    while (itr.hasNext()) {
        suchvorgang=itr.next();
        
        betriebsmittelgruppe.clear();
           try {
               betriebsmittelgruppe=con.ladeAlleBetrZuVorg(suchvorgang.getVorgangId(),netzPl.getIdNetzplan());
                       
                       Iterator<Betriebsmittelgruppe> itrbet = betriebsmittelgruppe.iterator();
                       Betriebsmittelgruppe betmittel = null;
          
               while (itrbet.hasNext())
                {   
                    betmittel=itrbet.next();
                    String n = valueOf(betmittel.getBetrMittelGrId());
                   
                    result.addValue(suchvorgang.getDauer(),suchvorgang.getName(),betmittel.getNameBetrMittelGr());
                    

                }
           } catch (SQLException ex) {
               Logger.getLogger(BmgView.class.getName()).log(Level.SEVERE, null, ex);
           }
                
      
     
    }

        
        
        
        return result;
    }
    
    /**
     *
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
