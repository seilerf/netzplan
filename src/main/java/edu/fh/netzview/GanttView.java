package edu.fh.netzview;
import edu.fh.application.Netzplanung;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.WindowConstants;
import edu.fh.netzcontroller.GanttController;
import edu.fh.netzcontroller.VorgangController;
import edu.fh.netzcontroller.VorganganzeigenController;
import edu.fh.netzplanModell.Vorgang;
import java.awt.Cursor;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;



/**
 *
 * @author Anton
 */
public class GanttView extends JFrame {
    
    Vorgang vorgangs;
    private LinkedList<Vorgang> vorgangList= new LinkedList<Vorgang>();
public GanttView(final String title, GanttController controller, Netzplanung netzPl) {

        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vorgangList =netzPl.getSortierteVorgaenge();
        final IntervalCategoryDataset dataset = createDataset();
        final JFreeChart chart = createChart(dataset);
        
        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        
        chartPanel.addChartMouseListener(new ChartMouseListener() {

    public void chartMouseClicked(ChartMouseEvent e) {
       
        
        System.out.println(e.getEntity());
        
        
        String string = e.getEntity().toString();
        String[] parts = string.split(","); //erstes Komma entfernen
        String part1 = parts[0]; // 004
        String part2 = parts[1]; 
        
        String[] parts2 = part2.split(","); //zweites Komma entfernen
        String part11 = parts2[0];
        System.out.println(part11);
        
        
        String[] parts3 = part11.split("="); //Endgültigen Namen bestimmen
        String endstring = parts3[1];
        System.out.println(endstring);
        
        vorgangs=searchVorgang(endstring);
        
        
        VorganganzeigenController vorgang = new VorganganzeigenController(vorgangs);

    }

            public void chartMouseMoved(ChartMouseEvent event) {

            }

     
        
   

});

    }

      /**
     * Creates a sample dataset for a Gantt chart.
     *
     * @return The dataset.
     */
    public  IntervalCategoryDataset createDataset() {
        
       
        int start = 1;
        final TaskSeries s1 = new TaskSeries("DiesdasAnanas");
        
        
        Iterator<Vorgang> itr = vorgangList.iterator();
        
        
        
        
        Vorgang suchvorgang = null;
        
   while (itr.hasNext()) {
        suchvorgang= itr.next();
        s1.add(new Task(suchvorgang.getName(),
        new Date(start), new Date((long) (suchvorgang.getDauer()+ start))));
              
        start+=suchvorgang.getDauer();
    }
        
      
        /*                            
        s1.add(new Task("Obtain Approval",
               new SimpleTimePeriod(date(9, Calendar.APRIL, 2014),
                                    date(9, Calendar.APRIL, 2014))));
        s1.add(new Task("Requirements Analysis",
               new SimpleTimePeriod(date(10, Calendar.APRIL, 2014),
                                    date(5, Calendar.MAY, 2014))));
        s1.add(new Task("Design Phase",
               new SimpleTimePeriod(date(6, Calendar.MAY, 2014),
                                    date(30, Calendar.MAY, 2014))));
        s1.add(new Task("Design Signoff",
               new SimpleTimePeriod(date(2, Calendar.JUNE, 2014),
                                    date(2, Calendar.JUNE, 2014))));
        s1.add(new Task("Alpha Implementation",
               new SimpleTimePeriod(date(3, Calendar.JUNE, 2014),
                                    date(31, Calendar.JULY, 2014))));
        s1.add(new Task("Design Review",
               new SimpleTimePeriod(date(1, Calendar.AUGUST, 2014),
                                    date(8, Calendar.AUGUST, 2014))));
        s1.add(new Task("Revised Design Signoff",
               new SimpleTimePeriod(date(10, Calendar.AUGUST, 2014),
                                    date(10, Calendar.AUGUST, 2014))));
        s1.add(new Task("Beta Implementation",
               new SimpleTimePeriod(date(12, Calendar.AUGUST, 2014),
                                    date(12, Calendar.SEPTEMBER, 2014))));
        s1.add(new Task("Testing",
               new SimpleTimePeriod(date(13, Calendar.SEPTEMBER, 2014),
                                    date(31, Calendar.OCTOBER, 2014))));
        s1.add(new Task("Final Implementation",
               new SimpleTimePeriod(date(1, Calendar.NOVEMBER, 2014),
                                    date(15, Calendar.NOVEMBER, 2014))));
        s1.add(new Task("Signoff",
               new SimpleTimePeriod(date(28, Calendar.NOVEMBER, 2014),
                                    date(30, Calendar.NOVEMBER, 2014))));

      */

        final TaskSeriesCollection collection = new TaskSeriesCollection();
        collection.add(s1);
        

        return collection;
    }

    /**
     * Utility method for creating <code>Date</code> objects.
     *
     * @param day  the date.
     * @param month  the month.
     * @param year  the year.
     *
     * @return a date.
     */
    private static Date date(final int day, final int month, final int year) {

        final Calendar calendar = Calendar.getInstance();
        calendar.set(0, 0, day);
        final Date result = calendar.getTime();
        return result;

    }
        
    /**
     * Creates a chart.
     * 
     * @param dataset  the dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "Gantt Chart Vorgänge",  // chart title
            "Vorgänge",              // domain axis label
            "Datum",              // range axis label
            dataset,             // data
            false,                // include legend
            false,                // tooltips
            false                // urls
        );    
        
      
        
        CategoryPlot plot =  (CategoryPlot) chart.getPlot();
        ValueAxis range = plot.getRangeAxis();
        range.setVisible(false);
        
        
return chart;    
    }
    
    /**
     * Starting point for the demonstration application.
     *
     * @param args  ignored.
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
