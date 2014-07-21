package edu.fh.netzview;
import javax.swing.JFrame;
import edu.fh.netzcontroller.GanttController;
import edu.fh.netzplanModell.ChartModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
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



/**
 *
 * @author Anton
 */
public class GanttView extends JFrame {
    
    
public GanttView(final String title, final GanttController controller, final ChartModel chartmodel) {

        super(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        IntervalCategoryDataset dataset = chartmodel.createDatasetGantt();
        final JFreeChart chart = createChart(dataset);
        
        // Chart zum Panel hinzufügen
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        chartPanel.setFocusable(true); //damit der keylistener auf dem Panel funktioniert

        chartPanel.addChartMouseListener(new ChartMouseListener() {

    public void chartMouseClicked(ChartMouseEvent e) {
       
        controller.mousecklicked(e);    //übergabe des events an Controller

    }

            public void chartMouseMoved(ChartMouseEvent event) {

            }

     
});
        chartPanel.addKeyListener(new KeyListener() {
     /**
     * @param e
     * ruft auf dem Controller die Keypressed Methode auf
     */
            public void keyTyped(KeyEvent e) {
                try {
                    System.out.println("Taste wurde gedrückt");
                    controller.keyTyped(e);
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
     * Kreiert das Chart.
     * 
     * @param dataset  
     * 
     * @return das Ganttchart.
     */
    private JFreeChart createChart(final IntervalCategoryDataset dataset) {
        final JFreeChart chart = ChartFactory.createGanttChart(
            "Gantt Chart Vorgänge",  // chart Titel
            "Vorgänge",              // Y Achse beschriftung
            "Datum",              // X Achse Dauer
            dataset,             // Daten mit Vorgängen
            true,                //  Legende
            false,                // mouseover
            false                // urls
        );    
        
      
        
        CategoryPlot plot =  (CategoryPlot) chart.getPlot();
        ValueAxis range = plot.getRangeAxis();              
        range.setVisible(false);                            //Verbergen der X-Achse 
        
return chart;    
    }
    

}
