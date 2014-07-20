package edu.fh.netzview;
import edu.fh.application.Netzplanung;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import edu.fh.netzcontroller.GanttController;
import edu.fh.netzcontroller.VorganganzeigenController;
import edu.fh.netzplanModell.ChartModel;
import edu.fh.netzplanModell.Vorgang;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;



/**
 *
 * @author Anton
 */
public class GanttView extends JFrame {
    
    Vorgang vorgangs;
    private LinkedList<Vorgang> vorgangList= new LinkedList<Vorgang>();
public GanttView(final String title, final GanttController controller, final ChartModel chartmodel) {

        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //vorgangList =netzPl.getSortierteVorgaenge();
        IntervalCategoryDataset dataset = chartmodel.createDatasetGantt();
        final JFreeChart chart = createChart(dataset);
        
        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        chartPanel.setFocusable(true);

        chartPanel.addChartMouseListener(new ChartMouseListener() {

    public void chartMouseClicked(ChartMouseEvent e) {
       
        controller.mousecklicked(e);

    }

            public void chartMouseMoved(ChartMouseEvent event) {

            }

     
});
        chartPanel.addKeyListener(new KeyListener() {
        
            public void keyTyped(KeyEvent e) {
                System.out.println("Taste wurde gedrückt");
                try {
                    controller.keyPressed(e);
                } catch (SQLException ex) {
                    Logger.getLogger(GanttView.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            public void keyReleased(KeyEvent e) {
                
            }

            public void keyPressed(KeyEvent e) {
                
            }
        
        
        });
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
            true,                // include legend
            false,                // tooltips
            false                // urls
        );    
        
      
        
        CategoryPlot plot =  (CategoryPlot) chart.getPlot();
        ValueAxis range = plot.getRangeAxis();
        range.setVisible(false);
        
        
return chart;    
    }
    
   
 
    
   

}
